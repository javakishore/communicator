package com.company.crm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.crm.dao.MessageDao;
import com.company.crm.model.Category;
import com.company.crm.model.Message;
import com.company.crm.service.CategoryService;
import com.company.crm.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageDao messageDao;
	
	public List<Category> getCategoryList() {
		return categoryService.getCategoryList();
	}
	@Transactional
	public void addMessage(Message message) {
		messageDao.addMessage(message);
		
	}
	/*SUDD sTARTS HERE*/
	@Transactional
	public void addMessageFileAndroid(Message message) {
		messageDao.addMessage(message);
		
	}
	/*SUDD eNDS HERE*/
	@Transactional
	public List<Message> getMessageList() {
		return messageDao.getMessageList();
	}
	@Transactional
	public List<Message> getMessageListById(int id) {
		List<Message> messageList = messageDao.getMessageListById(id);
		return messageList;
	}
}
