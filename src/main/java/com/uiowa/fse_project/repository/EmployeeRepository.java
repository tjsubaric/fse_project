package com.uiowa.fse_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Employee;

public interface EmployeeRepository extends JpaRepository <Employee, Long>{
    Optional<Employee> findById(long id);

    Employee findByEmail(String email);
}
