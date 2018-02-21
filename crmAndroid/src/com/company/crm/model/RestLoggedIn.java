package com.company.crm.model;

public class RestLoggedIn {

	public RestLoggedIn() {
		
	}
	private String userid;
	
	private String username;
	private String location;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public RestLoggedIn(String userid, String username, String location) {
		super();
		this.userid = userid;
		this.username = username;
		this.location = location;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
