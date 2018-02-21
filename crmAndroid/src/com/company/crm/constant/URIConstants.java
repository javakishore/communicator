package com.company.crm.constant;

public class URIConstants {
	public static final String GET_VERSION = "/getversion";
	public static final String GET_ALL_PROJECTS = "/restprojectlist";
	public static final String GET_ALL_CATEGORIES="/restcategorylist";
	public static final String DELETE_MESSAGE="/deletemessages/{id}";

	public static final String MY_MESSAGES="/mymessages";
//	Added By Tushar For Message Status
	public static final String MESSAGES_STATUS="/messagesStatus";
	public static final String PENDING_MESSAGES="/pendingmessages";
	public static final String APPROVE_MESSAGES="/approvemessages/{id}";
	public static final String ADD_MESSAGES="/addmessage";

	public static final String GET_READ_MESSAGES="/readmessages";

	public static final String LIKE_MESSAGES="/likemessage";
	public static final String FAVOURITE_MESSAGES="/favouritemessage";
	public static final String FAVOURITE_MESSAGES_COUNT="/favouritemessagecount";

}
