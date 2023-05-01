package com.uiowa.fse_project.controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;
import org.springframework.web.bind.annotation.PathVariable;
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

}
