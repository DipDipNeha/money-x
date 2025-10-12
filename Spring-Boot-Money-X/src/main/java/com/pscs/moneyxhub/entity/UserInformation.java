package com.pscs.moneyxhub.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_INFORMATION")
public class UserInformation  {

    @Id
    @Column(name = "COMMON_ID", length = 20)
    private String commonId;

    @Column(name = "USER_NAME", length = 200)
    private String userName;

    @Column(name = "USER_STATUS", length = 20)
    private String userStatus;

    @Column(name = "USER_TYPE", length = 200)
    private String userType;

    @Column(name = "ENTITY", length = 20)
    private String entity;

    @Column(name = "APPL_CODE", length = 20)
    private String applCode;

    @Column(name = "ADDRESS", length = 200)
    private String address;

    @Column(name = "COUNTRY", length = 20)
    private String country;

    @Column(name = "POSTAL_CODE", length = 20)
    private String postalCode;

    @Column(name = "FAX", length = 20)
    private String fax;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "MOBILE_NO", length = 20)
    private String mobileNo;

    @Column(name = "CONTACT_ADDRESS", length = 50)
    private String contactAddress;

    @Column(name = "USER_GROUPS", length = 200)
    private String userGroups;

    @Column(name = "PROVINCE", length = 200)
    private String province;

    @Column(name = "LOCATION", length = 200)
    private String location;

    @Column(name = "CITY", length = 200)
    private String city;

    @Column(name = "USER_PREV_STATUS", length = 200)
    private String userPrevStatus;

    @Column(name = "ADMIN_TYPE", length = 50)
    private String adminType;

    @Column(name = "EXPIRY_DATE")
    private Timestamp expiryDate;

    @Column(name = "USER_LEVEL", length = 100)
    private String userLevel;

    @Column(name = "EMP_ID", length = 20)
    private String empId;

    @Column(name = "LOCAL_RES_NUM", length = 20)
    private String localResNum;

    @Column(name = "LOCAL_OFF_NUM", length = 20)
    private String localOffNum;

    @Column(name = "JSON_DATA", columnDefinition = "TEXT")
    private String jsonData;

    @Column(name = "MERCHANT_ID", length = 50)
    private String merchantId;

    @Column(name = "STORE_ID", length = 20)
    private String storeId;

    @Column(name = "AUTHID", length = 200)
    private String authId;

    @Column(name = "AUTHDTTM")
    private Timestamp authDttm;

    @Column(name = "MAKERID", length = 200)
    private String makerId;

    @Column(name = "MAKERDTTM")
    private Timestamp makerDttm;

    @Column(name = "STATUS", length = 5)
    private String status;

    @Column(name = "AUTH_FLAG", length = 5)
    private String authFlag;

    @Column(name = "REF_NUM", length = 20)
    private String refNum;

    @Column(name = "SECANS1", length = 255)
    private String secAns1;

    @Column(name = "SECANS2", length = 255)
    private String secAns2;

    @Column(name = "SECANS3", length = 255)
    private String secAns3;

    @Column(name = "PROFILE_PIC", length = 255)
    private String profilePic;

    @Column(name = "LOGIN_USER_ID", length = 100)
    private String loginUserId;

    @Column(name = "SECQES1", length = 20)
    private String secQes1;

    @Column(name = "SECQES2", length = 20)
    private String secQes2;

    @Column(name = "SECQES3", length = 20)
    private String secQes3;

	public String getCommonId() {
		return commonId;
	}

	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getApplCode() {
		return applCode;
	}

	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(String userGroups) {
		this.userGroups = userGroups;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserPrevStatus() {
		return userPrevStatus;
	}

	public void setUserPrevStatus(String userPrevStatus) {
		this.userPrevStatus = userPrevStatus;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getLocalResNum() {
		return localResNum;
	}

	public void setLocalResNum(String localResNum) {
		this.localResNum = localResNum;
	}

	public String getLocalOffNum() {
		return localOffNum;
	}

	public void setLocalOffNum(String localOffNum) {
		this.localOffNum = localOffNum;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
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

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getSecQes1() {
		return secQes1;
	}

	public void setSecQes1(String secQes1) {
		this.secQes1 = secQes1;
	}

	public String getSecQes2() {
		return secQes2;
	}

	public void setSecQes2(String secQes2) {
		this.secQes2 = secQes2;
	}

	public String getSecQes3() {
		return secQes3;
	}

	public void setSecQes3(String secQes3) {
		this.secQes3 = secQes3;
	}

	@Override
	public String toString() {
		return "UserInformation [commonId=" + commonId + ", userName=" + userName + ", userStatus=" + userStatus
				+ ", userType=" + userType + ", entity=" + entity + ", applCode=" + applCode + ", address=" + address
				+ ", country=" + country + ", postalCode=" + postalCode + ", fax=" + fax + ", email=" + email
				+ ", mobileNo=" + mobileNo + ", contactAddress=" + contactAddress + ", userGroups=" + userGroups
				+ ", province=" + province + ", location=" + location + ", city=" + city + ", userPrevStatus="
				+ userPrevStatus + ", adminType=" + adminType + ", expiryDate=" + expiryDate + ", userLevel="
				+ userLevel + ", empId=" + empId + ", localResNum=" + localResNum + ", localOffNum=" + localOffNum
				+ ", jsonData=" + jsonData + ", merchantId=" + merchantId + ", storeId=" + storeId + ", authId="
				+ authId + ", authDttm=" + authDttm + ", makerId=" + makerId + ", makerDttm=" + makerDttm + ", status="
				+ status + ", authFlag=" + authFlag + ", refNum=" + refNum + ", secAns1=" + secAns1 + ", secAns2="
				+ secAns2 + ", secAns3=" + secAns3 + ", profilePic=" + profilePic + ", loginUserId=" + loginUserId
				+ ", secQes1=" + secQes1 + ", secQes2=" + secQes2 + ", secQes3=" + secQes3 + "]";
	}

   
}
