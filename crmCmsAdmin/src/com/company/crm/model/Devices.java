package com.company.crm.model;

public class Devices {

	public String token;
	public String badgeCount;

	public Devices() {
		
		
	}
	public Devices(String token,String badgeCount) {
		
		this.token=token;
		this.badgeCount=badgeCount;
	}

	public void setToken(String token)
	{
		this.token=token;
	}

	public void setBadgeCount(String badgeCount)
	{
		this.badgeCount=badgeCount;
	}

	public String getToken()
	{
		return this.token;
	}

	public String getBadgeCount()
	{
		return  this.badgeCount;
	}
}
