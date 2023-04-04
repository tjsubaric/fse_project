package com.uiowa.fse_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uiowa.fse_project.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

	public boolean existsByEmail(String email);

	public UserDtls findByEmail(String email);

}