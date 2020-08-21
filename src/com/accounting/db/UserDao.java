package com.accounting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;


public class UserDao {
	Connection conn;
	
	public UserDao(Connection conn) {
		this.conn = conn;
	}
	
	public int addUser(User user) {
		int rc = 0;
		String sql = "insert into [user] values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUser_id());
			pstmt.setString(2, user.getUser_name());
			pstmt.setString(3, user.getPhone());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getBirthday());
			pstmt.setString(6, user.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public User queryUser(String user_id) {
		User user = null;
		String sql = "select * from [user] where user_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				user = new User();
				user.setUser_id(rset.getString(1));
				user.setUser_name(rset.getString(2));
				user.setPhone(rset.getString(3));
				user.setEmail(rset.getString(4));
				user.setBirthday(rset.getString(5));
				user.setPassword(rset.getString(6));
			}
		} catch (SQLException e) {
			System.err.printf("Error Code [%s]\n", e.getSQLState());
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	
	public int modifyUser(User user) {
		int rc = 0;
		String sql = "update [user] set user_name = ?, phone = ?, email = ?, birthday = ?, password = ? "
				+ "where user_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUser_name());
			pstmt.setString(2, user.getPhone());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getBirthday());
			pstmt.setString(5, user.getPassword());
			pstmt.setString(6, user.getUser_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = -1;
		}
		return rc;
	}
	
	public String getPassword(String user_id) {
		String pwd = "";
		String sql = "select password from [user] where user_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rset = pstmt.executeQuery();
			while (rset.next()) pwd = rset.getString(1);
		} catch(SQLServerException e) {
			System.err.printf("Error Code [%d]", e.getSQLServerError().getErrorNumber());
			e.printStackTrace();
			pwd = null;
		} catch (SQLException e) {
			e.printStackTrace();
			pwd = null;
		}
		return pwd;
	}
}
