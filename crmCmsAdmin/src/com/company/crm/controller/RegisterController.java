package com.company.crm.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.crm.model.Login;
import com.company.crm.model.User;
import com.company.crm.service.RegisterService;
import com.company.crm.validation.RegistrationValidator;

@Controller
public class RegisterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private RegisterService registerService;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new RegistrationValidator());
    }
	@RequestMapping(value="/register",method = RequestMethod.GET)
    public String register(Model model) { 
		 	model.addAttribute("register", new User());
		 	ArrayList<String> list = new ArrayList<String>();
		 	list.add("User");
		 	list.add("Admin");
		 	model.addAttribute("userTypeList", list);
	        return "register";
	   }
	@RequestMapping(value="/register",method = RequestMethod.POST)
    public String registerSubmit(@Valid @ModelAttribute("register") User register, BindingResult result, Model model) { 
		ArrayList<String> list = new ArrayList<String>();
		list.add("User");
		list.add("Admin");
	 	model.addAttribute("userTypeList", list);
		if (result.hasErrors()) {
			return "register";
		} else {
			registerService.addUser(register);
			model.addAttribute("login", new Login());
			return "login";
		}
	}
}
