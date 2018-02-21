package com.company.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CATEGORY_SEQ")
	@SequenceGenerator(name="TAB_CATEGORY_SEQ", sequenceName="TAB_CATEGORY_SEQ", allocationSize=1)
	private Integer categoryId;
	@NotEmpty
	private String categoryName;
	
	public String strCategoryId;
	public String noOfMsgPerCat;
	public String categoryImage;
	public String noOfUnreadMsg;
	
	
	public Category() {
		// TODO Auto-generated constructor stub
	}
	
	public Category(String strCategoryId, String categoryName, String noOfMsgPerCat,String categoryImage,String noOfUnreadMsg,int categoryId){
		//super();
		this.strCategoryId = strCategoryId;
		this.categoryName = categoryName;
		this.noOfMsgPerCat=noOfMsgPerCat;
		this.categoryImage=categoryImage;
		this.noOfUnreadMsg=noOfUnreadMsg;
		this.categoryId=categoryId;
	}

	public Category(Integer categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}
