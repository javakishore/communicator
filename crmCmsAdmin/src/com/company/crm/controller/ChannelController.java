package com.company.crm.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.company.crm.constant.URIConstants;
import com.company.crm.model.Channel;
import com.company.crm.model.ConstantDb;
import com.company.crm.model.User;
import com.company.crm.util.DBConnection;

@Controller
public class ChannelController {
	private static final Logger LOGGER = LoggerFactory.getLogger((Class) ChannelController.class);

	
	
	@RequestMapping(value = URIConstants.MY_CHANNEL, method = RequestMethod.GET)
	public String myChannel(HttpServletResponse response, Model model, HttpSession session) {
		LOGGER.debug("Start channelList.");
		List<Channel> channelList = new ArrayList<Channel>();

		@SuppressWarnings("unchecked")
		List<String> array = new ArrayList<String>();
		array.add("array1");
		Connection conn = null;
		try {
			
			System.out.println("inside my channel");
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
			 sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			 }
			 else{
				 sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1  order by a.channelid desc";
			 }
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("channelList", channelList);
		
		return "mychannel";

	}
	
	@RequestMapping(value = URIConstants.SAVE_CHANNEL , method = RequestMethod.POST)
	public String savechannel(HttpServletResponse response,Model model, HttpServletRequest request)throws IOException {
		List<Channel> channelList = new ArrayList<Channel>();
		Connection conn = null;
		try {
			System.out.println("inside Save channel");
			conn = DBConnection.getConnection();
			//String strchannelid = request.getParameter("channelId");
			String strchannelName = request.getParameter("channelName");
			String strUserName=request.getParameter("userName");
			int rowcount = 0;
			String sql4 = "select count(*) as rowcount from channel a where a.channelname = '"+strchannelName+"'";
			Statement stmt4 = conn.createStatement();
			System.out.println("sql4" + sql4);
			ResultSet resultSet4 = stmt4.executeQuery(sql4);
	
			while (resultSet4.next()) {
				rowcount = resultSet4.getInt("rowcount");
				System.out.println("rowcount "+ rowcount);
			}
			
			if(rowcount == 0){
				String sql = "insert into channel  (channelid,channelname,isapproved,action,username) "
						+ "values(CHANNEL_SEQ.nextval,?,0,0,?)";
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, strchannelName);
				stmt.setString(2, strUserName);
				int queryresult = stmt.executeUpdate();	
				System.out.println("stmt"+queryresult);
			}			
			
			String sql1=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet = stmt1.executeQuery(sql1);

			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				System.out.println("channelid"+channelid);
				System.out.println("channelname"+channelname);
				System.out.println("isapproved"+isapproved);
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
			model.addAttribute("channelList", channelList);
			if(rowcount == 0){
				model.addAttribute("ischannelAdded", (Object) true);
			}else{
				model.addAttribute("isDuplicate", (Object) true);
			}
			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "mychannel";
	}
	
