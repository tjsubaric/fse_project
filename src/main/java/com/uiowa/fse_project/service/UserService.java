package com.uiowa.fse_project.service;
import com.uiowa.fse_project.model.UserDtls;

public interface UserService {

	public UserDtls createUser(UserDtls user);

	public boolean checkEmail(String email);

}