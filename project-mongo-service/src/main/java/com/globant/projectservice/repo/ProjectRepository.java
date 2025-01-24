package com.globant.projectservice.repo;

import com.globant.projectservice.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
