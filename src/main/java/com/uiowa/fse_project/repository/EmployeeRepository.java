package com.uiowa.fse_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uiowa.fse_project.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long>{
    Optional<Employee> findById(long id);
}
