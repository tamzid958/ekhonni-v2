package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.ReportStatus;
import com.ekhonni.backend.enums.ReportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;
    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private User reported;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reason;
    @Column(columnDefinition = "TEXT")
    private String details;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;
}
