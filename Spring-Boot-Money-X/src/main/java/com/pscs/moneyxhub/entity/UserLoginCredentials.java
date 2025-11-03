package com.pscs.moneyxhub.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "USER_LOGIN_CREDENTIALS")
public class UserLoginCredentials {
	
	@Id
	@Column(name="LOGIN_USER_ID")
	String loginUserId;
	
	@Column(name = "PASSWORD")
	String password;
	
	@Column(name = "COMMON_ID")
	String comonId;
	
	@Column(name = "OLD_PASSWORDS")
	String oldPasswords;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_PASSWD_CHANGE")
	Date lastPasswdChange;
	
	@Column(name = "REASON_PWD_CHANGE")
	String reasonPwdChange;
	
	
	@Column(name = "STATUS")
	String status;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGED_IN")
	Date lastLogedIn;
	

	
	@Column(name = "TXN_PIN")
	String txnPin;
	
	@Column(name = "TXN_PIN_STATUS")
	Integer txnPinStatus;
	
	@Column(name = "PREV_TXNPINS")
	String prevTxnPins;
	
	@Column(name = "INCORRECT_TXNPIN_CNT")
	Integer incorrectTxnPinCnt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TXNPIN_CHANGE_DATE")
	Date txnpinChangeDate;
	
	

	private String applicationId;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComonId() {
		return comonId;
	}

	public void setComonId(String comonId) {
		this.comonId = comonId;
	}

	public String getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(String oldPasswords) {
		this.oldPasswords = oldPasswords;
	}
	public Date getLastPasswdChange() {
		return lastPasswdChange;
	}

	public void setLastPasswdChange(Date lastPasswdChange) {
		this.lastPasswdChange = lastPasswdChange;
	}

	public String getReasonPwdChange() {
		return reasonPwdChange;
	}

	public void setReasonPwdChange(String reasonPwdChange) {
		this.reasonPwdChange = reasonPwdChange;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastLogedIn() {
		return lastLogedIn;
	}

	public void setLastLogedIn(Date lastLogedIn) {
		this.lastLogedIn = lastLogedIn;
	}

	public String getTxnPin() {
		return txnPin;
	}

	public void setTxnPin(String txnPin) {
		this.txnPin = txnPin;
	}

	public Integer getTxnPinStatus() {
		return txnPinStatus;
	}

	public void setTxnPinStatus(Integer txnPinStatus) {
		this.txnPinStatus = txnPinStatus;
	}

	public String getPrevTxnPins() {
		return prevTxnPins;
	}

	public void setPrevTxnPins(String prevTxnPins) {
		this.prevTxnPins = prevTxnPins;
	}

	public Integer getIncorrectTxnPinCnt() {
		return incorrectTxnPinCnt;
	}

	public void setIncorrectTxnPinCnt(Integer incorrectTxnPinCnt) {
		this.incorrectTxnPinCnt = incorrectTxnPinCnt;
	}

	public Date getTxnpinChangeDate() {
		return txnpinChangeDate;
	}

	public void setTxnpinChangeDate(Date txnpinChangeDate) {
		this.txnpinChangeDate = txnpinChangeDate;
	}
	
	
	
	
	
	

}
