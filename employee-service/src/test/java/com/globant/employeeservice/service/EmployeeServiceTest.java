package com.globant.employeeservice.service;

import com.globant.employeeservice.dto.EmployeeDto;
import com.globant.employeeservice.dto.ResponseDto;
import com.globant.employeeservice.entity.Employee;
import com.globant.employeeservice.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Spy
    private ModelMapper modelMapper;

    Employee employee;

    @BeforeEach
    public void setUp(){
        employee = Employee.builder()
                .id(101L)
                .firstName("Jack")
                .lastName("Jem")
                .email("jack@g.com")
                .location("US")
                .doj("22-08-2023")
                .dob("5-07-1990")
                .role("Engineer")
                .yearsOfExp(10)
                .skill("Angular,JS")
                .build();
    }

    @Test
    public void givenEmpObj_whenSaveEmp_thenReturnEmpObj() {
        given(employeeRepository.save(employee)).willReturn(employee);
        log.info("EmployeeRepositoryTest givenEmpObj_whenSaveEmp_thenReturnEmpObj - employee {} ", employee);
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        System.out.println("employeeDto " + employeeDto);
        EmployeeDto employeeDto1 = employeeService.saveEmployee(employeeDto);
        assertThat(employeeDto1).isNotNull();
    }

    @Test
    public void givenEmpObj_whenFindById_thenReturnEmpObj() {

        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        //given(employeeRepository.save(employee)).willReturn(employee);
        EmployeeDto employee1 = employeeService.getEmployee(employee.getId()).getEmployeeDto();
        assertThat(employee.getFirstName()).isEqualTo(employee1.getFirstName());
    }

    @Test
    public void givenEmpObj_whenFindById_thenThrowsException() {
        given(employeeRepository.findById(employee.getId())).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> employeeService.getEmployee(employee.getId()));
    }


}
