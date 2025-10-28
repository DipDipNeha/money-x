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
@Table(name = "MOB_SERVICE_MASTER")
public class MobServiceMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERVICE_NAME", length = 200)
    private String serviceName;

    @Column(name = "SERVICE_DESCRIPTION", length = 1000)
    private String serviceDescription;

    @Column(name = "SERVICE_CODE", length = 20)
    private String serviceCode;

    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name = "CREATED_BY", length = 20)
    private String createdBy;

    @Column(name = "AUTH_DTTM", length = 20)
    private String authDttm;

    @Column(name = "STATUS")
    private Integer status = 1; // Default value

    @Column(name = "USSD_PROCESSOR", length = 500)
    private String ussdProcessor;

    @Column(name = "APP_PROCESSOR", length = 500)
    private String appProcessor;

    @Column(name = "PROCESSOR_METHOD", length = 100)
    private String processorMethod;

    @Column(name = "SERVICE_TYPE", length = 20)
    private String serviceType;

    // ====== Getters and Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
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

    public String getAuthDttm() {
        return authDttm;
    }

    public void setAuthDttm(String authDttm) {
        this.authDttm = authDttm;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUssdProcessor() {
        return ussdProcessor;
    }

    public void setUssdProcessor(String ussdProcessor) {
        this.ussdProcessor = ussdProcessor;
    }

    public String getAppProcessor() {
        return appProcessor;
    }

    public void setAppProcessor(String appProcessor) {
        this.appProcessor = appProcessor;
    }

    public String getProcessorMethod() {
        return processorMethod;
    }

    public void setProcessorMethod(String processorMethod) {
        this.processorMethod = processorMethod;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
