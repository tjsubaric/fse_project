package com.uiowa.fse_project.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Appointments;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.UserDtls;
import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.repository.PatientRepository;
import com.uiowa.fse_project.service.AdminService;
import com.uiowa.fse_project.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private  PatientRepository patientRepository;

	@Autowired
	private  EmployeeRepository employeeRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}

	@GetMapping("/appointment_board")
	public String showAppointmentBoard(Model model, @RequestParam(required = false) Long newAppointment) {
		List<Appointments> appointments = appointmentRepository.findAll();
		model.addAttribute("appointments", appointments);
		return "admin/appointment_board";
	}

	@GetMapping("/employee_board")
	public String showEmployeeBoard(Model model, @RequestParam(defaultValue = "1") int pageNo,
									@RequestParam(defaultValue = "firstName") String sortField,
									@RequestParam(defaultValue = "asc") String sortDir) {
		int pageSize = 5;

		Page<Employee> page = adminService.findEmployeePaginated(pageNo, pageSize, sortField, sortDir);
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

		Page<Patient> page = adminService.findPatientPaginated(pageNo, pageSize, sortField, sortDir);
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

	@GetMapping("/create_appointment")
	public String showNewAppointmentForm(Model model) {
	 	List<Employee> employees = employeeRepository.findAll();
     	List<Patient> patients = patientRepository.findAll();
		model.addAttribute("employees", employees);
     	model.addAttribute("employees", employees);
    	model.addAttribute("patients", patients);
     	model.addAttribute("admin", new Admin());
     	return "admin/create_appointment";
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

	@GetMapping("/appointment_board/deleteAppointment/{id}")
	public String deleteAppointment(@PathVariable (value = "id") long id) {
		this.adminService.deleteAppointmentById(id);
		return "redirect:/admin/appointment_board";
	}
	
	@PostMapping("/create_appointment")
	public String createAppointment(@RequestParam("patient") long patient, @RequestParam("employee") long employee, @RequestParam("date") String Date, @RequestParam("time") String Time){
		Optional<Employee> doctor = employeeRepository.findById(employee);
		Optional<Patient> p = patientRepository.findById(patient);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime appointmentDateTime = LocalDateTime.parse(Date+'T'+Time, formatter);
		Appointments appointment = new Appointments();
		appointment.setFirstName(p.get().getFirstName());
		appointment.setLastName(p.get().getLastName());
		appointment.setDoctor(doctor.get().getFirstName() + " " + doctor.get().getLastName());
		appointment.setAppointmentdate(appointmentDateTime);
		appointmentRepository.save(appointment);
		p.ifPresent((patient2) -> {
			patient2.setDoctor(appointment.getDoctor());
			patient2.setdoctorId(employee);
			patient2.setAppointmentdate(appointment.getAppointmentdate());
			patientRepository.save(patient2);
		});
		return "redirect:appointment_board";
	}

    @PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute UserDtls user, HttpSession session) {

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createAdmin(user);
			if (userDtls != null) {
				// create a new Patient object and set the necessary properties
				Admin admin = new Admin();
				admin.setFirstName(user.getFirstName());
				admin.setLastName(user.getLastName());
				admin.setEmail(user.getEmail());
				admin.setPassword(passwordEncode.encode(user.getPassword()));
				// pass the patient object to the adminService.savePatient() method
				adminService.saveAdmin(admin);
				session.setAttribute("msg", "Register Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}
		return "redirect:/admin/";
	}

	@PostMapping("/employee_board/saveEmployee")
	public String saveEmployee(@ModelAttribute UserDtls user, HttpSession session) {

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createEmployee(user);
			if (userDtls != null) {
				// create a new Patient object and set the necessary properties
				Employee employee = new Employee();
				employee.setFirstName(user.getFirstName());
				employee.setLastName(user.getLastName());
				employee.setEmail(user.getEmail());
				employee.setPassword(passwordEncode.encode(user.getPassword()));
				// pass the patient object to the adminService.savePatient() method
				adminService.saveEmployee(employee);
				session.setAttribute("msg", "Register Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}
		return "redirect:/admin/employee_board";
	}

	@PostMapping("/patient_board/savePatient")
	public String savePatient(@ModelAttribute UserDtls user, HttpSession session) {

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createUser(user);
			if (userDtls != null) {
				// create a new Patient object and set the necessary properties
				Patient patient = new Patient();
				patient.setFirstName(user.getFirstName());
				patient.setLastName(user.getLastName());
				patient.setEmail(user.getEmail());
				patient.setPassword(passwordEncode.encode(user.getPassword()));
				// pass the patient object to the adminService.savePatient() method
				adminService.savePatient(patient);
				session.setAttribute("msg", "Register Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}
		return "redirect:/admin/patient_board";
	}

	@GetMapping("/showFormAdminUpdate/{id}")
	public String showFormAdminUpdate(@PathVariable ( value = "id") long id, Model model) {
		Admin admin = adminService.getAdminById(id);
		model.addAttribute("admin", admin);
		return "admin/update_admin";
	}

	@PostMapping("/showFormAdminUpdate/{id}")
	public String updateAdmin(@PathVariable(value = "id") long id, @ModelAttribute("admin") Admin admin) {
		adminService.saveAdmin(admin);
		return "redirect:/admin/";
	}

	@GetMapping("/employee_board/showFormEmployeeUpdate/{id}")
	public String showFormEmployeeUpdate(@PathVariable ( value = "id") long id, Model model) {
		Employee employee = adminService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "admin/update_employee";
	}

	@PostMapping("/employee_board/showFormEmployeeUpdate/{id}")
	public String updateEmployee(@PathVariable(value = "id") long id, @ModelAttribute("admin") Employee employee) {
		adminService.saveEmployee(employee);
		return "redirect:/admin/employee_board";
	}

	@GetMapping("/patient_board/showFormPatientUpdate/{id}")
	public String showFormPatientUpdate(@PathVariable ( value = "id") long id, Model model) {
		Patient patient = adminService.getPatientById(id);
		model.addAttribute("patient", patient);
		return "admin/update_patient";
	}

	@PostMapping("/patient_board/showFormPatientUpdate/{id}")
	public String updatePatient(@PathVariable(value = "id") long id, @ModelAttribute("admin") Patient patient) {
		adminService.savePatient(patient);
		return "redirect:/admin/patient_board";
	}
	
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable (value = "id") long id) {
		// call delete employee method 
		this.adminService.deleteAdminById(id);
		return "redirect:/admin/";
	}

	@GetMapping("/employee_board/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		this.adminService.deleteEmployeeById(id);
		return "redirect:/admin/employee_board";
	}

	@GetMapping("/patient_board/deletePatient/{id}")
	public String deletePatient(@PathVariable (value = "id") long id) {
		this.adminService.deletePatientById(id);
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