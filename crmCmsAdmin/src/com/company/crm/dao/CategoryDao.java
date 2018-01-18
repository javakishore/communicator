package com.company.crm.dao;

import java.util.List;

import com.company.crm.model.Category;
import com.company.crm.model.Channel;

public interface CategoryDao {
	
	/*public void addCategory(Category category);
	public List<Category> getCategoryList();
	public void updateCategory(int id, Category category);
	public void deleteCategory(int id);
	public List<Category> getSortedCategoryList(String sort, String order);*/
	

	public void addCategory(Category category);
	public List<Category> getCategoryList();
	public void updateCategory(int id, Category category);
	public void deleteCategory(int id);
	public List<Category> getSortedCategoryList(String sort, String order);
	public List<Channel> getChannelList();
	public void insertCategory(int id, Category category);
	public void addChannel(Channel channel);
	public void updateChannel(Channel channel);
	public void updateCategory(Category category);
	public List<Channel> listapproveChannel();
	
}
