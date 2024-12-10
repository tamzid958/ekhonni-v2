package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.BidLogDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.BidLogProjection;
import com.ekhonni.backend.repository.BidLogRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class BidLogService {

    private final BidLogRepository bidLogRepository;
    private final UserRepository userRepository;

    public BidLogDTO create(BidLogDTO bidLogDTO) {

        User user = userRepository.findById(bidLogDTO.bidderId()).orElseThrow(UserNotFoundException::new);
        BidLog bidLog = new BidLog(
                bidLogDTO.amount(),
                bidLogDTO.status(),
                user
        );

        bidLogRepository.save(bidLog);
        return bidLogDTO;
    }

    public BidLogProjection getById(Long id) {
        BidLog bidLog = bidLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bid Log not found for id: " + id));

        return bidLogRepository.findProjectionById(id);
    }

    public List<BidLogProjection> getAll() {
        return bidLogRepository.findAllProjection();
    }

    @Transactional
    public BidLogDTO update(Long id, BidLogDTO bidLogDTO) {
        BidLog bidLog = bidLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bid Log not found for id: " + id));

        bidLog.setStatus(bidLogDTO.status());

        return bidLogDTO;
    }

    public void delete(Long id) {
        BidLog bidLog = bidLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bid Log Not Found"));

        bidLogRepository.deleteById(id);
    }
}
