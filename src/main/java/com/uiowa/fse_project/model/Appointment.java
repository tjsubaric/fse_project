package com.uiowa.fse_project.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "patient")
    private Patient patient;

    @Column(name = "employee")
    private Employee employee;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "time")
    private String time;


    public Appointment(Patient patient, Employee employee, LocalDateTime dateTime, String time) {
        this.patient = patient;
        this.employee = employee;
        this.dateTime = dateTime;
        this.time = time;
    }

    public Appointment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatientId(Patient patient) {
        this.patient = patient;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getAppointmentDate() {
        return dateTime;
    }

    public void setAppointmentDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAppointmentTime() {
        return time;
    }

    public void setAppointmentTime(String time) {
        this.time = time;
    }

    public void setPatient(Patient patient2) {
    }
}