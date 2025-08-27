/**
 * 
 */
package com.pscs.moneyx.entity;

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
	private String businessName;
	private String businessType;
	private String businessEmail;
	private String businessPhone;
	private String businessIndustry;
	private String businessId;
	private String businessAddress;
	private String businessCountry;
	private String businessStatus;
	private String businessRole;
	
	private String invitedByEmail;
	
	
	private String ownerName;
	private String ownerEmail;
	private String ownerPhone;
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;
	
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

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getBusinessIndustry() {
		return businessIndustry;
	}

	public void setBusinessIndustry(String businessIndustry) {
		this.businessIndustry = businessIndustry;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessCountry() {
		return businessCountry;
	}

	public void setBusinessCountry(String businessCountry) {
		this.businessCountry = businessCountry;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getBusinessRole() {
		return businessRole;
	}

	public void setBusinessRole(String businessRole) {
		this.businessRole = businessRole;
	}

	public String getInvitedByEmail() {
		return invitedByEmail;
	}

	public void setInvitedByEmail(String invitedByEmail) {
		this.invitedByEmail = invitedByEmail;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
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

	public int getRetryLoginAttempt() {
		return retryLoginAttempt;
	}

	public void setRetryLoginAttempt(int retryLoginAttempt) {
		this.retryLoginAttempt = retryLoginAttempt;
	}

	@Override
	public String toString() {
		return "MoneyXBusiness [id=" + id + ", businessName=" + businessName + ", businessType=" + businessType
				+ ", businessEmail=" + businessEmail + ", businessPhone=" + businessPhone + ", businessIndustry="
				+ businessIndustry + ", businessId=" + businessId + ", businessAddress=" + businessAddress
				+ ", businessCountry=" + businessCountry + ", businessStatus=" + businessStatus + ", businessRole="
				+ businessRole + ", invitedByEmail=" + invitedByEmail + ", ownerName=" + ownerName + ", ownerEmail="
				+ ownerEmail + ", ownerPhone=" + ownerPhone + ", registrationDate=" + registrationDate
				+ ", accountNumber=" + accountNumber + ", accountType=" + accountType + ", currency=" + currency
				+ ", userName=" + userName + ", password=" + password + ", txnPin=" + txnPin + ", authType=" + authType
				+ ", authValue=" + authValue + ", customerType=" + customerType + ", customerId=" + customerId
				+ ", docInfo=" + docInfo + ", retryLoginAttempt=" + retryLoginAttempt + ", isLocked=" + isLocked
				+ ", isActive=" + isActive + ", lastLoginTime=" + lastLoginTime + ", isLoginAttemptActive="
				+ isLoginAttemptActive + "]";
	}

	
	
	
	

}
