package com.globant.trainingservice.repo;

import com.globant.trainingservice.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {


    //public List<Training> findByRole(String role);



}
