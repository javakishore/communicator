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
public class MessageStatus {
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
	public String read;
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getFavoriteid() {
		if(msgID.equals("89"))
		System.out.println(" inside messagestatus  class getFavoriteid " +this.favoriteid );
	
		return favoriteid;
	}
	public void setFavoriteid(String favoriteid) {
		//System.out.println(" inside messagestatus  class setFavoriteid " +this.favoriteid );
		
		this.favoriteid = favoriteid;
		if(msgID.equals("89"))
		System.out.println(" inside messagestatus  class setFavoriteid " +this.favoriteid );

	}
	public String getLikeid() {
		System.out.println(" inside messagestatus  class getlikeid likeid " +this.likeid );

		return likeid;
	}
	public void setLikeid(String likeid) {

		this.likeid = likeid;
		if(msgID.equals("89"))
		System.out.println(" inside messagestatus  class setlikeid likeid " +this.likeid );

	}
	public String likeid;
	public ArrayList<String> zone;
	public ArrayList<String> channel;
		public MessageStatus() {
		super();
	}
	public MessageStatus(Integer messageId, String messageName, String creationDate,
			Category category) {
		super();
		this.messageId = messageId;
		this.messageName = messageName;
		this.creationDate = creationDate;
		this.category = category;
	}
	
	//msg Status
	public MessageStatus(String MsgID,String msgTitle,String read,String likeid,String favoriteid) {
		super();
		this.msgID=MsgID;
		this.msgTitle=msgTitle;
		this.favoriteid=favoriteid;
		
		this.likeid=likeid;
		this.read=read;
		if(msgID.equals("89"))
		System.out.println(" inside messagestatus constructure  class msgID "+this.msgID+ " msgTitle " +this.msgTitle+ " favoriteid " +this.favoriteid+  " likeid " +this.likeid );

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
