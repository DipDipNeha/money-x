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

@Entity
@Table(name = "MOB_CUSTOMER_MASTER")
public class MobCustomerMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	Long id;
	
	@Column(name="CUSTOMER_CODE")
	String customerCode;
	
	@Column(name="FIRST_NAME")
	String firstName;
	
	@Column(name="MIDDLE_NAME")
	String middleName;
	
	@Column(name="LAST_NAME")
	String lastName;
	
	
	

	@Column(name="GENDER")
	String gender;
	

	@Column(name="EMAILID")
	String emailAddress;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DOB")
	Date dob;
	

	@Column(name="DOCTYPE")
	String docType;
	

	@Column(name="DOCID")
	String docId;
	

	@Column(name="NOTIFICATION")
	String notification;
	

	@Column(name="LANGUAGE_ID")
	Integer  languageId;
	

	@Column(name="SALUTATION")
	String salutation;
	

	@Column(name="PIN")
	String pin;
	

	@Column(name="PIN_RETRIES")
	Integer  pinRetries=0;
	

	@Column(name="PIN_STATUS")
	Integer  pinStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PIN_CHANGED_DATE")
	Date pinChnagedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PIN_CHANGED_PREV_DATE")
	Date pinChangedPrevDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATED")
	Date dateCreated;
	

	@Column(name="CREATED_BY")
	String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AUTHDTTM")
	Date authDttm;
	

	@Column(name="AUTHID")
	String authId;
	

	@Column(name="AUTH_FLAG")
	String authFlag;
	

	@Column(name="REF_NUM")
	Integer refNum;
	

	@Column(name="POSTAL_CODE")
	String postalCode;
	

	@Column(name="STATUS")
	String status;
	

	@Column(name="REMARKS")
	String remarks;
	

	@Column(name="HAS_USER_ID")
	String hasUSerId;
	

	@Column(name="USER_ID")
	String userName;
	
	private String password;

	@Column(name="TXN_PIN")
	String txnPin;
	

	@Column(name="TXN_PIN_RETRIES")
	Integer txnPinRetries=0;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TXN_PIN_CHANGED_DATE")
	Date txnPinChangedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TXN_PIN_PREV_CHANGED_DATE")
	Date txnPinPrevChangedDate;
	
	
	@Column(name="PIN_HISTORY")
	String pinHistory;
	
	@Column(name="TXN_PIN_HISTORY")
	String txnPinHistory;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PIN_EXPIRY")
	Date pinExpiry;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TXN_PIN_EXPIRY")
	Date txnPinExpiry;
	
	@Column(name="DISPNAME")
	String dispName;
	
	@Column(name="FILENAME")
	String fileName;
	
	@Column(name="PER_TXN_OTP_ENABLED")
	String perTxnOtpEnabled;
	
	@Column(name="M_PRD_CODE")
	String mPrdCode;
	
	@Column(name="M_PRD_DESC")
	String mPrdDesc;
	
	@Column(name="M_APP_TYPE")
	String mAppType;
	
	@Column(name="W_PRD_CODE")
	String wPrdCode;
	
	@Column(name="W_PRD_DESC")
	String wPrdDesc;
	
	@Column(name="W_APP_TYPE")
	String wAppType;
	
	@Column(name="USSD_STATUS")
	String ussdStatus;
	
	@Column(name="MOBILE_STATUS")
	String mobileStatus;
	
	@Column(name="W_USSD_STATUS")
	String wUssdStatus;
	
	@Column(name="W_CUST_TYPE")
	String wCustType;
	
	
	@Column(name="CUST_TYPE")
	String custType;
	
	@Column(name="SMSTOKEN")
	String smsToken;
	
	@Column(name="W_STATUS")
	String wStatus;
	
	@Column(name="ISBIO")
	String isBIo;
	
	@Column(name="BIO_REF_NO")
	String bioRefNo;
	
	@Column(name="INDEMNITY_FLAG")
	String indemnityFlag;
	
	@Column(name="S_FA_PIN")
	String sFaPin;
	
	@Column(name="SECRET_WORD")
	String secretWord;
	
	@Column(name="MAKE_MONEY_ENABLED")
	String makeMoneyEnabled;
	
	@Column(name="SEC_QSN_1")
	String secQsn1;
	
	@Column(name="SEC_QSN_2")
	String secQsn2;
	
	@Column(name="SEC_QSN_3")
	String secQsn3;
	
	@Column(name="SEC_ANS_1")
	String secAns1;
	
	@Column(name="SEC_ANS_2")
	String secAns2;
	
	@Column(name="SEC_ANS_3")
	String secAns3;
	
	@Column(name="FRAUD_STATUS")
	String fraudStatus;
	
	@Column(name="EKYC_STATUS")
	String ekycStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STATUS_CHANGED_DATE")
	Date statusChangedDate;
	
	@Column(name="STATUS_CHANGED_PREVIOUS_DATE")
	String statusChangedPreviousDate;
	
	@Column(name="STATUS_PREV_HST")
	String statusPrevHst;
	
	@Column(name = "SECQUES_RETRIES")
	int secQuesRetries;
	
	@Column(name = "OTP_RETRIES")
	int otpRetries;
	
	private String organizationId;
	private String customerTypeId;
	private String alias;
	private String bvn;
	private boolean bvnVerified;
	
	private String nin;
	private boolean ninVerified;
	
	private String authType;
	private String authValue;
	
	
	private String customerType;
	private String customerId;
	private String occupation;
	
	
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public Integer getPinRetries() {
		return pinRetries;
	}
	public void setPinRetries(Integer pinRetries) {
		this.pinRetries = pinRetries;
	}
	public Integer getPinStatus() {
		return pinStatus;
	}
	public void setPinStatus(Integer pinStatus) {
		this.pinStatus = pinStatus;
	}
	public Date getPinChnagedDate() {
		return pinChnagedDate;
	}
	public void setPinChnagedDate(Date pinChnagedDate) {
		this.pinChnagedDate = pinChnagedDate;
	}
	public Date getPinChangedPrevDate() {
		return pinChangedPrevDate;
	}
	public void setPinChangedPrevDate(Date pinChangedPrevDate) {
		this.pinChangedPrevDate = pinChangedPrevDate;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public Integer getRefNum() {
		return refNum;
	}
	public void setRefNum(Integer refNum) {
		this.refNum = refNum;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getHasUSerId() {
		return hasUSerId;
	}
	public void setHasUSerId(String hasUSerId) {
		this.hasUSerId = hasUSerId;
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
	public Integer getTxnPinRetries() {
		return txnPinRetries;
	}
	public void setTxnPinRetries(Integer txnPinRetries) {
		this.txnPinRetries = txnPinRetries;
	}
	public Date getTxnPinChangedDate() {
		return txnPinChangedDate;
	}
	public void setTxnPinChangedDate(Date txnPinChangedDate) {
		this.txnPinChangedDate = txnPinChangedDate;
	}
	public Date getTxnPinPrevChangedDate() {
		return txnPinPrevChangedDate;
	}
	public void setTxnPinPrevChangedDate(Date txnPinPrevChangedDate) {
		this.txnPinPrevChangedDate = txnPinPrevChangedDate;
	}
	public String getPinHistory() {
		return pinHistory;
	}
	public void setPinHistory(String pinHistory) {
		this.pinHistory = pinHistory;
	}
	public String getTxnPinHistory() {
		return txnPinHistory;
	}
	public void setTxnPinHistory(String txnPinHistory) {
		this.txnPinHistory = txnPinHistory;
	}
	public Date getPinExpiry() {
		return pinExpiry;
	}
	public void setPinExpiry(Date pinExpiry) {
		this.pinExpiry = pinExpiry;
	}
	public Date getTxnPinExpiry() {
		return txnPinExpiry;
	}
	public void setTxnPinExpiry(Date txnPinExpiry) {
		this.txnPinExpiry = txnPinExpiry;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPerTxnOtpEnabled() {
		return perTxnOtpEnabled;
	}
	public void setPerTxnOtpEnabled(String perTxnOtpEnabled) {
		this.perTxnOtpEnabled = perTxnOtpEnabled;
	}
	public String getmPrdCode() {
		return mPrdCode;
	}
	public void setmPrdCode(String mPrdCode) {
		this.mPrdCode = mPrdCode;
	}
	public String getmPrdDesc() {
		return mPrdDesc;
	}
	public void setmPrdDesc(String mPrdDesc) {
		this.mPrdDesc = mPrdDesc;
	}
	public String getmAppType() {
		return mAppType;
	}
	public void setmAppType(String mAppType) {
		this.mAppType = mAppType;
	}
	public String getwPrdCode() {
		return wPrdCode;
	}
	public void setwPrdCode(String wPrdCode) {
		this.wPrdCode = wPrdCode;
	}
	public String getwPrdDesc() {
		return wPrdDesc;
	}
	public void setwPrdDesc(String wPrdDesc) {
		this.wPrdDesc = wPrdDesc;
	}
	public String getwAppType() {
		return wAppType;
	}
	public void setwAppType(String wAppType) {
		this.wAppType = wAppType;
	}
	public String getUssdStatus() {
		return ussdStatus;
	}
	public void setUssdStatus(String ussdStatus) {
		this.ussdStatus = ussdStatus;
	}
	public String getMobileStatus() {
		return mobileStatus;
	}
	public void setMobileStatus(String mobileStatus) {
		this.mobileStatus = mobileStatus;
	}
	public String getwUssdStatus() {
		return wUssdStatus;
	}
	public void setwUssdStatus(String wUssdStatus) {
		this.wUssdStatus = wUssdStatus;
	}
	public String getwCustType() {
		return wCustType;
	}
	public void setwCustType(String wCustType) {
		this.wCustType = wCustType;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getSmsToken() {
		return smsToken;
	}
	public void setSmsToken(String smsToken) {
		this.smsToken = smsToken;
	}
	public String getwStatus() {
		return wStatus;
	}
	public void setwStatus(String wStatus) {
		this.wStatus = wStatus;
	}
	public String getIsBIo() {
		return isBIo;
	}
	public void setIsBIo(String isBIo) {
		this.isBIo = isBIo;
	}
	public String getBioRefNo() {
		return bioRefNo;
	}
	public void setBioRefNo(String bioRefNo) {
		this.bioRefNo = bioRefNo;
	}
	public String getIndemnityFlag() {
		return indemnityFlag;
	}
	public void setIndemnityFlag(String indemnityFlag) {
		this.indemnityFlag = indemnityFlag;
	}
	public String getsFaPin() {
		return sFaPin;
	}
	public void setsFaPin(String sFaPin) {
		this.sFaPin = sFaPin;
	}
	public String getSecretWord() {
		return secretWord;
	}
	public void setSecretWord(String secretWord) {
		this.secretWord = secretWord;
	}
	public String getMakeMoneyEnabled() {
		return makeMoneyEnabled;
	}
	public void setMakeMoneyEnabled(String makeMoneyEnabled) {
		this.makeMoneyEnabled = makeMoneyEnabled;
	}
	public String getSecQsn1() {
		return secQsn1;
	}
	public void setSecQsn1(String secQsn1) {
		this.secQsn1 = secQsn1;
	}
	public String getSecQsn2() {
		return secQsn2;
	}
	public void setSecQsn2(String secQsn2) {
		this.secQsn2 = secQsn2;
	}
	public String getSecQsn3() {
		return secQsn3;
	}
	public void setSecQsn3(String secQsn3) {
		this.secQsn3 = secQsn3;
	}
	public String getSecAns1() {
		return secAns1;
	}
	public void setSecAns1(String secAns1) {
		this.secAns1 = secAns1;
	}
	public String getSecAns2() {
		return secAns2;
	}
	public void setSecAns2(String secAns2) {
		this.secAns2 = secAns2;
	}
	public String getSecAns3() {
		return secAns3;
	}
	public void setSecAns3(String secAns3) {
		this.secAns3 = secAns3;
	}
	public String getFraudStatus() {
		return fraudStatus;
	}
	public void setFraudStatus(String fraudStatus) {
		this.fraudStatus = fraudStatus;
	}
	public String getEkycStatus() {
		return ekycStatus;
	}
	public void setEkycStatus(String ekycStatus) {
		this.ekycStatus = ekycStatus;
	}
	public Date getStatusChangedDate() {
		return statusChangedDate;
	}
	public void setStatusChangedDate(Date statusChangedDate) {
		this.statusChangedDate = statusChangedDate;
	}
	public String getStatusChangedPreviousDate() {
		return statusChangedPreviousDate;
	}
	public void setStatusChangedPreviousDate(String statusChangedPreviousDate) {
		this.statusChangedPreviousDate = statusChangedPreviousDate;
	}
	public String getStatusPrevHst() {
		return statusPrevHst;
	}
	public void setStatusPrevHst(String statusPrevHst) {
		this.statusPrevHst = statusPrevHst;
	}
	public int getSecQuesRetries() {
		return secQuesRetries;
	}
	public void setSecQuesRetries(int secQuesRetries) {
		this.secQuesRetries = secQuesRetries;
	}
	public int getOtpRetries() {
		return otpRetries;
	}
	public void setOtpRetries(int otpRetries) {
		this.otpRetries = otpRetries;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getBvn() {
		return bvn;
	}
	public void setBvn(String bvn) {
		this.bvn = bvn;
	}
	public boolean isBvnVerified() {
		return bvnVerified;
	}
	public void setBvnVerified(boolean bvnVerified) {
		this.bvnVerified = bvnVerified;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public boolean isNinVerified() {
		return ninVerified;
	}
	public void setNinVerified(boolean ninVerified) {
		this.ninVerified = ninVerified;
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
	public int getRetryLoginAttempt() {
		return retryLoginAttempt;
	}
	public void setRetryLoginAttempt(int retryLoginAttempt) {
		this.retryLoginAttempt = retryLoginAttempt;
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
	
	
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	@Override
	public String toString() {
		return "MobCustomerMaster [id=" + id + ", customerCode=" + customerCode + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", gender=" + gender + ", emailAddress="
				+ emailAddress + ", dob=" + dob + ", docType=" + docType + ", docId=" + docId + ", notification="
				+ notification + ", languageId=" + languageId + ", salutation=" + salutation + ", pin=" + pin
				+ ", pinRetries=" + pinRetries + ", pinStatus=" + pinStatus + ", pinChnagedDate=" + pinChnagedDate
				+ ", pinChangedPrevDate=" + pinChangedPrevDate + ", dateCreated=" + dateCreated + ", createdBy="
				+ createdBy + ", authDttm=" + authDttm + ", authId=" + authId + ", authFlag=" + authFlag + ", refNum="
				+ refNum + ", postalCode=" + postalCode + ", status=" + status + ", remarks=" + remarks + ", hasUSerId="
				+ hasUSerId + ", userName=" + userName + ", password=" + password + ", txnPin=" + txnPin
				+ ", txnPinRetries=" + txnPinRetries + ", txnPinChangedDate=" + txnPinChangedDate
				+ ", txnPinPrevChangedDate=" + txnPinPrevChangedDate + ", pinHistory=" + pinHistory + ", txnPinHistory="
				+ txnPinHistory + ", pinExpiry=" + pinExpiry + ", txnPinExpiry=" + txnPinExpiry + ", dispName="
				+ dispName + ", fileName=" + fileName + ", perTxnOtpEnabled=" + perTxnOtpEnabled + ", mPrdCode="
				+ mPrdCode + ", mPrdDesc=" + mPrdDesc + ", mAppType=" + mAppType + ", wPrdCode=" + wPrdCode
				+ ", wPrdDesc=" + wPrdDesc + ", wAppType=" + wAppType + ", ussdStatus=" + ussdStatus + ", mobileStatus="
				+ mobileStatus + ", wUssdStatus=" + wUssdStatus + ", wCustType=" + wCustType + ", custType=" + custType
				+ ", smsToken=" + smsToken + ", wStatus=" + wStatus + ", isBIo=" + isBIo + ", bioRefNo=" + bioRefNo
				+ ", indemnityFlag=" + indemnityFlag + ", sFaPin=" + sFaPin + ", secretWord=" + secretWord
				+ ", makeMoneyEnabled=" + makeMoneyEnabled + ", secQsn1=" + secQsn1 + ", secQsn2=" + secQsn2
				+ ", secQsn3=" + secQsn3 + ", secAns1=" + secAns1 + ", secAns2=" + secAns2 + ", secAns3=" + secAns3
				+ ", fraudStatus=" + fraudStatus + ", ekycStatus=" + ekycStatus + ", statusChangedDate="
				+ statusChangedDate + ", statusChangedPreviousDate=" + statusChangedPreviousDate + ", statusPrevHst="
				+ statusPrevHst + ", secQuesRetries=" + secQuesRetries + ", otpRetries=" + otpRetries
				+ ", organizationId=" + organizationId + ", customerTypeId=" + customerTypeId + ", alias=" + alias
				+ ", bvn=" + bvn + ", bvnVerified=" + bvnVerified + ", nin=" + nin + ", ninVerified=" + ninVerified
				+ ", authType=" + authType + ", authValue=" + authValue + ", customerType=" + customerType
				+ ", customerId=" + customerId + ", retryLoginAttempt=" + retryLoginAttempt + ", isLocked=" + isLocked
				+ ", isActive=" + isActive + ", lastLoginTime=" + lastLoginTime + ", isLoginAttemptActive="
				+ isLoginAttemptActive + "]";
	}
	
	
	
	
	

}
