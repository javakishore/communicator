package com.company.crm.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.company.crm.model.User;



public class RegistrationValidator implements Validator{
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationValidator.class);

	public boolean supports(Class<?> arg0) {
		//return Register.class.equals(arg0);
		return true;
		
	}

	public void validate(Object obj, Errors e) {
			ValidationUtils.rejectIfEmpty(e, "userName", "userName.empty");
			//ValidationUtils.rejectIfEmpty(e, "password", "password.empty");
			//ValidationUtils.rejectIfEmpty(e, "confirmPassword", "confirmPassword.empty");
			//ValidationUtils.rejectIfEmpty(e, "userType", "userType.empty");
			User register = (User)obj;
			if(register.getPassword().length() < 8 || register.getPassword().length() > 15) {
				e.rejectValue("password","password.size");
			} else if(!register.getPassword().equals(register.getConfirmPassword())){
		    e.rejectValue("confirmPassword","confirmPassword.notequal");
		   }
		   
		   
	}

}
