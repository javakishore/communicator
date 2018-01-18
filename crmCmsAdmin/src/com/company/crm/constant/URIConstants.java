package com.company.crm.constant;

public class URIConstants {
	public static final String GET_VERSION = "/getversion";
	public static final String GET_ALL_PROJECTS = "/restprojectlist";
	public static final String GET_ALL_CATEGORIES="/restchannellist";
	public static final String GET_ALL_CHANNELS="/restchennellist";
	public static final String DELETE_MESSAGE="/deletemessages/{id}";

	public static final String MY_MESSAGES="/mymessages";
	public static final String MY_CATEGORY="/myCategory";
	public static final String MY_CHANNEL="/myChannel";
	public static final String MY_CHANNEL_CATEGORY="/channelCategoryMapping";
	
//	Added By Tushar For Message Status
	public static final String MESSAGES_STATUS="/messagesStatus";
	
//	Added By Sachin For Set Category	
	public static final String SET_CATEGORY="/setCategory";
	
	public static final String LOGIN_USER="/loginDetails";
	public static final String PENDING_MESSAGES="/pendingmessages";
	public static final String APPROVE_MESSAGES="/approvemessages/{id}";
	public static final String ADD_MESSAGES="/addmessage";

	public static final String SAVE_CATEGORY="/savecategory";
	public static final String SAVE_CHANNEL="/savechannel";
	public static final String SAVE_CHANNEL_CATEGORY="/savechannelcategory";
	public static final String GET_READ_MESSAGES="/readmessages";

	public static final String LIKE_MESSAGES="/likemessage";
	public static final String FAVOURITE_MESSAGES="/favouritemessage";
	public static final String FAVOURITE_MESSAGES_COUNT="/favouritemessagecount";
	public static final String PENDING_CATEGORY = "/pendingcategory";
	public static final String PENDING_CHANNEL = "/pendingchannel";

}
