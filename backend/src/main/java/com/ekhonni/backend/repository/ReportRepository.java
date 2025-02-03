package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Report;
import com.ekhonni.backend.projection.ReportAgainstUserProjection;
import com.ekhonni.backend.projection.ReportByUserProjection;
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
    Page<ReportAgainstUserProjection> findAllByReportedId(Class<ReportAgainstUserProjection> projection, UUID userId, Pageable pageable);

    Page<ReportByUserProjection> findAllByReporterId(Class<ReportByUserProjection> projection, UUID userId, Pageable pageable);
}
