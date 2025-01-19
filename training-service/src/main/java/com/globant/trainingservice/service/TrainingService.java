package com.globant.trainingservice.service;

import com.globant.trainingservice.dto.TrainingDto;
import com.globant.trainingservice.entity.Training;
import com.globant.trainingservice.exception.TrainingNotFoundException;
import com.globant.trainingservice.repo.TrainingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.util.ReflectionUtils;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {

    private TrainingRepository trainingRepository;

    private ModelMapper modelMapper;
    public TrainingDto saveTraining(TrainingDto trainingDto) {

        Training training = modelMapper.map(trainingDto, Training.class);
        Training saveTraining = trainingRepository.save(training);
        return modelMapper.map(saveTraining, TrainingDto.class);

    }

    public TrainingDto getTraining(long id) {
        Training saveTraining = trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("Training not present"));
        return modelMapper.map(saveTraining, TrainingDto.class);
    }

    public TrainingDto updateTraining(TrainingDto trainingDto, long id) {
        Training saveTraining =trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("Training not present"));

        saveTraining.setTrainingName(trainingDto.getTrainingName());
        saveTraining.setDescription(trainingDto.getDescription());
        saveTraining.setEstimatedStartDate(trainingDto.getEstimatedStartDate());
        saveTraining.setEstimatedEndDate(trainingDto.getEstimatedEndDate());
        saveTraining.setActualStartDate(trainingDto.getActualStartDate());
        saveTraining.setActualEndDate(trainingDto.getActualEndDate());

        Training updatedTraining =  trainingRepository.save(saveTraining);
        return modelMapper.map(updatedTraining, TrainingDto.class);
    }

    public String deleteTraining(long id) {
        trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("Training not present"));
        trainingRepository.deleteById(id);
        Optional<Training> deleteTraining =trainingRepository.findById(id);
        if(deleteTraining.isEmpty())
            return "Training deleted successfully";
        else
            return "Training not deleted";

    }

    public TrainingDto updateTrainingByFields(long id, Map<String, Object> fields) {
        Training training = trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("Training not present"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Training.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, training, value);
        });
        log.info("TrainingService updateProductByFields - {}" , training);
        Training updatedTraining = trainingRepository.save(training);
        return modelMapper.map(updatedTraining, TrainingDto.class);
    }
}
