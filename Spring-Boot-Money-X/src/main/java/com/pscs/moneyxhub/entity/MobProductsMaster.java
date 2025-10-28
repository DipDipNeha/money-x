package com.pscs.moneyxhub.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MOB_PRODUCTS_MASTER")
public class MobProductsMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID", length = 20)
    private String productId;

    @Column(name = "PRODUCT_ID_DESC", length = 30)
    private String productIdDesc;

    @Column(name = "SUB_PRODUCT_ID", length = 20)
    private String subProductId;

    @Column(name = "SUB_PRODUCT_ID_DESC", length = 70)
    private String subProductIdDesc;

    @Column(name = "INSTITUTE_ID", length = 10)
    private String instituteId;

    @Column(name = "ISO_CURRENCY_CODE", length = 20)
    private String isoCurrencyCode;

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

    public String getProductIdDesc() {
        return productIdDesc;
    }

    public void setProductIdDesc(String productIdDesc) {
        this.productIdDesc = productIdDesc;
    }

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public String getSubProductIdDesc() {
        return subProductIdDesc;
    }

    public void setSubProductIdDesc(String subProductIdDesc) {
        this.subProductIdDesc = subProductIdDesc;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(String isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }
}
