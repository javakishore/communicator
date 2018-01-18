package com.company.crm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.crm.dao.LoginDao;
import com.company.crm.model.Login;
import com.company.crm.model.User;
import com.company.crm.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private LoginDao loginDao;
	@Transactional
	public User checkUserAuthentication(Login login) {
		return loginDao.checkUserAuthentication(login);
	}
	@Transactional
	public List<User> getUserList() {
		return loginDao.getUserList();
	}

}
