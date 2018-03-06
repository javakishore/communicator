package com.company.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CATEGORY_SEQ")
	@SequenceGenerator(name="TAB_CATEGORY_SEQ", sequenceName="TAB_CATEGORY_SEQ", allocationSize=1)
	@NotEmpty
	@NotNull
	private Integer categoryId;
	@NotEmpty
	private String categoryName;
	public String strCategoryId;
	public String noOfMsgPerCat;
	public String categoryImage;
	public String noOfUnreadMsg;
	private String isapproved;
	public String cateStatus;
	public String category_name_bhasa;
	public String category_image;
	public Integer action;
	public String userName;
	
	
	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getCategory_image() {
		return category_image;
	}

	public void setCategory_image(String category_image) {
		this.category_image = category_image;
	}

	public String getCategory_name_bhasa() {
		return category_name_bhasa;
	}

	public void setCategory_name_bhasa(String category_name_bhasa) {
		this.category_name_bhasa = category_name_bhasa;
	}

	public String getIsapproved() {
		return isapproved;
	}

	public void setIsapproved(String isapproved) {
		this.isapproved = isapproved;
	}
	
	public Category() {
		
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
	
	public Category(Integer categoryId, String categoryName,String isapproved) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.isapproved = isapproved;
	}
	
	public Category(Integer categoryId, String categoryName,String isapproved,String category_name_bhasa,String category_image) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.isapproved = isapproved;
		this.category_name_bhasa = category_name_bhasa;
		this.category_image = category_image;
		
	}

	public Category(Integer categoryId, String categoryName,String isapproved,String category_name_bhasa,String category_image,Integer action) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.isapproved = isapproved;
		this.category_name_bhasa = category_name_bhasa;
		this.category_image = category_image;
		this.action=action;
		
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

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}
	
	public String getStrCategoryId() {
		return strCategoryId;
	}

	public void setStrCategoryId(String strCategoryId) {
		this.strCategoryId = strCategoryId;
	}

	public String getNoOfMsgPerCat() {
		return noOfMsgPerCat;
	}

	public void setNoOfMsgPerCat(String noOfMsgPerCat) {
		this.noOfMsgPerCat = noOfMsgPerCat;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public String getNoOfUnreadMsg() {
		return noOfUnreadMsg;
	}

	public void setNoOfUnreadMsg(String noOfUnreadMsg) {
		this.noOfUnreadMsg = noOfUnreadMsg;
	}

	public String getCateStatus() {
		return cateStatus;
	}

	public void setCateStatus(String cateStatus) {
		this.cateStatus = cateStatus;
	}

	@Override
	public String toString() {
		return "Channel [categoryId=" + categoryId + ", categoryName="
				+ categoryName + "]";
	}
	
	public int getId() {
		
		return 0;
	}

}
