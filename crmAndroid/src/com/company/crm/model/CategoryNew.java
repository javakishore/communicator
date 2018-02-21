package com.company.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;



	@Entity
	@Table(name="category_message")
	public class CategoryNew {
		@Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_MESSAGE_SEQ")
		@SequenceGenerator(name="TAB_MESSAGE_SEQ", sequenceName="TAB_MESSAGE_SEQ", allocationSize=1)
			
		public String categoryName; 
		@Column
		private String creationDate;
		
		public String categoryId;
		
		
		public CategoryNew() {
			// TODO Auto-generated constructor stub
			super();
		}

		public CategoryNew(String categoryId,String categoryName) {
			// TODO Auto-generated constructor stub
			this.categoryId=categoryId;
			this.categoryName=categoryName;
		}
		
	}
