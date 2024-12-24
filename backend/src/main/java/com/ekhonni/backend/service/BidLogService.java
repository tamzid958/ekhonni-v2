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
    private final UserService userService;
    private final BidService bidService;

    public BidLogService(BidLogRepository bidLogRepository, UserService userRepository, BidService bidRepository) {
        super(bidLogRepository);
        this.bidLogRepository = bidLogRepository;
        this.userService = userRepository;
        this.bidService = bidRepository;
    }

    @Transactional
    public BidLogResponseDTO create(BidLogCreateDTO bidLogCreateDTO) {
        Bid bid = bidService.get(bidLogCreateDTO.bidId());
        User bidder = userService.get(bidLogCreateDTO.bidderId());
        BidLog bidLog = new BidLog(bid, bidder, bidLogCreateDTO.amount(), bidLogCreateDTO.currency(), BidLogStatus.PENDING);
        bidLogRepository.save(bidLog);
        return BidLogResponseDTO.from(bidLogCreateDTO, bidLog.getId(), bidLog.getStatus());
    }
}
