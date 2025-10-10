package com.pscs.moneyxhub.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "OTP_DATA_TABL")
public class OtpDataTabl {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "otp_id_generator" )
	@SequenceGenerator(name = "otp_id_generator", sequenceName = "OTP_DATA_SEQUENCE", allocationSize = 1)
	@Column(name="REF_NO")
	Long refNo;
	
	@Column(name="USER_ID")
	String userId;
	
	@Column(name="MOBILE_NO")
	String mobileNo;
	
	@Column(name="OTP")
	String otp;
	
	@Column(name="CHANNEL")
	String channel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRANS_DTTM")
	Date transDttm;
	
	@Column(name="OTP_STATUS")
	String otpStatus;
	
	@Column(name="TRANS_TYPE")
	String transType;
	
	@Column(name="AMOUNT")
	String amount;
	
	@Column(name="EMAILID")
	String emailId;

	
	
	public OtpDataTabl() {
		super();
	}

	public OtpDataTabl(Long refNo, String userId, String mobileNo, String otp, String channel, Date transDttm,
			String otpStatus, String transType, String amount, String emailId) {
		super();
		this.refNo = refNo;
		this.userId = userId;
		this.mobileNo = mobileNo;
		this.otp = otp;
		this.channel = channel;
		this.transDttm = transDttm;
		this.otpStatus = otpStatus;
		this.transType = transType;
		this.amount = amount;
		this.emailId = emailId;
	}


	

	public Long getRefNo() {
		return refNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getOtp() {
		return otp;
	}

	public String getChannel() {
		return channel;
	}

	public Date getTransDttm() {
		return transDttm;
	}

	public String getOtpStatus() {
		return otpStatus;
	}

	public String getTransType() {
		return transType;
	}

	public String getAmount() {
		return amount;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setRefNo(Long refNo) {
		this.refNo = refNo;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setTransDttm(Date transDttm) {
		this.transDttm = transDttm;
	}

	public void setOtpStatus(String otpStatus) {
		this.otpStatus = otpStatus;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@PrePersist
	private void onCreate() {
		transDttm = new Date();
	}

	@Override
	public String toString() {
		return "OtpDataTabl [refNo=" + refNo + ", userId=" + userId + ", mobileNo=" + mobileNo + ", otp=" + otp
				+ ", channel=" + channel + ", transDttm=" + transDttm + ", otpStatus=" + otpStatus + ", transType="
				+ transType + ", amount=" + amount + ", emailId=" + emailId + "]";
	}
	
	
	
}
