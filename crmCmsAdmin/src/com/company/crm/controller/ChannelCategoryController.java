package com.company.crm.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.crm.constant.URIConstants;
import com.company.crm.model.Category;
import com.company.crm.model.Channel;
import com.company.crm.model.ChannelCategory;
import com.company.crm.model.User;
import com.company.crm.util.DBConnection;

@Controller
public class ChannelCategoryController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( ChannelCategoryController.class);

	/*@RequestMapping(value="/pendingchannel", method = RequestMethod.GET)
	public String pendingChannel(Model model, HttpSession session) throws ClassNotFoundException, SQLException {
		LOGGER.debug("Start messageList.");

		List<Channel> channelList = new ArrayList<Channel>();
		
		Class.forName(constantDb.driverClassName);
		Connection conn = DriverManager.getConnection(constantDb.databaseUrl, constantDb.username,constantDb.password);
		
		String sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 0 order by a.channelid";
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery(sql);

		while (resultSet.next()) {
			int channelid = resultSet.getInt("channelid");
			String channelname = String.valueOf(resultSet.getString("channelname"));
			String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
			
			channelList.add(new Channel(channelid, channelname, isapproved));

		}
		model.addAttribute("channelList", channelList);
		model.addAttribute("channel", new Channel());
		return "pendingChannel";

	}
	
	@RequestMapping(value = "/approvechannel", method = RequestMethod.GET)
	public  String approveChannel(Model model,HttpServletResponse response,@RequestParam("catid") String  catid) 
	{
		
		System.err.println("Value =="+catid);
		System.err.println("Value =="+catid);
		System.err.println("Value =="+catid);
		//String channelId="";
		try {
			Class.forName(constantDb.driverClassName);
			Connection conn = DriverManager.getConnection(constantDb.databaseUrl, constantDb.username,constantDb.password);
			
			System.err.println("Inside the Method calllll");
			//update msg status
			String sql="UPDATE CHANNEL SET ISAPPROVED ='1' WHERE CHANNELID="+ catid;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
		}catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return "channelapproved";
		
	}*/
	
	
	@RequestMapping(value = URIConstants.MY_CHANNEL_CATEGORY, method = RequestMethod.GET)
	public String channelCategoryMapping(HttpServletResponse response, Model model, HttpSession session) {
		LOGGER.debug("Start channelList.");
		List<ChannelCategory> channelCategoryList = new ArrayList<ChannelCategory>();
		List<Channel> channelList = new ArrayList<Channel>();
		List<Category> categoryList = new ArrayList<Category>();

		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
			 sql=" select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql=" select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 order by a.categoryid desc";
			 }
			//String sql1="select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 order by a.categoryid";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql);
	
			while (resultSet1.next()) {
				int categoryId = resultSet1.getInt("categoryid");
				String categoryName = String.valueOf(resultSet1.getString("categoryname"));
				String isapproved = String.valueOf(resultSet1.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId, categoryName, isapproved));
	
			}
			
			String sql2="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
			 sql2=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			 }
			 else{
				 sql2=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 order by a.channelid desc";
			 }
			
			
		//	String sql2="select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 order by a.channelid";
			Statement stmt2 = conn.createStatement();
			ResultSet resultSet2 = stmt2.executeQuery(sql2);

			while (resultSet2.next()) {
				int channelid = resultSet2.getInt("channelid");
				String channelname = String.valueOf(resultSet2.getString("channelname"));
				String isapproved = String.valueOf(resultSet2.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
			
			String sql3="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){

					 sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
							   " join channel b on a.channelid = b.channelid " +
							   " join category c on a.categoryid = c.categoryid " +
							   " where c.username='"+strUserName+"' order by a.map_id desc";
			 }
			 else{
				 sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
						   " join channel b on a.channelid = b.channelid " +
						   " join category c on a.categoryid = c.categoryid " +
						   " order by a.map_id desc";
			 }
			/*String sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
					   " join channel b on a.channelid = b.channelid " +
					   " join category c on a.categoryid = c.categoryid " +
					   " order by a.map_id ";*/
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql3);

			while (resultSet.next()) {
				int map_id = resultSet.getInt("map_id");
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				int categoryid = resultSet.getInt("categoryid");
				String categoryname = String.valueOf(resultSet.getString("categoryname"));
				
				channelCategoryList.add(new ChannelCategory(map_id,channelid, channelname,categoryid, categoryname));
	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("channelList", channelList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("channelCategoryList", channelCategoryList);
		model.addAttribute("channelcategory", new ChannelCategory());
		
		return "mychannelCategoryMap";

	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = URIConstants.SAVE_CHANNEL_CATEGORY , method = RequestMethod.POST)
	public String savechannelcategory(HttpServletResponse response,Model model, HttpServletRequest request,HttpSession session)throws IOException {
		List<ChannelCategory> channelCategoryList = new ArrayList<ChannelCategory>();
		List<Channel> channelList = new ArrayList<Channel>();
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			String channelId = request.getParameter("channelId");
			String categoryId = request.getParameter("categoryId");
			//String strUserName=request.getParameter("userName");
			
			int rowcount = 0;
			String sql4 = "select count(*) as rowcount from map_channel_category a where a.categoryid = "+categoryId+" and a.channelid = "+channelId;
			Statement stmt4 = conn.createStatement();
			ResultSet resultSet4 = stmt4.executeQuery(sql4);
	
			while (resultSet4.next()) {
				rowcount = resultSet4.getInt("rowcount");
			}
			if(rowcount == 0){
				String sql = "insert into map_channel_category  (map_id,channelid,categoryid,USERNAME) "
						+ "values(MAP_CHANNEL_CATEGORY_SEQ.nextval,?,?,?)";
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, channelId);
				stmt.setString(2, categoryId);
				stmt.setString(3, strUserName);
				int queryresult = stmt.executeUpdate();	
			}
			
			String sql1=" select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql1);
	
			while (resultSet1.next()) {
				int categoryId1 = resultSet1.getInt("categoryid");
				String categoryName = String.valueOf(resultSet1.getString("categoryname"));
				String isapproved = String.valueOf(resultSet1.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId1, categoryName, isapproved));
	
			}
			
			String sql2=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			Statement stmt2 = conn.createStatement();
			ResultSet resultSet2 = stmt2.executeQuery(sql2);

			while (resultSet2.next()) {
				int channelid1 = resultSet2.getInt("channelid");
				String channelname = String.valueOf(resultSet2.getString("channelname"));
				String isapproved = String.valueOf(resultSet2.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid1, channelname, isapproved));
	
			}	
			
			String sql3="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){

					 sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
							   " join channel b on a.channelid = b.channelid " +
							   " join category c on a.categoryid = c.categoryid " +
							   " where c.username='"+strUserName+"' order by a.map_id desc";
			 }
			 else{
				 sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
						   " join channel b on a.channelid = b.channelid " +
						   " join category c on a.categoryid = c.categoryid " +
						   " order by a.map_id desc";
			 }
		/*		
			String sql3="select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
					   " join channel b on a.channelid = b.channelid " +
					   " join category c on a.categoryid = c.categoryid " +
					   " order by a.map_id ";*/
			Statement stmt3 = conn.createStatement();
			ResultSet resultSet = stmt3.executeQuery(sql3);

			while (resultSet.next()) {
				int map_id = resultSet.getInt("map_id");
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				int categoryid = resultSet.getInt("categoryid");
				String categoryname = String.valueOf(resultSet.getString("categoryname"));
				
				channelCategoryList.add(new ChannelCategory(map_id,channelid, channelname,categoryid, categoryname));
	
			}
			model.addAttribute("channelList", channelList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("channelCategoryList", channelCategoryList);
			model.addAttribute("channelcategory", new ChannelCategory());
			if(rowcount == 0){
				model.addAttribute("isAdded", (Object) true);
			}else{
				model.addAttribute("isDuplicate", (Object) true);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			model.addAttribute("channelcategory", new ChannelCategory());
			e.printStackTrace();
		} catch (Exception e) {
			model.addAttribute("channelcategory", new ChannelCategory());
			e.printStackTrace();
		} 

		return "mychannelCategoryMap";
	}
	

	@RequestMapping(value = "/deletechannelcategory/{id}", method = RequestMethod.GET)
	public String deletechannelcategory(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<ChannelCategory> channelCategoryList = new ArrayList<ChannelCategory>();
		List<Channel> channelList = new ArrayList<Channel>();
		List<Category> categoryList = new ArrayList<Category>();
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			
			String sql="delete from map_channel_category where map_id="+ id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			 
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql1="";
			 if(strUserName.equalsIgnoreCase("admin")|| strUserName.equalsIgnoreCase("banca"))
			 {
			  sql1=" select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.categoryid desc";
			 }
			 else{
				 sql1=" select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 order by a.categoryid desc";	 
			 }
			
			//String sql1=" select a.categoryid,a.categoryname,a.isapproved from category a where nvl(a.isapproved,0) = 1 order by a.categoryid";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql1);
	
			while (resultSet1.next()) {
				int categoryId1 = resultSet1.getInt("categoryid");
				String categoryName = String.valueOf(resultSet1.getString("categoryname"));
				String isapproved = String.valueOf(resultSet1.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				categoryList.add(new Category(categoryId1, categoryName, isapproved));
	
			}
			
			 String sql2="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
			 sql2=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			 }
			 else{
				 sql2=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1  order by a.channelid desc";
			 }
			
			//String sql2=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 order by a.channelid";
			Statement stmt2 = conn.createStatement();
			ResultSet resultSet2 = stmt2.executeQuery(sql2);

			while (resultSet2.next()) {
				int channelid1 = resultSet2.getInt("channelid");
				String channelname = String.valueOf(resultSet2.getString("channelname"));
				String isapproved = String.valueOf(resultSet2.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid1, channelname, isapproved));
	
			}	
			String sql3="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){

					 sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
							   " join channel b on a.channelid = b.channelid " +
							   " join category c on a.categoryid = c.categoryid " +
							   " where c.username='"+strUserName+"' order by a.map_id desc";
			 }
			 else{
				 sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
						   " join channel b on a.channelid = b.channelid " +
						   " join category c on a.categoryid = c.categoryid " +
						   " order by a.map_id desc";
			 }
			 
			/*String sql3=" select a.map_id,b.channelid,b.channelname,c.categoryid,c.categoryname from map_channel_category a " +
					   " join channel b on a.channelid = b.channelid " +
					   " join category c on a.categoryid = c.categoryid " +
					   " order by a.map_id ";*/
			Statement stmt3 = conn.createStatement();
			ResultSet resultSet = stmt3.executeQuery(sql3);

			while (resultSet.next()) {
				int map_id = resultSet.getInt("map_id");
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				int categoryid = resultSet.getInt("categoryid");
				String categoryname = String.valueOf(resultSet.getString("categoryname"));
				
				channelCategoryList.add(new ChannelCategory(map_id,channelid, channelname,categoryid, categoryname));
	
			}
			model.addAttribute("channelList", channelList);
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("channelCategoryList", channelCategoryList);
			model.addAttribute("channelcategory", new ChannelCategory());
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return "mychannelCategoryMap";

	}
	
}