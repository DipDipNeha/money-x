package com.pscs.moneyx.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "complaint_tbl")
public class Complaint {

	
	
	 @Id
	    @Column(name = "id") 
	    private String complaintId;
    private String paymentReference; // Link to Transaction
    private String accountNo;	  // Link to Account
    private String accountType;    // SAVINGS, CURRENT
    private String amount;       // Transaction amount
    private String currency;     // Currency code (e.g., USD, EUR)
    private String transactionDate; // Date of the transaction
    private String transactionType; // DEBIT, CREDIT
    private String userId;       // Link to CustomerLogin
    private String description;
    private String status;       // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    

    public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

	@Override
	public String toString() {
		return "Complaint [complaintId=" + complaintId + ", paymentReference=" + paymentReference + ", accountNo="
				+ accountNo + ", accountType=" + accountType + ", amount=" + amount + ", currency=" + currency
				+ ", transactionDate=" + transactionDate + ", transactionType=" + transactionType + ", userId=" + userId
				+ ", description=" + description + ", status=" + status + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + "]";
	}

	
}
