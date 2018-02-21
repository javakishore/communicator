package com.company.crm.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.company.crm.model.Message;
public class MessageValidator implements Validator{
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageValidator.class);

	public boolean supports(Class<?> arg0) {
		return true;
	}

	public void validate(Object arg0, Errors e) {
		ValidationUtils.rejectIfEmpty(e, "messageName", "message.name");
		Message message = (Message)arg0;
		if(message.getCategory() != null && (message.getCategory().getCategoryId() == null || "".equals(message.getCategory().getCategoryId()))) {
			e.rejectValue("category.categoryId","category.name");
		}
	}

}
