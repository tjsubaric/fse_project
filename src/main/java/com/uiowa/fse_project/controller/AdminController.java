package com.uiowa.fse_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.service.AdminService;
import com.uiowa.fse_project.service.EmployeeService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private EmployeeService employeeService;

    @Autowired
    private AdminService adminService;

	@GetMapping("/")
	public String home() {
		return "admin/home";
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

	@GetMapping("/employee_board")
    public String showEmployeeBoard(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "admin/employee_board";
    }

    @PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin) {
		// save employee to database
		adminService.saveAdmin(admin);
		return "redirect:/admin/";
	}
}