package com.company.crm.dao;

import java.util.List;

import com.company.crm.model.MessageStatus;

public interface MessageStatusDao {
	
	public void addCategory(MessageStatus category);
	public List<MessageStatus> getCategoryList();
	public void updateCategory(int id, MessageStatus category);
	public void deleteCategory(int id);
	public List<MessageStatus> getSortedCategoryList(String sort, String order);
	

}
