package com.globant.employeeservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.employeeservice.dto.EmployeeDto;
import com.globant.employeeservice.entity.Employee;
import com.globant.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @Spy
    ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;
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
    public void givenEmpObj_whenCreateEmp_thenReturnSsavedEmp() throws Exception {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        given(employeeService.saveEmployee(employeeDto)).willReturn(employeeDto);
        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())));


    }
}
