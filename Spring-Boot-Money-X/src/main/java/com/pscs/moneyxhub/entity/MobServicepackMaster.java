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
@Table(name = "MOB_SERVICEPACK_MASTER")
public class MobServicepackMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERVICEPACK_CODE", length = 200)
    private String servicepackCode;

    @Column(name = "SERVICEPACK_NAME", length = 200)
    private String servicepackName;

    @Column(name = "SP_STATUS", length = 50)
    private String spStatus;

    @Column(name = "REMARKS", length = 2000)
    private String remarks;

    @Column(name = "USER_LEVEL", length = 50)
    private String userLevel;

    @Column(name = "MAKER_ID", length = 100)
    private String makerId;

    @Column(name = "MAKER_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date makerDttm;

    @Column(name = "AUTH_ID", length = 100)
    private String authId;

    @Column(name = "AUTH_DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authDttm;

    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "REF_NUM", length = 20)
    private String refNum;

    @Column(name = "OPERATORID", length = 20)
    private String operatorId;

    // ====== Getters and Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServicepackCode() {
        return servicepackCode;
    }

    public void setServicepackCode(String servicepackCode) {
        this.servicepackCode = servicepackCode;
    }

    public String getServicepackName() {
        return servicepackName;
    }

    public void setServicepackName(String servicepackName) {
        this.servicepackName = servicepackName;
    }

    public String getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(String spStatus) {
        this.spStatus = spStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
