package com.company.crm.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.crm.dao.CategoryDao;
import com.company.crm.model.Category;
import com.company.crm.model.Channel;

@Component
public class CategoryDaoImpl implements CategoryDao{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	public void addCategory(Category category) {
		this.sessionFactory.getCurrentSession().save(category);
		LOGGER.debug("Category saved seccuessfully");
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategoryList() {
		List<Category> categoryList = this.sessionFactory.getCurrentSession().createCriteria(Category.class).list();
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
	public List<Category> getSortedCategoryList(String sort, String order) {
		Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(Category.class);
		if("asc".equals(order)) {
			cr.addOrder(Order.asc(sort));
		} else {
			cr.addOrder(Order.desc(sort));
		}
		return (List<Category>)cr.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Channel> getChannelList() {
		
			List<Channel> channelList = this.sessionFactory.getCurrentSession()
					.createCriteria(Channel.class).list();
			return channelList;
	
	}

	
	
	public void insertCategory(int id, Category category) {
		 Session session = this.sessionFactory.getCurrentSession();
		 session.update(category);
		 LOGGER.debug("Category updated seccuessfully");
		
	}

	public void addChannel(Channel channel) {
		
		System.out.println("Inside the Dao layer");
		 Session session = this.sessionFactory.openSession();
		 
		 
		 Transaction tx=null;
		 try
		 {
		
			 tx=session.beginTransaction();
			 

			
			 
			 System.err.println("session"+session);
			 System.out.println(channel.getChannelName());
			 System.out.println(channel.getIsapproved());
		    Integer id= (Integer)session.save(channel);
		     
		     System.out.println("After the save "+id);
		     
		 tx.commit();
		 
		 }
		 catch(Exception e)
		 {
			 if(tx!=null)
			 {
				 tx.rollback();
			 }
			 
		 }
	     
	     LOGGER.debug("Channel saved seccuessfully");
		
	}

	public void updateChannel(Channel channel) {
		 Session session = this.sessionFactory.getCurrentSession();
	     session.update(channel);
	     LOGGER.debug("Channel updated seccuessfully");
		
	}

	public void updateCategory(Category category) {
		 Session session = this.sessionFactory.getCurrentSession();
	     session.update(category);
	     LOGGER.debug("Category updated seccuessfully");
		
	}


	

	@SuppressWarnings("unchecked")
	public List<Channel> listapproveChannel() {
		 
		Criteria cr = this.sessionFactory.getCurrentSession().createCriteria(Channel.class);
		cr.add(Restrictions.eq("isapproved", "N"));
		List<Channel> channelList = cr.list();
			
		return channelList;
	
	}


}
