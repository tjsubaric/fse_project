package com.uiowa.fse_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uiowa.fse_project.model.Admin;


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

	@GetMapping("/new_admin")
	public String showNewAdminForm(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin/new_admin";
	}
}