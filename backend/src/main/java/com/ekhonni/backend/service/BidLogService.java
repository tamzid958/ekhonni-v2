package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.BidLogCreateDTO;
import com.ekhonni.backend.dto.BidLogResponseDTO;
import com.ekhonni.backend.enums.BidLogStatus;
import com.ekhonni.backend.exception.BidLogNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.BaseRepository;
import com.ekhonni.backend.repository.BidLogRepository;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class BidLogService extends BaseService<BidLog, Long> {
    private final BidLogRepository bidLogRepository;
    private final UserRepository userRepository;
    private final BidRepository bidRepository;

    public BidLogService(BidLogRepository bidLogRepository, UserRepository userRepository, BidRepository bidRepository) {
        super(bidLogRepository);
        this.bidLogRepository = bidLogRepository;
        this.userRepository = userRepository;
        this.bidRepository = bidRepository;
    }


    @Transactional
    public BidLogResponseDTO create(BidLogCreateDTO bidLogCreateDTO) {
        Bid bid = bidRepository.findById(bidLogCreateDTO.bidId())
                .orElseThrow(BidLogNotFoundException::new);
        User bidder = userRepository.findById(bidLogCreateDTO.bidderId())
                .orElseThrow(UserNotFoundException::new);
        BidLog bidLog = new BidLog(bid, bidder, bidLogCreateDTO.amount(), BidLogStatus.PENDING);
        bidLogRepository.save(bidLog);
        return BidLogResponseDTO.from(bidLogCreateDTO, bidLog.getId(), bidLog.getStatus());
    }
}
