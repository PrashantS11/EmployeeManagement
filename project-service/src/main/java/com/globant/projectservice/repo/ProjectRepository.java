package com.globant.projectservice.repo;

import com.globant.projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    //public List<Project> findByRole(String role);

    public List<Project> findBySkillContaining(String skill);

    //@Query(value = "SELECT u FROM Project u WHERE u.skill  :names")
    //List<Project> findUserByNameList(@Param("names") String names);
}
