package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.ReportDTO;
import com.ekhonni.backend.enums.ReportStatus;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.model.Report;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.ReportAgainstUserProjection;
import com.ekhonni.backend.projection.ReportByUserProjection;
import com.ekhonni.backend.repository.ReportRepository;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
@Service
public class ReportService extends BaseService<Report, Long> {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createReport(UUID reporterId, ReportDTO reportDTO) {

        User reporter = userRepository.findById(reporterId).orElseThrow(() -> new UserNotFoundException("reporter not found"));

        User reported = userRepository.findById(reportDTO.reportedId()).orElseThrow(() -> new UserNotFoundException("reported user not found"));

        Report report = new Report(reporter,
                reported,
                reportDTO.type(),
                reportDTO.details(),
                ReportStatus.PENDING);

        reportRepository.save(report);

    }


    public Page<ReportAgainstUserProjection> getAllReportsAgainstUser(Class<ReportAgainstUserProjection> projection, UUID userId, Pageable pageable) {
        return reportRepository.findAllByReportedId(projection, userId, pageable);
    }

    public Page<ReportByUserProjection> getAllReportsByUser(Class<ReportByUserProjection> projection, UUID userId, Pageable pageable) {
        return reportRepository.findAllByReporterId(projection, userId, pageable);
    }
}
