package com.ekhonni.backend.projection;

import com.ekhonni.backend.enums.ReportStatus;
import com.ekhonni.backend.enums.ReportType;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
public interface ReportAgainstUserProjection {

    UUID getReporterId();

    String getReporterEmail();

    ReportType getType();

    String getDetails();

    ReportStatus getStatus();
}
