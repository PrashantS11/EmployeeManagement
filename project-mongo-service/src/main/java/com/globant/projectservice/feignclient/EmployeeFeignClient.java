package com.globant.projectservice.feignclient;

import com.globant.projectservice.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "employee-service", url = "http://localhost:8080/employees/")
public interface EmployeeFeignClient {

    @GetMapping("skill/")
    public ResponseEntity<List<EmployeeDto>> getEmployeeBySkills(@RequestParam("skills") List<String> skills );

    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) ;
}