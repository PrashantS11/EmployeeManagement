package com.globant.projectservice.service;

import com.globant.projectservice.entity.Project;
import com.globant.projectservice.repo.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project projectDetails) {
        return projectRepository.findById(id).map(project -> {
            project.setName(projectDetails.getName());
            project.setClientName(projectDetails.getClientName());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            project.setDescription(projectDetails.getDescription());
            project.setRequiredSkills(projectDetails.getRequiredSkills());
            project.setLocation(projectDetails.getLocation());
            return projectRepository.save(project);
        }).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
