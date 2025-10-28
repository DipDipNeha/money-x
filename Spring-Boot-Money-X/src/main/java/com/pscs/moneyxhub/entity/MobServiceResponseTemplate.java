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
@Table(name = "MOB_SERVICE_RESPONSE_TEMPLATE")
public class MobServiceResponseTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "TPL_DESC", length = 500)
    private String tplDesc;

    @Column(name = "TPL_MSG", length = 4000)
    private String tplMsg;

    @Id
    @Column(name = "TPL_NAME", length = 50, nullable = false)
    private String tplName;

    @Column(name = "LANGUAGEID")
    private Long languageId;

    @Column(name = "SERVICE_CODE", length = 20)
    private String serviceCode;

    // Getters and Setters
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getTplDesc() {
        return tplDesc;
    }

    public void setTplDesc(String tplDesc) {
        this.tplDesc = tplDesc;
    }

    public String getTplMsg() {
        return tplMsg;
    }

    public void setTplMsg(String tplMsg) {
        this.tplMsg = tplMsg;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
