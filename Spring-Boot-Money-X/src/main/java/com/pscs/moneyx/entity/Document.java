/**
 * 
 */
package com.pscs.moneyx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 
 */
@Entity(name = "DOCUMENT_TYPE_TBL")
public class Document {
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String documentType;
	private String description;
	private String countryCode;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Override
	public String toString() {
		return "Document [id=" + id + ", documentType=" + documentType + ", description=" + description
				+ ", countryCode=" + countryCode + "]";
	}

}
