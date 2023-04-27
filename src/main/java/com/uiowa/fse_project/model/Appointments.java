package com.uiowa.fse_project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Appointments {
    private int patientId;
    private int doctorId;
    private int bedNumber;
    private LocalDateTime dateTime;
    private List<Appointments> appointments;

    public Appointments(int patientId, int doctorId, int bedNumber, LocalDateTime dateTime) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.bedNumber = bedNumber;
        this.dateTime = dateTime;
    }

    public Appointments() {
        appointments = new ArrayList<>();
    }

    public void setAppointments(ArrayList<Appointments> appointments) {
        this.appointments = appointments;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void addAppointment(int patientId, int doctorId, int bedNumber, LocalDateTime dateTime) {
        Appointments appointment = new Appointments(patientId, doctorId, bedNumber, dateTime);
        appointments.add(appointment);
    }

    public void createAppointment(int patientId, int doctorId, int bedNumber, LocalDateTime dateTime) {
        Appointments appointments = new Appointments();
        appointments.addAppointment(patientId, doctorId, bedNumber, dateTime);
    }

    public List<Appointments> getAppointments() {
        return appointments;
    }
}
