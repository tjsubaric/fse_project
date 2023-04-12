package com.uiowa.fse_project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.uiowa.fse_project.model.Patient;

public interface PatientService {
    List<Patient> getAllPatients();
    void savePatient(Patient patient);
    Patient getPatientById(long id);
    void deletePatientById(long id);
    Page<Patient> findPatientPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
