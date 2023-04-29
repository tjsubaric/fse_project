package com.uiowa.fse_project.service;
import org.springframework.data.domain.Page;
import com.uiowa.fse_project.model.Patient;

public interface EmployeeService {

    void giveDiagnosis(Patient patient);
    void givePrescription(Patient patient);
    void issueBill(long id, float amount);
    Patient getMyPatientById(long id);
    void dischargePatient(Patient patient);
    Page<Patient> findMyPatientsPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
