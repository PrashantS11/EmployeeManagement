package com.globant.employeeservice.service;


import com.globant.employeeservice.entity.AuditLog;
import com.globant.employeeservice.repo.AuditRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void logAction(String action, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        auditRepository.save(log);
    }
}