package com.uiowa.fse_project.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.uiowa.fse_project.repository.AppointmentRepository;
import com.uiowa.fse_project.repository.PatientRepository;
import com.uiowa.fse_project.model.Appointments;
import com.uiowa.fse_project.model.Patient;
import org.springframework.data.domain.Sort;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
    public void giveDiagnosis(Patient patient){
		Optional<Patient> optional = patientRepository.findById(patient.getId());
        Patient patientNew = null;
		if (optional.isPresent()) {
			patientNew = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + patient.getId());
		}
		patientNew.setDiagnosis(patient.getDiagnosis());
        this.patientRepository.save(patientNew);
    }

    @Override
    public void givePrescription(Patient patient){
		Optional<Patient> optional = patientRepository.findById(patient.getId());
        Patient patientNew = null;
		if (optional.isPresent()) {
			patientNew = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + patient.getId());
		}
		patientNew.setPrescription(patient.getPrescription());
        this.patientRepository.save(patientNew);
    }

	@Override
	public void dischargePatient(Patient patient){
		patient.setdoctorId(0);
		patientRepository.save(patient);
		//If any schedule cancelling needs to happen, it goes here
	}
    
    @Override
    public void issueBill(Patient patient){
        Optional<Patient> optional = patientRepository.findById(patient.getId());
        Patient patientNew = null;
		if (optional.isPresent()) {
			patientNew = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + patient.getId());
		}
        patientNew.setBill(patient.getBill());
		patientRepository.save(patientNew);
    }

    @Override
	public Patient getMyPatientById(long id){
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
    public Page<Patient> findMyPatientsPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.patientRepository.findAll(pageable);
	}
	@Override
    public Page<Appointments> findMyAppointmentsPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.appointmentRepository.findAll(pageable);
	}
}
