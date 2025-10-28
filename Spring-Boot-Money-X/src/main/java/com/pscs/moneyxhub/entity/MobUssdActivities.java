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
@Table(name = "MOB_USSD_ACTIVITIES")
public class MobUssdActivities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MSISDN", length = 20)
    private String msisdn;

    @Column(name = "SESSIONID", length = 200)
    private String sessionId;

    @Column(name = "SESSION_STRING", length = 4000)
    private String sessionString;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "DATE_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @Column(name = "NEXTPAGE", length = 200)
    private String nextPage;

    @Column(name = "NEXTSERVICE", length = 200)
    private String nextService;

    @Column(name = "OPERATORID", length = 20)
    private String operatorId;

    @Column(name = "OLD_SESSIONID", length = 50)
    private String oldSessionId;

    @Column(name = "CURRENTPAGE", length = 200)
    private String currentPage;

    @Column(name = "CURRENTSERVICE", length = 200)
    private String currentService;

    @Column(name = "SESSION_OUT", length = 20)
    private String sessionOut;

    @Column(name = "SHORTCODE_FLAG", length = 200)
    private String shortcodeFlag = "N";  // Default value as per table

    @Column(name = "CUSTOMER_FLOW", length = 4000)
    private String customerFlow;

    // ====== Getters and Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionString() {
        return sessionString;
    }

    public void setSessionString(String sessionString) {
        this.sessionString = sessionString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getNextService() {
        return nextService;
    }

    public void setNextService(String nextService) {
        this.nextService = nextService;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOldSessionId() {
        return oldSessionId;
    }

    public void setOldSessionId(String oldSessionId) {
        this.oldSessionId = oldSessionId;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentService() {
        return currentService;
    }

    public void setCurrentService(String currentService) {
        this.currentService = currentService;
    }

    public String getSessionOut() {
        return sessionOut;
    }

    public void setSessionOut(String sessionOut) {
        this.sessionOut = sessionOut;
    }

    public String getShortcodeFlag() {
        return shortcodeFlag;
    }

    public void setShortcodeFlag(String shortcodeFlag) {
        this.shortcodeFlag = shortcodeFlag;
    }

    public String getCustomerFlow() {
        return customerFlow;
    }

    public void setCustomerFlow(String customerFlow) {
        this.customerFlow = customerFlow;
    }
}
