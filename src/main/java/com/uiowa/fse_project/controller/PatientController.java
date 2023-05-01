package com.uiowa.fse_project.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.repository.PatientRepository;
import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.model.Appointments;
@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private PatientRepository patientrepo;

	@Autowired
	private EmployeeRepository employeeRepository;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		Patient patient = patientrepo.findByEmail(email);
		m.addAttribute("patient", patient);
	}

	@GetMapping("/")
	public String home() {
		return "patient/home";
	}

	@GetMapping("/appointments")
	public String showdoctor(Model model) {
		List<Employee> employees = employeeRepository.findAll();
		model.addAttribute("employees", employees);
		return "patient/appointments";
	}

	@GetMapping("/records")
	public String showRecords(){
		return "patient/records";
	}

	@PostMapping("/appointments")
	public String bookAppointment(@ModelAttribute("patient") Patient patient, @RequestParam("appointmentDate") String appointmentDate, @RequestParam("doctorName") long doctorName) {
    // Your code to save the appointmentDateTime to the database
		Optional<Employee> doctor = employeeRepository.findById(doctorName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentDate, formatter);
		Appointments appointment = new Appointments();
		appointment.setFirstName(patient.getFirstName());
		appointment.setLastName(patient.getLastName());
		appointment.setDoctor(doctor.get().getFirstName() + " " + doctor.get().getLastName());
		appointment.setAppointmentdate(appointmentDateTime);
		appointmentRepository.save(appointment);
		
		patient.setDoctor(doctor.get().getFirstName() + " " + doctor.get().getLastName());
		patient.setAppointmentdate(appointmentDateTime);
		patientrepo.save(patient);
    	return "redirect:/patient/appointments";
	}

}
