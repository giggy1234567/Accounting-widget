package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;

import com.accounting.db.DBConnection;
import com.accounting.db.User;
import com.accounting.db.UserDao;

public class UserModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "無法連接到資料庫\n請稍後再試";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "資料庫指令執行錯誤!";
	public static final int LOGIN_FAIL_RC = 3;
	public static final String LOGIN_FAIL_MSG = "帳號或密碼錯誤!";
	public static final int USER_ID_EXISTED_RC = 4;
	public static final String USER_ID_EXISTED_MSG = "該使用者帳號已存在\n請重新輸入";
	public static final String TOO_LONG_MSG = "輸入資料長度過長\n請重新輸入";
	public static final String LOGIN_SUCC_MSG = "登入成功!!";
	public static final String LOGIN_EMPTY_MSG = "請輸入帳號及密碼";
	public static final String REGISTER_EMPTY_MSG = "請填入所有欄位資料";
	public static final String MODIFY_SUCC_MSG = "修改成功!!";
	public static final String MODIFY_FAIL_MSG = "修改失敗!!";
	public static final String REGISTER_SUCC_MSG = "註冊成功!!";
	public static final String REGISTER_FAIL_MSG = "註冊失敗!!";
	
	public int login(String user_id, String password) {
		int rc = 0;
		String pwd = "";
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		UserDao userDao = new UserDao(conn);
		pwd = userDao.getPassword(user_id);
		if (pwd.equals("") || pwd == null || !password.equals(pwd)) rc = LOGIN_FAIL_RC;
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	public User getUserInfo(String user_id) {
		User user;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return null;
		UserDao userDao = new UserDao(conn);
		user = userDao.queryUser(user_id);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	
	public int register(User user) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		UserDao userDao = new UserDao(conn);
		rc = userDao.addUser(user);
		if (rc != 0) {
			if (rc == 2627) rc = USER_ID_EXISTED_RC;
			else rc = DB_EXCEPTION_RC;
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			rc = DB_EXCEPTION_RC;
		}
		return rc;
	}
	
	public int modify(User user) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		UserDao userDao = new UserDao(conn);
		rc = userDao.modifyUser(user);
		if (rc != 0) {
			if (rc == 2627) rc = USER_ID_EXISTED_RC;
			else rc = DB_EXCEPTION_RC;
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			rc = DB_EXCEPTION_RC;
		}
		return rc;
	}
}
