package com.globant.projectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

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
    private List<Long> projectAssignToEmpId;
}
