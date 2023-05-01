package com.uiowa.fse_project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
    @Column(name = "role")
	private String role = "ROLE_USER";
	@Column(name="doctor")
	private long doctorID = 0;
	@Column(name = "bill")
	private double bill = 0.00;
	@Column(name = "diagnosis")
	private String diagnosis = "None";
	@Column(name = "prescription")
	private String prescription = "None";
	@Column (name = "appointmentDATE")
	private LocalDateTime appointmentdate = LocalDateTime.now();
	@Column(name = "doctorN")
	private String doctorN = "None";

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    public boolean isPresent() {
        return false;
    }

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public double getBill(){
		return bill;
	}
	public void setBill(double amt){
		this.bill += amt;
	}
	public void incBill(double amt){
		this.bill += amt;
	}
	public void decBill(double amt){
		this.bill -= amt;
	}

	public long getdoctorId() {
		return doctorID;
	}
	public void setdoctorId(long id) {
		this.doctorID = id;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public String getPrescription() {
		return prescription;
	}

	public String getDoctor() {
		return doctorN;
	}
	
	public void setDoctor(String doctor) {
		this.doctorN = doctor;
	}

	public LocalDateTime getAppointmentdate() {
		return appointmentdate;
	}
	
	public void setAppointmentdate(LocalDateTime appointmentDateTime) {
		this.appointmentdate = appointmentDateTime;
	}
}