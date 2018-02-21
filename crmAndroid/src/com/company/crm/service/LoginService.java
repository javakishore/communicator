package com.company.crm.service;

import java.util.List;

import com.company.crm.model.Login;
import com.company.crm.model.User;

public interface LoginService {
	public User checkUserAuthentication(Login login);
	public List<User> getUserList();
}
