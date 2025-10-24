package com.pscs.moneyxhub.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "transactions_logs_TBL")
@Table(name = "transactions_logs_TBL")
public class Transactions {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

@Column(length = 20, nullable = false)
private String acctNo;

@Column(length = 100)
private String sourceAccountName;

@Column(length = 20)
private String txnType;

private double amount;

@Column(length = 50)
private String currency;

@Temporal(TemporalType.TIMESTAMP)
private Date txnDate;

@Column(length = 50)
private String paymentReference;

@Column(length = 20)
private String responseCode;

@Column(length = 200)
private String responseMessage;

@Temporal(TemporalType.TIMESTAMP)
private Date createdDate;

@Column(length = 20)
private String status;

@Column(length = 20)
private String accountType;

@Column(length = 50)
private String userId;

@Column(length = 20)
private String beneficiaryaccount;

@Column(length = 100)
private String beneficiaryname;

@Column(length = 100)
private String beneficiarybank;

@Column(length = 20)
private String destinationBankCode;

@Column(length = 200)
private String remarks;

@Column(length = 50)
private String channel;

@Column(length = 50)
private String country;

@Column(length = 4000)
private String requestJbody;

@Column(length = 4000)
private String responseJbody;

@Column(length = 200)
private String crnarration;

@Column(length = 200)
private String drnarration;

@Column(length = 500)
private String webhookUrl;

private String debitCreditIndicator;

public String getDebitCreditIndicator() {
	return debitCreditIndicator;
}
public void setDebitCreditIndicator(String debitCreditIndicator) {
		this.debitCreditIndicator = debitCreditIndicator;		
}

	
	
	
	
	public String getSourceAccountName() {
		return sourceAccountName;
	}
	public void setSourceAccountName(String sourceAccountName) {
		this.sourceAccountName = sourceAccountName;
	}
	public String getDestinationBankCode() {
		return destinationBankCode;
	}
	public void setDestinationBankCode(String destinationBankCode) {
		this.destinationBankCode = destinationBankCode;
	}
	public String getWebhookUrl() {
		return webhookUrl;
	}
	public void setWebhookUrl(String webhookUrl) {
		this.webhookUrl = webhookUrl;
	}
	public String getCrnarration() {
		return crnarration;
	}
	public void setCrnarration(String crnarration) {
		this.crnarration = crnarration;
	}
	public String getDrnarration() {
		return drnarration;
	}
	public void setDrnarration(String drnarration) {
		this.drnarration = drnarration;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBeneficiaryaccount() {
		return beneficiaryaccount;
	}
	public void setBeneficiaryaccount(String beneficiaryaccount) {
		this.beneficiaryaccount = beneficiaryaccount;
	}
	public String getBeneficiaryname() {
		return beneficiaryname;
	}
	public void setBeneficiaryname(String beneficiaryname) {
		this.beneficiaryname = beneficiaryname;
	}
	public String getBeneficiarybank() {
		return beneficiarybank;
	}
	public void setBeneficiarybank(String beneficiarybank) {
		this.beneficiarybank = beneficiarybank;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRequestJbody() {
		return requestJbody;
	}
	public void setRequestJbody(String requestJbody) {
		this.requestJbody = requestJbody;
	}
	public String getResponseJbody() {
		return responseJbody;
	}
	public void setResponseJbody(String responseJbody) {
		this.responseJbody = responseJbody;
	}
	@Override
	public String toString() {
		return "Transactions [id=" + id + ", acctNo=" + acctNo + ", txnType=" + txnType + ", amount=" + amount
				+ ", currency=" + currency + ", txnDate=" + txnDate + ", paymentReference=" + paymentReference
				+ ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", createdDate="
				+ createdDate + ", status=" + status + ", accountType=" + accountType + ", userId=" + userId
				+ ", beneficiaryaccount=" + beneficiaryaccount + ", beneficiaryname=" + beneficiaryname
				+ ", beneficiarybank=" + beneficiarybank + ", remarks=" + remarks + ", channel=" + channel
				+ ", country=" + country + ", requestJbody=" + requestJbody + ", responseJbody=" + responseJbody
				+ ", getId()=" + getId() + ", getAcctNo()=" + getAcctNo() + ", getTxnType()=" + getTxnType()
				+ ", getAmount()=" + getAmount() + ", getCurrency()=" + getCurrency() + ", getTxnDate()=" + getTxnDate()
				+ ", getPaymentReference()=" + getPaymentReference() + ", getResponseCode()=" + getResponseCode()
				+ ", getResponseMessage()=" + getResponseMessage() + ", getCreatedDate()=" + getCreatedDate()
				+ ", getStatus()=" + getStatus() + ", getAccountType()=" + getAccountType() + ", getUserId()="
				+ getUserId() + ", getBeneficiaryaccount()=" + getBeneficiaryaccount() + ", getBeneficiaryname()="
				+ getBeneficiaryname() + ", getBeneficiarybank()=" + getBeneficiarybank() + ", getRemarks()="
				+ getRemarks() + ", getChannel()=" + getChannel() + ", getCountry()=" + getCountry()
				+ ", getRequestJbody()=" + getRequestJbody() + ", getResponseJbody()=" + getResponseJbody()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
    
}
