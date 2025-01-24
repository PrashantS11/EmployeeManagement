package com.globant.employeeservice.controller;


import com.globant.employeeservice.entity.AuditLog;
import com.globant.employeeservice.repo.AuditRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditRepository auditRepository;

    public AuditLogController(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }

    @GetMapping("/by-action")
    public List<AuditLog> getLogsByAction(@RequestParam String action) {
        return auditRepository.findByAction(action);
    }

    @GetMapping("/by-timestamp")
    public List<AuditLog> getLogsByTimestamp(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return auditRepository.findByTimestampBetween(start, end);
    }
}