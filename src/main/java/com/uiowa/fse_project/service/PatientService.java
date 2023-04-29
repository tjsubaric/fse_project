package com.uiowa.fse_project.service;

public interface PatientService {
   
    void selectDoctor(long patientID, long doctorID);
    void payBill(long id, float amount);
}
