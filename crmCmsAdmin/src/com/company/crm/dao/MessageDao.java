package com.company.crm.dao;

import java.util.List;

import com.company.crm.model.Message;

public interface MessageDao {	
	public void addMessage(Message module);
	public void addMessageFileAndroid(Message module);
	public List<Message> getMessageList();
	public List<Message> getMessageListById(int id);
}
