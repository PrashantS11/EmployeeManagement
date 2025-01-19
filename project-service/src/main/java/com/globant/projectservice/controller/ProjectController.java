package com.globant.projectservice.controller;

import com.globant.projectservice.dto.EmployeeDto;
import com.globant.projectservice.dto.ProjectDto;
import com.globant.projectservice.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
@Slf4j
@Tag(
        name = "CRUD REST APIs for Project Resource",
        description = "CRUD REST APIs - Create Project, Update Project, Get Project, Get All Projects, Delete Project"
)
public class ProjectController {

    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> saveProject(@RequestBody ProjectDto projectDto) {
        log.info("ProjectController saveProject - {}" , projectDto);
        ProjectDto projectDto1 =  projectService.saveProject(projectDto);
        return new ResponseEntity<>(projectDto1, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable long id){
        log.info("ProjectController getProject - {}" , id);
        ProjectDto projectDto1 =  projectService.getProject(id);
        return new ResponseEntity<>(projectDto1, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProject(){
        log.info("ProjectController getAllProject");
        List<ProjectDto> projectDto1 =  projectService.getAllProject();
        return new ResponseEntity<>(projectDto1, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable long id, @RequestBody ProjectDto projectDto) {
        log.info("ProjectController updateProject - {}" , id);
        ProjectDto projectDto1 =  projectService.updateProject(projectDto,id);
        return new ResponseEntity<>(projectDto1, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProductFields(@PathVariable int id, @RequestBody Map<String, Object> fields){
        log.info("ProjectController updateProductFields - {}, {} " , id, fields );
        ProjectDto projectDto1 = projectService.updateProjectByFields(id,fields);
        return new ResponseEntity<>(projectDto1, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProject(@PathVariable long id) {
        log.info("ProjectController deleteProject - {}" , id);
        String message =  projectService.deleteProject(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/skill/")
    //public ResponseEntity<List<ProjectDto>> getProjectBySkills(@RequestBody Map<String, List<String>> fields){
    public ResponseEntity<List<ProjectDto>> getProjectBySkills(@RequestParam("skills") List<String> skills ){
        log.info("ProjectController getProjectBySkills - {}" , skills);
        List<ProjectDto> projectDto1 =  projectService.getProjectBySkills(skills);
        return new ResponseEntity<>(projectDto1, HttpStatus.OK);
    }

    //Fetch Employee by project skill
    @GetMapping("/emp/")
    public ResponseEntity<List<EmployeeDto>> getEmpByProjectSkills(@RequestParam("skills") List<String> skills ){
        log.info("ProjectController getEmpByProjectSkills - {}" , skills);
        List<EmployeeDto> employeeDto =  projectService.getEmpByProjectSkills(skills);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    //Suggest and Assign an employee to the project.
    @PostMapping("/assign")
    public void assignProject(@RequestBody Map<String, List<String>> skills){
        log.info("ProjectController assignProject - {}" , skills);
        List<EmployeeDto> employeeDto =  projectService.assignProject(skills.get("skills"));
        //return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
}
