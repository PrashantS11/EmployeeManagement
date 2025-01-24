package com.globant.employeeservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action; // e.g., "Add", "Update", "Delete"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String details; // e.g., "Employee with ID 1 was updated"

    @Column(nullable = false)
    private LocalDateTime timestamp;


}