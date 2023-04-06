package com.uiowa.fse_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/")
	public String home() {
		return "admin/home";
	}

	@GetMapping("/employee_board")
	public String showEmployeeBoard() {
    	return "admin/employee_board";
	}

	@GetMapping("/patient_board")
	public String showPatientBoard() {
    	return "admin/patient_board";
	}

}