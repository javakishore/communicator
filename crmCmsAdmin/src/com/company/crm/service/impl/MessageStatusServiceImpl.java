package com.company.crm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.crm.dao.MessageStatusDao;
import com.company.crm.model.Category;
import com.company.crm.model.MessageStatus;
import com.company.crm.service.MessageStatusService;

@Component
public class MessageStatusServiceImpl implements MessageStatusService{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Autowired
	private MessageStatusDao messageStatusDao;
	
	@Transactional
	public void addCategory(MessageStatus category) {
		this.messageStatusDao.addCategory(category);
	}
//	@Transactional
//	public List<MessageStatus> getCategoryList() {
//		List<MessageStatus> categoryList = this.messageStatusDao.getCategoryList();
//		return categoryList;
//	}
//	@Transactional
//	public void updateCategory(int id, Category category) {
//		this.messageStatusDao.updateCategory(id, category);
//		
//	}
	@Transactional
	public void deleteCategory(int id) {
		this.messageStatusDao.deleteCategory(id);
	}
//	@Transactional
//	public List<Category> getSortedCategoryList(String sort, String order) {
//		return this.messageStatusDao.getSortedCategoryList(sort, order);
//	}
	//@Override
	public void addCategory(Category category) {
		
		
	}
	//@Override
	public List<Category> getCategoryList() {
		
		return null;
	}
	//@Override
	public void updateCategory(int id, Category category) {
		
		
	}
	//@Override
	public List<Category> getSortedCategoryList(String sort, String order) {
		
		return null;
	}

}
