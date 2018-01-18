package com.company.crm.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConstantDb {	

	public  String driverClassName;
	public  String databaseUrl;
	public  String username;
	public  String password;
	public static ConstantDb obj = null;

	private ConstantDb() {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Properties props = new Properties();
			InputStream resourceStream = loader.getResourceAsStream("jdbc.properties");
			props.load(resourceStream);
			driverClassName=props.getProperty("jdbc.driverClassName");
			databaseUrl=props.getProperty("jdbc.databaseurl");
			username=props.getProperty("jdbc.username");
			password=props.getProperty("jdbc.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		public static ConstantDb getConstantDb(){
			if( obj == null)
				return new ConstantDb();
			else
				return obj;					
		}
	}
