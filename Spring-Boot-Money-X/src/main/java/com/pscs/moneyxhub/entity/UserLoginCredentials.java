package com.pscs.moneyxhub.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_LOGIN_CREDENTIALS")
public class UserLoginCredentials {
	

    @Id
    @Column(name = "LOGIN_USER_ID", length = 100)
    private String loginUserId;

    @Column(name = "PASSWORD", length = 200)
    private String password;

    @Column(name = "COMMON_ID", length = 200)
    private String commonId;

    @Column(name = "OLD_PASSWORDS", length = 2000)
    private String oldPasswords;

    @Column(name = "MAKER_ID", length = 20)
    private String makerId;

    @Column(name = "MAKER_DTTM")
    private Timestamp makerDttm;

    @Column(name = "APPL_CODE", length = 2000)
    private String applCode;

    @Column(name = "PREV_PASSWD", length = 200)
    private String prevPasswd;

    @Column(name = "LAST_PASSWD_CHANGE")
    private Timestamp lastPasswdChange;

    @Column(name = "INCORRECT_PASSWD_CNT")
    private Short incorrectPasswdCnt;

    @Column(name = "REASON_PWD_CHANGE", length = 200)
    private String reasonPwdChange;

    @Column(name = "PIN", length = 200)
    private String pin;

    @Column(name = "USER_LOCK_TIME")
    private Timestamp userLockTime;

    @Column(name = "AUTHID", length = 200)
    private String authId;

    @Column(name = "AUTHDTTM")
    private Timestamp authDttm;

    @Column(name = "STATUS", length = 5)
    private String status;

    @Column(name = "AUTH_FLAG", length = 5)
    private String authFlag;

    @Column(name = "REF_NUM", length = 20)
    private String refNum;

    @Column(name = "LAST_LOGED_IN")
    private Timestamp lastLoggedIn;

    @Column(name = "LAST_REQ_TIME", length = 200)
    private String lastReqTime;

    @Column(name = "IS_LOGGED_IN", length = 200)
    private String isLoggedIn;

    @Column(name = "PIN_STATUS")
    private Integer pinStatus;

    @Column(name = "USER_PASSWORD", length = 2000)
    private String userPassword;

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

	public String getCommonId() {
		return commonId;
	}

	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}

	public String getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(String oldPasswords) {
		this.oldPasswords = oldPasswords;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public Timestamp getMakerDttm() {
		return makerDttm;
	}

	public void setMakerDttm(Timestamp makerDttm) {
		this.makerDttm = makerDttm;
	}

	public String getApplCode() {
		return applCode;
	}

	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}

	public String getPrevPasswd() {
		return prevPasswd;
	}

	public void setPrevPasswd(String prevPasswd) {
		this.prevPasswd = prevPasswd;
	}

	public Timestamp getLastPasswdChange() {
		return lastPasswdChange;
	}

	public void setLastPasswdChange(Timestamp lastPasswdChange) {
		this.lastPasswdChange = lastPasswdChange;
	}

	public Short getIncorrectPasswdCnt() {
		return incorrectPasswdCnt;
	}

	public void setIncorrectPasswdCnt(Short incorrectPasswdCnt) {
		this.incorrectPasswdCnt = incorrectPasswdCnt;
	}

	public String getReasonPwdChange() {
		return reasonPwdChange;
	}

	public void setReasonPwdChange(String reasonPwdChange) {
		this.reasonPwdChange = reasonPwdChange;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Timestamp getUserLockTime() {
		return userLockTime;
	}

	public void setUserLockTime(Timestamp userLockTime) {
		this.userLockTime = userLockTime;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public Timestamp getAuthDttm() {
		return authDttm;
	}

	public void setAuthDttm(Timestamp authDttm) {
		this.authDttm = authDttm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public Timestamp getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Timestamp lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public String getLastReqTime() {
		return lastReqTime;
	}

	public void setLastReqTime(String lastReqTime) {
		this.lastReqTime = lastReqTime;
	}

	public String getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(String isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public Integer getPinStatus() {
		return pinStatus;
	}

	public void setPinStatus(Integer pinStatus) {
		this.pinStatus = pinStatus;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "UserLoginCredentials [loginUserId=" + loginUserId + ", password=" + password + ", commonId=" + commonId
				+ ", oldPasswords=" + oldPasswords + ", makerId=" + makerId + ", makerDttm=" + makerDttm + ", applCode="
				+ applCode + ", prevPasswd=" + prevPasswd + ", lastPasswdChange=" + lastPasswdChange
				+ ", incorrectPasswdCnt=" + incorrectPasswdCnt + ", reasonPwdChange=" + reasonPwdChange + ", pin=" + pin
				+ ", userLockTime=" + userLockTime + ", authId=" + authId + ", authDttm=" + authDttm + ", status="
				+ status + ", authFlag=" + authFlag + ", refNum=" + refNum + ", lastLoggedIn=" + lastLoggedIn
				+ ", lastReqTime=" + lastReqTime + ", isLoggedIn=" + isLoggedIn + ", pinStatus=" + pinStatus
				+ ", userPassword=" + userPassword + "]";
	}

    
}
