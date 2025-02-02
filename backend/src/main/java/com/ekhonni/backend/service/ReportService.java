package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.ReportDTO;
import com.ekhonni.backend.enums.ReportStatus;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Report;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ReportRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ReportService {
    private ReportRepository reportRepository;
    private UserRepository userRepository;

    @Transactional
    public Report createReport(UUID reporterId, ReportDTO reportDTO) {

        User reporter = userRepository.findById(reporterId).orElseThrow(() -> new UserNotFoundException("reporter not found"));

        User reported = userRepository.findById(reportDTO.reportedId()).orElseThrow(() -> new UserNotFoundException("reported user not found"));

        Report report = new Report(reporter,
                reported,
                reportDTO.type(),
                reportDTO.details(),
                ReportStatus.PENDING);

        reportRepository.save(report);

        return report;
    }

    public Page<Report> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    public Page<Report> getAllReportsAgainstUser(UUID userId, Pageable pageable) {
        return reportRepository.findAllByReportedId(userId, pageable);
    }

    public Page<Report> getAllReportsByUser(UUID userId, Pageable pageable) {
        return reportRepository.findAllByReporterId(userId, pageable);
    }
}
