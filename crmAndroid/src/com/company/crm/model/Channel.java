package com.company.crm.model;

public class Channel {
	private Integer channelId;
	private String channelName;
	
	public Channel(int channelId, String channelName) {
		this.channelId=channelId;
		this.channelName=channelName;
	}
	public Integer getchannelId() {
		return channelId;
	}
	public void setCHANNEL_ID(Integer channelId) {
		this.channelId = channelId;
	}
	public String getCHANNEL_NAME() {
		return channelName;
	}
	public void setCHANNEL_NAME(String channelName) {
		this.channelName = channelName;
	}
}
