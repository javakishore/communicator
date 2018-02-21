package com.company.crm.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
//@Table(name="category_message")
@Table
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_MESSAGE_SEQ")
	@SequenceGenerator(name="TAB_MESSAGE_SEQ", sequenceName="TAB_MESSAGE_SEQ", allocationSize=1)
	private Integer messageId;	
	private String messageName; 
	@Column
	private String creationDate;
	@OneToOne
	@JoinColumn(name="categoryId")
	private Category category;


	//
	public String msgDetailID = "";
	public String msgID = "";
	public String msgData = "";
	public String msgTitle = "";
	public String msgLink = "";
	public String msgStatus;
	public String effectiveDate;
	public String expiryDate;
	public String category_Id;
	public String userName;
	public String msgLink2;
	public String categoryName;
	public String timestamp;
	public String readReceipt;
	public String noOfLikes;
	public String isLiked;
	public String isEdit;
	public String isDelete;
	public String favoriteid;
	public String region;
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getFavoriteid() {
		return favoriteid;
	}
	public void setFavoriteid(String favoriteid) {
		this.favoriteid = favoriteid;
	}
	public String getLikeid() {
		return likeid;
	}
	public void setLikeid(String likeid) {
		this.likeid = likeid;
	}
	public String likeid;
	public ArrayList<String> zone;
	public ArrayList<String> channel;
		public Message() {
		super();
	}
	public Message(Integer messageId, String messageName, String creationDate,
			Category category) {
		super();
		this.messageId = messageId;
		this.messageName = messageName;
		this.creationDate = creationDate;
		this.category = category;
	}
	public Message(String msgID, String msgTitle, String userName)
	{
		this.msgID=msgID;
		this.msgTitle=msgTitle;
		this.userName=userName;
	}
	//read msgs
	public Message(String MsgID,String categoryName,String msgTitle,String userName,String timestamp)
	{
		this.msgID=MsgID;
		this.categoryName=categoryName;
		this.msgTitle=msgTitle;
		this.userName=userName;
		this.timestamp=timestamp;
	}

	//my messages
	public Message(String MsgID,String favoriteid) {
		super();
		this.msgID=MsgID;
//		this.msgTitle=msgTitle;
		this.favoriteid=favoriteid;
		

	}
	//msg Status
	public Message(String MsgID,String msgData,String msgTitle,String MsgLink,String msgStatus,String MsgLink2,String favoriteid) {
		super();
		this.msgID=MsgID;
		this.msgData=msgData;
		this.msgTitle=msgTitle;
		this.msgLink=MsgLink;
		this.msgStatus=msgStatus;
		this.msgLink2=MsgLink2;
		this.favoriteid=favoriteid;
		

	}
	//msg details
	public Message(String MsgID,String msgData,String msgTitle,String MsgLink,String MsgLink2,String noOfLikes,String msgStatus,String isLiked,String creationDate,String categoryName) {
		super();
		this.msgID=MsgID;
		this.msgData=msgData;
		this.msgTitle=msgTitle;
		this.msgLink=MsgLink;
		this.msgLink2=MsgLink2;
		this.noOfLikes=noOfLikes;
		this.msgStatus=msgStatus;
		this.isLiked=isLiked;
		this.creationDate=creationDate;

		this.categoryName=categoryName;
	}
	//get Fav,searched,time period,latest msg,pending msgs
	
	//Get Search Messages
	
	public Message(String MsgID,String msgData,String msgTitle,String MsgLink,String MsgLink2,String noOfLikes,String msgStatus,String isLiked) {
		super();
		this.msgID=MsgID;
		this.msgData=msgData;
		this.msgTitle=msgTitle;
		this.msgLink=MsgLink;
		this.msgLink2=MsgLink2;
		this.noOfLikes=noOfLikes;
		this.msgStatus=msgStatus;
		this.isLiked=isLiked;

	}
	//get all msgs
	public Message(String category_Id ,String MsgID,String msgData,String msgTitle,String MsgLink,String msgStatus,String MsgLink2,String categoryName,String read_receipt,String noOfLikes,String isLiked,String creationDate) {
		super();
		this.category_Id=category_Id;
		this.msgID=MsgID;
		this.msgData=msgData;
		this.msgTitle=msgTitle;
		this.msgLink=MsgLink;
		this.msgStatus=msgStatus;
		this.msgLink2=MsgLink2;
		this.categoryName=categoryName;
		this.readReceipt=read_receipt;
		this.noOfLikes=noOfLikes;
		this.isLiked=isLiked;
		this.creationDate=creationDate;
	}

	//get edited and next msgs
	public Message(String categoryId,String MsgID,String isDelete,String isEdit,String msgData,String msgTitle,String MsgLink,String msgStatus,String MsgLink2,String categoryName,String read_receipt,String noOfLikes,String isLiked,String creationDate) {
		super();
		this.category_Id=categoryId;
		this.msgID=MsgID;
		this.isDelete=isDelete;
		this.isEdit=isEdit;
		this.msgData=msgData;
		this.msgTitle=msgTitle;
		this.msgLink=MsgLink;
		this.msgStatus=msgStatus;
		this.msgLink2=MsgLink2;
		this.categoryName=categoryName;
		this.readReceipt=read_receipt;
		this.noOfLikes=noOfLikes;
		this.isLiked=isLiked;
		this.creationDate=creationDate;
	}

	public Message(String categoryId,String MsgID,String msgData,String msgTitle,String MsgLink,String MsgLink2,String categoryName,String noOfLikes,String message_status,String isLiked,String creationDate) {
		super();
		this.category_Id=categoryId;
		this.msgID=MsgID;
		//this.isEdit=isEdit;
		this.msgData=msgData;
		this.msgTitle=msgTitle;
		this.msgLink=MsgLink;
		this.msgStatus=message_status;
		this.msgLink2=MsgLink2;
		this.categoryName=categoryName;
		//this.readReceipt=read_receipt;
		this.noOfLikes=noOfLikes;
		this.isLiked=isLiked;
		this.creationDate=creationDate;
	}
	//edit msgs
	/*public Message(String MsgDetailID ,String MsgID,String msgData,String MsgTitle,
			String MsgLink,String msgLink2,String effectiveDate,String expiryDate,String category_Id) {
		super();
		this.msgDetailID=MsgDetailID;
		this.msgID=MsgID;
		this.msgData=msgData;
		this.msgTitle=MsgTitle;
		this.msgLink=MsgLink;
		this.msgLink2=msgLink2;
		this.effectiveDate=effectiveDate;
		this.expiryDate=expiryDate;
		this.category_Id=category_Id;*/
	public Message(String MsgDetailID ,String MsgID,String msgData,String MsgTitle,
			String MsgLink,String msgLink2,String effectiveDate,String expiryDate,String category_Id,ArrayList<String> zone, ArrayList<String> channel) {
		super();
		this.msgDetailID=MsgDetailID;
		this.msgID=MsgID;
		this.msgData=msgData;
		this.msgTitle=MsgTitle;
		this.msgLink=MsgLink;
		this.msgLink2=msgLink2;
		this.effectiveDate=effectiveDate;
		this.expiryDate=expiryDate;
		this.category_Id=category_Id;
		this.zone=zone;
		this.channel=channel;
	}


	public Message(String message_detail_Id, String message_id, String message_data, String message_title, String message_link, 
			String base64DataString, String created_date,String expiry_date,String categoryName)
	{
		super();
		this.msgDetailID=message_detail_Id;
		this.msgID=message_id;
		this.msgData=message_data;
		this.msgTitle=message_title;
		this.msgLink=message_link;
		this.msgLink2=base64DataString;
		this.effectiveDate=created_date;
		this.expiryDate=expiry_date;
		this.categoryName=categoryName;
	}
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String messageId) {
		this.msgID = messageId;
	}

	public String getMsgData() {
		return msgData;
	}

	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgLink(String msgLink) {
		this.msgLink = msgLink;
	}
	public String getMsgLink() {
		return msgLink;
	}

	public void setMsgLink2(String msgLink2) {
		this.msgLink2 = msgLink2;
	}
	public String getMsgLink2() {
		return msgLink2;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}


	/*public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}*/

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategory_Id() {
		return category_Id;
	}

	public void setCategory_Id(String category_Id) {
		this.category_Id = category_Id;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp=timestamp;
	}
	public String getTimestamp()
	{
		return this.timestamp;
	}
	public void setZone(ArrayList<String> zone)
	{
		this.zone=zone;
	}
	public void setChannel(ArrayList<String> channel)
	{
		this.channel=channel;
	}
	public ArrayList<String> getZone()
	{
		return this.zone;
	}
	public ArrayList<String> getChannel()
	{
		return this.channel;
	}
}
