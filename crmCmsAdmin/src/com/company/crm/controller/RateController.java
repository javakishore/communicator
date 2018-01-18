package com.company.crm.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.JNCryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.crm.model.ConstantDb;
import com.company.crm.model.Rate;
import com.company.crm.model.User;
import com.company.crm.util.DBConnection;
import com.company.crm.validation.MessageValidator;

@Controller
public class RateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
	ConstantDb constantDb= ConstantDb.getConstantDb();
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new MessageValidator());

	}
	
	
	@RequestMapping(value="/viewrating", method = RequestMethod.GET)
	public  String mymessages( Model model,HttpSession session) {
		LOGGER.debug("Start messageList.");
		List<Rate> rateList =new ArrayList<Rate>();
		Connection conn = null;
		
		try {
				conn = DBConnection.getConnection();
			model.addAttribute("ratecontent", "connection");
			int lang=1;
			User user1 = (User) session.getAttribute("user");
			String strUserName=user1.getUserName();
			String sql="";
			if(strUserName.equalsIgnoreCase("admin"))
			 {
				 sql = "select u.USERNAME,r.rate_details_id,r.rate_text,r.rate_value,r.rated_at from rate_details r left join user_m u on r.user_id = u.userid where r.user_id = '1' order by r.rated_at desc";
			 }
			 else if(strUserName.equalsIgnoreCase("banca"))
			 {
				 sql = "select u.USERNAME,r.rate_details_id,r.rate_text,r.rate_value,r.rated_at from rate_details r left join user_m u on r.user_id = u.userid where r.user_id = '2' order by r.rated_at desc";
			 }
			 else
			 {
				 sql = "select u.USERNAME,r.rate_details_id,r.rate_text,r.rate_value,r.rated_at from rate_details r left join user_m u on r.user_id = u.userid order by r.rated_at desc";
			 }
			
			//String sql="select * from rate_details";
			/*String sql = " select u.USERNAME,r.rate_details_id,r.rate_text,r.rate_value,r.rated_at " +
						 " from rate_details r " +
						 " left join user_m u on r.user_id = u.userid " +
						 " where r.user_id = '"+strUserName+"'" + 
						 " order by r.rated_at desc";*/
			model.addAttribute("ratecontent", sql);
			Statement stmt=conn.createStatement();
			ResultSet resultSet=stmt.executeQuery(sql);
			
			while(resultSet.next())
			{

				JNCryptor cryptor = new AES256JNCryptor();
				String password = "aes123";
				
				/*String sql1="select * from USER_M where USERID="+resultSet.getInt("USER_ID");
				model.addAttribute("ratecontent", sql1);
				
				Statement stmt1=conn.createStatement();
				ResultSet resultSet1=stmt1.executeQuery(sql1);
				//model.addAttribute("ratecontent", resultSet1.getString(3));
				//int userId=resultSet1.getString(1);
				String username = null;
				while(resultSet1.next())
				{
					username= String.valueOf(resultSet1.getString("USERNAME"));
					model.addAttribute("ratecontent", username);
				}*/
				
				String username= resultSet.getString("USERNAME");
				String rateText=resultSet.getString("RATE_TEXT");
				String rateValue=String.valueOf(resultSet.getInt("RATE_VALUE"));
				String timestamp=resultSet.getString("RATED_AT");
				model.addAttribute("rate", rateText);
				
				rateList.add(new Rate(username, rateText, rateValue,timestamp));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnection(conn);
		}

		model.addAttribute("rateList", rateList);
		//model.addAttribute("message", new Message());
		return "rateapp";
	}
}
