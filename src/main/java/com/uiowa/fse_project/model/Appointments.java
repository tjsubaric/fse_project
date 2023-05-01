package com.uiowa.fse_project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	public long id;

    @Column(name = "patientfname")
	private String firstName;
	
	@Column(name = "patientlname")
	private String lastName;
	
	@Column(name = "appointment")
	public LocalDateTime appointmentdate = LocalDateTime.now();

	@Column(name = "doctor")
	private String doctor;

    public String getDoctor() {
		return doctor;
	}
	
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(LocalDateTime appointmentdate) {
        this.appointmentdate = appointmentdate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}