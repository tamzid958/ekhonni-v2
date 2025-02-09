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
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("Starting archiving soft deleted bids");
        int pageNumber = 0;
        boolean hasMorePages = true;
        Sort sort = Sort.by("createdAt").ascending();
        while (hasMorePages) {
            PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE, sort);
            Page<Bid> bidPage = bidService.getAllDeleted(pageRequest);
            processBatch(bidPage.getContent());
            hasMorePages = bidPage.hasNext();
            pageNumber++;
        }
    }

    @Modifying
    @Transactional
    private void processBatch(List<Bid> bidsToArchive) {
        try {
            for (Bid bid : bidsToArchive) {
                try {
                    BidLog bidLog = new BidLog();
                    bidLog.setOriginalBidId(bid.getId());
                    bidLog.setBidData(convertBidToJson(bid));

                    bidLogRepository.save(bidLog);
                    log.info("Successfully archived bid: {}", bid.getId());

                    bidService.deletePermanently(bid.getId());
                } catch (Exception e) {
                    log.error("Error archiving bid {}: {}", bid.getId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error in bid archival process: {}", e.getMessage());
        }
    }

    private String convertBidToJson(Bid bid) throws Exception {
        return objectMapper.writeValueAsString(BidArchiveDTO.fromBid(bid));
    }
}
