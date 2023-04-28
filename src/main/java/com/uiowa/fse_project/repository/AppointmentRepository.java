package com.uiowa.fse_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Appointment;

public interface AppointmentRepository extends JpaRepository <Appointment, Long>{
    Optional<Appointment> findById(long id);
}