	@RequestMapping(value = "/updatechannel/{id}/{name}" , method = RequestMethod.GET)
	public String updatechannel(@PathVariable("id") int id,@PathVariable("name") String name,HttpServletResponse response,Model model, HttpServletRequest request)throws IOException {
		List<Channel> channelList = new ArrayList<Channel>();
		Connection conn = null;
		try {
			
			System.out.println("inside update channel");
			conn = DBConnection.getConnection();
			int rowcount = 0;
			String sql4 = "select count(*) as rowcount from channel a where a.channelname = '"+name+"'";
			Statement stmt4 = conn.createStatement();
			ResultSet resultSet4 = stmt4.executeQuery(sql4);
	
			while (resultSet4.next()) {
				rowcount = resultSet4.getInt("rowcount");
			}
			if(rowcount == 0){
				String sql="UPDATE channel SET channelname = '"+name+"',isapproved = '0', action = '1' WHERE channelid="+ id;
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			String sql1=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 order by a.channelid desc";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet = stmt1.executeQuery(sql1);

			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
			model.addAttribute("channelList", channelList);
			if(rowcount == 0){
				model.addAttribute("ischannelAdded", (Object) true);
			}else{
				model.addAttribute("isDuplicate", (Object) true);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
 

		return "mychannel";
	}
	
	@RequestMapping(value = "/viewchannel/{id}", method = RequestMethod.GET)
	public String viewchannel(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<Channel> channelList = new ArrayList<Channel>();
		Connection conn = null;
		try{
			System.out.println("inside view channel");
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
				 sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			 }
			 else{
				 sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1  order by a.channelid desc";
			 }
			//String sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
	
			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
			
			String sql1=" select a.channelid,a.channelname,a.isapproved from channel a where a.channelid = "+ id +" and  nvl(a.isapproved,0) = 1";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql1);
			String strChannelName = "";
			int  strChannelId = 0;
			while (resultSet1.next()) {
				strChannelId = resultSet1.getInt("channelid");
				strChannelName = String.valueOf(resultSet1.getString("channelname"));
			} 
			
			model.addAttribute("channelList", channelList);
			model.addAttribute("displayMsg", (Object) true);
			model.addAttribute("isView", (Object) true);
			model.addAttribute("strChannelId", strChannelId);
			model.addAttribute("strChannelName", strChannelName);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "mychannel";

	}
	@RequestMapping(value = "/editchannel/{id}", method = RequestMethod.GET)
	public String editchannel(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<Channel> channelList = new ArrayList<Channel>();
		Connection conn = null;
		try{
			System.out.println("inside edit channel");
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
				 sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid";
			 }
			 else{
				 sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1  order by a.channelid";
			 }
			//String sql=" select a.channelid,a.channelname,a.isapproved from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
	
			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
			
			String sql1=" select a.channelid,a.channelname,a.isapproved from channel a where a.channelid = "+ id +" and  nvl(a.isapproved,0) = 1";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet1 = stmt1.executeQuery(sql1);
			String strChannelName = "";
			int  strChannelId = 0;
			while (resultSet1.next()) {
				strChannelId = resultSet1.getInt("channelid");
				strChannelName = String.valueOf(resultSet1.getString("channelname"));
			} 
			
			model.addAttribute("channelList", channelList);
			model.addAttribute("displayMsg", (Object) true);
			model.addAttribute("isView", (Object) false);
			model.addAttribute("strChannelId", strChannelId);
			model.addAttribute("strChannelName", strChannelName);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "mychannel";

	}
	@RequestMapping(value = "/deletechannel/{id}", method = RequestMethod.GET)
	public String deletechannel(@PathVariable("id") int id, Model model, HttpSession session) throws SQLException {
		List<Channel> channelList = new ArrayList<Channel>();
		Connection conn = null;
		try{
			System.out.println("inside delete channel");
			conn = DBConnection.getConnection();
			 User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			/*String sql="delete from channel where channelid="+ id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);*/
			
			String sql="UPDATE CHANNEL SET ISAPPROVED ='0',ACTION='2'  WHERE CHANNELID="+ id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			 String sql1="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
				 sql1=" select a.channelid,a.channelname,a.isapproved,a.action from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid desc";
			 }
			 else{
				 sql1=" select a.channelid,a.channelname,a.isapproved,a.action from channel a where nvl(a.isapproved,0) = 1 order by a.channelid desc";
			 }
			//String sql1=" select a.channelid,a.channelname,a.isapproved,a.action from channel a where nvl(a.isapproved,0) = 1 and a.username='"+strUserName+"' order by a.channelid";
			Statement stmt1 = conn.createStatement();
			ResultSet resultSet = stmt1.executeQuery(sql1);
	
			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				channelList.add(new Channel(channelid, channelname, isapproved));
	
			}
			
			model.addAttribute("channelList", channelList);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		return "mychannel";

	}
	
	@RequestMapping(value="/pendingchannel", method = RequestMethod.GET)
	public String pendingChannel(Model model, HttpSession session) throws ClassNotFoundException, SQLException {
		LOGGER.debug("Start messageList.");
		System.out.println("inside pendingchannel");
		List<Channel> channelList = new ArrayList<Channel>();
		
		Connection conn = null;
		try{
			conn = DBConnection.getConnection();
			User user1 = (User) session.getAttribute("user");
			 String strUserName=user1.getUserName();
			 String sql="";
			 if(strUserName.equalsIgnoreCase("admin") || strUserName.equalsIgnoreCase("banca")){
				 sql=" select a.channelid,a.channelname,a.isapproved,a.action from channel a where nvl(a.isapproved,0) = 0 and a.username='"+strUserName+"' order by a.channelid desc";
			 }
			 else{
				 sql=" select a.channelid,a.channelname,a.isapproved,a.action from channel a where nvl(a.isapproved,0) = 0  order by a.channelid desc";
			 }
			//String sql=" select a.channelid,a.channelname,a.isapproved,a.action from channel a where nvl(a.isapproved,0) = 0 and a.username='"+strUserName+"' order by a.channelid";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			System.out.println("resultSet" + resultSet);
	
			while (resultSet.next()) {
				int channelid = resultSet.getInt("channelid");
				int action = resultSet.getInt("action");
				String channelname = String.valueOf(resultSet.getString("channelname"));
				String isapproved = String.valueOf(resultSet.getInt("isapproved")).equalsIgnoreCase("0") ? "Pending" : "Approved";
				
				System.out.println("channelid ["+channelid+"] channelname ["+channelname+"] isapproved ["+isapproved+"] action ["+ action +"] strUserName ["+ strUserName+"]" );
				channelList.add(new Channel(channelid, channelname, isapproved, action));
	
			}
			model.addAttribute("channelList", channelList);
			model.addAttribute("channel", new Channel());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		return "pendingChannel";

	}
	
	@RequestMapping(value = "/approvechannel", method = RequestMethod.GET)
	public  String approveChannel(Model model,HttpServletResponse response,@RequestParam("catid") String  catid) 
	{
		
		System.err.println("Value =="+catid);
		Connection conn = null;
		try {
			System.out.println("inside approvechannel");
			conn = DBConnection.getConnection();

			System.err.println("Inside the Method calllll");
			// update msg status

			String sql = " select a.action from channel a WHERE CHANNELID="
					+ catid;
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			int action = 0;
			while (resultSet.next()) {
				action = resultSet.getInt("action");
				System.out.println("action " + action);

			}

			if (action == 2) {
				System.out.println("inside if condition");
				String sql1 = "delete from channel where channelid=" + catid;
				Statement stmt1 = conn.createStatement();
				stmt1.executeUpdate(sql1);

			} else {

				System.out.println("inside else condition");
				String sql2 = "UPDATE CHANNEL SET ISAPPROVED ='1' WHERE CHANNELID="
						+ catid;
				Statement stmt2 = conn.createStatement();
				stmt2.executeUpdate(sql2);
			}
	
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}
		return "channelapproved";
		
	}
}