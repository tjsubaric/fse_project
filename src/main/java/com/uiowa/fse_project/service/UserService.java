package com.uiowa.fse_project.service;

import com.uiowa.fse_project.model.UserDtls;

public interface UserService {

	public UserDtls createUser(UserDtls user);

	public UserDtls createAdmin(UserDtls user);

	public UserDtls createEmployee(UserDtls user);

	public boolean checkEmail(String email);

}