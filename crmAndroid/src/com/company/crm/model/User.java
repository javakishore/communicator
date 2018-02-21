package com.company.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="user_m") 
public class User {  
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_USER_SEQ")
	@SequenceGenerator(name="TAB_USER_SEQ", sequenceName="TAB_USER_SEQ", allocationSize=1)
	private Integer userId;	 
	private String userName;
	private String password;
	@Transient
	private String confirmPassword;
	private String userType="";
	private String userLanguage="";
	
	public User(){}

	public User(Integer userId, String userName, String password,
			String confirmPassword, String userType,String userLanguage) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.userType = userType;
		this.userLanguage=userLanguage;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

}
