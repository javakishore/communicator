package com.company.crm.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.crm.dao.MessageDao;
import com.company.crm.model.Message;

@Repository
public class MessageDaoImpl implements MessageDao{
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;	
	
	public void addMessage(Message message) {
		this.sessionFactory.getCurrentSession().save(message);
		
	}
	//Sudd Starts Here
	public void addMessageFileAndroid(Message message) {
		this.sessionFactory.getCurrentSession().save(message);
		
	}
	/*Sudd Ends Here*/

	@SuppressWarnings("unchecked")
	public List<Message> getMessageList() {
		List<Message> messageList = (List<Message>)this.sessionFactory.getCurrentSession().createCriteria(Message.class).list();
		return messageList;
	}

	@SuppressWarnings("unchecked")
	public List<Message> getMessageListById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Message.class);
		cr.add(Restrictions.eq("category.categoryId", id));
		cr.addOrder(Order.asc("messageName"));
		List<Message> list = cr.list();
		return list;
		
	}

}
