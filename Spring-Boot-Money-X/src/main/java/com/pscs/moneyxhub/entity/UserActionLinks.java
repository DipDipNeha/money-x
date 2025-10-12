package com.pscs.moneyxhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_ACTION_LINKS")
public class UserActionLinks  {

    @Id
    @Column(name = "ID", length = 20)
    private String id;

    @Column(name = "PID", length = 20)
    private String pid;

    @Column(name = "NAME", length = 200)
    private String name;

    @Column(name = "CHECKED", length = 200)
    private String checked;

    @Column(name = "OPEN", length = 200)
    private String open;

    @Column(name = "TITLE", length = 200)
    private String title;

    @Column(name = "ACTION", length = 200)
    private String action;

    @Column(name = "ALIAS_NAME", length = 2000)
    private String aliasName;

    @Column(name = "ICON", length = 40)
    private String icon;

    @Column(name = "PATH", length = 40)
    private String path;

    @Column(name = "CHILD", length = 40)
    private String child;

    @Column(name = "ORDERBY", length = 20)
    private String orderBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public String toString() {
		return "UserActionLinks [id=" + id + ", pid=" + pid + ", name=" + name + ", checked=" + checked + ", open="
				+ open + ", title=" + title + ", action=" + action + ", aliasName=" + aliasName + ", icon=" + icon
				+ ", path=" + path + ", child=" + child + ", orderBy=" + orderBy + "]";
	}

}
