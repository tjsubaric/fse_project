package com.uiowa.fse_project.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.repository.PatientRepository;
import java.util.Optional;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.Employee;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
	private PatientRepository patientRepository;

	@Autowired
	private EmployeeRepository employeeRepository;


	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Override
	public Patient createPatient(Patient patient) {

		patient.setPassword(passwordEncode.encode(patient.getPassword()));
		patient.setRole("ROLE_PATIENT");

		return patientRepository.save(patient);
	}

	@Override
	public boolean checkEmail(String email) {

		return patientRepository.existsByEmail(email);
	}

	@Override
    public void selectDoctor(long patientID, long doctorID){
        Optional<Patient> optPat = patientRepository.findById(patientID);
        Patient patient = null;
		if (optPat.isPresent()) {
			patient = optPat.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + patientID);
		}

        Optional<Employee> optDoc = employeeRepository.findById(doctorID);
		if (!(optDoc.isPresent())) {
			throw new RuntimeException(" Doctor not found for id :: " + doctorID);
		} else {
			patient.setdoctorId(doctorID);
		}
    }

	@Override
	public void payBill(Patient patient){
		Optional<Patient> optional = patientRepository.findById(patient.getId());
        Patient p = null;
		if (optional.isPresent()) {
			p = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + patient.getId());
		}
		p.decBill(patient.getBill());
		patientRepository.save(p);
	}
}
