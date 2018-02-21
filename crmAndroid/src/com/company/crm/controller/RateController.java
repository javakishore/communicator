package com.company.crm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.JNCryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.crm.model.ConstantDb;
import com.company.crm.model.Rate;
import com.company.crm.model.RateDetails;
import com.company.crm.util.DBConnection;
import com.company.crm.validation.MessageValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new MessageValidator());

	}
	
	
	@RequestMapping(value="/viewrating", method = RequestMethod.GET)
	public  String mymessages( Model model) {
		LOGGER.debug("Start messageList.");
		List<Rate> rateList =new ArrayList<Rate>();
		Connection conn = null;
		
		try {
			conn = DBConnection.getConnection();
			
			model.addAttribute("ratecontent", "connection");
			int lang=1;
			//String sql="select * from rate_details";
			String sql = " select u.USERNAME,r.rate_details_id,r.rate_text,r.rate_value,r.rated_at " +
					 " from rate_details r " +
					 " left join user_m u on r.user_id = u.userid " +
					 " order by r.rated_at desc";

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
				
				//String MsgDetailID ,String MsgID,String msgData,String MsgTitle,String MsgLink,String msgStatus
				rateList.add(new Rate(username, rateText, rateValue,timestamp));
				//messageList.add(new Message(String.valueOf(resultSet.getInt(1)), String.valueOf(resultSet.getInt(2)), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
			}
			//model.addAttribute("ratecontent", rateList.size());
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
	
	@RequestMapping(value={"/rateapp/{userId}/{ratingText}/{ratingValue}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public String SaveRatingGet(@PathVariable(value="userId") int userId, @PathVariable(value="ratingText") String ratingText, @PathVariable(value="ratingValue") int ratingValue)
    {
        LOGGER.debug("Start messageList.");
        int result = 0;
        System.out.println("inside rateapp userId "+userId+" ratingText "+ratingText+" ratingValue "+ratingValue);
        Connection conn = null;
        try
        {
        	conn = DBConnection.getConnection();
        	
            String str1 = (new StringBuilder()).append("userId=").append(userId).append(" ratingText").append(ratingText).append(" ratingValue").append(ratingValue).toString();
            String UploadFolder = "/webapps/ROOT/";
            String base = System.getProperty("catalina.base");
            File dateDir = new File((new StringBuilder()).append(base).append(File.separator).append(UploadFolder).append(File.separator).append(File.separator).toString());
            if(!dateDir.exists())
                dateDir.mkdirs();
            File uploadedFile1 = new File((new StringBuilder()).append(dateDir.getAbsolutePath()).append(File.separator).append("fileGet.txt").toString());
            try
            {
                BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(uploadedFile1));
                stream1.write(str1.getBytes());
                stream1.close();
            }
            catch(FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            String sql = "insert into rate_details (RATE_DETAILS_ID,USER_ID,rate_text,rate_value,rated_at) values (rate_details_seq.nextval,?,?,?,sysdate)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, ratingText);
            stmt.setInt(3, ratingValue);
            result = stmt.executeUpdate();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }finally{
			DBConnection.closeConnection(conn);
		}
        System.out.println("saveRating");
        return (new StringBuilder()).append("{\"result\":").append(result).append("}").toString();
    }
  //  @RequestMapping(value={"/rateapp/{userId}/{ratingText}/{ratingValue}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	//@RequestMapping(value={"User.class"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	@RequestMapping(value ={"Rate.class"}, method = RequestMethod.POST)
	@ResponseBody
  public String SaveRatingPost(@PathVariable(value="userId") int userId, @PathVariable(value="ratingText") String ratingText, @PathVariable(value="ratingValue") int ratingValue)
 
    //public static int parseInt(String ratingValue)
	{
	
        LOGGER.debug("Start messageList.");
        int result = 0;
        Connection conn = null;
        ObjectMapper mapper = new ObjectMapper();
        try
     
        {	
        	
 
        	conn = DBConnection.getConnection();
        	
            String str1 = (new StringBuilder()).append("userId=").append(userId).append(" ratingText").append(ratingText).append(" ratingValue").append(ratingValue).toString();
            String UploadFolder = "/webapps/ROOT/";
            String base = System.getProperty("catalina.home");
            File dateDir = new File((new StringBuilder()).append(base).append(File.separator).append(UploadFolder).append(File.separator).append(File.separator).toString());
            if(!dateDir.exists())
                dateDir.mkdirs();
            File uploadedFile1 = new File((new StringBuilder()).append(dateDir.getAbsolutePath()).append(File.separator).append("filePost.txt").toString());
            try
            {
                BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(uploadedFile1));
                stream1.write(str1.getBytes());
                stream1.close();
            }
            catch(FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            String sql = "insert into rate_details (RATE_DETAILS_ID,USER_ID,rate_text,rate_value,rated_at) values (rate_details_seq.nextval,?,?,?,sysdate)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, ratingText);
            stmt.setInt(3, ratingValue);
            result = stmt.executeUpdate();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }finally{
			DBConnection.closeConnection(conn);
		}
        return (new StringBuilder()).append("{\"result\":").append(result).append("}").toString();
    }
	
	//started here for json code.
	
  @RequestMapping(value={"/rateapp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String SaveRatingPost(@RequestBody RateDetails rate)
 {
	  	LOGGER.debug("Start messageList.");
       
        int result = 0;
        ObjectMapper mapper = new ObjectMapper();
        Connection conn = null;
        try
        {
        	int  userId=rate.getUserid();
        	String ratingText=rate.getRatingtext();
        	int ratingValue=rate.getRatingValue();
        	conn = DBConnection.getConnection();
        	
            String str1 = (new StringBuilder()).append("userId=").append(userId).append(" ratingText").append(ratingText).append(" ratingValue").append(ratingValue).toString();
            String UploadFolder = "/webapps/ROOT/";
            String base = System.getProperty("catalina.home");
            File dateDir = new File((new StringBuilder()).append(base).append(File.separator).append(UploadFolder).append(File.separator).append(File.separator).toString());
            if(!dateDir.exists())
                dateDir.mkdirs();
            File uploadedFile1 = new File((new StringBuilder()).append(dateDir.getAbsolutePath()).append(File.separator).append("filePost.txt").toString());
            try
            {
                BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(uploadedFile1));
                stream1.write(str1.getBytes());
                stream1.close();
            }
            catch(FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            String sql = "insert into rate_details (RATE_DETAILS_ID,USER_ID,rate_text,rate_value,rated_at) values (rate_details_seq.nextval,?,?,?,sysdate)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, ratingText);
            stmt.setInt(3, ratingValue);
            result = stmt.executeUpdate();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }finally{
			DBConnection.closeConnection(conn);
		}
        
        return (new StringBuilder()).append("{\"result\":").append(result).append("}").toString();
    }
	
	//end here for json code
	

/*	@RequestMapping(value={"/rateapp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String SaveRatingPostLess(@ModelAttribute(value="userId") int userId, @ModelAttribute(value="ratingText") String ratingText, @ModelAttribute(value="ratingValue") int ratingValue)
    {
        LOGGER.debug("Start messageList.");
        int result = 0;
        try
        {
        	
        	
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(constantDb.databaseUrl, constantDb.username, constantDb.password);
            String str1 = (new StringBuilder()).append("userId=").append(userId).append(" ratingText").append(ratingText).append(" ratingValue").append(ratingValue).toString();
            String UploadFolder = "/webapps/ROOT/";
            String base = System.getProperty("catalina.base");
            File dateDir = new File((new StringBuilder()).append(base).append(File.separator).append(UploadFolder).append(File.separator).append(File.separator).toString());
            if(!dateDir.exists())
                dateDir.mkdirs();
            File uploadedFile1 = new File((new StringBuilder()).append(dateDir.getAbsolutePath()).append(File.separator).append("filePostless.txt").toString());
            try
            {
                BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(uploadedFile1));
                stream1.write(str1.getBytes());
                stream1.close();
            }
            catch(FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            String sql = "insert into rate_details (RATE_DETAILS_ID,USER_ID,rate_text,rate_value,rated_at) values (rate_details_seq.nextval,?,?,?,sysdate)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, ratingText);
            stmt.setInt(3, ratingValue);
            result = stmt.executeUpdate();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return (new StringBuilder()).append("{\"result\":").append(result).append("}").toString();
    }*/



}
