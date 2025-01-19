package com.globant.trainingservice.controller;

import com.globant.trainingservice.dto.TrainingDto;
import com.globant.trainingservice.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainings")
@AllArgsConstructor
@Slf4j
@Tag(
        name = "CRUD REST APIs for Training Resource",
        description = "CRUD REST APIs - Create Training, Update Training, Get Training, Get All Trainings, Delete Training"
)
public class TrainingController {

    private TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingDto> saveTraining(@RequestBody TrainingDto trainingDto) {

        log.info("TrainingController saveTraining - {}" , trainingDto);
        TrainingDto trainingDto1 =  trainingService.saveTraining(trainingDto);
        return new ResponseEntity<>(trainingDto1, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TrainingDto> getTraining(@PathVariable long id){
        log.info("TrainingController getEmployee - {}" , id);
        TrainingDto trainingDto1 =  trainingService.getTraining(id);
        return new ResponseEntity<>(trainingDto1, HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable long id, @RequestBody TrainingDto trainingDto) {
        log.info("TrainingController updateTraining - {}" , id);
        TrainingDto trainingDto1 =  trainingService.updateTraining(trainingDto,id);
        return new ResponseEntity<>(trainingDto1, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrainingDto> updateProductFields(@PathVariable int id,@RequestBody Map<String, Object> fields){
        log.info("TrainingController updateTrainingFields - {}, {} " , id, fields );
        TrainingDto trainingDto1 = trainingService.updateTrainingByFields(id,fields);
        return new ResponseEntity<>(trainingDto1, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        log.info("TrainingController deleteTraining - {}" , id);
        String message =  trainingService.deleteTraining(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
