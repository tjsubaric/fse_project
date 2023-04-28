package com.uiowa.fse_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Appointments;

public interface AppointmentsRepository extends JpaRepository <Appointments, Long>{
    Optional<Appointments> findById(long id);
}
