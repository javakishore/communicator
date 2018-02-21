package com.company.crm.service;

import java.util.List;

import com.company.crm.model.Category;
import com.company.crm.model.Message;

public interface MessageService {
	public List<Category> getCategoryList();
	public List<Message> getMessageList();
	public void addMessage(Message message);
	/*SUDD sTARTS HERE*/
	public void addMessageFileAndroid(Message message);
	/*SUDD ENDS HERE*/
	public List<Message> getMessageListById(int id);
}
