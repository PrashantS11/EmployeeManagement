package com.globant.projectservice.entity;

import lombok.Data;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String name;
    private String clientName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private List<String> requiredSkills;
    private String location;
}
