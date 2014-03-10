package cn.smarthome.sap.model;

import java.util.Date;

import cn.smarthome.sap.db.AbstractBaseModel;

public class UserInfo extends AbstractBaseModel {

	private static final long serialVersionUID = -7079062473198239915L;
	private String id;
	private int userID;  // 用户ID
	private String loginUserID; //登录用户名
	private String password;
	private int keepPassword; //是否保存密码（0：否，1：是）
	private int autoLogin;  //是否自动登录（0：否，1：是）
	private Date createTime = new Date();
	private Date updateTime = new Date();

	public UserInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLoginUserID() {
		return loginUserID;
	}

	public void setLoginUserID(String loginUserID) {
		this.loginUserID = loginUserID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getKeepPassword() {
		return keepPassword;
	}

	public void setKeepPassword(int keepPassword) {
		this.keepPassword = keepPassword;
	}

	public int getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(int autoLogin) {
		this.autoLogin = autoLogin;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
