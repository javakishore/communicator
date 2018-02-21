package com.company.crm.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.crm.dao.MessageStatusDao;
import com.company.crm.model.Category;
import com.company.crm.model.MessageStatus;

@Component
public class MessageStatusDaoImpl implements MessageStatusDao{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	public void addCategory(MessageStatus category) {
		this.sessionFactory.getCurrentSession().save(category);
		LOGGER.debug("Category saved seccuessfully");
	}

	@SuppressWarnings("unchecked")
	public List<MessageStatus> getCategoryList() {
		List<MessageStatus> categoryList = this.sessionFactory.getCurrentSession().createCriteria(Category.class).list();
		return categoryList;
	}

	public void updateCategory(int id, Category category) {
		 Session session = this.sessionFactory.getCurrentSession();
	     session.update(category);
	     LOGGER.debug("Category updated seccuessfully");
	}

	public void deleteCategory(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Category category = (Category) session.load(Category.class, new Integer(id));
        if(null != category){
            session.delete(category);
        }
        LOGGER.debug("Category deleted seccuessfully");
	}

	@SuppressWarnings("unchecked")
	public List<MessageStatus> getSortedCategoryList(String sort, String order) {
		Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(Category.class);
		if("asc".equals(order)) {
			cr.addOrder(Order.asc(sort));
		} else {
			cr.addOrder(Order.desc(sort));
		}
		return (List<MessageStatus>)cr.list();
	}

	@Override
	public void updateCategory(int id, MessageStatus category) {
		// TODO Auto-generated method stub
		
	}

}
