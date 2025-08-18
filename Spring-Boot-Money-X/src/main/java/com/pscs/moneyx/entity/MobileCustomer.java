package com.pscs.moneyx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
public class MobileCustomer {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String firstName;
private String lastName;
private String dob;
private String gender;
private String mail;
private String phone;
public Long getId() {
	return id;
}
public void setId(Long id) {
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
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
@Override
public String toString() {
	return "MobileCustomer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
			+ ", gender=" + gender + ", mail=" + mail + ", phone=" + phone + "]";
}


}
