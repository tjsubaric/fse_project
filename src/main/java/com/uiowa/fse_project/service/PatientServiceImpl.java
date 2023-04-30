package com.uiowa.fse_project.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.uiowa.fse_project.repository.EmployeeRepository;
import com.uiowa.fse_project.repository.PatientRepository;
import java.util.Optional;
import com.uiowa.fse_project.model.Patient;
import com.uiowa.fse_project.model.Employee;

public class PatientServiceImpl implements PatientService {

    @Autowired
	private EmployeeRepository employeeRepository;

    @Autowired
	private PatientRepository patientRepository;

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
	public void payBill(long id, long amount){
		Optional<Patient> optPat = patientRepository.findById(id);
        Patient patient = null;
		if (optPat.isPresent()) {
			patient = optPat.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + id);
		}
		patient.decBill(amount);
	}
}
