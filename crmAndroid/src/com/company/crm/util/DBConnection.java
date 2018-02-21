package com.company.crm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.company.crm.model.ConstantDb;


/******************************************************************
This class connects to the database using jdbc-odbc driver.

Functions:
1) synchronized static Connection getConnection()
2) synchronized static void returnConnection(Connection con)
                  
******************************************************************/

public class DBConnection {
	static ConstantDb constantDb = ConstantDb.getConstantDb();
	
/*	
	public synchronized static Connection getConnectionFromPool() throws Exception {
		Context ctx = null;

		try {
			ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup(constantDb.dataSource);
			System.out.println("Connection Obtained from Connection Pool");
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException caught in DBConnection.java...");
			throw e;
		} catch (NamingException e) {
			System.out.println("NamingException caught in DBConnection.java...");
			return null;
		} finally {
			try {
				if (ctx != null)
					ctx.close();
			} catch (NamingException e) {
				System.out.println("Exception caught in DBConnection.java...");
			}
		} 
	}   // getConnection
*/
	/**
	 * @return Sql Connection
	 * @throws Exception
	 */
	public synchronized static Connection getConnection() throws Exception {
		try {
			 Class.forName(constantDb.driverClassName);
			return DriverManager.getConnection(constantDb.databaseUrl, constantDb.username, constantDb.password);
			
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(ClassNotFoundException ce) {
			ce.printStackTrace();
			throw new Exception("Connection cannot be esablished as driver is not found.");
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Connection cannot be esablished in getSchedulerConnection() method.");
		}     
		return null;
	} 
	
	public synchronized static void closeConnection(Connection conn) {
		try {
			if (conn!=null)
				conn.close();
		} catch (Exception sqlex) {
			System.out.println("SQLException caught in DBConnection.java...");
		}
	}   // returnConnection

	public static void main(String...args)throws Exception{
		System.out.println(getConnection());
	}
}  

