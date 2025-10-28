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
@Table(name = "MOB_SERVICEPACK_MAP")
public class MobServicepackMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MENU_TYPE", length = 200)
    private String menuType;

    @Column(name = "DISPLAY_NAME", length = 200)
    private String displayName;

    @Column(name = "TITLE", length = 200)
    private String title;

    @Column(name = "MENU_CODE", length = 20)
    private String menuCode;

    @Column(name = "MENU_ORDER")
    private Long menuOrder;

    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(name = "INSTITUTE", length = 100)
    private String institute;

    @Column(name = "PID")
    private Long pid;

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

    @Column(name = "SER_STATUS", length = 10)
    private String serStatus;

    // ====== Getters and Setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Long getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Long menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public String getSerStatus() {
        return serStatus;
    }

    public void setSerStatus(String serStatus) {
        this.serStatus = serStatus;
    }
}
