package com.accounting.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DBConnection {
	private static SQLServerDataSource ds = new SQLServerDataSource();
	
	static SQLServerDataSource getDataSource() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ds.setServerName(properties.getProperty("servername"));
		ds.setPortNumber(Integer.valueOf(properties.getProperty("serverport")));
		ds.setDatabaseName(properties.getProperty("database"));
		ds.setUser(properties.getProperty("username"));
		ds.setPassword(properties.getProperty("password"));
		ds.setLoginTimeout(10);
		return ds;
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (conn != null) conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			conn = null;
		}
		return conn;
	}
}
