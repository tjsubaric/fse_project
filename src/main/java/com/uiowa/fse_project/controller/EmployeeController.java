package com.uiowa.fse_project.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uiowa.fse_project.model.Patient;
import org.springframework.web.bind.annotation.RequestParam;
import com.uiowa.fse_project.service.EmployeeService;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

	@GetMapping("/")
	public String home() {
		return "employee/home";
	}

	@GetMapping("/schedule")
	public String showEmployeeSchedule(){
		return "employee/schedule";
	}

	@GetMapping("/mypatients")
	public String showPatientBoard(Model model, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "firstName") String sortField, 
	@RequestParam(defaultValue = "asc") String sortDir) {
		int pageSize = 5;
		long docID = 1;
		Page<Patient> page = employeeService.findMyPatientsPaginated(pageNo, pageSize, sortField, sortDir);
		List<Patient> patients = page.getContent();
		ArrayList<Patient> myPatients = new ArrayList<Patient>();
		for(int i=0; i<patients.size(); i++){
			if (patients.get(i).getdoctorId() == (docID)){
				myPatients.add(patients.get(i));
			}
		}
	
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("patients", myPatients);

		return "employee/mypatients";
	}

	@GetMapping("/mypatients/prescription/{id}")
	public String prescribePatient(@PathVariable(value = "id") long id, Model model) {
		Patient patient = employeeService.getMyPatientById(id);
		model.addAttribute("patient", patient);
		return "employee/prescription";
	}

	@PostMapping("/mypatients/prescribePatient")
	public String prescribePatient(@ModelAttribute("patient") Patient patient) {
		employeeService.givePrescription(patient);
		return "redirect:/employee/mypatients";
	}

	@GetMapping("/mypatients/diagnosis/{id}")
	public String diagnosePatient(@PathVariable(value = "id") long id, Model model) {
		Patient patient = employeeService.getMyPatientById(id);
		model.addAttribute("patient", patient);
		return "employee/diagnosis";
	}

	@PostMapping("/mypatients/diagnosePatient")
	public String diagnosePatient(@ModelAttribute("patient") Patient patient) {
		employeeService.giveDiagnosis(patient);
		return "redirect:/employee/mypatients";
	}

	@GetMapping("/mypatients/discharge/{id}")
	public String dischargePatient(@PathVariable(value = "id") long id, Model model) {
		Patient patient = employeeService.getMyPatientById(id);
		model.addAttribute("patient", patient);
		return "employee/discharge";
	}

	@GetMapping("/mypatients/dischargePatient/{id}")
	public String dischargeMyPatient(@PathVariable (value = "id") long id) {
		Patient patient = employeeService.getMyPatientById(id);
		employeeService.dischargePatient(patient);
		return "redirect:/employee/mypatients";
	}

	@GetMapping("/mypatients/bill/{id}")
	public String billPatient(@PathVariable(value = "id") long id, Model model) {
		Patient patient = employeeService.getMyPatientById(id);
		model.addAttribute("patient", patient);
		return "employee/bill";
	}
}