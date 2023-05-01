package com.uiowa.fse_project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uiowa.fse_project.model.Appointments;


public interface AppointmentRepository extends JpaRepository<Appointments, Long>{
    @Query("SELECT a FROM Appointments a WHERE a.doctor = :doctorName")
    List<Appointments> findByDoctorName(@Param("doctorName") String doctorName);
}