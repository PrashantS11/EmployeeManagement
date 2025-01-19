package com.globant.projectservice.service;

import com.globant.projectservice.dto.EmployeeDto;
import com.globant.projectservice.dto.ProjectDto;
import com.globant.projectservice.entity.Project;
import com.globant.projectservice.exception.ProjectNotFoundException;
import com.globant.projectservice.feignclient.EmployeeFeignClient;
import com.globant.projectservice.repo.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

import org.springframework.util.ReflectionUtils;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectService {

    private ProjectRepository projectRepository;

    private ModelMapper modelMapper;

    private EmployeeFeignClient projectFeignClient;

    public ProjectDto saveProject(ProjectDto projectDto) {
        log.info("ProjectService assignProject - saveProject - {}" , projectDto);
        Project project = modelMapper.map(projectDto, Project.class);
        Project saveProject = projectRepository.save(project);
        return modelMapper.map(saveProject, ProjectDto.class);

    }

    public ProjectDto getProject(long id) {
        Project saveProject = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not present"));
        return modelMapper.map(saveProject, ProjectDto.class);
    }

    public ProjectDto updateProject(ProjectDto projectDto, long id) {
        Project saveProject = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not present"));
        saveProject.setProjectName(projectDto.getProjectName());
        saveProject.setClientName(projectDto.getClientName());
        saveProject.setDescription(projectDto.getDescription());
        saveProject.setDurationInDays(projectDto.getDurationInDays());
        saveProject.setProjectStartDate(projectDto.getProjectStartDate());
        saveProject.setProjectEndDate(projectDto.getProjectEndDate());
        saveProject.setSkill(projectDto.getSkill());

        Project updatedProject =  projectRepository.save(saveProject);
        return modelMapper.map(saveProject, ProjectDto.class);
    }

    public String deleteProject(long id) {
        Project saveProject = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not present"));
        projectRepository.deleteById(id);
        Optional<Project> deleteProject = projectRepository.findById(id);
        if(deleteProject.isEmpty())
            return "Project deleted successfully";
        else
            return "Project not deleted";

    }

    public ProjectDto updateProjectByFields(long id, Map<String, Object> fields) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project not present"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Project.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, project, value);
        });
        log.info("ProjectService updateProductByFields - {}" , project);
        Project updatedProject = projectRepository.save(project);
        return modelMapper.map(updatedProject, ProjectDto.class);
    }

    public List<ProjectDto> getAllProject() {
        return projectRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProjectDto.class)).toList();
    }


    public List<ProjectDto> getProjectBySkills(List<String> skill) {
        log.info("ProjectService getProjectBySkills - {}" , skill);
        List<Project> bySkillsIn = projectRepository.findBySkillContaining(skill.get(0));
        log.info("ProjectService getProjectBySkills - {}" , bySkillsIn);

        return bySkillsIn.stream()
                .map(p -> modelMapper.map(p, ProjectDto.class)).toList();
    }

    //As per Project skills, get eligible employees.
    public List<EmployeeDto> getEmpByProjectSkills(List<String> skills) {
        log.info("ProjectService getEmpByProjectSkills - {}" , skills);
        List<EmployeeDto> employeeDto = projectFeignClient.getEmployeeBySkills(skills).getBody();
        log.info("ProjectService getEmpByProjectSkills - employeeDto - {}" , employeeDto);
        return employeeDto;
        //return employeeDto.stream().map(p -> modelMapper.map(p, EmployeeDto.class)).toList();

    }

    public List<EmployeeDto> assignProject(List<String> skills) {
        log.info("ProjectService assignProject - {}" , skills);
        List<EmployeeDto> empBySkills = projectFeignClient.getEmployeeBySkills(skills).getBody();
        log.info("ProjectService assignProject - empBySkills - {}" , empBySkills);
        List<ProjectDto> projectBySkills = this.getProjectBySkills(skills);
        log.info("ProjectService assignProject - projectBySkills - {}" , projectBySkills);
        List<EmployeeDto> employeeDto = empBySkills.stream()
                .filter(e -> !e.isProjectAssigned())
                .map(e -> {
                    e.setProjectAssigned(true);
                    e.setAssignedProjectId(projectBySkills.getFirst().getId());
                    projectFeignClient.saveEmployee(e);
                    List<Long> projectAssignToEmpId = projectBySkills.getFirst().getProjectAssignToEmpId();
                    if(projectAssignToEmpId == null) {
                        ArrayList<Long> list = new ArrayList<>();
                        list.add(e.getId());
                        projectBySkills.getFirst().setProjectAssignToEmpId(list);

                    }else {
                        projectAssignToEmpId.add(e.getId());
                        projectBySkills.getFirst().setProjectAssignToEmpId(projectAssignToEmpId);
                        //projectAssignToEmpId.get().addFirst(e.getId());
                    }

                    log.info("ProjectService assignProject - projectBySkills1 - {}" , projectBySkills);
                    this.saveProject(projectBySkills.getFirst());
                    return e;
                }).toList();

        log.info("ProjectService assignProject - employeeDto {}" , employeeDto);
        return employeeDto;

    }
}

//http://localhost:8080/employees/skill/skill/?skills=Java
//http://localhost:8080/employees/skill/?skills=Java, kafka
//http://localhost:8080/employees/skill?skills=Java