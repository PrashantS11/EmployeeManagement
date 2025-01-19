package com.globant.employeeservice.feignclient;

import com.globant.employeeservice.dto.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="project-service", url = "http://localhost:8081/projects", configuration = FeignConfig.class)
public interface ProjectFeignClient {

    @GetMapping("{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable long id);
}
