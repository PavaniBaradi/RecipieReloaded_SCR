package com.scr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnectionManager {
	private static BasicDataSource connectionPool = null;
	private static Properties confProperties = PropertyLoader.getConfProperties();
	
	public static void initialize(){
		System.out.println("initializing DB...");
		connectionPool = new BasicDataSource(); 
		connectionPool.setDriverClassName(confProperties.getProperty("db.driver"));
		connectionPool.setUrl(confProperties.getProperty("db.url"));
		connectionPool.setUsername(confProperties.getProperty("db.username"));
		connectionPool.setPassword(confProperties.getProperty("db.password"));
		connectionPool.setTestOnBorrow(true);
		connectionPool.setValidationQuery(confProperties.getProperty("db.validation.query"));
		connectionPool.setInitialSize(Integer.parseInt("5"));
		connectionPool.setDefaultTransactionIsolation(2);
		
	}

	public static Connection getConnection() throws Exception {
		Connection connection = null;
		if(connectionPool == null){
			initialize();
		}
		connection = connectionPool.getConnection();
		System.out.println("DB initialized!!!");
		System.out.println("Connections Idle "+connectionPool.getNumIdle());
		System.out.println("Connections Active "+connectionPool.getNumActive());
		return connection;
	}


	public static void close(Connection connection, Statement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
				System.out.println("Connections Idle "+connectionPool.getNumIdle());
				System.out.println("Connections Active "+connectionPool.getNumActive());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		Connection conn=null;
		try {
			conn = DBConnectionManager.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnectionManager.close(conn, null, null);
		}
	}

}
