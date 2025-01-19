package com.globant.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private long id;
    private String projectName;
    private String clientName;
    private String durationInDays;
    private String description;
    private String projectStartDate;
    private String projectEndDate;
    private String skill;
}
