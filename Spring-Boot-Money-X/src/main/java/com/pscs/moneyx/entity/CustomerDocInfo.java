package com.pscs.moneyx.entity;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "CUSTOMER_DOC_INFO")
public class CustomerDocInfo {

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="IMAGE_DATA")
	@Lob
	private byte[] imageData;
	
	@Column(name = "IMAGE_TYPE")
	private String imageType;
	
	@Column(name="MAKER_ID")
	private String makerId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MAKER_DTTM")
	private Date makerDttm=new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public Date getMakerDttm() {
		return makerDttm;
	}

	public void setMakerDttm(Date makerDttm) {
		this.makerDttm = makerDttm;
	}

	@Override
	public String toString() {
		return "MobCustomerDocInfo [id=" + id + ", imageData=" + Arrays.toString(imageData) + ", imageType=" + imageType
				+ ", makerId=" + makerId + ", makerDttm=" + makerDttm + "]";
	}

	
	
	
	
}
