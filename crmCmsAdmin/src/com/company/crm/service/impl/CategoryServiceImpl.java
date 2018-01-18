package com.company.crm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.crm.dao.CategoryDao;
import com.company.crm.model.Category;
import com.company.crm.model.Channel;
import com.company.crm.service.CategoryService;

@Component
public class CategoryServiceImpl implements CategoryService{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Autowired
	private CategoryDao categoryDao;
	
	@Transactional
	public void addCategory(Category category) {
		this.categoryDao.addCategory(category);
	}
	@Transactional
	public List<Category> getCategoryList() {
		List<Category> categoryList = this.categoryDao.getCategoryList();
		return categoryList;
	}
	@Transactional
	public void updateCategory(int id, Category category) {
		this.categoryDao.updateCategory(id, category);
		
	}
	@Transactional
	public void deleteCategory(int id) {
		this.categoryDao.deleteCategory(id);
	}
	@Transactional
	public List<Category> getSortedCategoryList(String sort, String order) {
		return this.categoryDao.getSortedCategoryList(sort, order);
	}
	//@Override
	public List<Category> getAllCategory() {
		
		return null;
	}
	
	public void setCategory(Category category) {
		
		
	}
	/*public CategoryService getCategoryList1() {
		
		return null;
	}*/
	public List getsetCategoryList() {
		
		return null;
	}
	
	
	@Transactional
	public List<Channel> getChannelList() {
		List<Channel> channelList = this.categoryDao.getChannelList();
		return channelList;
	}
	
	/*public List<Channel> getChannelList() {
		List<Channel> channelList = this.categoryDao.getChannelList();
		return channelList;
	}*/
	public List<Channel> getSortedChannelList(String sort, String order) {
		
		return null;
	}
	
	public void insertCategory(int id, Category category) {
		this.categoryDao.insertCategory(id, category);
		
	}
	public void channel(Channel channel) {
		
		
	}
	@Transactional
	public void addChannel(Channel channel) {
		this.categoryDao.addChannel(channel);
		
	}
	public void updateChannel(Channel channel) {
		this.categoryDao.updateChannel(channel);
		
	}
	public void updateCategory(Category category) {
		this.categoryDao.updateCategory(category);
		
	}
	
	public void addchannel(Channel channel) {
		this.categoryDao.addChannel(channel);
		
	}
	@Transactional
	public List<Channel> listapproveChannel() {
		return categoryDao.listapproveChannel();
	}

}
