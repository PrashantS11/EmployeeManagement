package com.globant.employeeservice.service;

import com.globant.employeeservice.dto.EmployeeDto;
import com.globant.employeeservice.dto.ProjectDto;
import com.globant.employeeservice.dto.ResponseDto;
import com.globant.employeeservice.entity.Employee;
import com.globant.employeeservice.exception.EmployeeNotFoundException;
import com.globant.employeeservice.feignclient.ProjectFeignClient;
import com.globant.employeeservice.repo.EmployeeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

import org.springframework.util.ReflectionUtils;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ProjectFeignClient projectFeignClient;
    public KafkaProducerService kafkaProducerService;
    public AuditService auditService;
    private ModelMapper modelMapper;

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        log.info("EmployeeService saveEmployee - {}" , employeeDto);
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee saveEmployee = employeeRepository.save(employee);
        auditService.logAction("Add", "Employee added: " + saveEmployee.getId());
        kafkaProducerService.sendMessage("Employee added: " + saveEmployee.getId());
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Retry(name = "myRetry", fallbackMethod = "fallbackMethod")
    public ResponseDto getEmployee(long id) {
        Employee saveEmployee =employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not present"));

        ResponseEntity<ProjectDto> projectResponse = projectFeignClient.getProject(saveEmployee.getAssignedProjectId());
        log.info("EmployeeService getEmployee - projectResponse {}" , projectResponse);
        EmployeeDto employeeDto = modelMapper.map(saveEmployee, EmployeeDto.class);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setEmployeeDto(employeeDto);
        responseDto.setAssignedProject(projectResponse.getBody());
        //employeeDto.setAssignedProject(projectResponse.getBody());
        return responseDto;
    }

    public ResponseDto fallbackMethod(long id, Exception e) {
        log.info("EmployeeService fallbackMethod - {}" ,e.getMessage());
        Employee saveEmployee =employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not present"));
        EmployeeDto employeeDto = modelMapper.map(saveEmployee, EmployeeDto.class);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setEmployeeDto(employeeDto);
        return responseDto;
    }
    public List<EmployeeDto> getEmployeeByRole(String role) {
        log.info("EmployeeService getEmployeeByRole - {}" , role);
        return employeeRepository.findByRole(role) .stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class)).toList();
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
        Employee saveEmployee =employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not present"));

        saveEmployee.setFirstName(employeeDto.getFirstName());
        saveEmployee.setLocation(employeeDto.getLocation());
        saveEmployee.setLastName(employeeDto.getLastName());
        saveEmployee.setRole(employeeDto.getRole());
        saveEmployee.setEmail(employeeDto.getEmail());
        saveEmployee.setYearsOfExp(employeeDto.getYearsOfExp());

        Employee updatedEmployee =  employeeRepository.save(saveEmployee);
        return modelMapper.map(saveEmployee, EmployeeDto.class);
    }

    public String deleteEmployee(long id) {
        Employee saveEmployee =employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not present"));
        employeeRepository.deleteById(id);
        Optional<Employee> deleteEmployee =employeeRepository.findById(id);
        if(deleteEmployee.isEmpty())
            return "Employee deleted successfully";
        else
            return "Employee not deleted";

    }

    public EmployeeDto updateEmployeeByFields(long id, Map<String, Object> fields) {
        Employee employee =employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not present"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Employee.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, employee, value);
        });
        log.info("EmployeeService updateProductByFields - {}" , employee);
        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    public List<EmployeeDto> getEmployeeBySkills(List<String> skill) {
        log.info("EmployeeService getEmployeeBySkills - {}" , skill);
        List<Employee> bySkillsIn = employeeRepository.findBySkillContaining(skill.get(0));
        log.info("EmployeeService getEmployeeBySkills - {}" , bySkillsIn);

        return bySkillsIn.stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class)).toList();
    }

    public List<EmployeeDto> getAllEmployee() {
        log.info("EmployeeService getAllEmployee ");
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class)).toList();
    }
}
