package com.uiowa.fse_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	@Override
	public void saveAdmin(Admin admin) {
		this.adminRepository.save(admin);
	}

	@Override
	public Admin getAdminById(long id) {
		Optional<Admin> optional = adminRepository.findById(id);
		Admin admin = null;
		if (optional.isPresent()) {
			admin = optional.get();
		} else {
			throw new RuntimeException(" Admin not found for id :: " + id);
		}
		return admin;
	}

	@Override
	public void deleteAdminById(long id) {
		this.adminRepository.deleteById(id);
	}

	@Override
	public Page<Admin> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.adminRepository.findAll(pageable);
	}
}
