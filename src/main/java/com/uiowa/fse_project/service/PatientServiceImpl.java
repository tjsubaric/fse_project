package com.uiowa.fse_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
	private PatientRepository patientRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Override
	public Patient createPatient(Patient patient) {

		patient.setPassword(passwordEncode.encode(patient.getPassword()));
		patient.setRole("ROLE_PATIENT");

		return patientRepo.save(patient);
	}

	@Override
	public boolean checkEmail(String email) {

		return patientRepo.existsByEmail(email);
	}

}
