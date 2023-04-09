package com.uiowa.fse_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Employee;

public interface AdminRepository extends JpaRepository<Admin, Long>{

    //void save(Employee employee);

}
