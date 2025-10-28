package com.pscs.moneyxhub.entity;

import java.io.Serializable;
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
@Table(name = "MOB_CONTACT_INFO")
public class MobContactInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", length = 50, nullable = false)
	private Long id;

	@Column(name = "CUST_ID", length = 250)
	private String custId;

	@Column(name = "MOBILE_OPERATOR", length = 500)
	private String mobileOperator;

	@Column(name = "ISO_COUNTRY_CODE", length = 50)
	private String isoCountryCode;

	@Column(name = "MOBILE_NUMBER", length = 150)
	private String mobileNumber;

	@Column(name = "REF_NUM", length = 50)
	private String refNum;

	@Column(name = "AUTH_ID", length = 50)
	private String authId;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUTH_DTTM")
	private Date authDttm;

	@Column(name = "CREATED_BY", length = 50)
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DTTM")
	private Date createdDttm;

	@Column(name = "APP_TYPE", length = 20)
	private String appType;

	@Column(name = "ADDRESS", length = 200)
	private String address;

	@Column(name = "COUNTRY", length = 200)
	private String country;

	@Column(name = "CUST_TYPE", length = 200)
	private String custType = "M";

	@Column(name = "ID_DETAILS", length = 200)
	private String idDetails;

	@Column(name = "ID_TYPE", length = 200)
	private String idType;

	@Column(name = "NATIONALITY", length = 200)
	private String nationality;

	@Column(name = "RL_LGA", length = 200)
	private String rlLga;

	@Column(name = "R_STATE", length = 200)
	private String rState;

	@Column(name = "STREET_ADDR_LINE", length = 500)
	private String streetAddrLine;

	@Column(name = "STREET_ADDR_LINE1", length = 500)
	private String streetAddrLine1;

	@Column(name = "POSTAL_CODE", length = 10)
	private String postalCode;

	@Column(name = "CITY", length = 80)
	private String city;

	@Column(name = "LONGITUDE", length = 90)
	private String longitude;

	@Column(name = "LATITUDE", length = 90)
	private String latitude;

	@Column(name = "REGION", length = 20)
	private String region;

	@Column(name = "DISTRICT", length = 20)
	private String district;
	private String countryId;
	private String maritalStatus;

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getMobileOperator() {
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator) {
		this.mobileOperator = mobileOperator;
	}

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public Date getAuthDttm() {
		return authDttm;
	}

	public void setAuthDttm(Date authDttm) {
		this.authDttm = authDttm;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDttm() {
		return createdDttm;
	}

	public void setCreatedDttm(Date createdDttm) {
		this.createdDttm = createdDttm;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
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

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getIdDetails() {
		return idDetails;
	}

	public void setIdDetails(String idDetails) {
		this.idDetails = idDetails;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getRlLga() {
		return rlLga;
	}

	public void setRlLga(String rlLga) {
		this.rlLga = rlLga;
	}

	public String getrState() {
		return rState;
	}

	public void setrState(String rState) {
		this.rState = rState;
	}

	public String getStreetAddrLine() {
		return streetAddrLine;
	}

	public void setStreetAddrLine(String streetAddrLine) {
		this.streetAddrLine = streetAddrLine;
	}

	public String getStreetAddrLine1() {
		return streetAddrLine1;
	}

	public void setStreetAddrLine1(String streetAddrLine1) {
		this.streetAddrLine1 = streetAddrLine1;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}
