package com.globant.employeeservice.repo;

import com.globant.employeeservice.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByAction(String action);

    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

}
