package com.pscs.moneyxhub.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_GROUPS_TEMP_AUD")
public class UserGroupsTempAud  {

    @Id
    @Column(name = "GROUP_ID", length = 255)
    private String groupId;

    @Column(name = "REV")
    private Integer rev;

    @Column(name = "REVTYPE")
    private Short revType;

    @Column(name = "APPL_CODE", length = 255)
    private String applCode;

    @Column(name = "AUTH_FLAG", length = 255)
    private String authFlag;

    @Column(name = "CHECKERDTTM")
    private Timestamp checkerDttm;

    @Column(name = "CHECKERID", length = 255)
    private String checkerId;

    @Column(name = "ENTITY", length = 255)
    private String entity;

    @Column(name = "GROUP_NAME", length = 255)
    private String groupName;

    @Column(name = "GROUP_TYPE", length = 255)
    private String groupType;

    @Column(name = "MAKER_DTTM")
    private Timestamp makerDttm;

    @Column(name = "MAKER_ID", length = 255)
    private String makerId;

    @Column(name = "REF_NUM", length = 255)
    private String refNum;

    @Column(name = "REMARKS", length = 255)
    private String remarks;

    @Column(name = "STATUS", length = 255)
    private String status;

    @Column(name = "USER_LEVEL", length = 255)
    private String userLevel;

    @Column(name = "JSON_VAL", columnDefinition = "TEXT")
    private String jsonVal;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getRev() {
		return rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public Short getRevType() {
		return revType;
	}

	public void setRevType(Short revType) {
		this.revType = revType;
	}

	public String getApplCode() {
		return applCode;
	}

	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public Timestamp getCheckerDttm() {
		return checkerDttm;
	}

	public void setCheckerDttm(Timestamp checkerDttm) {
		this.checkerDttm = checkerDttm;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Timestamp getMakerDttm() {
		return makerDttm;
	}

	public void setMakerDttm(Timestamp makerDttm) {
		this.makerDttm = makerDttm;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getJsonVal() {
		return jsonVal;
	}

	public void setJsonVal(String jsonVal) {
		this.jsonVal = jsonVal;
	}

	@Override
	public String toString() {
		return "UserGroupsTempAud [groupId=" + groupId + ", rev=" + rev + ", revType=" + revType + ", applCode="
				+ applCode + ", authFlag=" + authFlag + ", checkerDttm=" + checkerDttm + ", checkerId=" + checkerId
				+ ", entity=" + entity + ", groupName=" + groupName + ", groupType=" + groupType + ", makerDttm="
				+ makerDttm + ", makerId=" + makerId + ", refNum=" + refNum + ", remarks=" + remarks + ", status="
				+ status + ", userLevel=" + userLevel + ", jsonVal=" + jsonVal + "]";
	}

    
}
