package com.globant.employeeservice.controller;

import com.globant.employeeservice.dto.EmployeeDto;
import com.globant.employeeservice.dto.ResponseDto;
import com.globant.employeeservice.feignclient.ProjectFeignClient;
import com.globant.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
//http://localhost:8082/swagger-ui/index.html#/
@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@Slf4j
@Tag(
        name = "CRUD REST APIs for Employee Resource",
        description = "CRUD REST APIs - Create Employee, Update Employee, Get Employee, Get All Employees, Delete Employee"
)
public class EmployeeController {

    private EmployeeService employeeService;

    @Operation(
            summary = "Create Employee REST API",
            description = "Create Employee REST API is used to save user in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {

        log.info("EmployeeController saveEmployee - {}" , employeeDto);
        EmployeeDto employeeDto1 =  employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        log.info("EmployeeController getAllEmployee ");
        List<EmployeeDto> employeeDto1 =  employeeService.getAllEmployee();
        return new ResponseEntity<>(employeeDto1, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getEmployee(@PathVariable long id){
        log.info("EmployeeController getEmployee - {}" , id);
        ResponseDto responseDto =  employeeService.getEmployee(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByRole(@PathVariable String role){
        log.info("EmployeeController getEmployeeByRole - {}" , role);
        List<EmployeeDto> employeeDto1 =  employeeService.getEmployeeByRole(role);
        log.info("EmployeeController getEmployeeByRole - {}" , employeeDto1);
        return new ResponseEntity<>(employeeDto1, HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        log.info("EmployeeController updateEmployee - {}" , id);
        EmployeeDto employeeDto1 =  employeeService.updateEmployee(employeeDto,id);
        return new ResponseEntity<>(employeeDto1, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeByFields(@PathVariable int id,@RequestBody Map<String, Object> fields){
        log.info("EmployeeController updateEmployeeByFields - {}, {} " , id, fields );
        EmployeeDto employeeDto1 = employeeService.updateEmployeeByFields(id,fields);
        return new ResponseEntity<>(employeeDto1, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        log.info("EmployeeController deleteEmployee - {}" , id);
        String message =  employeeService.deleteEmployee(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/skill/")
    public ResponseEntity<List<EmployeeDto>> getEmployeeBySkills(@RequestParam("skills") List<String> skills ){
        log.info("EmployeeController getEmployeeBySkills - {}" , skills);
        List<EmployeeDto> employeeDto =  employeeService.getEmployeeBySkills(skills);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
}
