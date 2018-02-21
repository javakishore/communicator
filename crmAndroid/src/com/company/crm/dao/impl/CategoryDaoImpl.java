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

import com.company.crm.dao.CategoryDao;
import com.company.crm.model.Category;

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

}
