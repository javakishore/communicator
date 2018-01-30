package com.company.crm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.company.crm.model.ConstantDb;

/******************************************************************
 * This class connects to the database using jdbc-odbc driver.
 * 
 * Functions: 1) synchronized static Connection getConnection() 2) synchronized
 * static void returnConnection(Connection con)
 ******************************************************************/

public class DBConnection {
	static ConstantDb constantDb = ConstantDb.getConstantDb();
/*
	public synchronized static Connection getConnectionFromPool()
			throws Exception {
		Context ctx = null;

		try {
			ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("DataSource_Name");
			System.out.println("Connection Obtained from Connection Pool");
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException caught in DBConnection.java...");
			throw e;
		} catch (NamingException e) {
			System.out
					.println("NamingException caught in DBConnection.java...");
			return null;
		} finally {
			try {
				if (ctx != null)
					ctx.close();
			} catch (NamingException e) {
				System.out.println("Exception caught in DBConnection.java...");
			}
		}
	} // getConnection
*/
	/**
	 * @return Sql Connection
	 * @throws Exception
	 */
	public synchronized static Connection getConnection() throws Exception {
		try {
			Class.forName(constantDb.driverClassName);
			return DriverManager.getConnection(constantDb.databaseUrl,constantDb.username, constantDb.password);
			
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
			throw new Exception(
					"Connection cannot be esablished as driver is not found.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(
					"Connection cannot be esablished in getSchedulerConnection() method.");
		}
		return null;
	}

	// To release ResultSet, PreparedStatement & Connection
	public synchronized static void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			System.out.println("SQLException caught in DBConnection.java...");
			ex.printStackTrace();
		}
	} // returnConnection

	// To release ResultSet, PreparedStatement & Connection
	public synchronized static void releaseResources(ResultSet rs,PreparedStatement pst, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception ex) {
			System.out.println("SQLException caught in DBConnection.java...");
			ex.printStackTrace();
		}
		try {
			if (pst != null)
				pst.close();
		} catch (Exception ex) {
			System.out.println("SQLException caught in DBConnection.java...");
			ex.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			System.out.println("SQLException caught in DBConnection.java...");
			ex.printStackTrace();
		}
	} // returnConnection

	public static void main(String... args) throws Exception {
		System.out.println(getConnection());
	}

}
