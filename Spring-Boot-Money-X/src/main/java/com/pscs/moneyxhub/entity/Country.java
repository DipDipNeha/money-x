/**
 * 
 */
package com.pscs.moneyxhub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 
 */
@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String countryName;
	private String countryCode;
	private String countryDialCode;
	
	public String getCountryDialCode() {
		return countryDialCode;
	}

	public void setCountryDialCode(String countryDialCode) {
		this.countryDialCode = countryDialCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", countryName=" + countryName + ", countryCode=" + countryCode
				+ ", countryDialCode=" + countryDialCode + "]";
	}
	
	
}
