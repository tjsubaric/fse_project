package com.uiowa.fse_project.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.repository.AdminRepository;
import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.repository.PatientRepository;
import com.uiowa.fse_project.model.Appointments;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Admin getAdminByEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}

	@Override
	public Patient getPatientByEmail(String email) {
		return patientRepository.findByEmail(email);
	}

	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public void saveAdmin(Admin admin) {
		this.adminRepository.save(admin);
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}

	@Override
	public void savePatient(Patient patient) {
		this.patientRepository.save(patient);
	}
	
	@Override
	public void deleteAppointmentById(long id) {
		this.appointmentRepository.deleteById(id);
	}
	@Override
	public Admin getAdminById(long id) {
		Optional<Admin> optional = adminRepository.findById(id);
		Admin admin = null;
		if (optional.isPresent()) {
			admin = optional.get();
		} else {
			throw new RuntimeException(" Admin not found for id :: " + id);
		}
		return admin;
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;
	}
	
	@Override
	public Appointments getAppointmentById(long id) {
		Optional<Appointments> optional = appointmentRepository.findById(id);
		Appointments appointment = null;
		if (optional.isPresent()) {
			appointment = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return appointment;
	}
	@Override
	public Patient getPatientById(long id) {
		Optional<Patient> optional = patientRepository.findById(id);
		Patient patient = null;
		if (optional.isPresent()) {
			patient = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + id);
		}
		return patient;
	}

	@Override
	public void deleteAdminById(long id) {
		this.adminRepository.deleteById(id);
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public void deletePatientById(long id) {
		this.patientRepository.deleteById(id);
	}

	@Override
	public Page<Admin> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.adminRepository.findAll(pageable);
	}

	@Override
	public Page<Employee> findEmployeePaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

	@Override
	public Page<Patient> findPatientPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.patientRepository.findAll(pageable);
	}

}
