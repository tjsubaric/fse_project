package com.uiowa.fse_project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uiowa.fse_project.repository.PatientRepository;
import com.uiowa.fse_project.model.Patient;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private PatientRepository patientRepository;

    @Override
    public void giveDiagnosis(long id, String diagnosis){
        Optional<Patient> optional = patientRepository.findById(id);
        Patient patient = null;
		if (optional.isPresent()) {
			patient = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + id);
		}
        patient.setDiagnosis(diagnosis);
        this.patientRepository.save(patient);
    }

    @Override
    public void givePrescription(long id, String prescription){
        Optional<Patient> optional = patientRepository.findById(id);
        Patient patient = null;
		if (optional.isPresent()) {
			patient = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + id);
		}
        patient.setPrescription(prescription);
        this.patientRepository.save(patient);
    }
    
}
