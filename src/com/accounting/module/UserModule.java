package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;

import com.accounting.db.DBConnection;
import com.accounting.db.User;
import com.accounting.db.UserDao;

public class UserModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "�L�k�s�����Ʈw\n�еy��A��";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "��Ʈw���O������~!";
	public static final int LOGIN_FAIL_RC = 3;
	public static final String LOGIN_FAIL_MSG = "�b���αK�X���~!";
	public static final int USER_ID_EXISTED_RC = 4;
	public static final String USER_ID_EXISTED_MSG = "�ӨϥΪ̱b���w�s�b\n�Э��s��J";
	public static final String TOO_LONG_MSG = "��J��ƪ��׹L��\n�Э��s��J";
	public static final String LOGIN_SUCC_MSG = "�n�J���\!!";
	public static final String LOGIN_EMPTY_MSG = "�п�J�b���αK�X";
	public static final String REGISTER_EMPTY_MSG = "�ж�J�Ҧ������";
	public static final String MODIFY_SUCC_MSG = "�ק令�\!!";
	public static final String MODIFY_FAIL_MSG = "�ק異��!!";
	public static final String REGISTER_SUCC_MSG = "���U���\!!";
	public static final String REGISTER_FAIL_MSG = "���U����!!";
	
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
