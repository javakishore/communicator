  package com.company.crm.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.crm.model.Login;
import com.company.crm.model.User;
import com.company.crm.util.DBConnection;

@Component
@RequestMapping("/")
public class LoginController {

	//public int testuserid=0;
	public int CHANNEL_ID=0;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("login", new Login());
		return "login";
	}

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String goHome(Model model) {
		LOGGER.debug("goHome");
		return "home";
	}

	@RequestMapping("/logout")

	public ModelAndView logout(HttpSession session ) {
		LOGGER.debug("@@@@@@@@@@@@ logout " + session);
		session.invalidate();
		return new ModelAndView("login", "login", new User());
		// return "login";
	}
	

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginSubmit(@Valid @ModelAttribute("login") Login login,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {

		/*if (result.hasErrors()) {
			return "home";
		} else {*/
			//User user = loginService.checkUserAuthentication(login);
			User user1 = new User();
			int resultUserLogin=0;
			Connection conn = null;
				try {
					conn = DBConnection.getConnection();
					String sqllogin="select * from user_m where USERNAME='"+login.getUserName()+"' and password='"+login.getPassword()+"'";
					model.addAttribute("testResult", "Result="+"2");
					PreparedStatement stmt1=conn.prepareStatement(sqllogin);
					ResultSet resultSetUserLogin=stmt1.executeQuery(sqllogin);
					while(resultSetUserLogin.next())
					{
						resultUserLogin=resultSetUserLogin.getInt("USERID");
						user1.setUserId(resultUserLogin);
						user1.setUserName(resultSetUserLogin.getString("USERNAME"));
						user1.setPassword(resultSetUserLogin.getString("PASSWORD"));
						user1.setUserType(resultSetUserLogin.getString("USERTYPE"));
						user1.setUserLanguage(resultSetUserLogin.getString("LANG"));
					}
					
					model.addAttribute("testResult", "Result="+sqllogin);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					DBConnection.closeConnection(conn);
				}
				
			
			if (resultUserLogin != 0) {
				session.setAttribute("user", user1);
				return "home";
			} else {
				model.addAttribute("invalidUser",
						"Invalid User Please register first");
			}

		//}

		return "login";
	}


	public int updateUserid(String username) throws ClassNotFoundException, SQLException{
		
        String sql = "";
        Connection conn = null;
        try{
            conn = DBConnection.getConnection();
            sql = "select * from user_m where USERNAME='" + username + "'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
            	return resultSet.getInt("USERID");
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
			DBConnection.closeConnection(conn);
		}
        return 0;
	}
	
	public void updateCHANNEL_ID(){
		int CHANNEL_IDtest=400;
		CHANNEL_IDtest=CHANNEL_IDtest+1;
		CHANNEL_ID=CHANNEL_IDtest;
		
	}
	
	/*{username}/{channel}/{zone}/{leaderFlag}*/
	 @RequestMapping(value={"/mobileloginandro"}, method={RequestMethod.POST}, produces={"application/json"})
	    @ResponseBody
	    public String getMobilelogin(
	    			@ModelAttribute(value="username") String username, 
	    			@ModelAttribute(value="channel") String channel, 
	    			@ModelAttribute(value="zone") String zone, 
	    			@ModelAttribute(value="leaderFlag") String leaderFlag, 
	    			@ModelAttribute(value="channelCode") String channelCode,
	    			Model model) {
	        int result;
	        
	        System.out.println("######################################################## UserName:"+username);
	        System.out.println("######################################################## Channel:"+channel);
	        System.out.println("######################################################## Zone:"+zone);
	        System.out.println("######################################################## leaderFlag:"+leaderFlag);
	        System.out.println("######################################################## channelCode:"+channelCode);
	        //int finalresult=0;
	        try {
	            result = 0;
	           // String strResult = "";
	            int zoneId = 0;
	            String sqlGetCHANNEL_ID = null;
	            String sql = "";
	            ArrayList<Integer> CHANNEL_IDs = new ArrayList<Integer>();
	            Connection conn = null;
	            try {
	                ResultSet resultSetGetCHANNEL_ID;
	                Statement stmtGetCHANNEL_ID;
	                conn = DBConnection.getConnection();
	                
	                sql = "select * from user_m where USERNAME='" + username + "'";
	                PreparedStatement stmt = conn.prepareStatement(sql);
	                ResultSet resultSet = stmt.executeQuery(sql);
	                
	                while (resultSet.next()) {
	                    result = resultSet.getInt("USERID");
	                    System.out.println("######################################################## select userid:"+result);
	                }
	                
	                sql = "select count(*) as count from user_m where USERNAME='" + username + "'";
	                PreparedStatement stmtCount = conn.prepareStatement(sql);
	                ResultSet resultSetCount = stmtCount.executeQuery(sql);
	                int count = 0;
	                while (resultSetCount.next()) {
	                count = resultSetCount.getInt("count");
	                }
	                
	                if (count == 0){
		                String sqlRegisterUser = "INSERT INTO USER_M (USERID, USERNAME, USERTYPE, LANG) VALUES (USER_M_SEQ.nextval, ?, 'user', '1')";
		                PreparedStatement stmtRegisterUser = conn.prepareStatement(sqlRegisterUser);
		                stmtRegisterUser.setString(1, username);
		                stmtRegisterUser.executeUpdate();
	
		                int testuserid = updateUserid(username);
		                
		                String sqlGetZoneId = "Select ZONE_ID from Zone where upper(ZONE_NAME)=upper('" + zone + "')";
		                Statement stmtGetZoneId = conn.createStatement();
		                ResultSet resultSetGetZoneId = stmtGetZoneId.executeQuery(sqlGetZoneId);
		                while (resultSetGetZoneId.next()) {
		                    zoneId = resultSetGetZoneId.getInt("ZONE_ID");
		                    System.out.println("########################################################zone selected:"+zoneId);
		                }
		                
	
		                String sqlSetUserZone = "INSERT INTO USER_ZONE (USER_ZONE_ID, USER_ID, ZONE_ID) VALUES (USER_ZONE_SEQ.nextval, ?, ?)";
		                PreparedStatement stmtSetUserZone = conn.prepareStatement(sqlSetUserZone);
		                stmtSetUserZone.setInt(1, testuserid);
		                stmtSetUserZone.setInt(2, zoneId);
		                
		                stmtSetUserZone.executeUpdate();
		                if (leaderFlag.equalsIgnoreCase("true")) {
		                    sqlGetCHANNEL_ID = "Select CHANNELID from CHANNEL where upper(CHANNELNAME)=upper('" + channel + " leaders" + "')";
		                    stmtGetCHANNEL_ID = conn.createStatement();
		                    resultSetGetCHANNEL_ID = stmtGetCHANNEL_ID.executeQuery(sqlGetCHANNEL_ID);
		                    
		                    while (resultSetGetCHANNEL_ID.next()) {
		                        CHANNEL_IDs.add(resultSetGetCHANNEL_ID.getInt("CHANNELID"));
		                    }
		                    
		                    sqlGetCHANNEL_ID = null;
	
		                    sqlGetCHANNEL_ID = "Select CHANNELID from CHANNEL where upper(CHANNELNAME)=upper('" + channel + " agents" + "')";
		                    Statement stmtGetCHANNEL_ID2 = conn.createStatement();
		                    ResultSet resultSetGetCHANNEL_IDAgent = stmtGetCHANNEL_ID2.executeQuery(sqlGetCHANNEL_ID);
		                    while (resultSetGetCHANNEL_IDAgent.next()) {
		                        CHANNEL_IDs.add(resultSetGetCHANNEL_IDAgent.getInt("CHANNELID"));
		                        System.out.println("##############################################################################Selected Chanel id");
		                    }
		                    
		                } else {
		                	
		                    sqlGetCHANNEL_ID = "Select CHANNELID from CHANNEL where upper(CHANNELNAME)=upper('" + channel + " agents" + "')";
		                    Statement stmtGetCHANNEL_ID2 = conn.createStatement();
		                    ResultSet resultSetGetCHANNEL_IDAgent = stmtGetCHANNEL_ID2.executeQuery(sqlGetCHANNEL_ID);
		                    while (resultSetGetCHANNEL_IDAgent.next()) {
		                        CHANNEL_IDs.add(resultSetGetCHANNEL_IDAgent.getInt("CHANNELID"));
		                        System.out.println("##############################################################################Selected Chanel id");
		                    }
		                }
		                
		                
		                //chg.Bilal.13-Feb-2017 17:09.Refactoring code to add sequence for user_channel_id 
		                for (int i = 0; i < CHANNEL_IDs.size(); ++i) {
		                    String sqlSetUserChannel = "INSERT INTO USER_CHANNEL (USER_CHANNEL_ID, USER_ID, CHANNEL_ID, CHANNELCODE) VALUES ((select max(user_channel_id)+1 from USER_CHANNEL), ?, ?,?)";
		                    PreparedStatement stmtSetUserChannel = conn.prepareStatement(sqlSetUserChannel);
		                   
		                    stmtSetUserChannel.setInt(1, testuserid);
		                    stmtSetUserChannel.setInt(2, (Integer)CHANNEL_IDs.get(i));
		                    stmtSetUserChannel.executeUpdate();
		                }
	                
	                }
	            }
	            
	            catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	            }finally{
	    			DBConnection.closeConnection(conn);
	    		}
	        }finally {
	        	
	        }
	        return "{\"result\":" + result + "}";
	    }

}