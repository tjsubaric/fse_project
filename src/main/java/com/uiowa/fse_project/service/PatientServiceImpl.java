package com.uiowa.fse_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
	private PatientRepository patientRepository;


	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public void savePatient(Patient patient) {
		this.patientRepository.save(patient);
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
	public void deletePatientById(long id) {
		this.patientRepository.deleteById(id);
	}

	@Override
	public Page<Patient> findPatientPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.patientRepository.findAll(pageable);
	}

}
