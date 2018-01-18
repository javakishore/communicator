package com.company.crm.model;

public class LoginUser {
	private String userid;
	private String username;
	private String status;
	private String logindate;
	private String location;

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogindate() {
		return logindate;
	}

	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}

	public LoginUser() {
		// TODO Auto-generated constructor stub
	}
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LoginUser(String userid, String username,String status, String logindate,String location) {
		this.userid=userid;
		this.username=username;
		this.status=status;
		this.logindate=logindate;
		this.location=location;
		// TODO Auto-generated constructor stub
	}

}
