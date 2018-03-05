package com.company.crm.model;

public class RateDetails {
	private int userid;
	
	
	private String ratingtext;
	private int  ratingValue;
	
	public RateDetails() {
		
	}
	public RateDetails(int username, String rateText,int rateValue) {
		
		this.userid=userid;
		this.ratingtext=ratingtext;
		this.ratingValue=ratingValue;
		
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getRatingtext() {
		return ratingtext;
	}
	public void setRatingtext(String ratingtext) {
		this.ratingtext = ratingtext;
	}
	public int getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}
	
}
