package com.globant.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private String role;
    private float yearsOfExp;
    private String dob;
    private String doj;
    private String skill;
    private boolean isProjectAssigned;
    private long assignedProjectId;
    //private ProjectDto assignedProject;
}
