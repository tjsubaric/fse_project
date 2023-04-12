package com.uiowa.fse_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.service.AdminService;
import com.uiowa.fse_project.service.EmployeeService;
import com.uiowa.fse_project.service.PatientService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private EmployeeService employeeService;

	@Autowired
    private PatientService patientService;

    @Autowired
    private AdminService adminService;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}

	@GetMapping("/employee_board")
	public String showEmployeeBoard(Model model, @RequestParam(defaultValue = "1") int pageNo,
									@RequestParam(defaultValue = "firstName") String sortField,
									@RequestParam(defaultValue = "asc") String sortDir) {
		int pageSize = 5;

		Page<Employee> page = employeeService.findEmployeePaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> employees = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("employees", employees);

		return "admin/employee_board";
	}

	@GetMapping("/patient_board")
	public String showPatientBoard(Model model, @RequestParam(defaultValue = "1") int pageNo,
								  @RequestParam(defaultValue = "firstName") String sortField,
	                              @RequestParam(defaultValue = "asc") String sortDir) {
		int pageSize = 5;

		Page<Patient> page = patientService.findPatientPaginated(pageNo, pageSize, sortField, sortDir);
		List<Patient> patients = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("patients", patients);

		return "admin/patient_board";
	}

	@GetMapping("/new_admin")
	public String showNewAdminForm(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin/new_admin";
	}

	@GetMapping("/employee_board/new_employee")
	public String showNewEmployeeForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "admin/new_employee";
	}

	@GetMapping("/patient_board/new_patient")
	public String showNewPatientForm(Model model) {
		model.addAttribute("patient", new Patient());
		return "admin/new_patient";
	}

    @PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin) {
		adminService.saveAdmin(admin);
		return "redirect:/admin/";
	}

	@PostMapping("/employee_board/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/admin/employee_board";
	}

	@PostMapping("/patient_board/savePatient")
	public String savePatient(@ModelAttribute("patient") Patient patient) {
		patientService.savePatient(patient);
		return "redirect:/admin/patient_board";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		Admin admin = adminService.getAdminById(id);
		model.addAttribute("admin", admin);
		return "admin/update_admin";
	}
	
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable (value = "id") long id) {
		// call delete employee method 
		this.adminService.deleteAdminById(id);
		return "redirect:/admin/";
	}

	@GetMapping("/employee_board/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/admin/employee_board";
	}

	@GetMapping("/patient_board/deletePatient/{id}")
	public String deletePatient(@PathVariable (value = "id") long id) {
		this.patientService.deletePatientById(id);
		return "redirect:/admin/patient_board";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Admin> page = adminService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Admin> admins = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("admins", admins);
		return "admin/home";
	}
}