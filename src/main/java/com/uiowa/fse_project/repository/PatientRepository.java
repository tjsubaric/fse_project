package com.uiowa.fse_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Patient;

public interface PatientRepository extends JpaRepository <Patient, Long>{
    Optional<Patient> findById(long id);

    Optional<Patient> findBydoctorID(long id);

    public boolean existsByEmail(String email);

	public Patient findByEmail(String email);
}
