package com.uiowa.fse_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uiowa.fse_project.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

}
