package com.company.crm.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.company.crm.model.Login;
import com.company.crm.model.User;
import com.company.crm.service.LoginService;
import com.company.crm.util.DBConnection;

@Component
@RequestMapping("/")
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

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
	}
	

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginSubmit(@Valid @ModelAttribute("login") Login login,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
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
			} catch (SQLException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DBConnection.closeConnection(conn);
			}
			
			if (resultUserLogin != 0) {
				session.setAttribute("user", user1);
				return "home";
			} else {
				model.addAttribute("invalidUser","Invalid User Please register first");
			}

		return "login";
	}

}
