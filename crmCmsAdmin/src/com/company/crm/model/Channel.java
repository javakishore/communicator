package com.company.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
public class Channel {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CHANNEL_SEQ")
	@SequenceGenerator(name="TAB_CHANNEL_SEQ", sequenceName="TAB_CHANNEL_SEQ", allocationSize=1)
	@NotEmpty

	@NotNull
	private Integer channelId;
	
	
	@NotEmpty
	private String channelName;
	
	private String isapproved;

	private int action;
	public String userName;
	
	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}
	
	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}
	
	public String getIsapproved() {
		return isapproved;
	}

	public void setIsapproved(String isapproved) {
		this.isapproved = isapproved;
	}

	public Channel() {
		
	}
	
	public Channel( String channelName,int channelId){
		//super();
		this.channelName = channelName;
		this.channelName=channelName;
	}
	
	public Channel(int channelId, String channelName) {
		super();
		this.channelId=channelId;
		this.channelName=channelName;
	}
	public Integer getChannelId() {
		return this.channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Channel(Integer channelId, String channelName, String isapproved) {
		super();
		this.channelId = channelId;
		this.channelName = channelName;
		this.isapproved = isapproved;
	}
	
	public Channel(Integer channelId, String channelName, String isapproved, Integer action) {
		super();
		this.channelId = channelId;
		this.channelName = channelName;
		this.isapproved = isapproved;
		this.action = action;
	}
	

	public String getChannelName() {
		return this.channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	
	
	
	@Override
	public String toString() {
		return "Channel [channelId=" + channelId + ", channelName="
				+ channelName + "]";
	}

	public int getId() {
		
		return 0;
	}
	
	
}
