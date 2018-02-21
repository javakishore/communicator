package com.company.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.crm.dao.RegisterDao;
import com.company.crm.model.User;
import com.company.crm.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService{
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServiceImpl.class);
	@Autowired
	private RegisterDao registerDao;
	
	@Transactional
	public void addUser(User user) {
		registerDao.addUser(user);
	}
		
}
