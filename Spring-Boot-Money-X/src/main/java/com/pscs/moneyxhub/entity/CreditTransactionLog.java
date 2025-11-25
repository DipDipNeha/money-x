/**
 * @author Dipak
 */

package com.pscs.moneyxhub.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_transaction_log")
public class CreditTransactionLog {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userId;
	private String sessionId;
	private String walletId;
	private String eventType;
	private String debitAccountNumber;
	private String creditAccountNumber;
	private String debitAccountName;
	private String creditAccountName;
	private double amount;
	private String currency;
	private String status;
	private String paymentReference;
	private String dateOfTransaction;
	private String processedAt;
	
	private Date createdAt;
	private String drcrStatus;
	private String description;
	private String requestData;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}
	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}
	public String getCreditAccountNumber() {
		return creditAccountNumber;
	}
	public void setCreditAccountNumber(String creditAccountNumber) {
		this.creditAccountNumber = creditAccountNumber;
	}
	public String getDebitAccountName() {
		return debitAccountName;
	}
	public void setDebitAccountName(String debitAccountName) {
		this.debitAccountName = debitAccountName;
	}
	public String getCreditAccountName() {
		return creditAccountName;
	}
	public void setCreditAccountName(String creditAccountName) {
		this.creditAccountName = creditAccountName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public String getDateOfTransaction() {
		return dateOfTransaction;
	}
	public void setDateOfTransaction(String dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	public String getProcessedAt() {
		return processedAt;
	}
	public void setProcessedAt(String processedAt) {
		this.processedAt = processedAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	
	public String getDrcrStatus() {
		return drcrStatus;
	}

	public void setDrcrStatus(String drcrStatus) {
		this.drcrStatus = drcrStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "CreditTransactionLog [id=" + id + ", userId=" + userId + ", sessionId=" + sessionId + ", walletId="
				+ walletId + ", eventType=" + eventType + ", debitAccountNumber=" + debitAccountNumber
				+ ", creditAccountNumber=" + creditAccountNumber + ", debitAccountName=" + debitAccountName
				+ ", creditAccountName=" + creditAccountName + ", amount=" + amount + ", currency=" + currency
				+ ", status=" + status + ", paymentReference=" + paymentReference + ", dateOfTransaction="
				+ dateOfTransaction + ", processedAt=" + processedAt + ", createdAt=" + createdAt + ", drcrStatus="
				+ drcrStatus + ", description=" + description + ", requestData=" + requestData + "]";
	}
	
	
}
