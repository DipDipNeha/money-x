package com.pscs.moneyx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WALLET_ACCT_DATA")
public class WalletAcctData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String walletGroupId;
	private String customerId;
	private Double availableBalance;
	private Double ledgerBalance;
	private String walletRestrictionId;
	private String walletClassificationId;
	private String currencyId;
	private Boolean isInternal;
	private Boolean isDefault;
	private String name;
	private Double overdraft;
	private String mobNum;
	private String customerTypeId;
	private String bankCode;
	private String bankName;	
	private String accountNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWalletGroupId() {
		return walletGroupId;
	}
	public void setWalletGroupId(String walletGroupId) {
		this.walletGroupId = walletGroupId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Double getLedgerBalance() {
		return ledgerBalance;
	}
	public void setLedgerBalance(Double ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}
	public String getWalletRestrictionId() {
		return walletRestrictionId;
	}
	public void setWalletRestrictionId(String walletRestrictionId) {
		this.walletRestrictionId = walletRestrictionId;
	}
	public String getWalletClassificationId() {
		return walletClassificationId;
	}
	public void setWalletClassificationId(String walletClassificationId) {
		this.walletClassificationId = walletClassificationId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public Boolean getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}
	public String getMobNum() {
		return mobNum;
	}
	public void setMobNum(String mobNum) {
		this.mobNum = mobNum;
	}
	public String getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@Override
	public String toString() {
		return "WalletAcctData [id=" + id + ", walletGroupId=" + walletGroupId + ", customerId=" + customerId
				+ ", availableBalance=" + availableBalance + ", ledgerBalance=" + ledgerBalance
				+ ", walletRestrictionId=" + walletRestrictionId + ", walletClassificationId=" + walletClassificationId
				+ ", currencyId=" + currencyId + ", isInternal=" + isInternal + ", isDefault=" + isDefault + ", name="
				+ name + ", overdraft=" + overdraft + ", mobNum=" + mobNum + ", customerTypeId=" + customerTypeId
				+ ", bankCode=" + bankCode + ", bankName=" + bankName + ", accountNumber=" + accountNumber + "]";
	}
	
	
	
}
	
	