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
public class ChannelCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_MAP_CHANNEL_CATEGORY_SEQ")
	@SequenceGenerator(name="TAB_MAP_CHANNEL_CATEGORY_SEQ", sequenceName="TAB_MAP_CHANNEL_CATEGORY_SEQ", allocationSize=1)
	@NotEmpty
	
	@NotNull
	private Integer map_Id;
	@NotNull
	private Integer channelId;
	@NotEmpty
	private String channelName;
	@NotNull
	private Integer categoryId;
	@NotEmpty
	private String categoryName;
	
	public ChannelCategory() {
		super();
	}

	public ChannelCategory(Integer map_Id, Integer channelId,
			String channelName, Integer categoryId, String categoryName) {
		super();
		this.map_Id = map_Id;
		this.channelId = channelId;
		this.channelName = channelName;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Integer getMap_Id() {
		return map_Id;
	}

	public void setMap_Id(Integer map_Id) {
		this.map_Id = map_Id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	@Override
	public String toString() {
		return "ChannelCategory [map_Id=" + map_Id + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", categoryId=" + categoryId
				+ ", categoryName=" + categoryName + "]";
	}
}
