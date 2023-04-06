package com.uiowa.fse_project.service;
import java.util.List;

import org.springframework.data.domain.Page;

import com.uiowa.fse_project.model.Admin;


public interface AdminService {
    List<Admin> getAllAdmins();
	void saveAdmin(Admin admin);
	Admin getAdminById(long id);
	void deleteAdminById(long id);
	Page<Admin> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
