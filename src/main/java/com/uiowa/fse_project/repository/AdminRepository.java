package com.uiowa.fse_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

    Admin findByEmail(String email);

}
