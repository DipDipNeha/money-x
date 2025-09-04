package com.pscs.moneyx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "transactions_TBL")
public class Transactions {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String acctNo;
	private String txnType;
	private double amount;
	private String currency;
	private String txnDate;
	private String paymentReference;
	private String responseCode;
	private String responseMessage;
	private String createdDate;
	private String status;
	private String accountType;
	private String userId;
	private String beneficiaryaccount;
	private String beneficiaryname;
	private String beneficiarybank;
	private String remarks;
	private String channel;
	private String country;
	private String requestJbody;
	private String responseJbody;
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
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
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
