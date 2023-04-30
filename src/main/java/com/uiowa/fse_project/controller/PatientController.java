package com.uiowa.fse_project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.UserDtls;
import com.uiowa.fse_project.repository.UserRepository;
import com.uiowa.fse_project.repository.PatientRepository;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PatientRepository patientRepo;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepo.findByEmail(email);

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

	@GetMapping("/appointments")
	public String appointments() {
		return "patient/appointments";
	}

	@GetMapping("/medInfo")
	public String medInfo() {
		return "patient/medInfo";
	}

	@GetMapping("/viewBill")
	public String viewBill() {
		return "patient/viewBill";
	}

}
