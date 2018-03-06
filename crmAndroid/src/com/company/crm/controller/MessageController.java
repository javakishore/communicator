package com.company.crm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.CryptorException;
import org.cryptonode.jncryptor.InvalidHMACException;
import org.cryptonode.jncryptor.JNCryptor;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Encoder;

import com.company.crm.constant.URIConstants;
import com.company.crm.model.Category;
import com.company.crm.model.Channel;
import com.company.crm.model.Devices;
import com.company.crm.model.Message;
import com.company.crm.model.MessageStatus;
import com.company.crm.model.RestLoggedIn;
import com.company.crm.model.User;
import com.company.crm.model.Version;
import com.company.crm.model.Zone;
import com.company.crm.service.MessageService;
import com.company.crm.util.DBConnection;
import com.company.crm.validation.MessageValidator;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.EnhancedApnsNotification;
import com.notnoop.exceptions.NetworkIOException;
//import java.util.Base64;
//import com.google.android.gcm.server.Message;

@Controller
/*
 * @MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
 * maxFileSize=1024*1024*10, // 10MB maxRequestSize=1024*1024*50)
 */
public class MessageController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageController.class);
	private static final String UploadFolder = "/webapps/crm/resources/android/";
	private static final String UploadFolderAndroid = "/webapps/crmAndroid/resources/android/";
	private static final String EditFolder = "/webapps/crmAndroid/resources/EditImage/";
	private static final String cerFolder = "/webapps/crmAndroid/resources/Apple/";
	
	@Autowired
	private MessageService messageService;
	private SessionFactory sessionFactory;
	public String testresult = "";

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new MessageValidator());

	}

	@RequestMapping(value = URIConstants.ADD_MESSAGES, method = RequestMethod.GET)
	public String message(Model model, HttpSession session) {

		List<Category> categoryList = messageService.getCategoryList();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("message", new Message());
		List<Zone> zoneList = new ArrayList<Zone>();
		Connection conn = null;
		
		// get Zones
		try {
			conn = DBConnection.getConnection();

			String sql = "select * from zone";

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				zoneList.add(new Zone(resultSet.getInt("ZONE_ID"), resultSet
						.getString("ZONE_NAME")));
			}

			model.addAttribute("zoneList", zoneList);
			model.addAttribute("zone", new Zone());
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageName", "name");
		return "addmessage";
	}

	@RequestMapping(value = URIConstants.LIKE_MESSAGES, method = RequestMethod.GET)
	public String likeMessages(HttpServletResponse response, Model model,
			HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();
		List<Category> categoryList = messageService.getCategoryList();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");

			// String sql="SELECT * FROM message_details where
			// MESSAGE_LANG="+Integer.parseInt(user.getUserLanguage());
			// String sql =
			// "SELECT a.* FROM message_details a, message b where a.message_id = b.message_id and a.MESSAGE_LANG=1 and  b.username ="+String.valueOf(user.getUserId());
			String sql = "SELECT a.*, c.USERNAME FROM message_details a, message_like b,user_m c where a.message_id  = b.message_id and c.USERID = b.USER_ID and a.MESSAGE_LANG=1";

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

				String enc1 = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String msgData = resultSet.getString("MESSAGE_DATA");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String msgLink = resultSet.getString("MESSAGE_LINK");
				String msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");

				String userName = String.valueOf(resultSet
						.getString("USERNAME"));

				System.out.println("user name>>>>>>>>" + userName);
				// System.out.println("count value is"+resultSet.getInt("TOTAL"));

				messageList.add(new Message(msgID, msgTitle, userName));

			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", new Category());
		// model.addAttribute("message", new Message());
		return "likemessage";

	}

	// Added By Sudd
	@RequestMapping(value = "/likeCountMessage/{id}", method = RequestMethod.GET)
	public String likeCountMessage(@PathVariable("id") int id,
			HttpServletResponse response, Model model, HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();
		List<Category> categoryList = messageService.getCategoryList();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		System.out.println("inside the likeCountMessage  xxxx ");
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");

			// String sql="SELECT * FROM message_details where
			// MESSAGE_LANG="+Integer.parseInt(user.getUserLanguage());
			// String sql =
			// "SELECT a.* FROM message_details a, message b where a.message_id = b.message_id and a.MESSAGE_LANG=1 and  b.username ="+String.valueOf(user.getUserId());
			String sql = "SELECT a.*, c.USERNAME FROM message_details a, message_like b,user_m c where a.message_id  = b.message_id and c.USERID = b.USER_ID and a.MESSAGE_LANG=1 and a.message_id="
					+ id;

			/*String sql = "SELECT a.*, c.USERNAME FROM message_details a, MESSAGE_LIKE b,user_m c where b.message_id  = a.message_id and c.USERID = b.USER_ID and a.MESSAGE_LANG=1 and a.message_id="
					+ id;*/
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

		System.out.println("admin likeCountMessage 0");
				
				String enc1 = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				System.out.println("admin likeCountMessage 1 ");
							
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				System.out.println("admin likeCountMessage 2 ");
				
				String msgData = resultSet.getString("MESSAGE_DATA");
				System.out.println("admin likeCountMessage 3");
				
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				System.out.println("admin likeCountMessage 4");
				
				String msgLink = resultSet.getString("MESSAGE_LINK");
				System.out.println("admin likeCountMessage 5");
				
				String msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");
				System.out.println("admin likeCountMessage 6");
				
				String userName = String.valueOf(resultSet
						.getString("USERNAME"));
				System.out.println("admin likeCountMessage 7 ");
				
				

				messageList.add(new Message(msgID, msgTitle, userName));

			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", new Category());
		// model.addAttribute("message", new Message());
		return "likemessage";

	}

	// Added By Sudd

	@RequestMapping(value = URIConstants.FAVOURITE_MESSAGES, method = RequestMethod.GET)
	public String favouriteMessages(HttpServletResponse response, Model model,
			HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();
		List<Category> categoryList = messageService.getCategoryList();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");

			// String sql="SELECT * FROM message_details where
			// MESSAGE_LANG="+Integer.parseInt(user.getUserLanguage());
			// String sql =
			// "SELECT a.* FROM message_details a, message b where a.message_id = b.message_id and a.MESSAGE_LANG=1 and  b.username ="+String.valueOf(user.getUserId());
			String sql = "SELECT a.*, c.USERNAME FROM message_details a, favourite b,user_m c where b.message_id  = a.message_id and c.USERID = b.USER_ID and a.MESSAGE_LANG=1";

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

				String enc1 = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String msgData = resultSet.getString("MESSAGE_DATA");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String msgLink = resultSet.getString("MESSAGE_LINK");
				String msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");

				String userName = String.valueOf(resultSet
						.getString("USERNAME"));

				System.out.println("user name>>>>>>>>" + userName);

				messageList.add(new Message(msgID, msgTitle, userName));

			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", new Category());
		// model.addAttribute("message", new Message());
		return "favouritemessage";

	}

	// Addedd By Sudd
	@RequestMapping(value = "/favoriteCountMessage/{id}", method = RequestMethod.GET)
	public String favouriteMessagesCount(@PathVariable("id") int id,
			HttpServletResponse response, Model model, HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();
		List<Category> categoryList = messageService.getCategoryList();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		System.out.println("inside the favoriteCountMessage");
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");

			// String sql="SELECT * FROM message_details where
			// MESSAGE_LANG="+Integer.parseInt(user.getUserLanguage());
			// String sql =
			// "SELECT a.* FROM message_details a, message b where a.message_id = b.message_id and a.MESSAGE_LANG=1 and  b.username ="+String.valueOf(user.getUserId());
			/*String sql = "SELECT a.*, c.USERNAME FROM message_details a, favourite b,user_m c where b.message_id  = a.message_id and c.USERID = b.USER_ID and a.MESSAGE_LANG=1 and a.message_id="
					+ id;
*/
			String sql="SELECT a.*, c.USERNAME FROM message_details a, favourite b,user_m c where a.message_id  = b.message_id and c.USERID = b.USER_ID and a.MESSAGE_LANG=1 and a.message_id="
					+ id;
			System.out.println("inside android favoriteCountMessage >>>>>>> sql "+sql);
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				System.out.println("while method of count");

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

				String enc1 = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String msgData = resultSet.getString("MESSAGE_DATA");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String msgLink = resultSet.getString("MESSAGE_LINK");
				String msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");

				String userName = String.valueOf(resultSet
						.getString("USERNAME"));

				System.out.println("user name>>>>>>>>" + userName);

				messageList.add(new Message(msgID, msgTitle, userName));

			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", new Category());
		// model.addAttribute("message", new Message());
		return "favoriteCountMessage";

	}

	// Added By Sudd

	@RequestMapping(value = URIConstants.GET_VERSION, method = RequestMethod.GET)
	public String releaseVersion(HttpServletResponse response, Model model,
			HttpSession session) {

		return "version";
	}

	@RequestMapping(value = "/viewmessage/{id}", method = RequestMethod.GET)
	public String viewMessage(@PathVariable("id") int id, Model model,
			HttpSession session, HttpServletRequest request)
			throws IOException, InvalidHMACException, CryptorException {
		// List<Category> categoryList = messageService.getCategoryList();

		// model.addAttribute("categoryList", categoryList);
		String categoryName = null;

		String password = "aes123";
		String base = System.getProperty("catalina.home");

		// model.addAttribute("categoryCall", "call to category" +
		// categoryList.get(0).getCategoryId());
		model.addAttribute("messageData", "MessageData");
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			
			// String sql1 =
			// "SELECT M.*, MD.*, C.CATEGORYNAME FROM MESSAGE M,MESSAGE_DETAILS MD,CATEGORY C WHERE C.CATEGORYID = M.CATEGORY_ID AND MD.MESSAGE_ID= "
			// + id
			// + " AND M.MESSAGE_ID = " + id;
			String sql1 = "SELECT M.MESSAGE_ID, M.CREATED_AT,M.EXPIRY_AT,M.CATEGORY_ID,M.USERNAME,M.IS_DELETED,M.IS_EDITED,M.MSG_STATUS, MD.MESSAGE_DETAIL_ID, "
					+ " MD.MESSAGE_DATA,MD.MESSAGE_TITLE, MD.MESSAGE_LANG,MD.MESSAGE_LINK,MD.MESSAGE_IMG_LINK, C.CATEGORYNAME FROM MESSAGE M,MESSAGE_DETAILS MD,CATEGORY C WHERE C.CATEGORYID = M.CATEGORY_ID AND MD.MESSAGE_ID= "
					+ id + " AND M.MESSAGE_ID = " + id;
			// SELECT M.MESSAGE_ID,to_char(M.CREATED_AT, 'dd-MM-yyyy
			// HH24:MI:SS.FF'),to_char(M.EXPIRY_AT, 'dd-MM-yyyy
			// HH24:MI:SS.FF'),M.CATEGORY_ID,MD.* FROM MESSAGE M,MESSAGE_DETAILS
			// MD WHERE MD.MESSAGE_ID= 360 AND M.MESSAGE_ID = 360;
			// String sql1 =
			// "SELECT M.MESSAGE_ID,to_char(M.CREATED_AT, 'dd-MM-yyyy HH24:MI:SS.FF'),to_char(M.EXPIRY_AT, 'dd-MM-yyyy HH24:MI:SS.FF'),,M.CATEGORY_ID,MD.* FROM MESSAGE M,MESSAGE_DETAILS MD WHERE MD.MESSAGE_ID= "+id+"  AND M.MESSAGE_ID = "+id;

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql1);
			List<Message> messageArray = new ArrayList<Message>();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			while (resultSet.next()) {
				String message_id = String.valueOf(resultSet
						.getInt("MESSAGE_ID"));
				String created_date = ""
						+ dateFormat.format(resultSet
								.getTimestamp("CREATED_AT"));
				// String created_date = "" +
				// resultSet.getTimestamp("CREATED_AT");
				String expiry_date = ""
						+ ""
						+ dateFormat
								.format(resultSet.getTimestamp("EXPIRY_AT"));
				// String expiry_date = "" +
				// resultSet.getTimestamp("EXPIRY_AT");
				String category_Id = String.valueOf(resultSet
						.getInt("CATEGORY_ID"));
				String message_detail_Id = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				String message_Id = String.valueOf(resultSet
						.getInt("MESSAGE_ID"));
				String message_data = resultSet.getString("MESSAGE_DATA");
				String message_title = resultSet.getString("MESSAGE_TITLE");
				String message_link = resultSet.getString("MESSAGE_LINK");
				String message_img_link = resultSet
						.getString("MESSAGE_IMG_LINK");

				categoryName = String.valueOf(resultSet
						.getString("CATEGORYNAME"));
				System.out.println("category name>>>>>>>>>>" + categoryName);
				String base64DataString = "";
				if (message_img_link != null) {

					if (message_img_link.length() > 0) {

						File encFile = new File(base + File.separator
								+ UploadFolder + message_img_link);
						// Sudd Added Code for Android without Encryption
						File androidFile = new File(base + File.separator
								+ UploadFolderAndroid + message_img_link);

						int rsize = (int) encFile.length();
						// for Android
						int rsizeAndroid = (int) androidFile.length();
						byte[] bytes = new byte[rsize];
						// For ANdroid
						byte[] bytesAndroid = new byte[rsizeAndroid];
						FileInputStream fis = new FileInputStream(encFile);
						// For Android
						FileInputStream fisAndroid = new FileInputStream(
								androidFile);
						fis.read(bytes);
						// For ANdroid
						fis.read(bytesAndroid);
						fis.close();

						JNCryptor cryptor = new AES256JNCryptor();
						byte[] decryptdata = cryptor.decryptData(bytes,
								password.toCharArray());

						byte[] encodeBase64 = Base64.encodeBase64(decryptdata);

						try {
							base64DataString = new String(encodeBase64, "UTF-8");

						} catch (UnsupportedEncodingException e) {
							
							e.printStackTrace();
						}

					}
				}

				String message_lang = String.valueOf(resultSet
						.getInt("MESSAGE_LANG"));
				// String message_status =
				// String.valueOf(resultSet.getInt("MESSAGE_STATUS"));
				/*
				 * messageArray.add(new Message(message_detail_Id, message_id,
				 * message_data, message_title, message_link, message_img_link,
				 * created_date, expiry_date, category_Id));
				 */
				messageArray.add(new Message(message_detail_Id, message_id,
						message_data, message_title, message_link,
						base64DataString, created_date, expiry_date,
						categoryName));
				model.addAttribute("categoryId", category_Id);

			}
			model.addAttribute("messageData", messageArray.get(0).getMsgData());

			// session.setAttribute("messageEng", messageArray.get(0));
			// session.setAttribute("messageBha", messageArray.get(1));
			model.addAttribute("messageEng", messageArray.get(0));
			model.addAttribute("messageBha", messageArray.get(1));
			// model.addAttribute("categoryName", categoryName);

			model.addAttribute("categoryName", categoryName);

		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "viewMessageDetails";

	}

	@RequestMapping(value = "/editmessage/{id}", method = RequestMethod.GET)
	public String EditMessage(@PathVariable("id") int id, Model model,
			HttpSession session) {
		
		Connection conn = null;
		
		try {
			List<Category> categoryList = messageService.getCategoryList();
			model.addAttribute("categoryList", categoryList);

			// get Zones
			List<Zone> zoneList = new ArrayList<Zone>();
			conn = DBConnection.getConnection();

			String sql = "select * from zone";

			Statement stmtZone = conn.createStatement();
			ResultSet resultSetZone = stmtZone.executeQuery(sql);
			while (resultSetZone.next()) {
				zoneList.add(new Zone(resultSetZone.getInt("ZONE_ID"),
						resultSetZone.getString("ZONE_NAME")));
			}

			model.addAttribute("zoneList", zoneList);

			// get Channels
			List<Channel> channelList = new ArrayList<Channel>();

			String sqlChannel = "select * from channel";

			Statement stmtChannel = conn.createStatement();
			ResultSet resultSetChannel = stmtChannel.executeQuery(sqlChannel);
			while (resultSetChannel.next()) {
				channelList.add(new Channel(resultSetChannel
						.getInt("CHANNEL_ID"), resultSetChannel
						.getString("CHANNEL_NAME")));
			}

			model.addAttribute("channelList", channelList);

			model.addAttribute("categoryCall", "call to category"
					+ categoryList.get(0).getCategoryId());
			model.addAttribute("messageData", "MessageData");

			String sql1 = "SELECT M.*,MD.* FROM MESSAGE M,MESSAGE_DETAILS MD WHERE MD.MESSAGE_ID= "
					+ id + " AND M.MESSAGE_ID = " + id;
			// SELECT M.MESSAGE_ID,to_char(M.CREATED_AT, 'dd-MM-yyyy
			// HH24:MI:SS.FF'),to_char(M.EXPIRY_AT, 'dd-MM-yyyy
			// HH24:MI:SS.FF'),M.CATEGORY_ID,MD.* FROM MESSAGE M,MESSAGE_DETAILS
			// MD WHERE MD.MESSAGE_ID= 360 AND M.MESSAGE_ID = 360;
			// String sql1 =
			// "SELECT M.MESSAGE_ID,to_char(M.CREATED_AT, 'dd-MM-yyyy HH24:MI:SS.FF'),to_char(M.EXPIRY_AT, 'dd-MM-yyyy HH24:MI:SS.FF'),,M.CATEGORY_ID,MD.* FROM MESSAGE M,MESSAGE_DETAILS MD WHERE MD.MESSAGE_ID= "+id+"  AND M.MESSAGE_ID = "+id;

			// ArrayList<String> zoneIds=new ArrayList<String>();
			// ArrayList<String> CHANNEL_IDs=new ArrayList<String>();

			String zoneIds = "";
			String CHANNEL_IDs = "";

			String sqlGetZoneIds = "select ZONE_ID from message_zone where MESSAGE_ID="
					+ id;
			Statement stmtGetZoneIds = conn.createStatement();
			ResultSet resultSetGetZoneIds = stmtGetZoneIds
					.executeQuery(sqlGetZoneIds);
			while (resultSetGetZoneIds.next()) {
				// zoneIds.add(String.valueOf(resultSetGetZoneIds.getInt("ZONE_ID")));
				zoneIds += String
						.valueOf(resultSetGetZoneIds.getInt("ZONE_ID")) + ",";
			}
			if (!zoneIds.equals("")) {
				zoneIds = zoneIds.substring(0, zoneIds.length() - 1);
			}

			String sqlGetCHANNEL_IDs = "select CHANNEL_ID from MESSAGE_CHANNEL where MESSAGE_ID="
					+ id;
			Statement stmtGetCHANNEL_IDs = conn.createStatement();
			ResultSet resultSetGetCHANNEL_IDs = stmtGetCHANNEL_IDs
					.executeQuery(sqlGetCHANNEL_IDs);
			while (resultSetGetCHANNEL_IDs.next()) {
				// CHANNEL_IDs.add(String.valueOf(resultSetGetCHANNEL_IDs.getInt("CHANNEL_ID")));
				CHANNEL_IDs += String.valueOf(resultSetGetCHANNEL_IDs
						.getInt("CHANNEL_ID")) + ",";
			}
			if (!CHANNEL_IDs.equals("")) {
				CHANNEL_IDs = CHANNEL_IDs.substring(0, CHANNEL_IDs.length() - 1);
			}

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql1);
			List<Message> messageArray = new ArrayList<Message>();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String message_id = "";
			String created_date = "";
			String expiry_date = "";
			String category_Id = "";
			String message_detail_Id = "";
			String message_Id = "";
			String message_data = "";
			String message_title = "";
			String message_link = "";
			String message_img_link = "";
			String message_lang = "";
			while (resultSet.next()) {
				message_id = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				created_date = ""
						+ dateFormat.format(resultSet
								.getTimestamp("CREATED_AT"));
				// String created_date = "" +
				// resultSet.getTimestamp("CREATED_AT");
				expiry_date = ""
						+ ""
						+ dateFormat
								.format(resultSet.getTimestamp("EXPIRY_AT"));
				// String expiry_date = "" +
				// resultSet.getTimestamp("EXPIRY_AT");
				category_Id = String.valueOf(resultSet.getInt("CATEGORY_ID"));
				message_detail_Id = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				message_Id = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				model.addAttribute("messageData", message_Id);

				message_data = resultSet.getString("MESSAGE_DATA") != null ? resultSet
						.getString("MESSAGE_DATA") : "";
				model.addAttribute("messageData", message_data);
				message_title = resultSet.getString("MESSAGE_TITLE") != null ? resultSet
						.getString("MESSAGE_TITLE") : "";
				model.addAttribute("messageData", message_data);
				message_link = resultSet.getString("MESSAGE_LINK") != null ? resultSet
						.getString("MESSAGE_LINK") : "";
				model.addAttribute("messageData", message_data);
				message_img_link = resultSet.getString("MESSAGE_IMG_LINK") != null ? resultSet
						.getString("MESSAGE_IMG_LINK") : "";
				model.addAttribute("messageData", message_data);
				message_lang = String.valueOf(resultSet.getInt("MESSAGE_LANG"));

				// String message_status =
				// String.valueOf(resultSet.getInt("MESSAGE_STATUS"));

				messageArray.add(new Message(message_detail_Id, message_id,
						message_data, message_title, message_link,
						message_img_link, created_date, expiry_date,
						category_Id));
				model.addAttribute("categoryId", category_Id);
			}
			model.addAttribute("zoneIds", zoneIds);
			model.addAttribute("CHANNEL_IDs", CHANNEL_IDs);
			/*
			 * model.addAttribute("ZoneIds1",zoneIds);
			 * model.addAttribute("ZoneIds2",zoneIds);
			 * model.addAttribute("ZoneIds3",zoneIds);
			 * model.addAttribute("ZoneIds4",zoneIds);
			 * model.addAttribute("ZoneIds5",zoneIds);
			 * model.addAttribute("ZoneIds6",zoneIds);
			 * model.addAttribute("ZoneIds7",zoneIds);
			 * model.addAttribute("ZoneIds8",zoneIds);
			 * model.addAttribute("ZoneIds9",zoneIds);
			 * model.addAttribute("CHANNEL_IDs1",CHANNEL_IDs);
			 * model.addAttribute("CHANNEL_IDs2",CHANNEL_IDs);
			 * model.addAttribute("CHANNEL_IDs3",CHANNEL_IDs);
			 * model.addAttribute("CHANNEL_IDs4",CHANNEL_IDs);
			 */

			String base = System.getProperty("catalina.home");

			File dir = new File(base + File.separator + UploadFolder);
			// Sudd Added code Without Encryption For Androif
			File dirAndroid = new File(base + File.separator
					+ UploadFolderAndroid);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// For Android
			if (!dirAndroid.exists()) {
				dirAndroid.mkdirs();
			}
			File editdir = new File(base + File.separator + EditFolder);

			if (!editdir.exists()) {
				editdir.mkdirs();
			}

			File dateDir = new File(base + File.separator + UploadFolder
					+ File.separator + File.separator);
			// For Android
			File dateDirAndroid = new File(base + File.separator
					+ UploadFolderAndroid + File.separator + File.separator);

			if (!dateDir.exists()) {
				dateDir.mkdirs();
			}
			// For Android
			if (!dateDirAndroid.exists()) {
				dateDirAndroid.mkdirs();
			}
			JNCryptor cryptor = new AES256JNCryptor();
			String password = "aes123";
			String imgstr = "";
			if (messageArray.get(0).getMsgLink2().length() > 0) {
				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + messageArray.get(0).getMsgLink2());
				// For Android
				File uploadedFileAndroid = new File(
						dateDirAndroid.getAbsolutePath() + File.separator
								+ messageArray.get(0).getMsgLink2());

				FileInputStream inputStream = new FileInputStream(uploadedFile);
				// For Android
				FileInputStream inputStreamAndroid = new FileInputStream(
						uploadedFileAndroid);

				byte[] filebytes = new byte[(int) uploadedFile.length()];
				// for Android
				byte[] filebytesAndroid = new byte[(int) uploadedFileAndroid
						.length()];
				inputStream.read(filebytes);

				// For Android
				inputStreamAndroid.read(filebytesAndroid);
				byte[] decryptedtext = cryptor.decryptData(filebytes,
						password.toCharArray());
				// byte[] encoded =
				// Base64.getEncoder().encode("Hello".getBytes());
				byte[] encodedImgData = Base64.encodeBase64(decryptedtext);
				String imgString = new String(encodedImgData, "UTF-8");
				imgstr = new String(encodedImgData, "UTF-8");
			}

			// model.addAttribute("encodedImgDataVal", encodedImgData);

			/*
			 * String filePath = base+File.separator + EditFolder
			 * +messageArray.get(0).getMsgLink2() ; File decryptedFile = new
			 * File(filePath); if (!decryptedFile.exists()) {
			 * decryptedFile.createNewFile(); }
			 * 
			 * FileOutputStream fos = new FileOutputStream(decryptedFile);
			 * fos.write(decryptedtext); fos.flush(); fos.close();
			 */
			String imgstr1 = "";
			if (messageArray.get(1).getMsgLink2().length() > 0) {
				File uploadedFile1 = new File(dateDir.getAbsolutePath()
						+ File.separator + messageArray.get(1).getMsgLink2());
				// for Android
				File uploadedFileAndroid = new File(
						dateDirAndroid.getAbsolutePath() + File.separator
								+ messageArray.get(1).getMsgLink2());
				FileInputStream inputStream1 = new FileInputStream(
						uploadedFile1);
				// For Android
				FileInputStream inputStreamAndroid = new FileInputStream(
						uploadedFileAndroid);

				byte[] filebytes1 = new byte[(int) uploadedFile1.length()];
				// For Android
				byte[] filebytesAndroid = new byte[(int) uploadedFileAndroid
						.length()];
				inputStream1.read(filebytes1);
				// For Android
				inputStreamAndroid.read(filebytesAndroid);
				byte[] decryptedtext1 = cryptor.decryptData(filebytes1,
						password.toCharArray());
				byte[] encodedImgData1 = Base64.encodeBase64(decryptedtext1);
				imgstr1 = new String(encodedImgData1, "UTF-8");
			}

			/*
			 * String filePath1 = base+File.separator + EditFolder
			 * +messageArray.get(1).getMsgLink2() ; File decryptedFile1 = new
			 * File(filePath1); if (!decryptedFile1.exists()) {
			 * decryptedFile1.createNewFile(); }
			 * 
			 * FileOutputStream fos1 = new FileOutputStream(decryptedFile1);
			 * fos1.write(decryptedtext1); fos1.flush(); fos1.close();
			 */

			if (messageArray.get(0).getMsgLink() != null) {
				if (!messageArray.get(0).getMsgLink().equalsIgnoreCase("")) {
					model.addAttribute("isattached", "Document is attached");
				}
			}
			if (messageArray.get(1).getMsgLink() != null) {
				if (!messageArray.get(1).getMsgLink().equalsIgnoreCase("")) {
					model.addAttribute("isattached1", "Document is attached");
				}
			}
			/*
			 * model.addAttribute("filePath",filePath );
			 * model.addAttribute("filePath1",filePath1 );
			 */
			model.addAttribute("filePath", imgstr);
			model.addAttribute("filePath1", imgstr1);
			model.addAttribute("imgfilename", messageArray.get(0).getMsgLink2());
			model.addAttribute("imgfilename1", messageArray.get(1)
					.getMsgLink2());
			model.addAttribute("messageData", messageArray.get(0).getMsgData());
			// session.setAttribute("messageEng", messageArray.get(0));
			// session.setAttribute("messageBha", messageArray.get(1));
			model.addAttribute("messageEng", messageArray.get(0));
			model.addAttribute("messageBha", messageArray.get(1));

		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InvalidHMACException e) {
			
			e.printStackTrace();
		} catch (CryptorException e) {
			
			e.printStackTrace();
		}catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "editmessage";

	}

	// Start By Sudd for ReadCount Message
	@RequestMapping(value = "/readCountMessage/{id}", method = RequestMethod.GET)
	// @RequestMapping(value = URIConstants.GET_READ_MESSAGES, method =
	// RequestMethod.GET)
	// public String EditMessage(@PathVariable("id") int id, Model model,
	// HttpSession session) {
	public String readcountmessages(@PathVariable("id") int id,
			HttpServletResponse response, Model model, HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");
			// String
			// sql="select md.* , um.USERNAME,c.CATEGORYNAME from message_details md , user_m um , message m,category c where m.message_id =  md.message_id and m.USERNAME = um.USERID and m.category_id=c.categoryId  and  md.message_id IN (select message_id from read_reciept) and md.message_lang = 1 and md.MESSAGE_STATUS =1";
			// String sql =
			// "select	md.* , 	um.USERNAME,	c.CATEGORYNAME,	m.CREATED_AT from 	message_details md ,	user_m um ,	message m,	read_reciept r,	category c where 	m.message_id =  md.message_id and 	r.USER_ID = um.USERID and 	m.category_id=c.categoryId  and  	md.message_id  = r.message_id and 	md.message_lang = 1 and  md.MESSAGE_STATUS =1";
			// select md.* , um.USERNAME, c.CATEGORYNAME, m.CREATED_AT from
			// message_details md , user_m um , message m, read_reciept r,
			// category c where m.message_id = md.message_id and r.USER_ID =
			// um.USERID and m.category_id=c.categoryId and md.message_id =
			// r.message_id and md.message_lang = 1 and m.MSG_STATUS =1 and
			// m.IS_DELETED=0
			String sql = "select	md.* , 	um.USERNAME,	c.CATEGORYNAME,	m.CREATED_AT from 	message_details md ,	user_m um ,	message m,	read_reciept r,	category c where 	m.message_id =  md.message_id and 	r.USER_ID = um.USERID and 	m.category_id=c.categoryId  and  	md.message_id  = r.message_id and 	md.message_lang = 1 and   m.MSG_STATUS =1 and m.IS_DELETED=0 and md.message_id ="
					+ id;
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				String msgId = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String categoryName = resultSet.getString("CATEGORYNAME");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String username = resultSet.getString("USERNAME");
				//String timestamp = resultSet.getString("CREATED_AT");
				String timestamp = resultSet.getString("READ_AT");

				// String MsgDetailID ,String MsgID,String msgData,String
				// MsgTitle,String MsgLink,String msgStatus
				messageList.add(new Message(msgId, categoryName, msgTitle,
						username, timestamp));
				// messageList.add(new
				// Message(String.valueOf(resultSet.getInt(1)),
				// String.valueOf(resultSet.getInt(2)), resultSet.getString(3),
				// resultSet.getString(4), resultSet.getString(5)));
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		// model.addAttribute("message", new Message());
		return "readCountMessage";
	}

	// Endend By sudd for ReadCount Message

	@RequestMapping(value = "/editmessage/editmessage", method = RequestMethod.POST)
	public String insertEditMessage(HttpServletResponse response,
			@Valid @ModelAttribute("message") Message message,
			BindingResult result, Model model, HttpServletRequest request,
			@RequestParam("file_image") MultipartFile file_image,
			@RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file2_image") MultipartFile file2_image)
			throws IOException {

		List<String> devices = new ArrayList<String>();
		List<Category> categoryList = messageService.getCategoryList();
		model.addAttribute("message", new Message());
		User user = (User) request.getSession().getAttribute("user");

		String fileName = "";
		String f1 = "";
		String f2 = "";
		String img_file1 = "";
		String img_file2 = "";
		if (!file_image.isEmpty()) {
			img_file1 = createFile(file_image);
		}
		if (!file2_image.isEmpty()) {
			img_file2 = createFile(file2_image);
		}
		PrintWriter script = response.getWriter();

		if (!file.isEmpty()) {
			try {
				// model.addAttribute("messageName", "in");
				byte[] bytes = file.getBytes();

				fileName = FilenameUtils.getName(file.getOriginalFilename());
				String type = fileName.substring(fileName.lastIndexOf("."));
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				// String
				f1 = name.trim() + "_" + new Date().getTime() + type;

				String base = System.getProperty("catalina.home");

				File dir = new File(base + File.separator + UploadFolder);

				if (!dir.exists()) {
					dir.mkdirs();
				}

				Date date = new Date();

				File dateDir = new File(base + File.separator + UploadFolder
						+ File.separator + File.separator);

				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}

				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + f1);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));

				stream.write(bytes);
				stream.close();

				model.addAttribute("msg", "file write");
				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";
				try {
					model.addAttribute("msg", "uploaded file" + uploadedFile);
					FileInputStream inputStream = new FileInputStream(
							uploadedFile);

					byte[] filebytes = new byte[(int) uploadedFile.length()];
					model.addAttribute("msg", "uploaded file"
							+ (int) uploadedFile.length());
					inputStream.read(filebytes);
					model.addAttribute("msg", "length of bytes"
							+ filebytes.length);

					byte[] ciphertext = cryptor.encryptData(filebytes,
							password.toCharArray());

					model.addAttribute("msg", "length of ciphertext"
							+ ciphertext.length);

					// String filePath = "D:/Varsha/apache-tomcat-8.0.24/" +
					// UploadFolder + f1;
					String filePath = base + File.separator + UploadFolder + f1;
					model.addAttribute("msg", "filePath" + filePath);
					File encryptedFile = new File(filePath);
					if (!encryptedFile.exists()) {
						encryptedFile.createNewFile();
					}

					try {
						model.addAttribute("msg", "write file" + filePath);
						FileOutputStream fos = new FileOutputStream(
								encryptedFile);
						fos.write(ciphertext);
						fos.flush();
						fos.close();
						// script.write("ok1");
					} catch (FileNotFoundException e) {
						
						// script.write("ok2");
						e.printStackTrace();
					} catch (IOException e) {
						
						// script.write("ok3");
						e.printStackTrace();
					}
					// script.write("ok");

				} catch (CryptorException e) {
					// script.write("fail");
					model.addAttribute("msg", "CryptorException");
					e.printStackTrace();
				}

			} catch (Exception e) {
				// script.write("ok1212");
				// model.addAttribute("msg", "Exception");

			}
		} else {

		}

		if (!file2.isEmpty()) {
			try {

				byte[] bytes = file2.getBytes();

				fileName = FilenameUtils.getName(file2.getOriginalFilename());
				String type = fileName.substring(fileName.lastIndexOf("."));
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				// String
				f2 = name.trim() + "_" + new Date().getTime() + type;

				String base = System.getProperty("catalina.home");

				File dir = new File(base + File.separator + UploadFolder);

				if (!dir.exists()) {
					dir.mkdirs();
				}

				Date date = new Date();

				File dateDir = new File(base + File.separator + UploadFolder
						+ File.separator + File.separator);

				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}

				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + f2);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));

				stream.write(bytes);
				stream.close();

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";
				try {
					model.addAttribute("msg", "uploaded file" + uploadedFile);
					FileInputStream inputStream = new FileInputStream(
							uploadedFile);

					byte[] filebytes = new byte[(int) uploadedFile.length()];
					model.addAttribute("msg", "uploaded file"
							+ (int) uploadedFile.length());
					inputStream.read(filebytes);
					model.addAttribute("msg", "length of bytes"
							+ filebytes.length);

					byte[] ciphertext = cryptor.encryptData(filebytes,
							password.toCharArray());

					model.addAttribute("msg", "length of ciphertext"
							+ ciphertext.length);

					// String filePath = "D:/Varsha/apache-tomcat-8.0.24/" +
					// UploadFolder + f2;
					String filePath = base + File.separator + UploadFolder + f2;
					model.addAttribute("msg", "filePath" + filePath);
					File encryptedFile = new File(filePath);
					if (!encryptedFile.exists()) {
						encryptedFile.createNewFile();
					}

					try {
						model.addAttribute("msg", "write file" + filePath);
						FileOutputStream fos = new FileOutputStream(
								encryptedFile);
						fos.write(ciphertext);
						fos.flush();
						fos.close();
						// script.write("ok1");
					} catch (FileNotFoundException e) {
						
						// script.write("ok2");
						e.printStackTrace();
					} catch (IOException e) {
						
						// script.write("ok3");
						e.printStackTrace();
					}
					// script.write("ok");

				} catch (CryptorException e) {
					// script.write("fail");
					model.addAttribute("msg", "CryptorException");
					e.printStackTrace();
				}

			} catch (Exception e) {

			}
		} else {

		}

		/*
		 * String zoneIds=""; String CHANNEL_IDs=""; String zoneId=""; String
		 * CHANNEL_ID=""; if(zone!=null) { for(int i=0;i< zone.length; i++) {
		 * if(zone[i].equalsIgnoreCase("all")) { zoneId="1"; } else
		 * if(zone[i].equalsIgnoreCase("north")) { zoneId="2"; } else
		 * if(zone[i].equalsIgnoreCase("east")) { zoneId="3"; } else
		 * if(zone[i].equalsIgnoreCase("west")) { zoneId="4"; } else
		 * if(zone[i].equalsIgnoreCase("south")) { zoneId="5"; }
		 * 
		 * if(i==0) { zoneIds=zoneIds+zoneId; } else zoneIds=zoneIds+","+zoneId;
		 * } } if(channel!=null) { for(int i=0;i< channel.length; i++) {
		 * if(channel[i].equalsIgnoreCase("all")) { CHANNEL_ID="1"; } else
		 * if(channel[i].equalsIgnoreCase("PBTB agents")) { CHANNEL_ID="2"; }
		 * else if(channel[i].equalsIgnoreCase("PBTB leaders")) { CHANNEL_ID="3";
		 * } else if(channel[i].equalsIgnoreCase("PAMB agents")) {
		 * CHANNEL_ID="4"; } else if(channel[i].equalsIgnoreCase("PAMB leaders"))
		 * { CHANNEL_ID="5"; }
		 * 
		 * if(i==0) { CHANNEL_IDs=CHANNEL_IDs+CHANNEL_ID; } else
		 * CHANNEL_IDs=CHANNEL_IDs+","+CHANNEL_ID; } }
		 */
		
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();

			String sqlCheckStatus = "select MSG_STATUS from message where MESSAGE_ID=?";
			PreparedStatement stmtCheckStatus = conn
					.prepareStatement(sqlCheckStatus);
			stmtCheckStatus.setString(1, request.getParameter("messageId"));
			ResultSet resultSetCheckStatus = stmtCheckStatus.executeQuery();
			if (resultSetCheckStatus != null) {
				while (resultSetCheckStatus.next()) {
					int msg_status = resultSetCheckStatus.getInt("MSG_STATUS");
					if (msg_status != 0) {
						String sqlSaveEditedMsg = "INSERT INTO MESSAGE_EDIT (MESSAGE_EDIT_ID, MESSAGE_ID) VALUES (MESSAGE_EDIT_SEQ.nextval, ?)";
						PreparedStatement stmtSaveEditedMsg = conn
								.prepareStatement(sqlSaveEditedMsg);
						stmtSaveEditedMsg.setInt(1, Integer.parseInt(request
								.getParameter("messageId")));
						stmtSaveEditedMsg.executeUpdate();
					}
				}
			}

			model.addAttribute("msg",
					"write file" + request.getParameter("effdate"));
			String sql = "UPDATE MESSAGE SET CREATED_AT= TO_DATE('"
					+ request.getParameter("effdate")
					+ "', 'DD-MM-YYYY:HH24:MI:SS'), EXPIRY_AT=TO_DATE('"
					+ request.getParameter("expdate")
					+ "', 'DD-MM-YYYY:HH24:MI:SS') , CATEGORY_ID= " 
					+ request.getParameter("category.categoryId") 
					+ ", CATEGORYID= " 
					+ request.getParameter("category.categoryId") 
					+ ",MSG_STATUS=0  WHERE MESSAGE_ID="
					+ request.getParameter("messageId");
			/*
			 * String sql = "UPDATE MESSAGE SET CREATED_AT= '" +
			 * request.getParameter("effdate") + "', EXPIRY_AT='" +
			 * request.getParameter("expdate") +
			 * "' , CATEGORY_ID= "+request.getParameter
			 * ("category.categoryId")+",MSG_STATUS=0  WHERE MESSAGE_ID=" +
			 * request.getParameter("messageId");
			 */
			Statement stmt = conn.createStatement();
			int queryresult = stmt.executeUpdate(sql);

			String sql1 = "UPDATE message_details SET MESSAGE_DATA= '"
					+ (request.getParameter("messageName")
							.replaceAll("'", "''"))
					+ "', MESSAGE_TITLE='"
					+ (request.getParameter("headlineName").replaceAll("'",
							"''")) + "'";

			if (f1 != null && f1.length() > 0)
				sql1 = sql1 + " , MESSAGE_LINK= '" + f1 + "'";
			if (request.getParameter("category.categoryId").equalsIgnoreCase(
					"6")) {

				sql1 = sql1 + " , MESSAGE_IMG_LINK= '"
						+ request.getParameter("videoURL1") + "'";

			} else {
				if (img_file1 != null && img_file1.length() > 0) {
					sql1 = sql1 + " , MESSAGE_IMG_LINK= '" + img_file1 + "'";
				}
			}

			sql1 = sql1 + "  WHERE MESSAGE_ID="
					+ request.getParameter("messageId") + " and MESSAGE_LANG=1";

			model.addAttribute("testMessage", sql1);
			/*
			 * String sql1 = "UPDATE message_details SET MESSAGE_DATA= '" +
			 * request.getParameter("messageName") + "', MESSAGE_TITLE='" +
			 * request.getParameter("headlineName") + "' , MESSAGE_LINK= '" +
			 * f1+"', MESSAGE_IMG_LINK= '"+img_file1 + "'  WHERE MESSAGE_ID=" +
			 * request.getParameter("messageId") + " and MESSAGE_LANG=1";
			 */
			Statement stmtUpdate = conn.createStatement();
			stmtUpdate.executeUpdate(sql1);

			String sql11 = "UPDATE message_details SET MESSAGE_DATA= '"
					+ (request.getParameter("messageName1").replaceAll("'",
							"''"))
					+ "', MESSAGE_TITLE='"
					+ (request.getParameter("headlineName1").replaceAll("'",
							"''")) + "'";
			if (f2 != null && f2.length() > 0)
				sql11 = sql11 + " , MESSAGE_LINK= '" + f2 + "'";
			if (request.getParameter("category.categoryId").equalsIgnoreCase(
					"6")) {

				sql11 = sql11 + " , MESSAGE_IMG_LINK= '"
						+ request.getParameter("videoURL2") + "'";

			} else {
				if (img_file2 != null && img_file2.length() > 0) {
					sql11 = sql11 + " , MESSAGE_IMG_LINK= '" + img_file2 + "'";
				}
			}

			sql11 = sql11 + "  WHERE MESSAGE_ID="
					+ request.getParameter("messageId") + " and MESSAGE_LANG=2";
			/*
			 * String sql11 = "UPDATE message_details SET MESSAGE_DATA= '" +
			 * request.getParameter("messageName1") + "', MESSAGE_TITLE='" +
			 * request.getParameter("headlineName1") + "' , MESSAGE_LINK= '" +
			 * f2+"', MESSAGE_IMG_LINK= '"+img_file2 + "'  WHERE MESSAGE_ID=" +
			 * request.getParameter("messageId") + " and MESSAGE_LANG=2";
			 */
			Statement stmt11 = conn.createStatement();
			stmt11.executeUpdate(sql11);

			String zone[] = request.getParameterValues("zone");
			String channel[] = request.getParameterValues("channel");

			String sqlDeleteZone = "delete from MESSAGE_ZONE where MESSAGE_ID=?";
			PreparedStatement stmtDeleteZone = conn
					.prepareStatement(sqlDeleteZone);
			stmtDeleteZone.setInt(1,
					Integer.parseInt(request.getParameter("messageId")));
			stmtDeleteZone.executeUpdate();

			String sqlDeleteChannel = "delete from MESSAGE_CHANNEL where MESSAGE_ID=?";
			PreparedStatement stmtDeleteChannel = conn
					.prepareStatement(sqlDeleteChannel);
			stmtDeleteChannel.setInt(1,
					Integer.parseInt(request.getParameter("messageId")));
			stmtDeleteChannel.executeUpdate();

			if (zone != null) {
				for (int i = 0; i < zone.length; i++) {
					int zoneId = 0;
					// String
					// sqlGetZoneId="select zone_id from zone where ZONE_NAME=?";
					// PreparedStatement
					// stmtGetZoneId=conn.prepareStatement(sqlGetZoneId);
					// stmtGetZoneId.setString(1, zone[i]);
					// ResultSet
					// resultSetGetZoneId=stmtGetZoneId.executeQuery();
					// while(resultSetGetZoneId.next())
					// {
					// zoneId=resultSetGetZoneId.getInt("ZONE_ID");
					zoneId = Integer.parseInt(zone[i]);
					// }

					String sqlZone = "INSERT INTO MESSAGE_ZONE (MSG_ZONE_ID, MESSAGE_ID, ZONE_ID) VALUES (MESSAGE_ZONE_SEQ.nextval, ?, ?)";
					PreparedStatement stmtZone = conn.prepareStatement(sqlZone);
					stmtZone.setInt(1,
							Integer.parseInt(request.getParameter("messageId")));
					stmtZone.setInt(2, zoneId);
					stmtZone.executeUpdate();
				}
			}

			if (channel != null) {
				for (int i = 0; i < channel.length; i++) {
					int CHANNEL_ID = 0;
					// String
					// sqlGetCHANNEL_ID="select CHANNEL_ID from CHANNEL where CHANNEL_NAME=?";
					// model.addAttribute("testZone", sqlGetCHANNEL_ID);
					// PreparedStatement
					// stmtGetCHANNEL_ID=conn.prepareStatement(sqlGetCHANNEL_ID);
					// stmtGetCHANNEL_ID.setString(1, channel[i]);
					// ResultSet
					// resultSetGetCHANNEL_ID=stmtGetCHANNEL_ID.executeQuery();
					// while(resultSetGetCHANNEL_ID.next())
					// {
					// CHANNEL_ID=resultSetGetCHANNEL_ID.getInt("CHANNEL_ID");
					CHANNEL_ID = Integer.parseInt(channel[i]);
					// }

					String sqlChannel = "INSERT INTO MESSAGE_CHANNEL (MSG_CHANNEL_ID, MESSAGE_ID, CHANNEL_ID) VALUES (MESSAGE_CHANNEL_SEQ.nextval, ?, ?)";
					PreparedStatement stmtChannel = conn
							.prepareStatement(sqlChannel);
					stmtChannel
							.setInt(1, Integer.parseInt(request
									.getParameter("messageId")));
					stmtChannel.setInt(2, CHANNEL_ID);
					stmtChannel.executeUpdate();
				}
			}

			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "msgedited";
		// return "editmessage";

	}

	// @RequestMapping(value = URIConstants.GET_LATEST_MESSAGES, method =
	// RequestMethod.GET)

	@RequestMapping(value = URIConstants.DELETE_MESSAGE, method = RequestMethod.GET)
	public String deleteMessage(@PathVariable("id") int id, Model model) {
		int result = 0;
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			// String sql1 =
			// "Update message_details set MSG_IS_DELETED=1 where MESSAGE_ID=" +
			// id;
			String sql2 = "Update MESSAGE set IS_DELETED=1 where MESSAGE_ID="
					+ id;

			Statement stmt = conn.createStatement();
			// result = stmt.executeUpdate(sql1);
			result = stmt.executeUpdate(sql2);

			String sqlSaveDeletedMsg = "INSERT INTO MESSAGE_DELETE (DELETE_ID, MESSAGE_ID) VALUES (MESSAGE_DELETE_SEQ.nextval, ?)";
			PreparedStatement stmtSaveDeletedMsg = conn
					.prepareStatement(sqlSaveDeletedMsg);
			stmtSaveDeletedMsg.setInt(1, id);
			stmtSaveDeletedMsg.executeUpdate();
			conn.close();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "msgdeleted";

	}

	// Used in mymessages cms for filter
	@RequestMapping(value = URIConstants.MY_MESSAGES, method = RequestMethod.POST)
	public String getMessagesForTimePeriodWeb(HttpServletRequest request,
			Model model, HttpSession session) {
		LOGGER.debug("Start messageList.");
		int all_messsages = 1;
		List<Message> messageList = new ArrayList<Message>();

		// System.out.println("date="+previousdate);
		// System.out.println("date="+nextdate);
		model.addAttribute("messageData", request.getParameter("time"));
		String previousdate = null;
		String nextdate = null;
		Calendar cal = Calendar.getInstance();
		String result;
		Date date;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int lang = 1;
		if (request.getParameter("time").equalsIgnoreCase("all_messages")) {

			all_messsages = 1;
		} else if (request.getParameter("time").equalsIgnoreCase("6_month")) {

			all_messsages = 0;
			cal.add(Calendar.MONTH, -6);
			date = cal.getTime();
			previousdate = dateFormat.format(date);
			nextdate = dateFormat.format(new Date());
			model.addAttribute("messageData", "-" + previousdate + "---"
					+ nextdate);

		} else if (request.getParameter("time").equalsIgnoreCase("this_month")) {
			all_messsages = 0;
			cal.add(Calendar.MONTH, -1);
			date = cal.getTime();
			previousdate = dateFormat.format(date);
			nextdate = dateFormat.format(new Date());
			model.addAttribute("messageData", "-" + previousdate + "---"
					+ nextdate);
		} else if (request.getParameter("time").equalsIgnoreCase("this_week")) {
			all_messsages = 0;
			cal.add(Calendar.DATE, -7);
			date = cal.getTime();
			previousdate = dateFormat.format(date);
			nextdate = dateFormat.format(new Date());
			model.addAttribute("messageData", "-" + previousdate + "---"
					+ nextdate);
		} else if (request.getParameter("time").equalsIgnoreCase("yesterday")) {
			all_messsages = 0;
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();
			previousdate = dateFormat.format(date);
			nextdate = previousdate;
			model.addAttribute("messageData", "-" + previousdate + "---"
					+ nextdate);
		} else if (request.getParameter("time").equalsIgnoreCase("today")) {
			all_messsages = 0;
			date = new Date();
			previousdate = dateFormat.format(date);
			nextdate = previousdate;
			model.addAttribute("messageData", "-" + previousdate + "---"
					+ nextdate);
		}
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			
			User user = (User) session.getAttribute("user");
			// String sql="SELECT * from message_details where message_id IN
			// (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN
			// TO_DATE ('2016/02/08', 'yyyy/mm/dd') AND TO_DATE ('2016/02/09',
			// 'yyyy/mm/dd'))and MESSAGE_LANG ="+
			// lang+" AND MESSAGE_STATUS=1";
			String sql = null;
			if (all_messsages == 1) {
				// SELECT md.*,c.CATEGORYNAME,m.CREATED_AT FROM message_details
				// md,message m,category c where m.message_id=md.message_id and
				// m.IS_DELETED=0 and m.category_id=c.categoryId and
				// md.MESSAGE_LANG=1 and m.USERNAME=2
				// sql="SELECT md.*,c.CATEGORYNAME,m.CREATED_AT FROM message_details md,message m,category c where m.message_id=md.message_id and  m.IS_DELETED=0 and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and m.USERNAME="+String.valueOf(user.getUserId())+" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				// sql="SELECT md.*,c.CATEGORYNAME,m.CREATED_AT,m.MSG_STATUS FROM message_details md,message m,category c where m.message_id=md.message_id and  m.IS_DELETED=0 and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and m.USERNAME="+String.valueOf(user.getUserId())+" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				/*
				 * sql=
				 * "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS,mz.ZONE_ID,mc.CHANNEL_ID"
				 * +
				 * " FROM message_details md, message m,category c ,MESSAGE_ZONE mz,MESSAGE_CHANNEL mc"
				 * +
				 * " where m.IS_DELETED=0 and md.message_id = m.message_id and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and  m.username ="
				 * +String.valueOf(user.getUserId()) +
				 * " and mz.ZONE_ID IN(Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
				 * +String.valueOf(user.getUserId())+
				 * ") and m.MESSAGE_ID=mz.MESSAGE_ID" +
				 * " and mc.CHANNEL_ID IN (SELECT USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID = "
				 * +String.valueOf(user.getUserId())+
				 * ") and mc.MESSAGE_ID=m.MESSAGE_ID"
				 * +" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				 */
				sql = "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS"
						+ " FROM message_details md, message m,category c "
						+ " where m.IS_DELETED=0 and md.message_id = m.message_id and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and  m.username ="
						+ String.valueOf(user.getUserId())
						+ " and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
			} else {
				/*
				 * sql =
				 * "SELECT * from message_details where message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"
				 * + previousdate + "', 'yyyy-mm-dd') AND TO_DATE ('" + nextdate
				 * + "', 'yyyy-mm-dd'))and MESSAGE_LANG =" + lang +
				 * " AND MESSAGE_STATUS=1";
				 */
				// SELECT md.*,c.CATEGORYNAME from message_details md,message
				// m,category c where md.message_id IN (SELECT
				// message.MESSAGE_ID FROM message WHERE created_at BETWEEN
				// TO_DATE ('2016-02-08', 'yyyy-mm-dd') AND TO_DATE
				// ('2016-03-30', 'yyyy-mm-dd'))and m.IS_DELETED=0 and
				// md.MESSAGE_LANG =1 and m.message_id=md.message_id and
				// m.category_id=c.categoryId AND md.MESSAGE_STATUS=1 and
				// m.USERNAME=2
				// SELECT md.*,c.CATEGORYNAME from message_details md,message
				// m,category c where md.message_id IN (SELECT
				// message.MESSAGE_ID FROM message WHERE created_at BETWEEN
				// TO_DATE ('2016-03-30', 'yyyy-mm-dd') AND TO_DATE
				// ('2016-04-30', 'yyyy-mm-dd'))and m.IS_DELETED=0 and
				// MESSAGE_LANG =1 and m.message_id=md.message_id and
				// m.category_id=c.categoryId AND m.MSG_STATUS=1 and
				// m.USERNAME=2 and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT
				// desc
				// sql="SELECT md.*,c.CATEGORYNAME from message_details md,message m,category c where md.message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"+previousdate+"', 'yyyy-mm-dd') AND TO_DATE ('"+nextdate+"', 'yyyy-mm-dd'))and m.IS_DELETED=0 and MESSAGE_LANG ="+lang+" and m.message_id=md.message_id and m.category_id=c.categoryId AND MESSAGE_STATUS=1 and m.USERNAME="+String.valueOf(user.getUserId())+" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				// sql="SELECT md.*,c.CATEGORYNAME from message_details md,message m,category c where md.message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"+previousdate+"', 'yyyy-mm-dd') AND TO_DATE ('"+nextdate+"', 'yyyy-mm-dd'))and m.IS_DELETED=0 and MESSAGE_LANG ="+lang+" and m.message_id=md.message_id and m.category_id=c.categoryId AND m.MSG_STATUS=1 and m.USERNAME="+String.valueOf(user.getUserId())+" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				// sql="SELECT md.*,c.CATEGORYNAME,m.MSG_STATUS from message_details md,message m,category c where md.message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"+previousdate+"', 'yyyy-mm-dd') AND TO_DATE ('"+nextdate+"', 'yyyy-mm-dd'))and m.IS_DELETED=0 and MESSAGE_LANG ="+lang+" and m.message_id=md.message_id and m.category_id=c.categoryId AND m.MSG_STATUS=1 and m.USERNAME="+String.valueOf(user.getUserId())+" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				/*
				 * sql=
				 * "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS,mz.ZONE_ID,mc.CHANNEL_ID"
				 * +
				 * " FROM message_details md, message m,category c ,MESSAGE_ZONE mz,MESSAGE_CHANNEL mc"
				 * +
				 * " where m.IS_DELETED=0 and md.message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"
				 * +previousdate+"', 'yyyy-mm-dd') AND TO_DATE ('"+nextdate+
				 * "', 'yyyy-mm-dd'))" +
				 * " and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and  m.username ="
				 * +String.valueOf(user.getUserId()) +
				 * " and mz.ZONE_ID IN(Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
				 * +String.valueOf(user.getUserId())+
				 * " and m.MESSAGE_ID=mz.MESSAGE_ID)" +
				 * " and mc.CHANNEL_ID IN (SELECT USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID = "
				 * +String.valueOf(user.getUserId())+
				 * ") and mc.MESSAGE_ID=m.MESSAGE_ID"
				 * +" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
				 */
				sql = "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS"
						+ " FROM message_details md, message m,category c "
						+ " where m.IS_DELETED=0 and md.message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"
						+ previousdate
						+ "', 'yyyy-mm-dd') AND TO_DATE ('"
						+ nextdate
						+ "', 'yyyy-mm-dd'))"
						+ " and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and  m.username ="
						+ String.valueOf(user.getUserId())
						+ " and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";

				model.addAttribute("messageData", "sql-" + sql);
				// sql="SELECT * from message_details where message_id IN
				// (SELECT message.MESSAGE_ID FROM message WHERE created_at
				// BETWEEN TO_DATE ('2016-02-08', 'yyyy-mm-dd') AND TO_DATE
				// ('2016-02-09', 'yyyy-mm-dd'))and MESSAGE_LANG =1 AND
				// MESSAGE_STATUS=1";
			}

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

				/*
				 * String enc1 =
				 * String.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID")); String
				 * msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				 * String msgData = resultSet.getString("MESSAGE_DATA"); String
				 * msgTitle = resultSet.getString("MESSAGE_TITLE"); String
				 * msgLink = resultSet.getString("MESSAGE_LINK"); String
				 * msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");
				 */
				// String enc7 =
				// String.valueOf(resultSet.getInt("MESSAGE_STATUS"));
				// String categoryName =
				// String.valueOf(resultSet.getString("CATEGORYNAME"));

				// String MsgDetailID ,String MsgID,String msgData,String
				// MsgTitle,String MsgLink,String msgStatus
				/*
				 * messageList.add(new Message( msgID, msgData, msgTitle,
				 * msgLink,
				 * String.valueOf(resultSet.getInt(7)).equalsIgnoreCase("0") ?
				 * "Pending" : "Approved", msgLink2,categoryName));
				 */

				String enc1 = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String msgData = resultSet.getString("MESSAGE_DATA");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String msgLink = resultSet.getString("MESSAGE_LINK");
				String msgStatus = String.valueOf(resultSet
						.getInt("MSG_STATUS"));
				String msg_img_Link = String.valueOf(resultSet
						.getString("MESSAGE_IMG_LINK"));
				String categoryName = String.valueOf(resultSet
						.getString("CATEGORYNAME"));

				/*
				 * messageList.add(new Message(msgID, msgData, msgTitle,
				 * msgLink,
				 * String.valueOf(resultSet.getInt("MSG_STATUS")).equalsIgnoreCase
				 * ("0") ? "Pending" : "Approved", msg_img_Link,categoryName));
				 */
				if (messageList.size() == 0) {
					messageList.add(new Message(msgID, msgData, msgTitle,
							msgLink, String.valueOf(
									resultSet.getInt("MSG_STATUS"))
									.equalsIgnoreCase("0") ? "Pending"
									: "Approved", msg_img_Link, categoryName));
				}
				boolean flagToadd = true;
				for (int i = 0; i < messageList.size(); i++) {
					if (Integer.parseInt(messageList.get(i).msgID) == Integer
							.parseInt(msgID)) {
						flagToadd = false;
					}
				}
				if (flagToadd) {
					messageList.add(new Message(msgID, msgData, msgTitle,
							msgLink, String.valueOf(
									resultSet.getInt("MSG_STATUS"))
									.equalsIgnoreCase("0") ? "Pending"
									: "Approved", msg_img_Link, categoryName));
				}
				// messageList.add(new
				// Message(String.valueOf(resultSet.getInt(1)),
				// String.valueOf(resultSet.getInt(2)), resultSet.getString(3),
				// resultSet.getString(4), resultSet.getString(5)));
			}
			model.addAttribute("messageList", messageList);
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		return "mymessages";
	}

	@RequestMapping(value = URIConstants.MY_MESSAGES, method = RequestMethod.GET)
	public String mymessages(HttpServletResponse response, Model model,
			HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");
			// SELECT a.*,c.CATEGORYNAME FROM message_details a, message
			// b,category c where b.IS_DELETED=0 and a.message_id = b.message_id
			// and b.category_id=c.categoryId and a.MESSAGE_LANG=1 and
			// b.username =2 and b.EXPIRY_AT>=SYSDATE ORDER BY b.CREATED_AT desc
			// String
			// sql="SELECT a.*,c.CATEGORYNAME FROM message_details a, message b,category c where b.IS_DELETED=0 and a.message_id = b.message_id and b.category_id=c.categoryId and a.MESSAGE_LANG=1 and  b.username ="+String.valueOf(user.getUserId())+"  and b.EXPIRY_AT>=SYSDATE ORDER BY b.CREATED_AT desc";

			/*
			 * SELECT a.*,c.CATEGORYNAME,b.MSG_STATUS,b.ZONE, b.CHANNEL FROM
			 * message_details a, message b,category c where b.IS_DELETED=0 and
			 * a.message_id = b.message_id and b.category_id=c.categoryId and
			 * a.MESSAGE_LANG=1 and b.username =3 and b.EXPIRY_AT>=SYSDATE and
			 * b.zone In(select ZONE_ID from USER_ZONE where user_ID=3) and
			 * b.CHANNEL IN(select CHANNEL_ID from USER_CHANNEL where user_ID=3)
			 * ORDER BY b.CREATED_AT desc;
			 */
			/*
			 * String sql=
			 * "SELECT a.*,c.CATEGORYNAME,b.MSG_STATUS FROM message_details a, message b,category c where b.IS_DELETED=0 and a.message_id = b.message_id and b.category_id=c.categoryId and a.MESSAGE_LANG=1 and  b.username ="
			 * +String.valueOf(user.getUserId()) +
			 * "  and b.EXPIRY_AT>=SYSDATE and b.zone In(select ZONE_ID from USER_ZONE where user_ID="
			 * +String.valueOf(user.getUserId()) +
			 * ") and b.CHANNEL IN(select CHANNEL_ID from USER_CHANNEL where user_ID="
			 * +String.valueOf(user.getUserId())
			 * +") ORDER BY b.CREATED_AT desc";
			 */
			/*
			 * String sql=
			 * "SELECT a.*,c.CATEGORYNAME,b.MSG_STATUS,mz.ZONE_ID FROM message_details a, message b,category c,MESSAGE_ZONE mz "
			 * + " where b.IS_DELETED=0 and a.message_id = b.message_id " +
			 * " and b.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select ZONE_ID from USER_ZONE where USER_ID="
			 * +String.valueOf(user.getUserId())+") " +
			 * " and b.category_id=c.categoryId and a.MESSAGE_LANG=1 " +
			 * " and  b.username ="+String.valueOf(user.getUserId())+
			 * " and b.EXPIRY_AT>=SYSDATE ORDER BY b.CREATED_AT desc";
			 */
			/*
			 * String sql=
			 * "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS,mz.ZONE_ID,mc.CHANNEL_ID"
			 * +
			 * " FROM message_details md, message m,category c ,MESSAGE_ZONE mz,MESSAGE_CHANNEL mc"
			 * +
			 * " where m.IS_DELETED=0 and md.message_id = m.message_id and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and  m.username ="
			 * +String.valueOf(user.getUserId()) +
			 * " and mz.ZONE_ID IN(Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
			 * +
			 * String.valueOf(user.getUserId())+") and m.MESSAGE_ID=mz.MESSAGE_ID"
			 * +
			 * " and mc.CHANNEL_ID IN (SELECT USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID = "
			 * +
			 * String.valueOf(user.getUserId())+") and mc.MESSAGE_ID=m.MESSAGE_ID"
			 * +" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
			 */
			String sql = "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS"
					+ " FROM message_details md, message m,category c "
					+ " where m.IS_DELETED=0 "
					+ " and md.message_id = m.message_id "
					+ " and m.category_id=c.categoryId "
					+ " and md.MESSAGE_LANG=1 "
					+ "and  m.username ="
					+ String.valueOf(user.getUserId())
					// +" and m.EXPIRY_AT>=SYSDATE"
					+ " ORDER BY m.CREATED_AT desc";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

				// String enc1 =
				// String.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"));
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String msgData = resultSet.getString("MESSAGE_DATA");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String msgLink = resultSet.getString("MESSAGE_LINK");
				String msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");
				// String enc7 =
				// String.valueOf(resultSet.getInt("MESSAGE_STATUS"));
				String categoryName = String.valueOf(resultSet
						.getString("CATEGORYNAME"));

				// String MsgDetailID ,String MsgID,String msgData,String
				// MsgTitle,String MsgLink,String msgStatus
				if (messageList.size() == 0) {
					messageList.add(new Message(msgID, msgData, msgTitle,
							msgLink, String.valueOf(
									resultSet.getInt("MSG_STATUS"))
									.equalsIgnoreCase("0") ? "Pending"
									: "Approved", msgLink2, categoryName));
				}
				boolean flagToadd = true;
				for (int i = 0; i < messageList.size(); i++) {
					if (Integer.parseInt(messageList.get(i).msgID) == Integer
							.parseInt(msgID)) {
						flagToadd = false;
					}
				}
				if (flagToadd) {
					messageList.add(new Message(msgID, msgData, msgTitle,
							msgLink, String.valueOf(
									resultSet.getInt("MSG_STATUS"))
									.equalsIgnoreCase("0") ? "Pending"
									: "Approved", msgLink2, categoryName));
				}

				/*
				 * messageList.add(new Message( msgID, msgData, msgTitle,
				 * msgLink,
				 * String.valueOf(resultSet.getInt("MSG_STATUS")).equalsIgnoreCase
				 * ("0") ? "Pending" : "Approved", msgLink2,categoryName));
				 */
				// messageList.add(new
				// Message(String.valueOf(resultSet.getInt(1)),
				// String.valueOf(resultSet.getInt(2)), resultSet.getString(3),
				// resultSet.getString(4), resultSet.getString(5)));
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		// model.addAttribute("message", new Message());
		return "mymessages";

	}

	// Suddddddddddddddd
	@RequestMapping(value = URIConstants.MESSAGES_STATUS, method = RequestMethod.GET)
	public String messagesStatus(HttpServletResponse response, Model model,
			HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<MessageStatus> messageList = new ArrayList<MessageStatus>();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		System.out.println("inside messagesStatus "  );
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			User user = (User) session.getAttribute("user");
			// SELECT a.*,c.CATEGORYNAME FROM message_details a, message
			// b,category c where b.IS_DELETED=0 and a.message_id = b.message_id
			// and b.category_id=c.categoryId and a.MESSAGE_LANG=1 and
			// b.username =2 and b.EXPIRY_AT>=SYSDATE ORDER BY b.CREATED_AT desc
			// String
			// sql="SELECT a.*,c.CATEGORYNAME FROM message_details a, message b,category c where b.IS_DELETED=0 and a.message_id = b.message_id and b.category_id=c.categoryId and a.MESSAGE_LANG=1 and  b.username ="+String.valueOf(user.getUserId())+"  and b.EXPIRY_AT>=SYSDATE ORDER BY b.CREATED_AT desc";

			/*
			 * SELECT a.*,c.CATEGORYNAME,b.MSG_STATUS,b.ZONE, b.CHANNEL FROM
			 * message_details a, message b,category c where b.IS_DELETED=0 and
			 * a.message_id = b.message_id and b.category_id=c.categoryId and
			 * a.MESSAGE_LANG=1 and b.username =3 and b.EXPIRY_AT>=SYSDATE and
			 * b.zone In(select ZONE_ID from USER_ZONE where user_ID=3) and
			 * b.CHANNEL IN(select CHANNEL_ID from USER_CHANNEL where user_ID=3)
			 * ORDER BY b.CREATED_AT desc;
			 */
			/*
			 * String sql=
			 * "SELECT a.*,c.CATEGORYNAME,b.MSG_STATUS FROM message_details a, message b,category c where b.IS_DELETED=0 and a.message_id = b.message_id and b.category_id=c.categoryId and a.MESSAGE_LANG=1 and  b.username ="
			 * +String.valueOf(user.getUserId()) +
			 * "  and b.EXPIRY_AT>=SYSDATE and b.zone In(select ZONE_ID from USER_ZONE where user_ID="
			 * +String.valueOf(user.getUserId()) +
			 * ") and b.CHANNEL IN(select CHANNEL_ID from USER_CHANNEL where user_ID="
			 * +String.valueOf(user.getUserId())
			 * +") ORDER BY b.CREATED_AT desc";
			 */
			/*
			 * String sql=
			 * "SELECT a.*,c.CATEGORYNAME,b.MSG_STATUS,mz.ZONE_ID FROM message_details a, message b,category c,MESSAGE_ZONE mz "
			 * + " where b.IS_DELETED=0 and a.message_id = b.message_id " +
			 * " and b.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select ZONE_ID from USER_ZONE where USER_ID="
			 * +String.valueOf(user.getUserId())+") " +
			 * " and b.category_id=c.categoryId and a.MESSAGE_LANG=1 " +
			 * " and  b.username ="+String.valueOf(user.getUserId())+
			 * " and b.EXPIRY_AT>=SYSDATE ORDER BY b.CREATED_AT desc";
			 */
			/*
			 * String sql=
			 * "SELECT  md.MESSAGE_DETAIL_ID ,md.MESSAGE_ID,md.MESSAGE_TITLE,md.MESSAGE_DATA,md.MESSAGE_LINK,md.MESSAGE_IMG_LINK,c.CATEGORYNAME,m.MSG_STATUS,mz.ZONE_ID,mc.CHANNEL_ID"
			 * +
			 * " FROM message_details md, message m,category c ,MESSAGE_ZONE mz,MESSAGE_CHANNEL mc"
			 * +
			 * " where m.IS_DELETED=0 and md.message_id = m.message_id and m.category_id=c.categoryId and md.MESSAGE_LANG=1 and  m.username ="
			 * +String.valueOf(user.getUserId()) +
			 * " and mz.Z hONE_ID IN(Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
			 * +
			 * String.valueOf(user.getUserId())+") and m.MESSAGE_ID=mz.MESSAGE_ID"
			 * +
			 * " and mc.CHANNEL_ID IN (SELECT USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID = "
			 * +
			 * String.valueOf(user.getUserId())+") and mc.MESSAGE_ID=m.MESSAGE_ID"
			 * +" and m.EXPIRY_AT>=SYSDATE ORDER BY m.CREATED_AT desc";
			 */
			String sql = "SELECT  distinct md.MESSAGE_ID,md.MESSAGE_TITLE,"
					+ "  (select count(r.message_id) from read_reciept r where md.message_id = r.message_id  )as readcount ,"
					// +" where m.IS_DELETED=0 "
					//+ "(select count(l.message_id) from favourite f where md.message_id = f.message_id  )as likecount, "
					+ "(select count(f.message_id) from favourite f where f.message_id= md.message_id  )as favcount, "
					
					// +" and f.message_id=l.MESSAGE_ID "
					+ " (select count(l.message_id) from message_like l where md.message_id = l.message_id  )as likecount "
					+ "FROM message_details md, read_reciept r, favourite f, message_like l"
					+ " where md.message_id = r.message_id (+)"
					+ "and md.message_id =f.message_id (+) "
					+ "and md.message_id =l.message_id (+) "
					+ "and md.MESSAGE_LANG=1 order by md.message_id";

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			System.out.println("@@@@@@@@Tush" + sql);


			while (resultSet.next()) {

				// JNCryptor cryptor = new AES256JNCryptor();
				// String password = "aes123";

				// String enc1 =
				// String.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"));
				String msgID = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				// String msgData = resultSet.getString("MESSAGE_DATA");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				// String msgLink = resultSet.getString("MESSAGE_LINK");
				// String msgLink2 = resultSet.getString("MESSAGE_IMG_LINK");
				// String enc7 =
				// String.valueOf(resultSet.getInt("MESSAGE_STATUS"));
				// String categoryName =
				// String.valueOf(resultSet.getString("CATEGORYNAME"));
				String readcount = resultSet.getString("readcount");
				String likecount = resultSet.getString("likecount");
				String favoritecount = resultSet.getString("favoritecount");

				// String MsgDetailID ,String MsgID,String msgData,String
				// MsgTitle,String MsgLink,String msgStatus
				if (messageList.size() == 0) {
					messageList.add(new MessageStatus(msgID, msgTitle,
							readcount, likecount, favoritecount));
					model.addAttribute("messageList", messageList);
				}
				boolean flagToadd = true;
				for (int i = 0; i < messageList.size(); i++) {
					if (Integer.parseInt(messageList.get(i).msgID) == Integer
							.parseInt(msgID)) {
						flagToadd = false;
					}
				}
				if (flagToadd) {
					messageList.add(new MessageStatus(msgID, msgTitle,
							readcount, likecount, favoritecount));
				}

				/*
				 * messageList.add(new Message( msgID, msgData, msgTitle,
				 * msgLink,
				 * String.valueOf(resultSet.getInt("MSG_STATUS")).equalsIgnoreCase
				 * ("0") ? "Pending" : "Approved", msgLink2,categoryName));
				 */
				// messageList.add(new
				// Message(String.valueOf(resultSet.getInt(1)),
				// String.valueOf(resultSet.getInt(2)), resultSet.getString(3),
				// resultSet.getString(4), resultSet.getString(5)));
			}
			 stmt=null;
			resultSet=null;
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		// String sqlfav="select * from favourite where message_id="+ msgID +"";

		model.addAttribute("messageList", messageList);
		// model.addAttribute("message", new Message());
		return "messageStatus";

	}

	// Suddddddddddddddddd

	@RequestMapping(value = URIConstants.PENDING_MESSAGES, method = RequestMethod.GET)
	public String pendingMessages(Model model, HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");

		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");

			// String sql =
			// "select * from message_details where Message_LANG=1  and Message_status=0";
			String sql = "select * from message_details  where Message_LANG=1  and MESSAGE_ID IN (select MESSAGE_ID from message where MSG_STATUS=0 and IS_DELETED=0)";

			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";

				String msg_detail_Id = String.valueOf(resultSet
						.getInt("MESSAGE_DETAIL_ID"));
				String msg_Id = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String msg_data = resultSet.getString("MESSAGE_DATA");
				String msg_title = resultSet.getString("MESSAGE_TITLE");
				String msg_link = resultSet.getString("MESSAGE_LINK");
				String msg_img_link = resultSet.getString("MESSAGE_IMG_LINK");

				// String MsgDetailID ,String MsgID,String msgData,String
				// MsgTitle,String MsgLink,String msgStatus
				/*
				 * messageList.add(new Message(msg_Id, msg_data, msg_title,
				 * msg_link,msg_img_link,"0",
				 * String.valueOf(resultSet.getInt("MESSAGE_STATUS"
				 * )).equalsIgnoreCase("0") ? "Pending" : "Approved", "0"));
				 */
				messageList.add(new Message(msg_Id, msg_data, msg_title,
						msg_link, msg_img_link, "0", "Pending", "0"));
				// messageList.add(new
				// Message(String.valueOf(resultSet.getInt(1)),
				// String.valueOf(resultSet.getInt(2)), resultSet.getString(3),
				// resultSet.getString(4), resultSet.getString(5)));
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		// model.addAttribute("message", new Message());
		return "pendingmessages";

	}

	@RequestMapping(value = URIConstants.APPROVE_MESSAGES, method = RequestMethod.GET)
	public String approveMessage(@PathVariable("id") int id, Model model,
			HttpServletResponse response) {
		// @RequestMapping(value="/approvemessages", method =
		// RequestMethod.POST)
		// public String approveMessage(HttpServletRequest request,
		// @RequestParam("id") int id, Model model) {
		String datefromdb = "";
		String category_Id = "";
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();

			// update msg status
			String sql = "UPDATE MESSAGE SET MSG_STATUS = 1 WHERE Message_Id="
					+ id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);

			String sqlGetCategory = "select CATEGORY_ID from MESSAGE where MESSAGE_ID="
					+ id;
			Statement stmtGetCategory = conn.createStatement();
			ResultSet resultSetGetCat = stmtGetCategory
					.executeQuery(sqlGetCategory);
			while (resultSetGetCat.next()) {
				category_Id = resultSetGetCat.getString("CATEGORY_ID");
			}

			model.addAttribute("prumsg", "before sendNotificationForAndroid");
			// For android
			sendNotificationForAndroid(id, category_Id, model);
			PrintWriter script = response.getWriter();

			// send notification in approve msg
			String msgToSend = "";
			String zone = "";
			String channel = "";
			List<Devices> devices = new ArrayList<Devices>();

			// String
			// sqlGetMessage="Select UNIQUE md.*,m.CREATED_AT,m.ZONE,m.CHANNEL from message_details md,message m where md.message_id="+id+" and md.MESSAGE_LANG=1 and m.MESSAGE_ID="+id;
			String sqlGetMessage = "Select UNIQUE md.*,m.CREATED_AT,mz.ZONE_ID,mc.CHANNEL_ID from message_details md,message m ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc"
					+ " where md.message_id="
					+ id
					+ " and md.MESSAGE_LANG=1 and m.MESSAGE_ID="
					+ id
					+ " and mz.MESSAGE_ID=" + id + " and mc.MESSAGE_ID=" + id;
			stmt = conn.createStatement();
			String date = "";
			ResultSet resultset = stmt.executeQuery(sqlGetMessage);

			ArrayList<Integer> zoneIds = new ArrayList<Integer>();
			ArrayList<Integer> CHANNEL_IDs = new ArrayList<Integer>();
			while (resultset.next()) {
				msgToSend = resultset.getString("MESSAGE_TITLE");
				zoneIds.add(resultset.getInt("ZONE_ID"));
				CHANNEL_IDs.add(resultset.getInt("CHANNEL_ID"));
				date = resultset.getString("CREATED_AT");
				// msgToSend=String.valueOf(resultset.getInt("ZONE_ID"));
			}

			if (date != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date date2 = dateFormat.parse(date);
				long time = date2.getTime();

				String date3 = dateFormat.format(new Date());
				Date date4 = dateFormat.parse(date3);
				long time2 = date4.getTime();
				Timestamp t1 = new Timestamp(time);
				Timestamp t2 = new Timestamp(new Date().getTime());
				// String date3=""+date2;
				// String date4=dateFormat.format();
				/*
				 * if(time<=time2) {
				 */
				// For develoement
				/*
				 * ApnsService service = APNS.newService() .withCert(
				 * "D:/Varsha/apache-tomcat-8.0.24/webapps/ROOT/Apple/aps_dev_java.p12"
				 * , "jktls1234") .withSandboxDestination().build();
				 */

				String base = System.getProperty("catalina.home");
				File cerDir = new File(base + File.separator + cerFolder);

				if (!cerDir.exists()) {
					cerDir.mkdirs();
				}
				// For production
				ApnsService service = APNS
						.newService()
						.withCert(
								base + File.separator + File.separator
										+ cerFolder
										+ "PruBSNNotificationCertificate.p12",
								"prubsn@123").withProductionDestination()
						.build();

				/*
				 * String sql1 = "select DEVICE_TOKEN,USER_ID from Devices";
				 * Statement stmt1 = conn.createStatement(); ResultSet resultSet
				 * = stmt1.executeQuery(sql1);
				 */

				int userId = 0;

				String sqlGetAllUSers = "select USERID from User_M";
				Statement stmtGetAllUsers = conn.createStatement();
				ResultSet resultSetGetAllUsers = stmtGetAllUsers
						.executeQuery(sqlGetAllUSers);
				while (resultSetGetAllUsers.next()) {
					int readCount = 0;
					int badgeCount = 0;

					userId = resultSetGetAllUsers.getInt("USERID");

					String sqlGetBadge = "select DEVICE_BADGE_COUNT from BADGE_COUNT where USER_ID="
							+ userId;
					Statement stmtGetBadge = conn.createStatement();
					ResultSet resultSetBadge = stmtGetBadge
							.executeQuery(sqlGetBadge);
					while (resultSetBadge.next()) {
						badgeCount = resultSetBadge
								.getInt("DEVICE_BADGE_COUNT");
					}

					String sqlGetReadCount = "select count(*) from read_reciept where user_Id="
							+ userId;
					Statement stmtGetReadCount = conn.createStatement();
					ResultSet resultSetReadCount = stmtGetReadCount
							.executeQuery(sqlGetReadCount);
					while (resultSetReadCount.next()) {
						readCount = resultSetReadCount.getInt(1);
					}

					int badgeCountToSend = 1;
					if (badgeCount != 0 && badgeCount > readCount) {
						if (readCount == 0) {
							badgeCountToSend = badgeCount + 1;
						} else {
							badgeCountToSend = badgeCount - readCount;
						}

					}
					/*
					 * String sqlGetDeviceForUser=
					 * "select DEVICE_TOKEN from devices where USER_ID="+userId;
					 * Statement stmtGetDevicesForUser=conn.createStatement();
					 * ResultSet
					 * resultSetDevicesForUser=stmtGetDevicesForUser.executeQuery
					 * (sqlGetDeviceForUser);
					 * while(resultSetDevicesForUser.next()) { String
					 * deviceToken
					 * =resultSetDevicesForUser.getString("DEVICE_TOKEN");
					 * devices.add(new Devices(deviceToken,
					 * String.valueOf(badgeCountToSend))); }
					 * 
					 * //UPDATE BADGE_COUNT SET BADGE_COUNT = '1' WHERE
					 * USER_ID=2; badgeCount=badgeCount+1; String
					 * sqlInsertBadgeCount
					 * ="UPDATE BADGE_COUNT SET DEVICE_BADGE_COUNT = "
					 * +badgeCount+" WHERE USER_ID="+userId; Statement
					 * stmtBadgeCount=conn.createStatement();
					 * stmtBadgeCount.executeUpdate(sqlInsertBadgeCount);
					 */
					// msgToSend=""+badgeCountToSend;
					for (int i = 0; i < zoneIds.size(); i++) {
						for (int j = 0; j < CHANNEL_IDs.size(); j++) {
							String sqlisValidUser = "select uz.*, uc.* from USER_ZONE uz,USER_CHANNEL uc"
									+ " where uz.USER_ID="
									+ userId
									+ " and uz.ZONE_ID="
									+ zoneIds.get(i)
									+ " and uc.USER_ID="
									+ userId
									+ " and uc.CHANNEL_ID=" + CHANNEL_IDs.get(j);
							Statement stmtisValidUser = conn.createStatement();
							// msgToSend=sqlisValidUser;
							ResultSet resultSetIsValidUser = stmtisValidUser
									.executeQuery(sqlisValidUser);
							if (resultSetIsValidUser != null) {
								while (resultSetIsValidUser.next()) {
									// msgToSend="new"+sqlisValidUser;
									String sqlGetDeviceForUser = "select DEVICE_TOKEN from devices where USER_ID="
											+ userId;
									Statement stmtGetDevicesForUser = conn
											.createStatement();
									ResultSet resultSetDevicesForUser = stmtGetDevicesForUser
											.executeQuery(sqlGetDeviceForUser);
									while (resultSetDevicesForUser.next()) {
										String deviceToken = resultSetDevicesForUser
												.getString("DEVICE_TOKEN");
										devices.add(new Devices(
												deviceToken,
												String.valueOf(badgeCountToSend)));
									}

									// UPDATE BADGE_COUNT SET BADGE_COUNT = '1'
									// WHERE USER_ID=2;
									badgeCount = badgeCount + 1;
									String sqlInsertBadgeCount = "UPDATE BADGE_COUNT SET DEVICE_BADGE_COUNT = "
											+ badgeCount
											+ " WHERE USER_ID="
											+ userId;
									Statement stmtBadgeCount = conn
											.createStatement();
									stmtBadgeCount
											.executeUpdate(sqlInsertBadgeCount);
								}

							}
						}
					}
					/*
					 * String sqlGetDeviceForUser=
					 * "select DEVICE_TOKEN from devices where USER_ID="+userId;
					 * Statement stmtGetDevicesForUser=conn.createStatement();
					 * ResultSet
					 * resultSetDevicesForUser=stmtGetDevicesForUser.executeQuery
					 * (sqlGetDeviceForUser);
					 * while(resultSetDevicesForUser.next()) { String
					 * deviceToken
					 * =resultSetDevicesForUser.getString("DEVICE_TOKEN");
					 * devices.add(new Devices(deviceToken,
					 * String.valueOf(badgeCountToSend))); }
					 * 
					 * //UPDATE BADGE_COUNT SET BADGE_COUNT = '1' WHERE
					 * USER_ID=2; badgeCount=badgeCount+1; String
					 * sqlInsertBadgeCount
					 * ="UPDATE BADGE_COUNT SET DEVICE_BADGE_COUNT = "
					 * +badgeCount+" WHERE USER_ID="+userId; Statement
					 * stmtBadgeCount=conn.createStatement();
					 * stmtBadgeCount.executeUpdate(sqlInsertBadgeCount);
					 */

				}

				for (int i = 0; i < devices.size(); i++) {

					Devices device = devices.get(i);
					String token = device.getToken();
					String badge = device.getBadgeCount();
					/*
					 * //String token =devices.get(i); String payload =
					 * APNS.newPayload().alertBody(msgToSend+"===="+badge)
					 * .build(); service.push(token, payload);
					 */

					String payload = APNS.newPayload()
							.badge(Integer.parseInt(badge))
							.customField("categoryId", category_Id)
							.customField("MessageId", id)
							.localizedKey(msgToSend).actionKey("Play").build();

					int now = (int) (new Date().getTime() / 1000);

					EnhancedApnsNotification notification = new EnhancedApnsNotification(
							EnhancedApnsNotification.INCREMENT_ID() /* Next ID */,
							now + 60 * 60 /* Expire in one hour */, token /*
																		 * Device
																		 * Token
																		 */,
							payload);

					service.push(notification);
				}
				// } time if
			}

		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
			
			e.printStackTrace();
		} catch (NetworkIOException ex) {

		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		// return "pendingmessages";
		return "msgapproved";
		// return ""+datefromdb;
	}

	public void sendNotificationForAndroid(int id, String category_Id,
			Model model) {
		model.addAttribute("prumsg", "sendNotificationForAndroid");
		String msgToSend = "";
		List<Devices> devices = new ArrayList<Devices>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();
			
			String sqlGetMessage = "Select UNIQUE md.*,m.CREATED_AT from message_details md,message m where md.message_id="
					+ id + " and md.MESSAGE_LANG=1 and m.MESSAGE_ID=" + id;
			Statement stmt = conn.createStatement();
			String date = "";
			ResultSet resultset = stmt.executeQuery(sqlGetMessage);
			while (resultset.next()) {
				msgToSend = resultset.getString("MESSAGE_TITLE");
				date = resultset.getString("CREATED_AT");
			}
			model.addAttribute("prumsg", "sendNotificationForAndroid"
					+ msgToSend);
			if (date != null) {

				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date date2 = dateFormat.parse(date);
				long time = date2.getTime();

				String date3 = dateFormat.format(new Date());
				Date date4 = dateFormat.parse(date3);
				long time2 = date4.getTime();
				Timestamp t1 = new Timestamp(time);
				Timestamp t2 = new Timestamp(new Date().getTime());
				// String date3=""+date2;
				// String date4=dateFormat.format();
				/*
				 * if(time<=time2) {
				 */

				int readCount = 0;
				int badgeCount = 0;
				int userId = 0;

				String sqlGetAllUSers = "select USERID from User_M";
				Statement stmtGetAllUsers = conn.createStatement();
				ResultSet resultSetGetAllUsers = stmtGetAllUsers
						.executeQuery(sqlGetAllUSers);
				while (resultSetGetAllUsers.next()) {
					userId = resultSetGetAllUsers.getInt("USERID");
					model.addAttribute("prumsg",
							"sendNotificationForAndroid userId" + userId);
					String sqlGetBadge = "select DEVICE_BADGE_COUNT from BADGE_COUNT where USER_ID="
							+ userId;
					model.addAttribute("prumsg",
							"sendNotificationForAndroid sqlGetBadge"
									+ sqlGetBadge);
					Statement stmtGetBadge = conn.createStatement();
					ResultSet resultSetBadge = stmtGetBadge
							.executeQuery(sqlGetBadge);
					while (resultSetBadge.next()) {
						badgeCount = resultSetBadge
								.getInt("DEVICE_BADGE_COUNT");
					}
					model.addAttribute("prumsg",
							"sendNotificationForAndroid badgeCount"
									+ badgeCount);
					String sqlGetReadCount = "select count(*) from read_reciept where user_Id="
							+ userId;
					Statement stmtGetReadCount = conn.createStatement();
					ResultSet resultSetReadCount = stmtGetReadCount
							.executeQuery(sqlGetReadCount);
					while (resultSetReadCount.next()) {
						readCount = resultSetReadCount.getInt(1);
					}
					model.addAttribute("prumsg",
							"sendNotificationForAndroid readCount" + readCount);
					int badgeCountToSend = 1;
					if (badgeCount != 0 && badgeCount > readCount) {
						if (readCount == 0) {
							badgeCountToSend = badgeCount + 1;
						} else {
							badgeCountToSend = badgeCount - readCount;
						}

					}

					String sqlGetDeviceForUser = "select DEVICE_TOKEN from devices_android where USER_ID="
							+ userId;
					Statement stmtGetDevicesForUser = conn.createStatement();
					ResultSet resultSetDevicesForUser = stmtGetDevicesForUser
							.executeQuery(sqlGetDeviceForUser);
					while (resultSetDevicesForUser.next()) {

						String deviceToken = resultSetDevicesForUser
								.getString("DEVICE_TOKEN");
						// model.addAttribute("script",
						// "deviceToken="+deviceToken);
						// model.addAttribute("script1", "   badgeCountToSend"+
						// badgeCountToSend);
						devices.add(new Devices(deviceToken, String
								.valueOf(badgeCountToSend)));
						// devices.add(new Devices(deviceToken,
						// String.valueOf(badgeCount)));
						model.addAttribute("prumsg",
								"sendNotificationForAndroid deviceToken"
										+ deviceToken);
					}

					// UPDATE BADGE_COUNT SET BADGE_COUNT = '1' WHERE USER_ID=2;
					badgeCount = badgeCount + 1;
					model.addAttribute("prumsg",
							"sendNotificationForAndroid badgeCount"
									+ badgeCount);
					// UPDATE BADGE_COUNT SET DEVICE_BADGE_COUNT =
					String sqlInsertBadgeCount = "UPDATE BADGE_COUNT SET DEVICE_BADGE_COUNT = "
							+ badgeCount + " WHERE USER_ID=" + userId;
					Statement stmtBadgeCount = conn.createStatement();
					stmtBadgeCount.executeUpdate(sqlInsertBadgeCount);
				}
				model.addAttribute("prumsg",
						"before sendNotificationForAndroid" + category_Id + id
								+ msgToSend + devices);
				sendMulptipleNotifiaction(category_Id, id, msgToSend, devices,
						model);

				// } time if

			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

	}

	// private String sendMulptipleNotifiaction(String message, ArrayList
	// devices)
	private String sendMulptipleNotifiaction(String category_Id, int msg_id,
			String msgToSend, List<Devices> devices, Model model) {
		Sender sender = new Sender("AIzaSyDj7ehkkT0Qcp_p630JCdwZ3h5fokfjTUY"); // Here
																				// you
																				// will
																				// write
																				// APP
																				// key
																				// given
																				// by
																				// Android
																				// end
		for (int i = 0; i < devices.size(); i++) {
			Devices device = devices.get(i);
			String token = device.getToken();
			String badge = device.getBadgeCount();

			com.google.android.gcm.server.Message msg = new com.google.android.gcm.server.Message.Builder()
					.addData("badgeCount", badge)
					.addData("categoryId", category_Id)
					.addData("msgId", String.valueOf(msg_id))
					.addData("message", msgToSend).build();
			// "message", message).build();
			model.addAttribute("prumsg", msg);
			String str = null;
			try {
				Result results = sender.send(msg, token, 5); // Where appId is
																// given by
																// Android end

				if (results.getMessageId() != null) {
					str = "true";
				} else {
					str = "false";
					String error = results.getErrorCodeName();
					// logger.info("message sending failed:: "+error);
					if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		return "";

		/*
		 * Sender sender = new
		 * Sender("AIzaSyDj7ehkkT0Qcp_p630JCdwZ3h5fokfjTUY");// Here you will
		 * write APP key given by Android end
		 * com.google.android.gcm.server.Message msg = new
		 * com.google.android.gcm.server.Message.Builder().addData("message",
		 * message).build(); String str=null; try { MulticastResult result =
		 * sender.send(msg, devices, 5); // where devices is the list of
		 * multiple device AppId's for (Result r : result.getResults()) { if
		 * (r.getMessageId() != null) { str = "true"; } else { str= "false";
		 * String error = r.getErrorCodeName(); if
		 * (error.equals(Constants.ERROR_NOT_REGISTERED)) { } } } } catch
		 * (IOException e) { e.printStackTrace(); } return str;
		 */
	}

	/*
	 * @RequestMapping(value="/mymessages", method = RequestMethod.GET) public
	 * String messages(@Valid @ModelAttribute("message") Message message,
	 * BindingResult result, Model model) {
	 * 
	 * List<ObjectArrays> messageList = new ArrayList<ObjectArrays>();
	 * 
	 * //List<Message> messageList =new ArrayList<Message>();
	 * 
	 * try { Class.forName(ConstantDb.driverClassName); Connection
	 * conn=DriverManager.getConnection(
	 * "jdbc:oracle:thin:@localhost:1521:crmuser",username,password); String
	 * sql;
	 * 
	 * int lang=1; sql=
	 * "SELECT MESSAGE_DETAIL_ID FROM message_details where MESSAGE_LANG="+lang+
	 * " and MESSAGE_STATUS=1";
	 * 
	 * 
	 * Statement stmt=conn.createStatement(); ResultSet
	 * resultSet=stmt.executeQuery(sql);
	 * 
	 * while(resultSet.next()) { messageList.add(type name = new type(););
	 * 
	 * //messageList.add(new Message(String.valueOf(resultSet.getInt(1)),
	 * String.valueOf(resultSet.getInt(1)), resultSet.getString(1),
	 * resultSet.getString(1), resultSet.getString(1)));
	 * model.addAttribute("messageitem",
	 * "messages="+String.valueOf(resultSet.getInt(1))+" "
	 * +String.valueOf(resultSet.getInt(2))+" "+ resultSet.getString(3)+" "
	 * +resultSet.getString(4)+" "+resultSet.getString(5));
	 * //model.addAttribute("messageitem", "messages="+messageList.size()); } }
	 * catch (ClassNotFoundException e) { 
	 * e.printStackTrace(); } catch (SQLException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * model.addAttribute("messageList", messageList);
	 * //model.addAttribute("message", new Message()); return "mymessages"; }
	 */
	/*	 *//**
	 * Upload single file using Spring Controller
	 */
	/*
	 * @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	 * public @ResponseBody String uploadFileHandler(@RequestParam("name")
	 * String name,
	 * 
	 * @RequestParam("file") MultipartHttpServletRequest request) {
	 * 
	 * MultipartFile file=request.getFile("file"); if (!file.isEmpty()) { try {
	 * byte[] bytes = file.getBytes();
	 * 
	 * // Creating the directory to store file String rootPath =
	 * System.getProperty("catalina.home"); File dir = new File(rootPath +
	 * File.separator + "tmpFiles"); if (!dir.exists()) dir.mkdirs();
	 * 
	 * // Create the file on server File serverFile = new
	 * File(dir.getAbsolutePath() + File.separator + name); BufferedOutputStream
	 * stream = new BufferedOutputStream( new FileOutputStream(serverFile));
	 * stream.write(bytes); stream.close();
	 * 
	 * logger.info("Server File Location=" + serverFile.getAbsolutePath());
	 * 
	 * return "You successfully uploaded file=" + name; } catch (Exception e) {
	 * return "You failed to upload " + name + " => " + e.getMessage(); } } else
	 * { return "You failed to upload " + name + " because the file was empty.";
	 * } }
	 */

	// consumes = MediaType.MULTIPART_FORM_DATA , produces =
	// MediaType.APPLICATION_JSON_VALUE, consumes =
	// MediaType.MULTIPART_FORM_DATA

	public String createFile(MultipartFile file) {
		String fileName = "";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				fileName = FilenameUtils.getName(file.getOriginalFilename());
				String type = fileName.substring(fileName.lastIndexOf("."));
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				fileName = name.trim() + "_" + new Date().getTime() + type;
				String base = System.getProperty("catalina.home");

				File dir = new File(base + File.separator + UploadFolder);
				// For Android
				File dirAndroid = new File(base + File.separator
						+ UploadFolderAndroid);

				if (!dir.exists()) {
					dir.mkdirs();
				}
				// For ANdroid
				if (!dirAndroid.exists()) {
					dirAndroid.mkdirs();
				}
				File dateDir = new File(base + File.separator + UploadFolder
						+ File.separator + File.separator);
				// For Android
				File dateDirAndroid = new File(base + File.separator
						+ UploadFolderAndroid + File.separator + File.separator);

				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}
				// For ANdroid
				if (!dateDirAndroid.exists()) {
					dateDirAndroid.mkdirs();
				}

				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + fileName);

				// For Android
				File uploadedFileAndroid = new File(
						dateDirAndroid.getAbsolutePath() + File.separator
								+ fileName);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));
				// For Android
				BufferedOutputStream streamAndroid = new BufferedOutputStream(
						new FileOutputStream(uploadedFileAndroid));

				stream.write(bytes);
				streamAndroid.write(bytes);
				stream.close();
				streamAndroid.close();
				writeEncryptedFile(uploadedFileAndroid, fileName);
				// if(type.equalsIgnoreCase(".jpg") ||
				// type.equalsIgnoreCase(".png") ||
				// type.equalsIgnoreCase(".jpeg"))
				// {
				// Thumbnails.of(uploadedFile).size(600,
				// 600).toFile(uploadedFile);
				// Thumbnails.of(uploadedFileAndroid).size(600,
				// 600).toFile(uploadedFileAndroid);
				// }
				// Commented by sudd
				// writeEncryptedFile(uploadedFile, fileName);
				// writeEncryptedFile(uploadedFileAndroid, fileName);

			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return fileName;
	}

	/* Sudd Starts Here For Android File */
	public String createFileForAndroid(MultipartFile file) {
		String fileName = "";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				fileName = FilenameUtils.getName(file.getOriginalFilename());
				String type = fileName.substring(fileName.lastIndexOf("."));
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				fileName = name.trim() + "_" + new Date().getTime() + type;
				String base = System.getProperty("catalina.home");

				File dir = new File(base + File.separator + UploadFolderAndroid);

				if (!dir.exists()) {
					dir.mkdirs();
				}
				File dateDir = new File(base + File.separator
						+ UploadFolderAndroid + File.separator + File.separator);

				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}

				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + fileName);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));

				stream.write(bytes);
				stream.close();

				if (type.equalsIgnoreCase(".jpg")
						|| type.equalsIgnoreCase(".png")
						|| type.equalsIgnoreCase(".jpeg")) {
					Thumbnails.of(uploadedFile).size(600, 600)
							.toFile(uploadedFile);
				}
				// Commented by sudd
				// writeEncryptedFile(uploadedFile, fileName);

			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return fileName;
	}

	/* Sudd Ends Here For Anroid File */

	@RequestMapping(value = URIConstants.ADD_MESSAGES, method = RequestMethod.POST)
	public String addMessage(HttpServletResponse response,
			@Valid @ModelAttribute("message") Message message,
			BindingResult result, Model model, HttpServletRequest request,
			@RequestParam("file_image") MultipartFile file_image,
			@RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file2_image") MultipartFile file2_image)
			throws IOException {

		List<String> devices = new ArrayList<String>();
		List<Category> categoryList = messageService.getCategoryList();
		model.addAttribute("message", new Message());
		User user = (User) request.getSession().getAttribute("user");

		String fileName = "";
		String f1 = "";
		String fandroid = "";
		String f2 = "";
		String fandroidimg = "";
		String img_file1 = "";
		String img_file2 = "";
		if (!file_image.isEmpty()) {
			img_file1 = createFile(file_image);

		}
		if (!file2_image.isEmpty()) {
			img_file2 = createFile(file2_image);

		}

		PrintWriter script = response.getWriter();

		if (!file.isEmpty()) {
			try {
				// model.addAttribute("messageName", "in");
				byte[] bytes = file.getBytes();

				fileName = FilenameUtils.getName(file.getOriginalFilename());
				String type = fileName;
				String name = fileName;
				// String

				f1 = name;
				fandroid = name;
				String base = System.getProperty("catalina.home");

				File dir = new File(base + File.separator + UploadFolder);
				/* Test For Android */
				File dirAndroid = new File(base + File.separator
						+ UploadFolderAndroid);

				if (!dir.exists()) {
					dir.mkdirs();
				}
				// For Android
				if (!dirAndroid.exists()) {
					dirAndroid.mkdirs();
				}

				File dateDir = new File(base + File.separator + UploadFolder
						+ File.separator + File.separator);
				// For ANdroid
				File dateDirAndroid = new File(base + File.separator
						+ UploadFolderAndroid + File.separator + File.separator);
				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}
				// For Android
				if (!dateDirAndroid.exists()) {
					dateDirAndroid.mkdirs();
				}

				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + f1);

				// For Android
				File uploadedFileAndroid = new File(
						dateDirAndroid.getAbsolutePath() + File.separator + f1);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));
				BufferedOutputStream streamAndroid = new BufferedOutputStream(
						new FileOutputStream(uploadedFileAndroid));

				stream.write(bytes);
				streamAndroid.write(bytes);
				stream.close();
				streamAndroid.close();

				model.addAttribute("typeFile", "type=" + type);
				if (type.equalsIgnoreCase(".jpg")
						|| type.equalsIgnoreCase(".png")
						|| type.equalsIgnoreCase(".jpeg")) {
					// File resizedFile=new File( base + File.separator +
					// UploadFolder + fileName);
					// size(200, 200) to (600, 600) chg by Bilal
					Thumbnails.of(uploadedFile).size(600, 600)
							.toFile(uploadedFile);
					Thumbnails.of(uploadedFileAndroid).size(600, 600)
							.toFile(uploadedFileAndroid);
				}

				writeEncryptedFile(uploadedFile, f1);
				writeEncryptedFile(uploadedFileAndroid, f1);

			} catch (Exception e) {
				// script.write("ok1212");
				// model.addAttribute("msg", "Exception");

			}
		} else {

		}

		if (!file2.isEmpty()) {
			try {

				byte[] bytes = file2.getBytes();

				fileName = FilenameUtils.getName(file2.getOriginalFilename());
				String type = fileName.substring(fileName.lastIndexOf("."));
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				// String
				f2 = name.trim() + "_" + new Date().getTime() + type;
				fandroidimg = name.trim() + "_" + new Date().getTime() + type;

				String base = System.getProperty("catalina.home");

				File dir = new File(base + File.separator + UploadFolder);
				// For ANdroid
				File dirAndroid = new File(base + File.separator
						+ UploadFolderAndroid);

				if (!dir.exists()) {
					dir.mkdirs();
				}
				// For Android
				if (!dirAndroid.exists()) {
					dirAndroid.mkdirs();
				}

				Date date = new Date();

				File dateDir = new File(base + File.separator + UploadFolder
						+ File.separator + File.separator);
				// For Android
				File dateDirAndroid = new File(base + File.separator
						+ UploadFolderAndroid + File.separator + File.separator);

				if (!dateDir.exists()) {
					dateDir.mkdirs();
				}
				// For ANdroid
				if (!dateDirAndroid.exists()) {
					dateDirAndroid.mkdirs();
				}
				File uploadedFile = new File(dateDir.getAbsolutePath()
						+ File.separator + f2);
				File uploadedFileAndroidImg = new File(
						dateDirAndroid.getAbsolutePath() + File.separator
								+ fandroidimg);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));
				BufferedOutputStream streamAndroidImg = new BufferedOutputStream(
						new FileOutputStream(uploadedFileAndroidImg));

				stream.write(bytes);
				streamAndroidImg.write(bytes);
				stream.close();
				streamAndroidImg.close();
				if (type.equalsIgnoreCase(".jpg")
						|| type.equalsIgnoreCase(".png")
						|| type.equalsIgnoreCase(".jpeg")) {
					// File resizedFile=new File( base + File.separator +
					// UploadFolder + fileName);
					Thumbnails.of(uploadedFile).size(600, 600)
							.toFile(uploadedFile);
					Thumbnails.of(uploadedFileAndroidImg).size(600, 600)
							.toFile(uploadedFileAndroidImg);
				}

				writeEncryptedFile(uploadedFile, f2);
				writeEncryptedFile(uploadedFileAndroidImg, f2);

			} catch (Exception e) {

			}
		} else {

		}
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();
			
			model.addAttribute("messageName123", "postnameafter connection");

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd  hh:mm:ss.SSS");
			model.addAttribute("messageName123", "effective date="
					+ request.getParameter("effdate").toString());
			String effectivedate = request.getParameter("effdate").toString();

			/*
			 * Date parsedeffectiveDate =
			 * dateFormat.parse(request.getParameter("effdate").toString());
			 * Timestamp effectiveTimestamp = new
			 * java.sql.Timestamp(parsedeffectiveDate.getTime()); Date
			 * parsedExpiryDate =
			 * dateFormat.parse(request.getParameter("expdate").toString());
			 * Timestamp expiryTimestamp = new
			 * java.sql.Timestamp(parsedExpiryDate.getTime());
			 */

			// model.addAttribute("messageName123",
			// "parsedeffectiveDate="+parsedeffectiveDate);

			// insert into message (MESSAGE_ID,CREATED_AT,EXPIRY_AT,CATEGORY_ID,
			// USERNAME,ZONE,CHANNEL)values(MESSAGE_SEQ.nextval,TO_DATE(?,
			// 'DD-MM-YYYY:HH12:MI:SS'),TO_DATE(?,'DD-MM-YYYY:HH12:MI:SS'),?,?,?,?)
			String sql = "insert into message  (MESSAGE_ID,CREATED_AT,EXPIRY_AT,CATEGORY_ID, USERNAME,MSG_STATUS,IS_DELETED,IS_EDITED) "
					+ "values(MESSAGE_SEQ.nextval,TO_DATE(?, 'DD-MM-YYYY:HH24:MI:SS'),TO_DATE(?,'DD-MM-YYYY:HH24:MI:SS'),?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, request.getParameter("effdate").toString());
			stmt.setString(2, request.getParameter("expdate").toString());
			stmt.setString(3, request.getParameter("category.categoryId"));
			stmt.setString(4, "" + user.getUserId());
			stmt.setInt(5, 0);
			stmt.setInt(6, 0);
			stmt.setInt(7, 0);

			int queryresult = stmt.executeUpdate();

			String sqlGetMax = "SELECT MAX(MESSAGE_ID) AS id FROM message";
			Statement stmtgetMax = conn.createStatement();
			ResultSet resultSet = stmtgetMax.executeQuery(sqlGetMax);
			int id = 0;
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			model.addAttribute("messageName", id);

			model.addAttribute("message1", request.getParameter("messageName1")
					.toString());
			model.addAttribute("message2", request
					.getParameter("headlineName1").toString());
			model.addAttribute("message3", f2);
			// INSERT INTO "SYSTEM"."MESSAGE_DETAILS" (MESSAGE_DETAIL_ID,
			// MESSAGE_ID, MESSAGE_DATA, MESSAGE_TITLE, MESSAGE_LINK,
			// MESSAGE_LANG, MESSAGE_STATUS) VALUES ('90', '122', 'english
			// data', 'english', 'english.png', '1', '1')

			// INSERT INTO MESSAGE_DETAILS (MESSAGE_DETAIL_ID, MESSAGE_ID,
			// MESSAGE_DATA, MESSAGE_TITLE, MESSAGE_LINK, MESSAGE_LANG,
			// MESSAGE_STATUS, MESSAGE_IMG_LINK) VALUES ('219', '219', 'test',
			// 'test title', 'aa', '1', '1', 'bb')

			String sql1 = "insert into message_details (MESSAGE_DETAIL_ID,MESSAGE_ID,MESSAGE_DATA,MESSAGE_TITLE,MESSAGE_LINK,MESSAGE_LANG, MESSAGE_IMG_LINK,MESSAGE_IMG_LINK_ANDROID) values(MESSAGE_DETAILS_SEQ.nextval,?,?,?,?,?,?,?)";

			PreparedStatement stmt1 = conn.prepareStatement(sql1);
			stmt1.setInt(1, id);
			stmt1.setString(2, request.getParameter("messageName").toString()
					.replaceAll("'", "''"));
			stmt1.setString(3, request.getParameter("headlineName").toString()
					.replaceAll("'", "''"));
			stmt1.setString(4, f1);
			stmt1.setInt(5, 1);
			if (request.getParameter("category.categoryId").equalsIgnoreCase(
					"6"))
				stmt1.setString(6, request.getParameter("videoURL1"));
			else
				stmt1.setString(6, img_file1);
			stmt1.setString(7, fandroid);

			stmt1.executeUpdate();

			String sql11 = "insert into message_details (MESSAGE_DETAIL_ID,MESSAGE_ID,MESSAGE_DATA,MESSAGE_TITLE,MESSAGE_LINK,MESSAGE_LANG, MESSAGE_IMG_LINK,MESSAGE_LINK_ANDROID) values(MESSAGE_DETAILS_SEQ.nextval,?,?,?,?,?,?,?)";

			PreparedStatement stmt11 = conn.prepareStatement(sql11);
			stmt11.setInt(1, id);
			stmt11.setString(2, request.getParameter("messageName1").toString()
					.replaceAll("'", "''"));
			stmt11.setString(3, request.getParameter("headlineName1")
					.toString().replaceAll("'", "''"));
			stmt11.setString(4, f2);
			stmt11.setInt(5, 2);
			if (request.getParameter("category.categoryId").equalsIgnoreCase(
					"6"))
				stmt11.setString(6, request.getParameter("videoURL2"));
			else {
				stmt11.setString(6, img_file2);
			}
			stmt11.setString(7, fandroidimg);
			stmt11.executeUpdate();
			String region = request.getParameter("region");
			System.out.println("REGION:" + region);
			String zone[] = request.getParameterValues("zone");
			String allpamb[] = request.getParameterValues("allpamb");
			String allpbtb[] = request.getParameterValues("allpbtb");
			String channel[] = request.getParameterValues("channel");

			if (zone != null) {
				for (int i = 0; i < zone.length; i++) {
					int zoneId = 0;

					zoneId = Integer.parseInt(zone[i]);
					// }
					String sqlZone = "INSERT INTO MESSAGE_ZONE (MSG_ZONE_ID, MESSAGE_ID, ZONE_ID) VALUES (MESSAGE_ZONE_SEQ.nextval, ?, ?)";
					PreparedStatement stmtZone = conn.prepareStatement(sqlZone);
					stmtZone.setInt(1, id);
					stmtZone.setInt(2, zoneId);
					stmtZone.executeUpdate();
				}
			} else if (testresult.equals("PAMB")) {
				for (int i = 0; i < allpamb.length; i++) {
					int allpambId = 0;
					allpambId = Integer.parseInt(allpamb[i]);
					String sqlZone = "INSERT INTO MESSAGE_ZONE (MSG_ZONE_ID, MESSAGE_ID, ZONE_ID) VALUES (MESSAGE_ZONE_SEQ.nextval, ?, ?)";
					PreparedStatement stmtZone = conn.prepareStatement(sqlZone);
					stmtZone.setInt(1, id);
					stmtZone.setInt(2, allpambId);
					stmtZone.executeUpdate();
				}
			} else if (testresult.equals("PBTB")) {
				for (int i = 0; i < allpbtb.length; i++) {
					int allpbtbId = 0;
					allpbtbId = Integer.parseInt(allpbtb[i]);
					String sqlZone = "INSERT INTO MESSAGE_ZONE (MSG_ZONE_ID, MESSAGE_ID, ZONE_ID) VALUES (MESSAGE_ZONE_SEQ.nextval, ?, ?)";
					PreparedStatement stmtZone = conn.prepareStatement(sqlZone);
					stmtZone.setInt(1, id);
					stmtZone.setInt(2, allpbtbId);
					stmtZone.executeUpdate();
				}
			}
			if (channel != null) {

				for (int i = 0; i < channel.length; i++) {
					int CHANNEL_ID = 0;
					// String
					// sqlGetCHANNEL_ID="select CHANNEL_ID from CHANNEL where CHANNEL_NAME=?";
					// model.addAttribute("testZone", sqlGetCHANNEL_ID);
					// PreparedStatement
					// stmtGetCHANNEL_ID=conn.prepareStatement(sqlGetCHANNEL_ID);
					// stmtGetCHANNEL_ID.setString(1, channel[i]);
					// ResultSet
					// resultSetGetCHANNEL_ID=stmtGetCHANNEL_ID.executeQuery();
					// while(resultSetGetCHANNEL_ID.next())
					// {
					// CHANNEL_ID=resultSetGetCHANNEL_ID.getInt("CHANNEL_ID");
					CHANNEL_ID = Integer.parseInt(channel[i]);
					// }
					model.addAttribute("testZone", "CHANNEL_ID" + CHANNEL_ID);
					String sqlChannel = "INSERT INTO MESSAGE_CHANNEL (MSG_CHANNEL_ID, MESSAGE_ID, CHANNEL_ID) VALUES (MESSAGE_CHANNEL_SEQ.nextval, ?, ?)";
					PreparedStatement stmtChannel = conn
							.prepareStatement(sqlChannel);
					stmtChannel.setInt(1, id);
					stmtChannel.setInt(2, CHANNEL_ID);
					stmtChannel.executeUpdate();
				}
			}
			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		/*
		 * ApnsService service = APNS.newService() .withCert(
		 * "D:/Varsha/apache-tomcat-8.0.24/webapps/ROOT/Apple/aps_dev_java.p12",
		 * "jktls1234") .withSandboxDestination().build();
		 * 
		 * try { Class.forName(ConstantDb.driverClassName); Connection conn =
		 * DriverManager.getConnection(ConstantDb.databaseUrl, "system",
		 * password); String sql = "select DEVICE_TOKEN from Devices"; Statement
		 * stmt = conn.createStatement(); ResultSet resultSet =
		 * stmt.executeQuery(sql); while (resultSet.next()) {
		 * devices.add(resultSet.getString("DEVICE_TOKEN")); } } catch
		 * (ClassNotFoundException e) { 
		 * e.printStackTrace(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * for (int i = 0; i < devices.size(); i++) { String payload =
		 * APNS.newPayload().alertBody("New msg \n" +
		 * request.getParameter("headlineName").toString()) .build(); String
		 * token = devices.get(i); service.push(token, payload); }
		 */

		// return "addmessage";
		// addMessageFileAndroid();
		return "msgadded";
	}

	public void writeEncryptedFile(File uploadedFile, String f1) {
		JNCryptor cryptor = new AES256JNCryptor();
		String password = "aes123";

		try {
			FileInputStream inputStream = new FileInputStream(uploadedFile);
			byte[] filebytes = new byte[(int) uploadedFile.length()];
			inputStream.read(filebytes);
			byte[] ciphertext = cryptor.decryptData(filebytes,
					password.toCharArray());
			String filePath = System.getProperty("catalina.home")
					+ File.separator + UploadFolder + f1;

			File encryptedFile = new File(filePath);
			if (!encryptedFile.exists()) {
				encryptedFile.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(encryptedFile);
			fos.write(ciphertext);
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (CryptorException e) {
			
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/SendNotification", method = RequestMethod.GET)
	public void SendNotification() {
		// Setup the connection
		/*
		 * List<String> devices = new ArrayList<String>(); ApnsService service =
		 * APNS.newService() .withCert(
		 * "D:/Varsha/apache-tomcat-8.0.24/webapps/ROOT/Apple/aps_dev_java.p12",
		 * "jktls1234") .withSandboxDestination().build();
		 * 
		 * String token
		 * ="cc30b508a50758da9257193d07a583ec0d4d0caf11fa92ab051827cf26da0a7c";
		 * String payload =
		 * APNS.newPayload().badge(5).localizedKey("GAME_PLAY_REQUEST_FORMAT")
		 * 
		 * .actionKey("Play").build();
		 * 
		 * int now = (int)(new Date().getTime()/1000);
		 * 
		 * EnhancedApnsNotification notification = new
		 * EnhancedApnsNotification(EnhancedApnsNotification.INCREMENT_ID() Next
		 * ID , now + 60 * 60 Expire in one hour , token Device Token ,
		 * payload);
		 * 
		 * service.push(notification);
		 */
		/*
		 * ZeroPush.getConfiguration().setTokens(
		 * "AIzaSyDj7ehkkT0Qcp_p630JCdwZ3h5fokfjTUY", "536417885426"); String
		 * DEVICE_TOKEN=
		 * "APA91bEsfiY8FDGq1GKTsBbnvA27z6H5STCLRFxRp6ufwg-2lRecPj3n_AoLFkMzspDNAQ8ZrGE1JX5WcOWEkm4ILn1Sp06KQkqiMiIPg8rYkDC0pQlYQFmo04T0CBbdwfGVExt-_8Z5"
		 * ; ZeroPushNotification boardingNotification = new
		 * AndroidPushNotification.Builder() .addDeviceToken(DEVICE_TOKEN)
		 * .addDatum("alert", "Now Boarding") .addDatum("username",
		 * "fred.droid") .build();
		 * 
		 * // A built notification is actually sent like this:
		 * 
		 * ZeroPush.notification(boardingNotification).send();
		 * 
		 * // In order to broadcast it, you have to set broadcasting flag:
		 * 
		 * ZeroPush.notification(boardingNotification).broadcast().send();
		 * 
		 * // You may want to broadcast it only for some channel like this:
		 * 
		 * ZeroPush.notification(boardingNotification).broadcast("CHANNEL_NAME").send
		 * ();
		 */

		Sender sender = new Sender("AIzaSyDj7ehkkT0Qcp_p630JCdwZ3h5fokfjTUY"); // Here
																				// you
																				// will
																				// write
																				// APP
																				// key
																				// given
																				// by
																				// Android
																				// end
		com.google.android.gcm.server.Message msg = new com.google.android.gcm.server.Message.Builder()
				.addData("message", "message").build();
		// "message", message).build();
		String str = null;
		try {
			Result results = sender
					.send(msg,
							"eBiEJDIfLgE:APA91bEyE_L2SK_yRV-93M7iLHfWTpTA4d4K__1Xl6WdY3i2YKaNjmPnzziX2TfXd9hxKc2N9qhgpD1cXK1YbfOemLZ6DuNibKlQOEsTCJC3X-bTzTkC1DE86L4wG_Z-i5yZHZgcG4Lk",
							5); // Where appId is given by Android end
			if (results.getMessageId() != null) {
				// /str = val_true;
			} else {
				// str= val_false;
				String error = results.getErrorCodeName();
				// logger.info("message sending failed:: "+error);
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return str;

	}

	public String writeFile(byte[] plainText, String filename) {

		String filePath = "/webapps/crm/resources/ios" + filename;
		File encryptedFile = new File(filePath);

		try {
			FileOutputStream fos = new FileOutputStream(encryptedFile);
			fos.write(plainText);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return "success";

	}

	@RequestMapping(value = "/addmessage/category/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Message> getMessageList(@PathVariable int id, Model model) {
		LOGGER.debug("@@@@ categoryId :: " + id);
		LOGGER.debug("@@@@ model :: " + model);
		List<Message> list = messageService.getMessageListById(id);
		LOGGER.debug("@@@@ list :: " + list.size());
		return list;
	}

	@RequestMapping(value = { "/restmessagebycategoryandro/{lang}/{allFlag}/{userId}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> getAllMessagesAndro(
			@PathVariable(value = "lang") int lang,
			@PathVariable(value = "allFlag") int allFlag,
			@PathVariable(value = "userId") int userId) {

		LOGGER.debug("Start messageList.");
		String msg = "1";
		String readFlag = "0";
		List messageList = new ArrayList();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql;
			if (allFlag == 0)
				sql = (new StringBuilder())
						.append("SELECT unique md.*,cc.*,mm.* FROM message_details md,CATEGORY cc,MESSAGE mm ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.MESSAGE_LANG=")
						.append(lang)
						.append(" and mm.MSG_STATUS=1 and mm.IS_DELETED=0 and cc.categoryid = mm.category_id")
						.append(" and md.MESSAGE_ID = mm.MESSAGE_ID")
						.append(" and mm.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=")
						.append(userId)
						.append(")")
						.append(" and mm.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=")
						.append(userId)
						.append(")")
						.append(" and mm.created_at<SYSDATE and mm.EXPIRY_AT>=SYSDATE ORDER BY mm.CREATED_AT desc")
						.toString();
			else
				sql = (new StringBuilder())
						.append("select DISTINCT md.* , m.CREATED_AT, c.CATEGORYNAME,c.category_name_bhasa from message_details md , message m, category c,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where c.CATEGORYID=")
						.append(allFlag)
						.append(" and m.MESSAGE_ID = md.MESSAGE_ID")
						.append(" and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and categoryid=")
						.append(allFlag)
						.append(" and MSG_STATUS=1  and IS_DELETED=0)")
						.append(" and m.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID=")
						.append(userId)
						.append(")")
						.append(" and m.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN(Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID=")
						.append(userId).append(")")
						.append(" and MESSAGE_LANG=").append(lang)
						.append("  ORDER BY m.CREATED_AT desc ").toString();
			System.out.println("GET >> getAllMessagesAndro >> SQL :"+sql);
			Statement stmt = conn.createStatement();
			// JNCryptor cryptor;
			BASE64Encoder cryptor;
			String password;
			String category_Id;
			String msg_Id;
			String msg_data;
			String category_name;
			String msg_title;
			String msg_link;
			String msg_link2;
			String creationdate;
			String noOfLikes;
			String msgLike;
			String msgFav;
			Date date = new Date();
			// Locale locale = null;
			// SimpleDateFormat dateFormatCN = new
			// SimpleDateFormat("dd-MMM-yyyy", locale);
			// SimpleDateFormat dateFormat = new
			// SimpleDateFormat("dd-MMM-yyyy");

			for (ResultSet resultSet = stmt.executeQuery(sql); resultSet.next(); messageList
					.add(new Message(category_Id, msg_Id, msg_data, msg_title,
							msg_link, new String(cryptor.encode(msgFav
									.getBytes())), msg_link2, category_name,
							readFlag, noOfLikes, new String(cryptor
									.encode(msgLike.getBytes())), creationdate))) {
				cryptor = new BASE64Encoder();
				// password = "aes123";
				int message_Id = resultSet.getInt("MESSAGE_ID");
				category_Id = "";
				if (allFlag == 0)
					category_Id = new String(cryptor.encode(String.valueOf(
							resultSet.getInt("CATEGORYID")).getBytes()));
				else
					category_Id = (new StringBuilder()).append("")
							.append(allFlag).toString();
				msg_Id = new String(cryptor.encode(String.valueOf(message_Id)
						.getBytes()));
				msg_data = "";
				category_name = "";
				msg_title = "";
				msg_link = "";
				String msg_status = "";
				msg_link2 = "1";
				creationdate = "";
				noOfLikes = "0";
				if (resultSet.getString("MESSAGE_DATA") != null)
					msg_data = new String(cryptor.encode(resultSet.getString(
							"MESSAGE_DATA").getBytes()));
				if (resultSet.getString("MESSAGE_TITLE") != null)
					msg_title = new String(cryptor.encode(String.valueOf(
							resultSet.getString("MESSAGE_TITLE")).getBytes()));
				if (resultSet.getString("MESSAGE_LINK") != null)
					msg_link = new String(cryptor.encode(String.valueOf(
							resultSet.getString("MESSAGE_LINK")).getBytes()));
				if (resultSet.getString("MESSAGE_STATUS") != null)
					msg_status = new String(cryptor.encode(String.valueOf(
							resultSet.getInt("MESSAGE_STATUS")).getBytes()));
				if (resultSet.getString("MESSAGE_IMG_LINK") != null)
					msg_link2 = new String(
							cryptor.encode(String.valueOf(
									resultSet.getString("MESSAGE_IMG_LINK"))
									.getBytes()));

				if (resultSet.getTimestamp("CREATED_AT") != null)
					creationdate = new String(cryptor.encode(String.valueOf(
							resultSet.getTimestamp("CREATED_AT")).getBytes()));
				if (lang == 2) {
					if (resultSet.getString("CATEGORY_NAME_BHASA") != null)
						category_name = new String(
								cryptor.encode(String
										.valueOf(
												resultSet
														.getString("CATEGORY_NAME_BHASA"))
										.getBytes()));
				} else if (resultSet.getString("CATEGORYNAME") != null)
					category_name = new String(cryptor.encode(String.valueOf(
							resultSet.getString("CATEGORYNAME")).getBytes()));

				String sqlReadFlag = (new StringBuilder())
						.append("select * from Read_reciept where user_Id=")
						.append(userId).append(" and message_Id=")
						.append(message_Id).toString();
				Statement stmtReadFlag = conn.createStatement();
				ResultSet resultSetReadFlag = stmtReadFlag
						.executeQuery(sqlReadFlag);
				if (resultSetReadFlag != null && resultSetReadFlag.next())
					readFlag = new String(cryptor.encode("1".getBytes()));
				else
					readFlag = new String(cryptor.encode("0".getBytes()));
				String sqlNoOfLikes = (new StringBuilder())
						.append("select COUNT(*) from message_like where message_Id=")
						.append(message_Id).toString();
				Statement stmt2 = conn.createStatement();
				ResultSet resultSet2 = stmt2.executeQuery(sqlNoOfLikes);
				if (resultSet2 != null && resultSet2.next())
					do
						noOfLikes = new String(cryptor.encode(String.valueOf(
								resultSet2.getInt(1)).getBytes()));
					while (resultSet2.next());
				msgLike = "0";
				String sqlGetLikes = (new StringBuilder())
						.append("select * from message_like where Message_Id=")
						.append(message_Id).append(" and user_Id=")
						.append(userId).toString();
				Statement stmtGetLikes = conn.createStatement();
				ResultSet resultSetGetLikes = stmtGetLikes
						.executeQuery(sqlGetLikes);
				if (resultSetGetLikes != null && resultSetGetLikes.next())
					msgLike = "1";
				msgFav = "0";
				String sqlGetFav = (new StringBuilder())
						.append("select * from favourite where Message_Id=")
						.append(message_Id).append(" and user_Id=")
						.append(userId).toString();
				Statement stmtGetFav = conn.createStatement();
				ResultSet resultSetGetFav = stmtGetFav.executeQuery(sqlGetFav);
				if (resultSetGetFav != null && resultSetGetFav.next())
					msgFav = "1";
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return messageList;
	}

	// Sudd Addded By Android services.
	// sudd Added By Android Services Stop

	// cms
	@RequestMapping(value = URIConstants.GET_READ_MESSAGES, method = RequestMethod.GET)
	public String readmessages(HttpServletResponse response, Model model,
			HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Message> messageList = new ArrayList<Message>();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();

			User user = (User) session.getAttribute("user");
			// String
			// sql="select md.* , um.USERNAME,c.CATEGORYNAME from message_details md , user_m um , message m,category c where m.message_id =  md.message_id and m.USERNAME = um.USERID and m.category_id=c.categoryId  and  md.message_id IN (select message_id from read_reciept) and md.message_lang = 1 and md.MESSAGE_STATUS =1";
			// String sql =
			// "select	md.* , 	um.USERNAME,	c.CATEGORYNAME,	m.CREATED_AT from 	message_details md ,	user_m um ,	message m,	read_reciept r,	category c where 	m.message_id =  md.message_id and 	r.USER_ID = um.USERID and 	m.category_id=c.categoryId  and  	md.message_id  = r.message_id and 	md.message_lang = 1 and  md.MESSAGE_STATUS =1";
			// select md.* , um.USERNAME, c.CATEGORYNAME, m.CREATED_AT from
			// message_details md , user_m um , message m, read_reciept r,
			// category c where m.message_id = md.message_id and r.USER_ID =
			// um.USERID and m.category_id=c.categoryId and md.message_id =
			// r.message_id and md.message_lang = 1 and m.MSG_STATUS =1 and
			// m.IS_DELETED=0
			String sql = "select	r.*, md.* , 	um.USERNAME,	c.CATEGORYNAME,	m.CREATED_AT from 	message_details md ,	user_m um ,	message m,	read_reciept r,	category c where 	m.message_id =  md.message_id and 	r.USER_ID = um.USERID and 	m.category_id=c.categoryId  and  	md.message_id  = r.message_id and 	md.message_lang = 1 and   m.MSG_STATUS =1 and m.IS_DELETED=0";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {

				String msgId = String.valueOf(resultSet.getInt("MESSAGE_ID"));
				String categoryName = resultSet.getString("CATEGORYNAME");
				String msgTitle = resultSet.getString("MESSAGE_TITLE");
				String username = resultSet.getString("USERNAME");
				//String timestamp = resultSet.getString("CREATED_AT");
				String timestamp = resultSet.getString("READ_AT");
				// String MsgDetailID ,String MsgID,String msgData,String
				// MsgTitle,String MsgLink,String msgStatus
				messageList.add(new Message(msgId, categoryName, msgTitle,
						username, timestamp));
				// messageList.add(new
				// Message(String.valueOf(resultSet.getInt(1)),
				// String.valueOf(resultSet.getInt(2)), resultSet.getString(3),
				// resultSet.getString(4), resultSet.getString(5)));
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("messageList", messageList);
		// model.addAttribute("message", new Message());
		return "readmessages";
	}

	/* Added by Sudd Starts */
	@RequestMapping(value = "allregion/{id}", method = RequestMethod.GET)
	public String allregion(@PathVariable("id") String id, Model model,
			HttpSession session, HttpServletRequest request, Message message) {
		// String region1=request.getParameter("region");
		System.out.println("Suddd ALL REGIOn:" + id);
		// Message message = new Message();
		// message.setRegion(id);
		// List<Message> messageList = new ArrayList<Message>();
		Message message2 = new Message();
		// messageList.add(new Message(id));
		// message.setRegion(id);
		// String message1=message.region;
		// testresult="";
		testresult = id;
		System.out.println("Suddd ALL REGIOn@@@@:" + testresult);
		// model.addAttribute("testresult", new Message());
		return "addmessage";
	}

	/* Addedd By Sudd Ends */

	// Sudd Changes Start Here
	@RequestMapping(value = { "/nextmessagebycategoryandro/{lang}/{allFlag}/{userId}/{messageId}/{devicetoken}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> getAllMessagesAndro(
			@PathVariable(value = "lang") int lang,
			@PathVariable(value = "allFlag") int allFlag,
			@PathVariable(value = "userId") int userId,
			@PathVariable(value = "devicetoken") String device_token,
			@PathVariable(value = "messageId") int messageId) {
		LOGGER.debug("Start messageList.");
		// AES256JNCryptor cryptor = new AES256JNCryptor();
		BASE64Encoder cryptor = new BASE64Encoder();
		String password = "aes123";
		String msg = "1";
		String readFlag = "0";
		ArrayList<Message> messageList = new ArrayList<Message>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = allFlag == 0 ? "SELECT unique md.*,cc.*,mm.* FROM message_details md,CATEGORY cc,MESSAGE mm ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.MESSAGE_LANG="
					+ lang
					+ " and mm.MSG_STATUS=1 and mm.IS_DELETED=0 and cc.categoryid = mm.category_id"
					+ " and md.MESSAGE_ID = mm.MESSAGE_ID"
					+ " and mm.MESSAGE_ID>"
					+ messageId
					+ " and mm.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
					+ userId
					+ ")"
					+ " and mm.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID="
					+ userId
					+ ")"
					+ " and mm.created_at<SYSDATE and mm.EXPIRY_AT>=SYSDATE ORDER BY mm.CREATED_AT desc"
					: "select DISTINCT md.* , m.CREATED_AT, c.CATEGORYNAME from message_details md , message m, category c,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where c.CATEGORYID="
							+ allFlag
							+ " and m.MESSAGE_ID = md.MESSAGE_ID"
							+ " and m.MESSAGE_ID>"
							+ messageId
							+ " and md.message_id IN(select message_id from message where created_at<SYSDATE and EXPIRY_AT>=SYSDATE and category_id="
							+ allFlag
							+ " and MSG_STATUS=1  and IS_DELETED=0)"
							+ " and m.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
							+ userId
							+ ")"
							+ " and m.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN(Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID="
							+ userId
							+ ")"
							+ " and MESSAGE_LANG="
							+ lang
							+ "  ORDER BY m.CREATED_AT desc ";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				int message_Id = resultSet.getInt("MESSAGE_ID");
				String category_Id = "";
				if (allFlag == 0) {
					category_Id = new String(cryptor.encode(String.valueOf(
							resultSet.getInt("CATEGORYID")).getBytes()));
				}
				String msg_Id = new String(cryptor.encode(String.valueOf(
						message_Id).getBytes()));
				String msg_data = "";
				String category_name = "";
				String msg_title = "";
				String msg_link = "";
				String msg_status = "";
				String msg_link2 = "1";
				String creationdate = "";
				String noOfLikes = "0";
				if (resultSet.getString("MESSAGE_DATA") != null) {
					msg_data = new String(cryptor.encode(resultSet.getString(
							"MESSAGE_DATA").getBytes()));
				}
				if (resultSet.getString("MESSAGE_TITLE") != null) {
					msg_title = new String(cryptor.encode(String.valueOf(
							resultSet.getString("MESSAGE_TITLE")).getBytes()));
				}
				if (resultSet.getString("MESSAGE_LINK") != null) {
					msg_link = new String(cryptor.encode(String.valueOf(
							resultSet.getString("MESSAGE_LINK")).getBytes()));
				}
				if (resultSet.getString("MESSAGE_STATUS") != null) {
					msg_status = new String(cryptor.encode(String.valueOf(
							resultSet.getInt("MESSAGE_STATUS")).getBytes()));
				}
				if (resultSet.getString("MESSAGE_IMG_LINK") != null) {
					msg_link2 = new String(
							cryptor.encode(String.valueOf(
									resultSet.getString("MESSAGE_IMG_LINK"))
									.getBytes()));
				}
				if (resultSet.getTimestamp("CREATED_AT") != null) {
					creationdate = new String(cryptor.encode(String.valueOf(
							resultSet.getTimestamp("CREATED_AT")).getBytes()));
				}
				if (resultSet.getString("CATEGORYNAME") != null) {
					category_name = new String(cryptor.encode(String.valueOf(
							resultSet.getString("CATEGORYNAME")).getBytes()));
				}
				String sqlReadFlag = "select * from Read_reciept where user_Id="
						+ userId + " and message_Id=" + message_Id;
				Statement stmtReadFlag = conn.createStatement();
				ResultSet resultSetReadFlag = stmtReadFlag
						.executeQuery(sqlReadFlag);
				readFlag = resultSetReadFlag != null
						&& resultSetReadFlag.next() ? new String(
						cryptor.encode("1".getBytes())) : new String(
						cryptor.encode("0".getBytes()));
				String sqlNoOfLikes = "select COUNT(*) from message_like where message_Id="
						+ message_Id;
				Statement stmt2 = conn.createStatement();
				ResultSet resultSet2 = stmt2.executeQuery(sqlNoOfLikes);
				if (resultSet2 != null && resultSet2.next()) {
					do {
						noOfLikes = new String(cryptor.encode(String.valueOf(
								resultSet2.getInt(1)).getBytes()));
					} while (resultSet2.next());
				}
				String msgLike = "0";
				String sqlGetLikes = "select * from message_like where Message_Id="
						+ message_Id + " and user_Id=" + userId;
				Statement stmtGetLikes = conn.createStatement();
				ResultSet resultSetGetLikes = stmtGetLikes
						.executeQuery(sqlGetLikes);
				if (resultSetGetLikes != null && resultSetGetLikes.next()) {
					msgLike = "1";
				}
				String msgFav = "0";
				String sqlGetFav = "select * from favourite where Message_Id="
						+ message_Id + " and user_Id=" + userId;
				Statement stmtGetFav = conn.createStatement();
				ResultSet resultSetGetFav = stmtGetFav.executeQuery(sqlGetFav);
				if (resultSetGetFav != null && resultSetGetFav.next()) {
					msgFav = "1";
				}
				messageList.add(new Message(category_Id, msg_Id, new String(
						cryptor.encode("0".getBytes())), new String(cryptor
						.encode("0".getBytes())), msg_data, msg_title,
						msg_link,
						new String(cryptor.encode(msgFav.getBytes())),
						msg_link2, category_name, readFlag, noOfLikes,
						new String(cryptor.encode(msgLike.getBytes())),
						creationdate));
			}
			String sqlGetEditedMsgs = "SELECT * from message_edit";
			Statement stmtGetEditedMsgs = conn.createStatement();
			ResultSet resultsetGetEditedMsgs = stmtGetEditedMsgs
					.executeQuery(sqlGetEditedMsgs);
			while (resultsetGetEditedMsgs.next()) {
				int message_edit_id = resultsetGetEditedMsgs
						.getInt("MESSAGE_EDIT_ID");
				int message_id = resultsetGetEditedMsgs.getInt("MESSAGE_ID");
				String sqlCheckSent = "SELECT * from SENT_EDITED_MESSAGES where USER_ID=? and DEVICE_TOKEN=? and MESSAGE_EDIT_ID=?";
				PreparedStatement stmtCheckSent = conn
						.prepareStatement(sqlCheckSent);
				stmtCheckSent.setInt(1, userId);
				stmtCheckSent.setString(2, device_token);
				stmtCheckSent.setInt(3, message_edit_id);
				ResultSet resultSetCheckSent = stmtCheckSent.executeQuery();
				if (resultSetCheckSent != null && resultSetCheckSent.next()) {
					while (resultSetCheckSent.next()) {
					}
					continue;
				}
				if (allFlag == 0) {
					String msgData = "";
					String msgTitle = "";
					String msgLink = "";
					String msgImgLink = "";
					String categoryName = "";
					String created_at = "";
					String category_Id = "";
					String sqlGetEditedMsgData = "SELECT md.MESSAGE_IMG_LINK,md.MESSAGE_DATA,md.MESSAGE_TITLE,md.MESSAGE_LINK,cc.CATEGORYID,  cc.CATEGORYNAME, mm.CREATED_AT from MESSAGE_DETAILS md , Category cc,MESSAGE mm WHERE md.MESSAGE_ID=? and md.MESSAGE_LANG=? and cc.CATEGORYID=mm.CATEGORY_ID and mm.MESSAGE_ID=?";
					PreparedStatement stmtGetEditedMsgData = conn
							.prepareStatement(sqlGetEditedMsgData);
					stmtGetEditedMsgData.setInt(1, message_id);
					stmtGetEditedMsgData.setInt(2, lang);
					stmtGetEditedMsgData.setInt(3, message_id);
					ResultSet resultSetGetEditedMsgData = stmtGetEditedMsgData
							.executeQuery();
					while (resultSetGetEditedMsgData.next()) {
						category_Id = String.valueOf(resultSetGetEditedMsgData
								.getInt("CATEGORYID"));
						msgData = resultSetGetEditedMsgData
								.getString("MESSAGE_DATA");
						msgTitle = resultSetGetEditedMsgData
								.getString("MESSAGE_TITLE");
						msgLink = resultSetGetEditedMsgData
								.getString("MESSAGE_LINK") != null ? resultSetGetEditedMsgData
								.getString("MESSAGE_LINK") : "";
						msgImgLink = resultSetGetEditedMsgData
								.getString("MESSAGE_IMG_LINK") != null ? resultSetGetEditedMsgData
								.getString("MESSAGE_IMG_LINK") : "";
						categoryName = resultSetGetEditedMsgData
								.getString("CATEGORYNAME");
						created_at = ""
								+ resultSetGetEditedMsgData
										.getTimestamp("CREATED_AT");
					}
					String msgLike = "0";
					String sqlGetIsLike = "select * from MESSAGE_LIKE where MESSAGE_ID=? and USER_ID=?";
					PreparedStatement stmtGetIsLike = conn
							.prepareStatement(sqlGetIsLike);
					stmtGetIsLike.setInt(1, message_id);
					stmtGetIsLike.setInt(2, userId);
					ResultSet resultSetGetIsLike = stmtGetIsLike.executeQuery();
					while (resultSetGetIsLike.next()) {
						msgLike = "1";
					}
					String msgFav = "0";
					String sqlGetIsFavourite = "select * from FAVOURITE where MESSAGE_ID=? and USER_ID=?";
					PreparedStatement stmtGetIsFavourite = conn
							.prepareStatement(sqlGetIsFavourite);
					stmtGetIsFavourite.setInt(1, message_id);
					stmtGetIsFavourite.setInt(2, userId);
					ResultSet resultSetGetIsFavourite = stmtGetIsFavourite
							.executeQuery();
					while (resultSetGetIsFavourite.next()) {
						msgFav = "1";
					}
					String noOfLikes = "0";
					String sqlGetNoOfLikes = "select count(*) from MESSAGE_LIKE where MESSAGE_ID=?";
					PreparedStatement stmtGetNoOfLikes = conn
							.prepareStatement(sqlGetNoOfLikes);
					stmtGetNoOfLikes.setInt(1, message_id);
					ResultSet resultSetGetNoOfLikes = stmtGetNoOfLikes
							.executeQuery();
					while (resultSetGetNoOfLikes.next()) {
						noOfLikes = String.valueOf(resultSetGetNoOfLikes
								.getInt(1));
					}
					String isRead = "0";
					String sqlGetReadReceipt = "select * from READ_RECIEPT where MESSAGE_ID=? and USER_ID=?";
					PreparedStatement stmtGetReadReceipt = conn
							.prepareStatement(sqlGetReadReceipt);
					stmtGetReadReceipt.setInt(1, message_id);
					stmtGetReadReceipt.setInt(2, userId);
					ResultSet resultSetGetReadReceipt = stmtGetReadReceipt
							.executeQuery();
					while (resultSetGetReadReceipt.next()) {
						isRead = "1";
					}
					messageList.add(new Message(new String(cryptor
							.encode(category_Id.getBytes())), new String(
							cryptor.encode(String.valueOf(message_id)
									.getBytes())), new String(cryptor
							.encode("0".getBytes())), new String(cryptor
							.encode("1".getBytes())), new String(cryptor
							.encode(msgData.getBytes())), new String(cryptor
							.encode(msgTitle.getBytes())), new String(cryptor
							.encode(msgLink.getBytes())), new String(cryptor
							.encode(msgFav.getBytes())), new String(cryptor
							.encode(msgImgLink.getBytes())), new String(cryptor
							.encode(categoryName.getBytes())), new String(
							cryptor.encode(isRead.getBytes())), new String(
							cryptor.encode(noOfLikes.getBytes())), new String(
							cryptor.encode(msgLike.getBytes())), new String(
							cryptor.encode(created_at.getBytes()))));
					String sqlInsertInSentEditMsg = "INSERT INTO SENT_EDITED_MESSAGES (SENT_ID, USER_ID, DEVICE_TOKEN, MESSAGE_EDIT_ID) VALUES (SENT_EDITED_MESSAGES_SEQ.nextval, ?, ?, ?)";
					PreparedStatement stmtInsertInSentEditMsg = conn
							.prepareStatement(sqlInsertInSentEditMsg);
					stmtInsertInSentEditMsg.setInt(1, userId);
					stmtInsertInSentEditMsg.setString(2, device_token);
					stmtInsertInSentEditMsg.setInt(3, message_edit_id);
					stmtInsertInSentEditMsg.executeUpdate();
					continue;
				}
				String sqlGetCatOfMsg = "select * from message where CATEGORY_ID=? and MESSAGE_ID=?";
				PreparedStatement stmtGetCatOfMsg = conn
						.prepareStatement(sqlGetCatOfMsg);
				stmtGetCatOfMsg.setInt(1, allFlag);
				stmtGetCatOfMsg.setInt(2, message_id);
				ResultSet resultSetGetCatOfMsg = stmtGetCatOfMsg.executeQuery();
				if (resultSetGetCatOfMsg == null)
					continue;
				String msgData = "";
				String msgTitle = "";
				String msgLink = "";
				String msgImgLink = "";
				String categoryName = "";
				String created_at = "";
				String category_Id = "";
				String sqlGetEditedMsgData = "SELECT md.MESSAGE_IMG_LINK,md.MESSAGE_DATA,md.MESSAGE_TITLE,md.MESSAGE_LINK,cc.CATEGORYID ,  cc.CATEGORYNAME, mm.CREATED_AT from MESSAGE_DETAILS md , Category cc,MESSAGE mm WHERE md.MESSAGE_ID=? and md.MESSAGE_LANG=? and cc.CATEGORYID=? and mm.MESSAGE_ID=?";
				PreparedStatement stmtGetEditedMsgData = conn
						.prepareStatement(sqlGetEditedMsgData);
				stmtGetEditedMsgData.setInt(1, message_id);
				stmtGetEditedMsgData.setInt(2, lang);
				stmtGetEditedMsgData.setInt(3, allFlag);
				stmtGetEditedMsgData.setInt(4, message_id);
				ResultSet resultSetGetEditedMsgData = stmtGetEditedMsgData
						.executeQuery();
				while (resultSetGetEditedMsgData.next()) {
					category_Id = String.valueOf(resultSetGetEditedMsgData
							.getInt("CATEGORYID"));
					msgData = resultSetGetEditedMsgData
							.getString("MESSAGE_DATA");
					msgTitle = resultSetGetEditedMsgData
							.getString("MESSAGE_TITLE");
					msgLink = resultSetGetEditedMsgData
							.getString("MESSAGE_LINK");
					msgImgLink = resultSetGetEditedMsgData
							.getString("MESSAGE_IMG_LINK");
					categoryName = resultSetGetEditedMsgData
							.getString("CATEGORYNAME");
					created_at = ""
							+ resultSetGetEditedMsgData
									.getTimestamp("CREATED_AT");
				}
				String msgLike = "0";
				String sqlGetIsLike = "select * from MESSAGE_LIKE where MESSAGE_ID=? and USER_ID=?";
				PreparedStatement stmtGetIsLike = conn
						.prepareStatement(sqlGetIsLike);
				stmtGetIsLike.setInt(1, message_id);
				stmtGetIsLike.setInt(2, userId);
				ResultSet resultSetGetIsLike = stmtGetIsLike.executeQuery();
				while (resultSetGetIsLike.next()) {
					msgLike = "1";
				}
				String msgFav = "0";
				String sqlGetIsFavourite = "select * from FAVOURITE where MESSAGE_ID=? and USER_ID=?";
				PreparedStatement stmtGetIsFavourite = conn
						.prepareStatement(sqlGetIsFavourite);
				stmtGetIsFavourite.setInt(1, message_id);
				stmtGetIsFavourite.setInt(2, userId);
				ResultSet resultSetGetIsFavourite = stmtGetIsFavourite
						.executeQuery();
				while (resultSetGetIsFavourite.next()) {
					msgFav = "1";
				}
				String noOfLikes = "0";
				String sqlGetNoOfLikes = "select count(*) from MESSAGE_LIKE where MESSAGE_ID=?";
				PreparedStatement stmtGetNoOfLikes = conn
						.prepareStatement(sqlGetNoOfLikes);
				stmtGetNoOfLikes.setInt(1, message_id);
				ResultSet resultSetGetNoOfLikes = stmtGetNoOfLikes
						.executeQuery();
				while (resultSetGetNoOfLikes.next()) {
					noOfLikes = String.valueOf(resultSetGetNoOfLikes.getInt(1));
				}
				String isRead = "0";
				String sqlGetReadReceipt = "select * from READ_RECIEPT where MESSAGE_ID=? and USER_ID=?";
				PreparedStatement stmtGetReadReceipt = conn
						.prepareStatement(sqlGetReadReceipt);
				stmtGetReadReceipt.setInt(1, message_id);
				stmtGetReadReceipt.setInt(2, userId);
				ResultSet resultSetGetReadReceipt = stmtGetReadReceipt
						.executeQuery();
				while (resultSetGetReadReceipt.next()) {
					isRead = "1";
				}
				messageList.add(new Message(new String(cryptor
						.encode(category_Id.getBytes())), new String(cryptor
						.encode(String.valueOf(message_id).getBytes())),
						new String(cryptor.encode("0".getBytes())), new String(
								cryptor.encode("1".getBytes())), new String(
								cryptor.encode(msgData.getBytes())),
						new String(cryptor.encode(msgTitle.getBytes())),
						new String(cryptor.encode(msgLink != null ? msgLink
								.getBytes() : "".getBytes())), new String(
								cryptor.encode(msgFav.getBytes())), new String(
								cryptor.encode(msgImgLink != null ? msgImgLink
										.getBytes() : "".getBytes())),
						new String(cryptor.encode(categoryName.getBytes())),
						new String(cryptor.encode(isRead.getBytes())),
						new String(cryptor.encode(noOfLikes.getBytes())),
						new String(cryptor.encode(msgLike.getBytes())),
						new String(cryptor.encode(created_at.getBytes()))));
				String sqlInsertInSentEditMsg = "INSERT INTO SENT_EDITED_MESSAGES (SENT_ID, USER_ID, DEVICE_TOKEN, MESSAGE_EDIT_ID) VALUES (SENT_EDITED_MESSAGES_SEQ.nextval, ?, ?, ?)";
				PreparedStatement stmtInsertInSentEditMsg = conn
						.prepareStatement(sqlInsertInSentEditMsg);
				stmtInsertInSentEditMsg.setInt(1, userId);
				stmtInsertInSentEditMsg.setString(2, device_token);
				stmtInsertInSentEditMsg.setInt(3, message_edit_id);
				stmtInsertInSentEditMsg.executeUpdate();
			}
			String sqlGetDeletedMsg = "select * from MESSAGE_DELETE";
			Statement stmtGetDeletedMsg = conn.createStatement();
			ResultSet resultSetGetDeletedMsg = stmtGetDeletedMsg
					.executeQuery(sqlGetDeletedMsg);
			while (resultSetGetDeletedMsg.next()) {
				int message_delete_id = resultSetGetDeletedMsg
						.getInt("DELETE_ID");
				int message_id = resultSetGetDeletedMsg.getInt("MESSAGE_ID");
				String sqlCheckSent = "SELECT * from SENT_DELETED_MESSAGES where USER_ID=? and DEVICE_TOKEN=? and DELETE_ID=?";
				PreparedStatement stmtCheckSent = conn
						.prepareStatement(sqlCheckSent);
				stmtCheckSent.setInt(1, userId);
				stmtCheckSent.setString(2, device_token);
				stmtCheckSent.setInt(3, message_delete_id);
				ResultSet resultSetCheckSent = stmtCheckSent.executeQuery();
				if (resultSetCheckSent != null && resultSetCheckSent.next()) {
					while (resultSetCheckSent.next()) {
					}
					continue;
				}
				messageList.add(new Message("", new String(cryptor
						.encode(String.valueOf(message_id).getBytes())),
						new String(cryptor.encode("1".getBytes())), new String(
								cryptor.encode("0".getBytes())), "", "", "",
						"", "", "", "", "", "", ""));
				String sqlInsertInSentDeleteMsg = "INSERT INTO SENT_DELETED_MESSAGES (SENT_ID, USER_ID, DEVICE_TOKEN, DELETE_ID) VALUES (SENT_DELETED_MESSAGES_SEQ.nextval, ?, ?, ?)";
				PreparedStatement stmtInsertInSentDeleteMsg = conn
						.prepareStatement(sqlInsertInSentDeleteMsg);
				stmtInsertInSentDeleteMsg.setInt(1, userId);
				stmtInsertInSentDeleteMsg.setString(2, device_token);
				stmtInsertInSentDeleteMsg.setInt(3, message_delete_id);
				stmtInsertInSentDeleteMsg.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return messageList;
	}

	// Sudd Changes End Here
	// Sudd Rest Details
	@RequestMapping(value = { "/restGetMessagesDetailandro/{lang}/{messageId}/{userId}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> getMessageDetailsAndro(
			@PathVariable(value = "lang") int lang,
			@PathVariable(value = "messageId") int messageId,
			@PathVariable(value = "userId") int userId, Model model) {
		ArrayList<Message> list = new ArrayList<Message>();
		String noOfLikes = "0";
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "SELECT md.*,m.CREATED_AT, c.CATEGORYNAME  FROM message_details md, message m, category c where md.MESSAGE_LANG="
					+ lang
					+ " and md.message_id="
					+ messageId
					+ " and c.CATEGORYID= m.CATEGORY_ID and m.MSG_STATUS=1 and m.IS_DELETED=0  and m.MESSAGE_ID="
					+ messageId;
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			String sqlGetLikes = "select count(*) from message_like where message_Id="
					+ messageId;
			Statement stmtGetLikes = conn.createStatement();
			ResultSet resultSetGetLikes = stmtGetLikes
					.executeQuery(sqlGetLikes);
			// AES256JNCryptor cryptor = new AES256JNCryptor();
			BASE64Encoder cryptor = new BASE64Encoder();
			String password = "aes123";
			if (resultSetGetLikes != null && resultSetGetLikes.next()) {
				noOfLikes = new String(cryptor.encode(String.valueOf(
						resultSetGetLikes.getInt(1)).getBytes()));
			}
			while (resultSet.next()) {
				String msg_detail_Id = new String(cryptor.encode(String
						.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"))
						.getBytes()));
				String msg_Id = new String(cryptor.encode(String.valueOf(
						messageId).getBytes()));
				String msg_data = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_DATA")).getBytes()));
				String msg_title = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_TITLE")).getBytes()));
				String msg_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_LINK")).getBytes()));
				String msg_img_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_IMG_LINK")).getBytes()));
				String creationDate = new String(cryptor.encode(String.valueOf(
						resultSet.getTimestamp("CREATED_AT")).getBytes()));
				String categoryName = new String(cryptor.encode(String.valueOf(
						resultSet.getString("CATEGORYNAME")).getBytes()));
				String msg_Liked = "0";
				String sqlGetLike = "select * from message_like where Message_Id="
						+ messageId + " and user_Id=" + userId;
				Statement stmtGetLike = conn.createStatement();
				ResultSet resultSetGetLike = stmtGetLike
						.executeQuery(sqlGetLike);
				if (resultSetGetLike != null && resultSetGetLike.next()) {
					msg_Liked = "1";
				}
				String msg_status = "0";
				String sqlGetFav = "select * from Favourite where Message_Id="
						+ messageId + " and user_Id=" + userId;
				Statement stmtGetFav = conn.createStatement();
				ResultSet resultSetGetFav = stmtGetFav.executeQuery(sqlGetFav);
				if (resultSetGetFav != null && resultSetGetFav.next()) {
					msg_status = "1";
				}
				list.add(new Message(msg_Id, msg_data, msg_title, msg_link,
						msg_img_link, noOfLikes, new String(cryptor
								.encode(msg_status.getBytes())), new String(
								cryptor.encode(msg_Liked.getBytes())),
						creationDate, categoryName));
			}
			String sqlCheck = "Select * from READ_RECIEPT where message_Id="
					+ messageId + " and user_Id=" + userId;
			Statement stmtSqlCheck = conn.createStatement();
			ResultSet resultSetCheck = stmtSqlCheck.executeQuery(sqlCheck);
			if (resultSetCheck == null || !resultSetCheck.next()) {
				String sqlInsert = "INSERT INTO READ_RECIEPT (READ_RECIEPT_ID,USER_ID, MESSAGE_ID, READ_AT) VALUES (READ_RECIEPT_SEQ.nextval,?, ?, sysdate)";
				PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
				stmtInsert.setInt(1, userId);
				stmtInsert.setInt(2, messageId);
				stmtInsert.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return list;
	}

	// Sudd GetLAtest Message Details
	@RequestMapping(value = { "/restGetLatestMessagesAndro/{lang}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> getLatestMessagesAndro(
			@PathVariable(value = "lang") int lang) {
		LOGGER.debug("Start messageList.");
		ArrayList<Message> messageList = new ArrayList<Message>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "SELECT * FROM (SELECT b.MESSAGE_DETAIL_ID, b.MESSAGE_ID, b.MESSAGE_DATA, b.MESSAGE_TITLE, b.MESSAGE_LINK, a.MSG_STATUS, b.MESSAGE_IMG_LINK FROM message a, MESSAGE_DETAILS b where a.MESSAGE_ID = b.MESSAGE_ID and b.message_lang = 1 and a.MSG_STATUS = 1 and a.IS_DELETED=0  and trunc(a.created_at)<TO_DATE(SYSDATE) and trunc(a.EXPIRY_AT)>=TO_DATE(SYSDATE) ORDER BY a.created_at desc ) WHERE ROWNUM <= 3";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				// AES256JNCryptor cryptor = new AES256JNCryptor();
				BASE64Encoder cryptor = new BASE64Encoder();
				String password = "aes123";
				int message_Id = resultSet.getInt("MESSAGE_ID");
				String msg_detail_Id = new String(cryptor.encode(String
						.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"))
						.getBytes()));
				String msg_Id = new String(cryptor.encode(String.valueOf(
						message_Id).getBytes()));
				String msg_data = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_DATA")).getBytes()));
				String msg_title = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_TITLE")).getBytes()));
				String msg_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_LINK")).getBytes()));
				String msg_img_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_IMG_LINK")).getBytes()));
				String msg_status = "0";
				String sqlGetFavourite = "select * from favourite where message_Id="
						+ message_Id;
				Statement stmtGetFav = conn.createStatement();
				ResultSet resultSetGetFav = stmtGetFav
						.executeQuery(sqlGetFavourite);
				if (resultSetGetFav != null && resultSetGetFav.next()) {
					msg_status = "1";
				}
				messageList.add(new Message(msg_Id, msg_data, msg_title,
						msg_link, msg_img_link, "0", new String(cryptor
								.encode(msg_status.getBytes())), "0"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return messageList;
	}

	// getLatest Messagew Ends
	// GetTodays Message Strats
	@RequestMapping(value = { "/restGetTodaysMessagesAndro/{lang}/{previousdate}/{nextdate}/{categoryId}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> getMessagesForTimePeriodAndro(
			@PathVariable(value = "lang") int lang,
			@PathVariable(value = "previousdate") String previousdate,
			@PathVariable(value = "nextdate") String nextdate,
			@PathVariable(value = "categoryId") String categoryId) {
		LOGGER.debug("Start messageList.");
		ArrayList<Message> messageList = new ArrayList<Message>();
		System.out.println("date=" + previousdate);
		System.out.println("date=" + nextdate);
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = Integer.parseInt(categoryId) != 0 ? "SELECT * from message_details where message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"
					+ previousdate
					+ "', 'yyyy-mm-dd') AND TO_DATE ('"
					+ nextdate
					+ "', 'yyyy-mm-dd') and message.CATEGORY_ID="
					+ categoryId
					+ " and  message.MSG_STATUS=1 and IS_DELETED=0)and MESSAGE_LANG ="
					+ lang
					: "SELECT * from message_details where message_id IN (SELECT message.MESSAGE_ID FROM message WHERE created_at BETWEEN TO_DATE ('"
							+ previousdate
							+ "', 'yyyy-mm-dd') AND TO_DATE ('"
							+ nextdate
							+ "', 'yyyy-mm-dd') and MSG_STATUS=1 and message.IS_DELETED=0)and MESSAGE_LANG ="
							+ lang;
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				// AES256JNCryptor cryptor = new AES256JNCryptor();
				BASE64Encoder cryptor = new BASE64Encoder();
				String password = "aes123";
				String msg_detail_Id = new String(cryptor.encode(String
						.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"))
						.getBytes()));
				String msg_Id = new String(cryptor.encode(String.valueOf(
						resultSet.getInt("MESSAGE_ID")).getBytes()));
				String msg_data = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_DATA")).getBytes()));
				String msg_title = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_TITLE")).getBytes()));
				String msg_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_LINK")).getBytes()));
				String msg_status = new String(cryptor.encode(String.valueOf(
						resultSet.getInt("MESSAGE_STATUS")).getBytes()));
				String msg_img_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_IMG_LINK")).getBytes()));
				messageList.add(new Message(msg_Id, msg_data, msg_title,
						msg_link, msg_img_link, "0", msg_status, "0"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return messageList;
	}

	// GetTodays message Ends
	// Rest Searched Messages Starts
	@RequestMapping(value = { "/restSearchedMessagesAndro/{lang}/{keyword}/{userId}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> searchByKeywordAndro(
			@PathVariable(value = "lang") int lang,
			@PathVariable(value = "keyword") String keyword,
			@PathVariable(value = "userId") int userId) {
		LOGGER.debug("Start messageList.");
		ArrayList<Message> messageList = new ArrayList<Message>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			// String sql =
			// "select md.* from message_details md ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.MESSAGE_TITLE  like '%"
			// + keyword + "%' and md.MESSAGE_LANG=" + lang +
			// " and md.MESSAGE_ID IN(select MESSAGE_ID from message where message.IS_DELETED=0 and message.MSG_STATUS=1)"
			// +
			// " and md.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
			// + userId + ")" +
			// " and md.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID="
			// + userId + ")";
			String sql = "SELECT unique md.*,cc.*,mm.* FROM message_details md,CATEGORY cc,MESSAGE mm ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc  where md.MESSAGE_TITLE  like '%"
					+ keyword
					+ "%' and md.MESSAGE_LANG="
					+ lang
					+ " and mm.MSG_STATUS=1 and mm.IS_DELETED=0 and cc.categoryid = mm.category_id and md.MESSAGE_ID = mm.MESSAGE_ID and mm.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
					+ userId
					+ ") and mm.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID="
					+ userId + ")";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			String category_name = "";
			while (resultSet.next()) {
				// AES256JNCryptor cryptor = new AES256JNCryptor();
				BASE64Encoder cryptor = new BASE64Encoder();
				String password = "aes123";
				String msg_detail_Id = new String(cryptor.encode(String
						.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"))
						.getBytes()));
				String msg_Id = new String(cryptor.encode(String.valueOf(
						resultSet.getInt("MESSAGE_ID")).getBytes()));
				String msg_data = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_DATA")).getBytes()));
				String msg_title = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_TITLE")).getBytes()));
				String msg_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_LINK")).getBytes()));
				String msg_status = new String(cryptor.encode(String.valueOf(
						resultSet.getInt("MESSAGE_STATUS")).getBytes()));
				String msg_img_link = new String(cryptor.encode(String.valueOf(
						resultSet.getString("MESSAGE_IMG_LINK")).getBytes()));
				if (lang == 2) {
					category_name = new String(cryptor.encode(String.valueOf(
							resultSet.getString("CATEGORY_NAME_BHASA"))
							.getBytes()));
				} else
					category_name = new String(cryptor.encode(String.valueOf(
							resultSet.getString("CATEGORYNAME")).getBytes()));
				String creation_date = new String(cryptor.encode(String
						.valueOf(resultSet.getString("CREATED_AT")).getBytes()));
				String category_Id = new String(cryptor.encode(String.valueOf(
						resultSet.getInt("CATEGORYID")).getBytes()));
				messageList.add(new Message(msg_Id, msg_data, msg_title,
						msg_link, msg_img_link, "0", msg_status, "0",
						creation_date, category_name));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return messageList;
	}

	// Rest Searched Message Ends
	@RequestMapping(value = { "/restGetFavouriteMessagesAndro/{userId}/{lang}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<Message> searchByKeywordAndro(
			@PathVariable(value = "userId") int userId,
			@PathVariable(value = "lang") int lang) {
		LOGGER.debug("Start messageList.");
		String msg = "1";
		String readFlag = "0";
		ArrayList<Message> messageList = new ArrayList<Message>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "SELECT unique md.*,cc.*,mm.* FROM message_details md,CATEGORY cc,MESSAGE mm ,MESSAGE_ZONE mz, MESSAGE_CHANNEL mc where md.message_id IN (SELECT message_id from favourite where user_id="
					+ userId
					+ ")"
					+ " and md.MESSAGE_LANG="
					+ lang
					+ " and mm.MSG_STATUS=1 and mm.IS_DELETED=0 "
					+ " and cc.categoryid = mm.category_id and md.MESSAGE_ID = mm.MESSAGE_ID "
					+ " and mm.MESSAGE_ID=mz.MESSAGE_ID and mz.ZONE_ID IN (Select USER_ZONE.ZONE_ID from USER_ZONE where USER_ZONE.USER_ID="
					+ userId
					+ ")"
					+ " and mm.MESSAGE_ID=mc.MESSAGE_ID and mc.CHANNEL_ID IN (Select USER_CHANNEL.CHANNEL_ID from USER_CHANNEL where USER_CHANNEL.USER_ID="
					+ userId
					+ ")"
					+ " and mm.created_at<SYSDATE and mm.EXPIRY_AT>=SYSDATE ORDER BY mm.CREATED_AT desc";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				// AES256JNCryptor cryptor = new AES256JNCryptor();
				BASE64Encoder cryptor = new BASE64Encoder();
				String password = "aes123";
				int message_Id = resultSet.getInt("MESSAGE_ID");
				String msg_detail_Id = new String(cryptor.encode(String
						.valueOf(resultSet.getInt("MESSAGE_DETAIL_ID"))
						.getBytes()));
				String msg_Id = new String(cryptor.encode(String.valueOf(
						message_Id).getBytes()));
				String msg_data = "";
				String category_name = "";
				String msg_title = "";
				String msg_link = "";
				String msg_status = "";
				String msg_link2 = "1";
				String creationdate = "";
				String noOfLikes = "0";
				if (resultSet.getString("MESSAGE_DATA") != null) {
					msg_data = new String(cryptor.encode(resultSet.getString(
							"MESSAGE_DATA").getBytes()));
				}
				if (resultSet.getString("MESSAGE_TITLE") != null) {
					msg_title = new String(cryptor.encode(String.valueOf(
							resultSet.getString("MESSAGE_TITLE")).getBytes()));
				}
				if (resultSet.getString("MESSAGE_LINK") != null) {
					msg_link = new String(cryptor.encode(String.valueOf(
							resultSet.getString("MESSAGE_LINK")).getBytes()));
				}
				if (resultSet.getString("MESSAGE_STATUS") != null) {
					msg_status = new String(cryptor.encode(String.valueOf(
							resultSet.getInt("MESSAGE_STATUS")).getBytes()));
				}
				if (resultSet.getString("MESSAGE_IMG_LINK") != null) {
					msg_link2 = new String(
							cryptor.encode(String.valueOf(
									resultSet.getString("MESSAGE_IMG_LINK"))
									.getBytes()));
				}
				if (resultSet.getTimestamp("CREATED_AT") != null) {
					creationdate = new String(cryptor.encode(String.valueOf(
							resultSet.getTimestamp("CREATED_AT")).getBytes()));
				}

				if (lang == 2) {
					category_name = new String(cryptor.encode(String.valueOf(
							resultSet.getString("CATEGORY_NAME_BHASA"))
							.getBytes()));
				} else if (resultSet.getString("CATEGORYNAME") != null) {
					category_name = new String(cryptor.encode(String.valueOf(
							resultSet.getString("CATEGORYNAME")).getBytes()));
				}
				String sqlReadFlag = "select * from Read_reciept where user_Id="
						+ userId + " and message_Id=" + message_Id;
				Statement stmtReadFlag = conn.createStatement();
				ResultSet resultSetReadFlag = stmtReadFlag
						.executeQuery(sqlReadFlag);
				readFlag = resultSetReadFlag != null
						&& resultSetReadFlag.next() ? new String(
						cryptor.encode("1".getBytes())) : new String(
						cryptor.encode("0".getBytes()));
				String sqlNoOfLikes = "select COUNT(*) from message_like where message_Id="
						+ message_Id;
				Statement stmt2 = conn.createStatement();
				ResultSet resultSet2 = stmt2.executeQuery(sqlNoOfLikes);
				if (resultSet2 != null && resultSet2.next()) {
					do {
						noOfLikes = new String(cryptor.encode(String.valueOf(
								resultSet2.getInt(1)).getBytes()));
					} while (resultSet2.next());
				}
				String msgLike = "0";
				String sqlGetLikes = "select * from message_like where Message_Id="
						+ message_Id + " and user_Id=" + userId;
				Statement stmtGetLikes = conn.createStatement();
				ResultSet resultSetGetLikes = stmtGetLikes
						.executeQuery(sqlGetLikes);
				if (resultSetGetLikes != null && resultSetGetLikes.next()) {
					msgLike = "1";
				}
				String msgFav = "0";
				String sqlGetFav = "select * from favourite where Message_Id="
						+ message_Id + " and user_Id=" + userId;
				Statement stmtGetFav = conn.createStatement();
				ResultSet resultSetGetFav = stmtGetFav.executeQuery(sqlGetFav);
				if (resultSetGetFav != null && resultSetGetFav.next()) {
					msgFav = "1";
				}
				messageList.add(new Message(msg_detail_Id, msg_Id, msg_data,
						msg_title, msg_link, new String(cryptor.encode(msgFav
								.getBytes())), msg_link2, category_name,
						readFlag, noOfLikes, new String(cryptor.encode(msgLike
								.getBytes())), creationdate));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return messageList;
	}

	//@RequestMapping(value = { "/restloggedin/{userid}/{username}/{location}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	 @RequestMapping(value={"/restloggedin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public String getLoggedin(@RequestBody RestLoggedIn  restlogged)
	/*public String getLoggedin(@PathVariable(value = "userid") String userid,
			@PathVariable(value = "username") String username,
			@PathVariable(value = "location") String location)*/ {
		// LOGGER.debug("Start searchByKeywordTestAndro.");
		 
		//server side code for onesignal
			
			try {
				
				System.out.println("inside send notification");
				   String jsonResponse;
				   
				   URL url = new URL("https://onesignal.com/api/v1/notifications");
				   HttpURLConnection con = (HttpURLConnection)url.openConnection();
				   con.setUseCaches(false);
				   con.setDoOutput(true);
				   con.setDoInput(true);

				   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				   con.setRequestProperty("Authorization", "Basic MWIwYzFlMTYtYzAyMS00NmI3LTliNDMtOTA4MDQxMjgzZTNm");
				   con.setRequestMethod("POST");

				   String strJsonBody = "{"
				                      +   "\"app_id\": \"28073ff7-477c-4bd5-80e8-00a1aa2a0c9f\","
				                      +   "\"included_segments\": [\"All\"],"
				                      +   "\"data\": {\"foo\": \"bar\"},"
				                      +   "\"contents\": {\"en\": \"GO TO MESSAGES SATYA2\"}"
				                      + "}";
				         
				   
				   System.out.println("xxxxxxxxxx strJsonBody:\n" + strJsonBody);

				   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
				   con.setFixedLengthStreamingMode(sendBytes.length);

				   OutputStream outputStream = con.getOutputStream();
				   outputStream.write(sendBytes);

				   int httpResponse = con.getResponseCode();
				   System.out.println("httpResponse: " + httpResponse);

				   if (  httpResponse >= HttpURLConnection.HTTP_OK
				      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				      //Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
				      //jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				      jsonResponse = "hello 1111111 SUCCESS";
				      
				      //scanner.close();
				   }
				   else {
				      //Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
				      //jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				      //scanner.close();
				      jsonResponse = "hello 2222222 SUCCESS";
				   }
				   System.out.println(" XXXXXXXXXXXXXX jsonResponse:\n" + jsonResponse);
				   
				} catch(Throwable t) {
				   t.printStackTrace();
				   
				}
			System.out.println("Done------------");
			
		String location=restlogged.getLocation();
		String userid=restlogged.getUserid();
		String username=restlogged.getUsername();

		System.out.println("Location value is " + location +" userid "+ userid +" username "+ username);
		byte[] valueDecoded = Base64.decodeBase64(location);
		System.out.println("Decoded value is " + new String(valueDecoded));
		String locationapp = new String(valueDecoded);
		System.out.println("locationapp++" + locationapp);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// get current date time with Date()
		Date date = new Date();
		System.out.println("get loggedin xxxxxxxxxxx ");
		String loggeddt = dateFormat.format(date);
		// System.out.println(dateFormat.format(date));
		// ArrayList<Version> versionList = new ArrayList<Version>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sqlSaveEditedMsg = "INSERT INTO loggedin (USERID,USER_NAME,STATUS,LOGGEDIN_DT,LOCATION) VALUES (?,?,?,?,?)";
			PreparedStatement stmtSaveEditedMsg = conn
					.prepareStatement(sqlSaveEditedMsg);
			stmtSaveEditedMsg.setString(1, userid);
			stmtSaveEditedMsg.setString(2, username);
			stmtSaveEditedMsg.setString(3, "Y");
			stmtSaveEditedMsg.setString(4, loggeddt);
			stmtSaveEditedMsg.setString(5, locationapp);

			// get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			// System.out.println(dateFormat.format(cal.getTime()));
			stmtSaveEditedMsg.executeUpdate();
			// String sql="SELECT * FROM loggedin where USERID="+userid;
			// Statement stmt = conn.createStatement();
			// ResultSet resultSet = stmt.executeQuery(sql);
			// while (resultSet.next()) {
			// // AES256JNCryptor cryptor = new AES256JNCryptor();
			// BASE64Encoder cryptor=new BASE64Encoder();
			// String password = "aes123";
			// double versionno = resultSet.getDouble("VERSION_NO");
			//
			// versionList.add(new Version(versionno));
			// }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "success";
	}

	@RequestMapping(value = { "/restlogoff/{userid}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public String getLogoff(@PathVariable(value = "userid") int userid) {
		LOGGER.debug("Start searchByKeywordTestAndro.");

		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sqlSaveEditedMsg = "INSERT INTO loggedin (USERID,STATUS) VALUES (?,?)";
			PreparedStatement stmtSaveEditedMsg = conn
					.prepareStatement(sqlSaveEditedMsg);
			stmtSaveEditedMsg.setInt(1, userid);
			stmtSaveEditedMsg.setString(2, "N");
			stmtSaveEditedMsg.executeUpdate();
			// String sql="SELECT * FROM loggedin where USERID="+userid;
			// Statement stmt = conn.createStatement();
			// ResultSet resultSet = stmt.executeQuery(sql);
			// while (resultSet.next()) {
			// // AES256JNCryptor cryptor = new AES256JNCryptor();
			// BASE64Encoder cryptor=new BASE64Encoder();
			// String password = "aes123";
			// double versionno = resultSet.getDouble("VERSION_NO");
			//
			// versionList.add(new Version(versionno));
			// }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "success";
	}

	// @RequestMapping(value={"/restgetversion/{version}"},method={RequestMethod.GET},
	// produces={"application/json"})
	// @ResponseBody
	// public ArrayList<Version> getVersion(@PathVariable(value="version")
	// double version) {
	// LOGGER.debug("Start restgetversion.");
	//
	// ArrayList<Version> versionList = new ArrayList<Version>();
	// try {
	// Class.forName(this.constantDb.driverClassName);
	// Connection conn =
	// DriverManager.getConnection(this.constantDb.databaseUrl,
	// this.constantDb.username, this.constantDb.password);
	// String sql="SELECT * FROM version where VERSION_NO="+version;
	// Statement stmt = conn.createStatement();
	// ResultSet resultSet = stmt.executeQuery(sql);
	// while (resultSet.next()) {
	// // AES256JNCryptor cryptor = new AES256JNCryptor();
	// BASE64Encoder cryptor=new BASE64Encoder();
	// String password = "aes123";
	// // double versionno = resultSet.getDouble("VERSION_NO");
	//
	// versionList.add(new Version(versionno));
	// }
	// }
	// catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// }
	// catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return versionList;
	// }
	//
	@RequestMapping(value = { "/restgetversion/{major}/{minor}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public Boolean getVersionMajor(@PathVariable(value = "major") int major,
			@PathVariable(value = "minor") int minor) {
		LOGGER.debug("Start restgetversion.");
		Boolean result = false;
		int sqlresult = 0;
		int versionno = 0;
		int minorresult = 0;
		ArrayList<Version> versionList = new ArrayList<Version>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "SELECT * FROM version where MAJOR=" + major
					+ " and MINOR<=" + minor;
			// String sqlminor="SELECT * FROM version where MINOR="+minor;
			Statement stmt = conn.createStatement();
			sqlresult = stmt.executeUpdate(sql);
			Statement stmtminor = conn.createStatement();
			// ResultSet resultSetminor = stmt.executeQuery(sqlminor);
			// while (resultSet.next()) {
			// // AES256JNCryptor cryptor = new AES256JNCryptor();
			// BASE64Encoder cryptor=new BASE64Encoder();
			// String password = "aes123";
			// //versionno = resultSet.getInt("MAJOR");
			//
			// // versionList.add(new Version(versionno));
			// }
			if (sqlresult == 1) {

				result = true;

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return result;
	}

	@RequestMapping(value = { "/setreadmessagesandro/{messageId}/{userId}" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public String setReadMessagesAndro(
			@PathVariable(value = "userId") int userId,
			@PathVariable(value = "messageId") int messageId, Model model) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sqlCheck = (new StringBuilder())
					.append("Select * from READ_RECIEPT where message_Id=")
					.append(messageId).append(" and user_Id=").append(userId)
					.toString();
			Statement stmtSqlCheck = conn.createStatement();
			ResultSet resultSetCheck = stmtSqlCheck.executeQuery(sqlCheck);
			if (resultSetCheck == null || !resultSetCheck.next()) {
				String sqlInsert = "INSERT INTO READ_RECIEPT (READ_RECIEPT_ID,USER_ID, MESSAGE_ID, READ_AT) VALUES (READ_RECIEPT_SEQ.nextval,?, ?, sysdate)";
				PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
				stmtInsert.setInt(1, userId);
				stmtInsert.setInt(2, messageId);
				result = stmtInsert.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return (new StringBuilder()).append("{\"result\":").append(result)
				.append("}").toString();
	}

	@RequestMapping(value = { "/restLikeMessages/{userId}/{messageId}" }, method = { RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public String setLike(@PathVariable(value = "userId") int userId,
			@PathVariable(value = "messageId") int messageId, Model model) {
		int result = 0;
		System.out.println("inside restlikemessage");
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "insert into MESSAGE_LIKE (LIKE_ID, USER_ID, MESSAGE_ID) values (MESSAGE_LIKE_SEQ.nextval, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, messageId);
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return "{\"result\":" + result + "}";
	}

	@RequestMapping(value = { "/setreadmessages/{messageId}/{userId}" }, method = { RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public String setReadMessages(@PathVariable(value = "userId") int userId,
			@PathVariable(value = "messageId") int messageId, Model model) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sqlCheck = "Select * from READ_RECIEPT where message_Id="
					+ messageId + " and user_Id=" + userId;
			Statement stmtSqlCheck = conn.createStatement();
			ResultSet resultSetCheck = stmtSqlCheck.executeQuery(sqlCheck);
			if (resultSetCheck == null || !resultSetCheck.next()) {
				String sqlInsert = "INSERT INTO READ_RECIEPT (READ_RECIEPT_ID,USER_ID, MESSAGE_ID, READ_AT) VALUES (READ_RECIEPT_SEQ.nextval,?, ?, sysdate)";
				PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
				stmtInsert.setInt(1, userId);
				stmtInsert.setInt(2, messageId);
				result = stmtInsert.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return "{\"result\":" + result + "}";
	}

	@RequestMapping(value = { "/restFavouriteMessages/{userId}/{messageId}" }, method = { RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public String setFavourite(@PathVariable(value = "userId") int userId,
			@PathVariable(value = "messageId") int messageId, Model model) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "insert into FAVOURITE (FAVOURITE_ID, USER_ID, MESSAGE_ID) values (FAVOURITE_SEQ.nextval, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, messageId);
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return "{\"result\":" + result + "}";
	}

	@RequestMapping(value = { "/insertdevicetoken/{token}/{userId}" }, method = { RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public String getDeviceId(@PathVariable(value = "token") String token,
			@PathVariable(value = "userId") int userId, Model model) {
		int result = 0;
		if (token.trim().length() > 0 && !token.equalsIgnoreCase("(null)")) {
			Connection conn = null;
			
			try {
				conn = DBConnection.getConnection();
				
				if (token.length() > 0 && token != null) {
					PreparedStatement stmt;
					String sql;
					String sqlCheckDeviceExist = "select * from devices where  DEVICE_TOKEN=?";
					PreparedStatement stmtCheckDeviceExist = conn
							.prepareStatement(sqlCheckDeviceExist);
					stmtCheckDeviceExist.setString(1, token);
					ResultSet resultSetCheckDeviceExist = stmtCheckDeviceExist
							.executeQuery();
					if (resultSetCheckDeviceExist != null
							&& resultSetCheckDeviceExist.next()) {
						sql = "update devices set user_Id=? where DEVICE_TOKEN=?";
						stmt = conn.prepareStatement(sql);
						stmt.setInt(1, userId);
						stmt.setString(2, token);
						result = stmt.executeUpdate();
					} else {
						sql = "insert into devices (DEVICE_ID, DEVICE_TOKEN,USER_ID) values (DEVICES_SEQ.nextval, ?,?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, token);
						stmt.setInt(2, userId);
						result = stmt.executeUpdate();
					}
					String checkUserId = "select * from badge_count where user_Id="
							+ userId;
					Statement stmtCheck = conn.createStatement();
					ResultSet resultSetCheck = stmtCheck
							.executeQuery(checkUserId);
					if (resultSetCheck == null || !resultSetCheck.next()) {
						String sqlInserInitialBadgeCount = "INSERT INTO BADGE_COUNT (BADGE_COUNT_ID, USER_ID, DEVICE_BADGE_COUNT) VALUES (BADGE_COUNT_SEQ.nextval, "
								+ userId + ", '0')";
						Statement stmtBadgeCount = conn.createStatement();
						stmtBadgeCount.executeUpdate(sqlInserInitialBadgeCount);
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				DBConnection.closeConnection(conn);
			}
		}
		
		return "" + result;
	}

	@RequestMapping(value = { "/insertdevicetokenandroid/{token}/{userId}" }, method = { RequestMethod.POST }, consumes = { "application/json" })
	@ResponseBody
	public String getDeviceIdAndroid(
			@PathVariable(value = "token") String token,
			@PathVariable(value = "userId") int userId, Model model) {
		int result = 0;
		if (token.trim().length() > 0 && !token.equalsIgnoreCase("(null)")) {
			Connection conn = null;
			
			try {
				conn = DBConnection.getConnection();
				
				if (token.length() > 0 && token != null) {
					PreparedStatement stmt;
					String sql;
					String sqlCheckDeviceExist = "select * from devices_android where  DEVICE_TOKEN=?";
					PreparedStatement stmtCheckDeviceExist = conn
							.prepareStatement(sqlCheckDeviceExist);
					stmtCheckDeviceExist.setString(1, token);
					ResultSet resultSetCheckDeviceExist = stmtCheckDeviceExist
							.executeQuery();
					if (resultSetCheckDeviceExist != null
							&& resultSetCheckDeviceExist.next()) {
						sql = "update devices_android set user_Id=? where DEVICE_TOKEN=?";
						stmt = conn.prepareStatement(sql);
						stmt.setInt(1, userId);
						stmt.setString(2, token);
						result = stmt.executeUpdate();
					} else {
						sql = "insert into devices_android (DEVICE_ID, DEVICE_TOKEN,USER_ID) values (DEVICES_ANDROID_SEQ.nextval, ?,?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, token);
						stmt.setInt(2, userId);
						result = stmt.executeUpdate();
					}
					String checkUserId = "select * from badge_count where user_Id="
							+ userId;
					Statement stmtCheck = conn.createStatement();
					ResultSet resultSetCheck = stmtCheck
							.executeQuery(checkUserId);
					if (resultSetCheck == null || !resultSetCheck.next()) {
						String sqlInserInitialBadgeCount = "INSERT INTO BADGE_COUNT (BADGE_COUNT_ID, USER_ID, BADGE_COUNT) VALUES (BADGE_COUNT_SEQ.nextval, "
								+ userId + ", '0')";
						Statement stmtBadgeCount = conn.createStatement();
						stmtBadgeCount.executeUpdate(sqlInserInitialBadgeCount);
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				DBConnection.closeConnection(conn);
			}
			
		}
		return "{\"result\":" + result + "}";
	}

	@RequestMapping(value = { "/deletefavourites/{userId}/{messageId}" }, method = { RequestMethod.GET })
	@ResponseBody
	public String deleteFevourites(@PathVariable(value = "userId") int userId,
			@PathVariable(value = "messageId") int messageId, Model model) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "delete from FAVOURITE where USER_ID = " + userId
					+ " and MESSAGE_ID = " + messageId;
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return "\"result\":" + result;
	}

	@RequestMapping(value = { "/deletelikes/{userId}/{messageId}" }, method = { RequestMethod.GET })
	@ResponseBody
	public String deleteLikes(@PathVariable(value = "userId") int userId,
			@PathVariable(value = "messageId") int messageId, Model model) {
		int result = 0;
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "delete from MESSAGE_LIKE where USER_ID = " + userId
					+ " and MESSAGE_ID = " + messageId;
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		
		return "\"result\":" + result;
	}

}
