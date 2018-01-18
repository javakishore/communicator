package com.company.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="user")
public class Login {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;	
	
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
	private String userType="";
	
	public Login(){}

	public Login(Integer userId, String userName, String password,String userType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.userType=userType;
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
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	

}
