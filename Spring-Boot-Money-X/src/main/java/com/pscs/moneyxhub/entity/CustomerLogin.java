/**
 * 
 */
package com.pscs.moneyxhub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 
 */
@Entity(name = "CUSTOMER_LOGIN_TBL")
public class CustomerLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String authType;
	private String authValue;
	private String customerType;
	private String lastLogin;
	private String customerId;
	private String fullName ;
	private String accountNumber;
	private String country;
	private String nationalId;
	private String mobileNumber;
	private String email;
	private String nationalType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getAuthValue() {
		return authValue;
	}
	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getNationalType() {
		return nationalType;
	}
	public void setNationalType(String nationalType) {
		this.nationalType = nationalType;
	}
	@Override
	public String toString() {
		return "CustomerLogin [id=" + id + ", username=" + username + ", password=" + password + ", authType="
				+ authType + ", authValue=" + authValue + ", customerType=" + customerType + ", lastLogin=" + lastLogin
				+ ", customerId=" + customerId + ", fullName=" + fullName + ", accountNumber=" + accountNumber
				+ ", country=" + country + ", nationalId=" + nationalId + ", mobileNumber=" + mobileNumber + ", email="
				+ email + "]";
	}
	
}
