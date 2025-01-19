package com.globant.employeeservice.repository;

import com.globant.employeeservice.entity.Employee;
import com.globant.employeeservice.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
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
    public void givenEmpObj_whenSave_thenReturnSaveEmp(){
        Employee saveEmp = employeeRepository.save(employee);
        log.info("EmployeeRepositoryTest givenEmpObj_whenSave_thenReturnSaveEmp - {} ", saveEmp);
        assertThat(saveEmp).isNotNull();
        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo(saveEmp.getFirstName());
    }

    @Test
    public void givenEmpObj_whenFindById_thenReturnEmpObj() {
        Employee saveEmp = employeeRepository.save(employee);
        Employee emp = employeeRepository.findById(employee.getId()).get();
        log.info("EmployeeRepositoryTest givenEmpObj_whenFindById_thenReturnEmpObj - {} ", emp);
        assertThat(emp).isNotNull();
    }

}
