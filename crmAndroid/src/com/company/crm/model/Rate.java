package com.company.crm.model;

public class Rate {
	private String username;
	private String rateText;
	private String rateValue;
	private String timestamp;
	
	public Rate() {
		// TODO Auto-generated constructor stub
	}
	public Rate(String username, String rateText,String rateValue,String timestamp) {
		// TODO Auto-generated constructor stub
		this.username=username;
		this.rateText=rateText;
		this.rateValue=rateValue;
		this.timestamp=timestamp;
	}
	public void setUsername(String username)
	{
		this.username=username;
		
	}

	public void setRateText(String rateText)
	{
		this.rateText=rateText;
		
	}
	public void setRateValue()
	{
		this.rateValue=rateValue;
		
	}
	
	public String getUsername()
	{
		return this.username;
		
	}

	public String getRateText()
	{
		return this.rateText;
		
	}
	public String getRateValue()
	{
		return this.rateValue;
		
	}
	public void setTimestamp(String timestamp)
	{
		this.timestamp=timestamp;
	}
	
	public String getTimestamp()
	{
		return this.timestamp;
	}
	
}
