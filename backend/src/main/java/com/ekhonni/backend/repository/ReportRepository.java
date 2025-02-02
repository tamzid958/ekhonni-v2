package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
@Repository
public interface ReportRepository extends BaseRepository<Report, Long> {
    Page<Report> findAllByReportedId(UUID userId, Pageable pageable);

    Page<Report> findAllByReporterId(UUID userId, Pageable pageable);
}
