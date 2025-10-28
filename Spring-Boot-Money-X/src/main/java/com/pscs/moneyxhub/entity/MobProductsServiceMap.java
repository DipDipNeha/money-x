package com.pscs.moneyxhub.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "MOB_PRODUCTS_SERVICE_MAP")
public class MobProductsServiceMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID", length = 20)
    private String productId;

    @Column(name = "SPACK_ID", length = 20)
    private String spackId;

    @Column(name = "SERVICE_ID", length = 20)
    private String serviceId;

    @Column(name = "FREQUENCY", length = 50)
    private String frequency;

    @Column(name = "CONDITION", length = 50)
    private String condition;

    @Column(name = "FROM_VALUE", length = 50)
    private String fromValue;

    @Column(name = "TO_VALUE", length = 50)
    private String toValue;

    @Column(name = "SLAB", length = 50)
    private String slab;

    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name = "CREATED_BY", length = 300)
    private String createdBy;

    @Column(name = "AUTH_DTTM")
    @Temporal(TemporalType.DATE)
    private Date authDttm;

    @Column(name = "AUTH_FLAG", length = 30)
    private String authFlag;

    @Column(name = "REF_NUM", length = 30)
    private String refNum;

    @Column(name = "STATUS", length = 30)
    private String status;

    // ====== Getters and Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSpackId() {
        return spackId;
    }

    public void setSpackId(String spackId) {
        this.spackId = spackId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    public String getSlab() {
        return slab;
    }

    public void setSlab(String slab) {
        this.slab = slab;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
