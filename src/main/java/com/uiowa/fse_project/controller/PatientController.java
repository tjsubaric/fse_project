package com.uiowa.fse_project.controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uiowa.fse_project.model.Appointments;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.repository.UserRepository;
import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import com.uiowa.fse_project.service.PatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PatientRepository patientRepo;

	@Autowired
    private PatientService patientService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		Patient user = patientRepo.findByEmail(email);

		m.addAttribute("patient", user);

	}

	@ModelAttribute
	private void patientDetails(Model m, Principal p) {
		String email = p.getName();
		Patient patient = patientRepo.findByEmail(email);

		m.addAttribute("patientuser", patient);

	}

	@GetMapping("/")
	public String home() {
		return "patient/home";
	}

	@GetMapping("/medInfo")
	public String medInfo() {
		return "patient/medInfo";
	}

	@GetMapping("/viewBill")
	public String viewBill() {
		return "patient/viewBill";
	}

	@GetMapping("/makePayment/{id}")
	public String makePayment(@PathVariable(value = "id") long id, Model model) {
		Optional<Patient> optional = patientRepo.findById(id);
        Patient p = null;
		if (optional.isPresent()) {
			p = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + id);
		}
		model.addAttribute("p", p);
		return "patient/makePayment";
	}

	@PostMapping("/payBill")
	public String payBill(@ModelAttribute("p") Patient patient){
		patientService.payBill(patient);
		return"redirect:/patient/";
	}

	@GetMapping("/appointments")
	public String showdoctor(Model model) {
		List<Employee> employees = employeeRepository.findAll();
		model.addAttribute("employees", employees);
		return "patient/appointments";
	}

	@PostMapping("/appointments")
	public String bookAppointment(@ModelAttribute("patient") Patient patient, @RequestParam("appointmentDate") String appointmentDate, @RequestParam("doctorName") long doctorName) {
		Optional<Employee> doctor = employeeRepository.findById(doctorName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentDate, formatter);
		Appointments appointment = new Appointments();
		appointment.setFirstName(patient.getFirstName());
		appointment.setLastName(patient.getLastName());
		appointment.setDoctor(doctor.get().getFirstName() + " " + doctor.get().getLastName());
		appointment.setAppointmentdate(appointmentDateTime);
		appointmentRepository.save(appointment);
		
		patient.setdoctorId(doctorName);
		patient.setDoctor(doctor.get().getFirstName() + " " + doctor.get().getLastName());
		patient.setAppointmentdate(appointmentDateTime);
		patientRepo.save(patient);
    	return "redirect:/patient/appointments";
	}

}
