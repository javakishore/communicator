package com.company.crm.service;

import java.util.List;

import com.company.crm.model.Category;
import com.company.crm.model.Channel;

public interface CategoryService {
	
	/*public void addCategory(Category category);
	public List<Category> getCategoryList();
	public void updateCategory(int id, Category category);
	public void deleteCategory(int id);
	public List<Category> getSortedCategoryList(String sort, String order);
//	Added By Sudd
	public List<Category>  getAllCategory();
*/
	
	public void addCategory(Category category);
	public List<Category> getCategoryList();
	public void updateCategory(int id, Category category);
	public void deleteCategory(int id);
	public List<Category> getSortedCategoryList(String sort, String order);
	public List<Channel> getSortedChannelList(String sort, String order);
//	Added By Sudd
	public List<Category>  getAllCategory();
	public void setCategory(Category category);
	public List<Channel> getChannelList();
	public List getsetCategoryList();
	
	public List<Channel> listapproveChannel();
	
}
