package com.uiowa.fse_project.service;

import com.uiowa.fse_project.model.Patient;

public interface PatientService {

    public Patient createPatient(Patient patient);

	public boolean checkEmail(String email);

    void selectDoctor(long patientID, long doctorID);
    void payBill(Patient patient);
}
