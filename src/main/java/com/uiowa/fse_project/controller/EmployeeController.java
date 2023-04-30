package com.uiowa.fse_project.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.model.Appointments;
import com.uiowa.fse_project.model.Employee;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@GetMapping("/")
	public String home() {
		return "employee/home";
	}

	@GetMapping("/(show appointments page)")
	public String showAppointments(Model model, Principal principal) {
		Employee employee = employeeRepository.findByEmail(principal.getName());
		String doctorFullName = employee.getFirstName() + " " + employee.getLastName();	
		List<Appointments> appointments = appointmentRepository.findByDoctorName(doctorFullName);
		model.addAttribute("appointments", appointments);
		return "html location of show appointments page";
	}
}