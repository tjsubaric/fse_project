package com.uiowa.fse_project.service;
import org.springframework.data.domain.Page;

import com.uiowa.fse_project.model.Appointments;
import com.uiowa.fse_project.model.Patient;

public interface EmployeeService {

    void giveDiagnosis(Patient patient);
    void givePrescription(Patient patient);
    void issueBill(Patient patient);
    Patient getMyPatientById(long id);
    void dischargePatient(Patient patient);
    Page<Patient> findMyPatientsPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Appointments> findMyAppointmentsPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
