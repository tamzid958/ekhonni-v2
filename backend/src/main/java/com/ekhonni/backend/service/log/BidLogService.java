package com.ekhonni.backend.service.log;

import com.ekhonni.backend.dto.bid.BidArchiveDTO;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.log.BidLog;
import com.ekhonni.backend.repository.log.BidLogRepository;
import com.ekhonni.backend.service.BaseService;
import com.ekhonni.backend.service.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Author: Asif Iqbal
 * Date: 2/6/25
 */
@Slf4j
@Service
public class BidLogService extends BaseService<BidLog, Long> {

    private final BidService bidService;
    private final BidLogRepository bidLogRepository;
    private final ObjectMapper objectMapper;

    private final int BATCH_SIZE = 50;

    public BidLogService(BidService bidService, BidLogRepository bidLogRepository, ObjectMapper objectMapper) {
        super(bidLogRepository);
        this.bidService = bidService;
        this.bidLogRepository = bidLogRepository;
        this.objectMapper = objectMapper;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void archiveDeletedBids() {
        log.info("Starting moving soft deleted bids to log table");
        int pageNumber = 0;
        boolean hasMorePages = true;
        Sort sort = Sort.by("id").ascending();
        while (hasMorePages) {
            PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE, sort);
            Page<Bid> bidPage = bidService.getAllDeleted(pageRequest);
            processBatch(bidPage.getContent());
            hasMorePages = bidPage.hasNext();
            pageNumber++;
        }
        log.info("Done moving soft deleted bids to log table");
    }

    private void processBatch(List<Bid> bidsToArchive) {
        for (Bid bid : bidsToArchive) {
            try {
                archiveSingleBid(bid);
            } catch (Exception e) {
                log.error("Error archiving bid {}: {}", bid.getId(), e.getMessage());
            }
        }
    }

    @Transactional
    public void archiveSingleBid(Bid bid) throws Exception {
        BidLog bidLog = new BidLog();
        bidLog.setBidId(bid.getId());
        bidLog.setProductId(bid.getProduct().getId());
        bidLog.setBidData(convertBidToJson(bid));

        bidLogRepository.save(bidLog);
        bidService.deletePermanently(bid.getId());
    }

    private String convertBidToJson(Bid bid) throws Exception {
        return objectMapper.writeValueAsString(BidArchiveDTO.fromBid(bid));
    }

    public Page<BidLog> getByProductId(Long productId, Pageable pageable) {
        return bidLogRepository.findByProductIdAndDeletedAtIsNull(productId, pageable);
    }

    public Page<BidLog> getByBidId(Long bidId, Pageable pageable) {
        return bidLogRepository.findByBidIdAndDeletedAtIsNull(bidId, pageable);
    }

    public Page<BidLog> getByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return bidLogRepository.findByCreatedAtBetweenAndDeletedAtIsNull(startDate, endDate, pageable);
    }

}
