package com.company.crm.service.impl;

import java.util.List;

import com.company.crm.model.Category;
import com.company.crm.service.CategoryService;

public class MsgServiceImpl {

	private CategoryService categoryService;
	
	public List<Category> getCategoryList() {
		return categoryService.getCategoryList();
	}
}
