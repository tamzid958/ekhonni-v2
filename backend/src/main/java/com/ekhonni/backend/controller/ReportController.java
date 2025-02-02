package com.ekhonni.backend.controller;

import com.ekhonni.backend.model.Report;
import com.ekhonni.backend.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v2")
public class ReportController {

    ReportService reportService;

    @GetMapping("/reports")
    public ResponseEntity<Page<Report>> getAllReports(Pageable pageable) {
        return ResponseEntity.ok(reportService.getAllReports(pageable));
    }

    @GetMapping("/reports/{userId}")
    public ResponseEntity<Page<Report>> getAllReportsAgainstUser(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(reportService.getAllReportsAgainstUser(userId, pageable));
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
//        reportService.deleteReport(id);
//        return ResponseEntity.noContent().build();
//    }
}
