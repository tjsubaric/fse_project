package com.uiowa.fse_project.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.Appointments;
import com.uiowa.fse_project.model.Employee;
import org.springframework.web.bind.annotation.RequestParam;
import com.uiowa.fse_project.service.EmployeeService;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.security.Principal;

import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		String email = p.getName();
		Employee user = employeeRepo.findByEmail(email);
		m.addAttribute("employee", user);
	}

	@GetMapping("/")
	public String home() {
		return "employee/home";
	}

	@GetMapping("/mypatients/{id}")
	public String showPatientBoard(Model model, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "firstName") String sortField, 
	@RequestParam(defaultValue = "asc") String sortDir, @PathVariable(value = "id") long id) {
		int pageSize = 5;
		long docID = id;
		Page<Patient> page = employeeService.findMyPatientsPaginated(pageNo, pageSize, sortField, sortDir);
		List<Patient> patients = page.getContent();
		ArrayList<Patient> myPatients = new ArrayList<>();
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
	@GetMapping("/schedule/{id}")
	public String showAppointmentBoard(Model model, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "firstName") String sortField, 
	@RequestParam(defaultValue = "asc") String sortDir, @PathVariable(value = "id") long id) {
		int pageSize = 5;
		Optional<Employee> employee = employeeRepo.findById(id);
		Page<Appointments> page = employeeService.findMyAppointmentsPaginated(pageNo, pageSize, sortField, sortDir);
		List<Appointments> appointments = page.getContent();
		ArrayList<Appointments> myAppointments = new ArrayList<>();
		for(int i=0; i<appointments.size(); i++){
			if (appointments.get(i).getDoctor() == (employee.get().getFirstName()+' '+employee.get().getLastName())){
				myAppointments.add(appointments.get(i));
			}
		}

		String doctorFullName = employee.get().getFirstName() + " " + employee.get().getLastName();	
		List<Appointments> appointments2 = appointmentRepository.findByDoctorName(doctorFullName);
		model.addAttribute("appointments", appointments);

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("appointments", myAppointments);
		model.addAttribute("appointments2", appointments2);

		return "employee/schedule";
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
		return "redirect:/employee/";
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
		return "redirect:/employee/";
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
		return "redirect:/employee/";
	}

	@GetMapping("/mypatients/bill/{id}")
	public String billPatient(@PathVariable(value = "id") long id, Model model) {
		Patient patient = employeeService.getMyPatientById(id);
		model.addAttribute("patient", patient);
		return "employee/bill";
	}

	@PostMapping("/mypatients/billPatient")
	public String billPatient(@ModelAttribute("patient") Patient patient) {
		employeeService.issueBill(patient);
		return "redirect:/employee/";
	}
}
