package com.company.crm.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.crm.dao.RegisterDao;
import com.company.crm.model.User;

@Repository
public class RegisterDaoImpl implements RegisterDao{
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterDaoImpl.class);
	@Autowired
	private  SessionFactory sessionFactory;
	
	public void addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(user);
		LOGGER.debug("Registration is successfull.");
		
	}
	
	

}
