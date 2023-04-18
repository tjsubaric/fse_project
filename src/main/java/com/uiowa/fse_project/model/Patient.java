package com.uiowa.fse_project.model;

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

	@Column(name = "diagnosis")
	private String diagnosis = "None";
	@Column(name = "prescription")
	private String prescription = "None";


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
}