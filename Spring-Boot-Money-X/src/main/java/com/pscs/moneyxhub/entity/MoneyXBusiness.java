/**
 * 
 */
package com.pscs.moneyxhub.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 
 */
@Entity(name = "MONEYX_BUSINESS_TBL")
public class MoneyXBusiness {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String organizationId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String emailAddress;
	private String mobileNumber;
	private String dob;
	private String customerTypeId;
	private String address;
	private String city;
	
	private String countryId;
	
	
	private String alias;
	private String bvn;
	private String nin;
	private String meterNumber;
	private String houseAddress;
	
	private String accountNumber;
	private String accountType;
	private String currency;
	
	private String userName;
	private String password;
	private String txnPin;
	private String authType;
	private String authValue;
	private String customerType;
	private String customerId;
	
	private String docInfo;
	private int retryLoginAttempt;
	private String isLocked;
	private String isActive;
	private String lastLoginTime;
	@Column(name = "retry_login_active")
	private String isLoginAttemptActive;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getBvn() {
		return bvn;
	}
	public void setBvn(String bvn) {
		this.bvn = bvn;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTxnPin() {
		return txnPin;
	}
	public void setTxnPin(String txnPin) {
		this.txnPin = txnPin;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDocInfo() {
		return docInfo;
	}
	public void setDocInfo(String docInfo) {
		this.docInfo = docInfo;
	}
	public int getRetryLoginAttempt() {
		return retryLoginAttempt;
	}
	public void setRetryLoginAttempt(int retryLoginAttempt) {
		this.retryLoginAttempt = retryLoginAttempt;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getIsLoginAttemptActive() {
		return isLoginAttemptActive;
	}
	public void setIsLoginAttemptActive(String isLoginAttemptActive) {
		this.isLoginAttemptActive = isLoginAttemptActive;
	}
	@Override
	public String toString() {
		return "MoneyXBusiness [id=" + id + ", organizationId=" + organizationId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middleName=" + middleName + ", emailAddress=" + emailAddress
				+ ", mobileNumber=" + mobileNumber + ", dob=" + dob + ", customerTypeId=" + customerTypeId
				+ ", address=" + address + ", city=" + city + ", countryId=" + countryId + ", alias=" + alias + ", bvn="
				+ bvn + ", nin=" + nin + ", meterNumber=" + meterNumber + ", houseAddress=" + houseAddress
				+ ", accountNumber=" + accountNumber + ", accountType=" + accountType + ", currency=" + currency
				+ ", userName=" + userName + ", password=" + password + ", txnPin=" + txnPin + ", authType=" + authType
				+ ", authValue=" + authValue + ", customerType=" + customerType + ", customerId=" + customerId
				+ ", docInfo=" + docInfo + ", retryLoginAttempt=" + retryLoginAttempt + ", isLocked=" + isLocked
				+ ", isActive=" + isActive + ", lastLoginTime=" + lastLoginTime + ", isLoginAttemptActive="
				+ isLoginAttemptActive + "]";
	}
	
	
	
	

}
