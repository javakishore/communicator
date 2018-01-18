package com.company.crm.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.crm.dao.LoginDao;
import com.company.crm.model.Login;
import com.company.crm.model.User;

@Repository
public class LoginDaoImpl implements LoginDao{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	public User checkUserAuthentication(Login login) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("userName", login.getUserName()));
		cr.add(Restrictions.eq("password", login.getPassword()));
		
		List<User> list = cr.list();
		
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		List<User> list = (List<User>)cr.list();
		return list;
	}

}
