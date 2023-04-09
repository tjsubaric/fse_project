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
import com.uiowa.fse_project.service.EmployeeService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private EmployeeService employeeService;

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

    @GetMapping("/employee_board/new_employee")
    public String showNewEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "admin/employee_board/new_employee";
    }

    @PostMapping("/employee_board/employee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employee_board";
    }

    @GetMapping("/employee_board/edit_employee/{id}")
    public String showEditEmployeeForm(@PathVariable(value = "id") long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "admin/employee_board/edit_employee";
    }

    @PostMapping("/employee_board/update_employee/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, @ModelAttribute("employee") Employee employee) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        existingEmployee.setEmail(employee.getEmail());
        employeeService.saveEmployee(existingEmployee);
        return "redirect:/admin/employee_board";
    }

    @GetMapping("/employee_board/delete_employee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/admin/employee_board";
    }
}