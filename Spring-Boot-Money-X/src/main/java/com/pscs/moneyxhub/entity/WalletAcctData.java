package com.pscs.moneyxhub.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
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
	@Column(name = "ID")
	Long id;

	@Column(name = "CUST_ID")
	String custId;
	
	@Column(name = "WALLET_ACCT_ID")
	private String walletAcctId;

	@Column(name = "ACCT_NO")
	String acctNo;

	@Column(name = "ALIAS_NAME")
	String aliasName;

	@Column(name = "ACCT_NAME")
	String acctName;

	@Column(name = "PRODUCT_TYPE")
	String productType;

	@Column(name = "BRANCH_CODE")
	String branchCode;

	@Column(name = "ACCT_TYPE")
	String acctType;

	@Column(name = "ACCT_STATUS")
	String acctStatus;

	@Column(name = "INSTITUTE")
	String institute;

	@Column(name = "PRIM_FLAG")
	String primFlag;

	@Column(name = "CREATED_BY")
	String createdBy;

	@Column(name = "DATE_CREATED")
	Date dateCreated;

	@Column(name = "AUTHDTTM")
	Date authDttm;

	@Column(name = "AUTHID")
	String authId;

	@Column(name = "AUTH_FLAG")
	String authFlag;

	@Column(name = "REF_NUM")
	String refNum;

	@Column(name = "ISO_CURRENCY_CODE")
	String isoCurrencyCode;

	@Column(name = "REG_STATUS")
	String regStatus;

	@Column(name = "REMARKS")
	String remarks;

	@Column(name = "APP_TYPE")
	String appType;

	@Column(name = "BALANCE")
	BigDecimal balance;

	@Column(name = "CUST_TYPE")
	String custType;

	@Column(name = "BY_USING")
	String byUsing;

	@Column(name = "BY_USING_REF")
	String byUsingRef;

	@Column(name = "SERVICE_CODE")
	String serviceCode;

	@Column(name = "BY_CREATED")
	String byCreated;

	@Column(name = "INSTITUTION_CODE")
	String institutionCode;

	@Column(name = "LEDGER_BAL")
	BigDecimal ledgerBal;

	@Column(name = "LIEN_BAL")
	BigDecimal lienBal;

	@Column(name = "QRCODE")
	String qrCode;

	@Column(name = "CUST_PRD_CODE")
	String custPrdCode;

	@Column(name = "ACCT_CRCY")
	String acctCrcy;

	@Column(name = "ISNUBAN")
	String isNuban;

	@Column(name = "OLD_ACCT_NO")
	String oldAcctNo;

	@Column(name = "ZERO_FEE")
	String zeroFee;

	@Column(name = "API_CODE")
	String apiCode;

	private String bankCode;
	private String bankName;	
	
	private Date createdAt;
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWalletAcctId() {
		return walletAcctId;
	}

	public void setWalletAcctId(String walletAcctId) {
		this.walletAcctId = walletAcctId;
	}
	
	private String customerTypeId;
	private String currencyId;
	private String walletRestrictionId;
	private String walletClassificationId;

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
	
	public String getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getPrimFlag() {
		return primFlag;
	}

	public void setPrimFlag(String primFlag) {
		this.primFlag = primFlag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getAuthDttm() {
		return authDttm;
	}

	public void setAuthDttm(Date authDttm) {
		this.authDttm = authDttm;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
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

	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}

	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	

	
	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getByUsing() {
		return byUsing;
	}

	public void setByUsing(String byUsing) {
		this.byUsing = byUsing;
	}

	public String getByUsingRef() {
		return byUsingRef;
	}

	public void setByUsingRef(String byUsingRef) {
		this.byUsingRef = byUsingRef;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getByCreated() {
		return byCreated;
	}

	public void setByCreated(String byCreated) {
		this.byCreated = byCreated;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	

	

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getLedgerBal() {
		return ledgerBal;
	}

	public void setLedgerBal(BigDecimal ledgerBal) {
		this.ledgerBal = ledgerBal;
	}

	public BigDecimal getLienBal() {
		return lienBal;
	}

	public void setLienBal(BigDecimal lienBal) {
		this.lienBal = lienBal;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCustPrdCode() {
		return custPrdCode;
	}

	public void setCustPrdCode(String custPrdCode) {
		this.custPrdCode = custPrdCode;
	}

	public String getAcctCrcy() {
		return acctCrcy;
	}

	public void setAcctCrcy(String acctCrcy) {
		this.acctCrcy = acctCrcy;
	}

	public String getIsNuban() {
		return isNuban;
	}

	public void setIsNuban(String isNuban) {
		this.isNuban = isNuban;
	}

	public String getOldAcctNo() {
		return oldAcctNo;
	}

	public void setOldAcctNo(String oldAcctNo) {
		this.oldAcctNo = oldAcctNo;
	}

	public String getZeroFee() {
		return zeroFee;
	}

	public void setZeroFee(String zeroFee) {
		this.zeroFee = zeroFee;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	
	

	@Override
	public String toString() {
		return "WalletAcctData [id=" + id + ", custId=" + custId + ", acctNo=" + acctNo + ", aliasName=" + aliasName
				+ ", acctName=" + acctName + ", productType=" + productType + ", branchCode=" + branchCode
				+ ", acctType=" + acctType + ", acctStatus=" + acctStatus + ", institute=" + institute + ", primFlag="
				+ primFlag + ", createdBy=" + createdBy + ", dateCreated=" + dateCreated + ", authDttm=" + authDttm
				+ ", authId=" + authId + ", authFlag=" + authFlag + ", refNum=" + refNum + ", isoCurrencyCode="
				+ isoCurrencyCode + ", regStatus=" + regStatus + ", remarks=" + remarks + ", appType=" + appType
				+ ", balance=" + balance + ", custType=" + custType + ", byUsing=" + byUsing + ", byUsingRef="
				+ byUsingRef + ", serviceCode=" + serviceCode + ", byCreated=" + byCreated + ", institutionCode="
				+ institutionCode + ", ledgerBal=" + ledgerBal + ", lienBal=" + lienBal + ", qrCode=" + qrCode
				+ ", custPrdCode=" + custPrdCode + ", acctCrcy=" + acctCrcy + ", isNuban=" + isNuban + ", oldAcctNo="
				+ oldAcctNo + ", zeroFee=" + zeroFee + ", apiCode=" + apiCode + "]";
	}

	
	
	
	

}
