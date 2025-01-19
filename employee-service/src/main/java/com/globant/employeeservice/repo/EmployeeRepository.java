package com.globant.employeeservice.repo;

import com.globant.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    public List<Employee> findByRole(String role);
    public List<Employee> findBySkillContaining(String skill);


}
