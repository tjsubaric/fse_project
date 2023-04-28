package com.uiowa.fse_project.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.Appointment;
import com.uiowa.fse_project.model.Employee;
import com.uiowa.fse_project.model.Patient;

public interface AdminService {

	// all update Admin methods
    List<Admin> getAllAdmins();
	void saveAdmin(Admin admin);
	Admin getAdminById(long id);
	void deleteAdminById(long id);
	Page<Admin> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	// all update Employee methods
	List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    Page<Employee> findEmployeePaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	// all update Patient methods
	List<Patient> getAllPatients();
    void savePatient(Patient patient);
    Patient getPatientById(long id);
    void deletePatientById(long id);
    Page<Patient> findPatientPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    Appointment createAppointment(Appointment appointment);

}
