package com.uiowa.fse_project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uiowa.fse_project.model.Appointments;

public interface AppointmentRepository extends JpaRepository<Appointments, Long>{

}