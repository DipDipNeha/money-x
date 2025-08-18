/**
 * 
 */
package com.pscs.moneyx.entity;

import java.util.Date;

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
	private Date lastLogin;
	private String customer_code;
	
	
	
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
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}
	@Override
	public String toString() {
		return "CustomerLogin [id=" + id + ", username=" + username + ", password=" + password + ", authType="
				+ authType + ", authValue=" + authValue + ", customerType=" + customerType + ", lastLogin=" + lastLogin
				+ ", customer_code=" + customer_code + "]";
	}
	
	
	

}
