package com.pscs.moneyxhub.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_GROUPS")
public class UserGroups  {

    @Id
    @Column(name = "GROUP_ID", length = 200)
    private String groupId;

    @Column(name = "GROUP_NAME", length = 200)
    private String groupName;

    @Column(name = "APPL_CODE", length = 200)
    private String applCode;

    @Column(name = "MAKER_ID", length = 200)
    private String makerId;

    @Column(name = "MAKER_DTTM")
    private Timestamp makerDttm;

    @Column(name = "ENTITY", length = 200)
    private String entity;

    @Column(name = "JSON_VAL", columnDefinition = "TEXT")
    private String jsonVal;

    @Column(name = "GROUP_TYPE", length = 200)
    private String groupType;

    @Column(name = "USER_LEVEL", length = 50)
    private String userLevel;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Column(name = "REMARKS", length = 400)
    private String remarks;

    @Column(name = "CHECKERID", length = 20)
    private String checkerId;

    @Column(name = "CHECKERDTTM")
    private Timestamp checkerDttm;

    @Column(name = "AUTH_FLAG", length = 20)
    private String authFlag;

    @Column(name = "REF_NUM", length = 25)
    private String refNum;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getApplCode() {
		return applCode;
	}

	public void setApplCode(String applCode) {
		this.applCode = applCode;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public Timestamp getMakerDttm() {
		return makerDttm;
	}

	public void setMakerDttm(Timestamp makerDttm) {
		this.makerDttm = makerDttm;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getJsonVal() {
		return jsonVal;
	}

	public void setJsonVal(String jsonVal) {
		this.jsonVal = jsonVal;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public Timestamp getCheckerDttm() {
		return checkerDttm;
	}

	public void setCheckerDttm(Timestamp checkerDttm) {
		this.checkerDttm = checkerDttm;
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

	@Override
	public String toString() {
		return "UserGroups [groupId=" + groupId + ", groupName=" + groupName + ", applCode=" + applCode + ", makerId="
				+ makerId + ", makerDttm=" + makerDttm + ", entity=" + entity + ", jsonVal=" + jsonVal + ", groupType="
				+ groupType + ", userLevel=" + userLevel + ", status=" + status + ", remarks=" + remarks
				+ ", checkerId=" + checkerId + ", checkerDttm=" + checkerDttm + ", authFlag=" + authFlag + ", refNum="
				+ refNum + "]";
	}

    
    
}
