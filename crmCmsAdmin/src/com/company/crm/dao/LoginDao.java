package com.company.crm.dao;

import java.util.List;

import com.company.crm.model.Login;
import com.company.crm.model.User;

public interface LoginDao {
	public User checkUserAuthentication(Login login);
	public List<User> getUserList();
}
