package com.globant.trainingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {

    private long id;
    private String trainingName;
    private String description;
    private String estimatedStartDate;
    private String estimatedEndDate;
    private String actualStartDate;
    private String actualEndDate;
    //skills


}
