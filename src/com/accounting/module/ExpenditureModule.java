package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.accounting.db.DBConnection;
import com.accounting.db.Expenditure;
import com.accounting.db.ExpenditureDao;
import com.accounting.db.RecordDao;

public class ExpenditureModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "無法連接到資料庫\n請稍後再試";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "資料庫指令執行錯誤!";
	public static final int EXPEND_NAME_EXISTED_RC = 3;
	public static final String EXPEND_NAME_EXISTED_MSG = "該支出項目已存在\n請重新輸入";
	public static final String SELECT_EMPTY_MSG = "請選擇支出項目";
	public static final String CREATE_FAIL_MSG = "新增失敗!!";
	public static final String MODIFY_FAIL_MSG = "修改失敗!!";
	public static final String REMOVE_FAIL_MSG = "刪除失敗!!";
	
	public int createDefault(String ledger_id) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		rc = expendDao.insertExpenditureDefault(ledger_id);
		if (rc != 0) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rc = DB_EXCEPTION_RC;
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
	
	public ArrayList<Expenditure> getExpends(String ledger_id) {
		ArrayList<Expenditure> expend_List = new ArrayList<Expenditure>();
		Connection conn = DBConnection.getConnection();
		if (conn == null) expend_List = null;
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		expend_List = expendDao.queryExpenditure(ledger_id);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return expend_List;
	}

	public int add(String expend_name, String ledger_id) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		rc = expendDao.addExpenditure(new Expenditure(expend_name, ledger_id));
		if (rc != 0) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rc = DB_EXCEPTION_RC;
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
	
	public int modify(String oldName, Expenditure expend) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		rc = expendDao.modifyExpend(oldName, expend);
		if (rc != 0) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rc = DB_EXCEPTION_RC;
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
	
	public int delete(Expenditure expend) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		rc = expendDao.deleteExpend(expend);
		if (rc != 0) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rc = DB_EXCEPTION_RC;
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
	
	public int monthStat(Expenditure expend) {
		int amount = 0;
		String date = "";
		Connection conn = DBConnection.getConnection();
		if (conn == null) return -(DB_CONNECT_FAIL_RC);
		RecordDao recordDao = new RecordDao(conn);
		Calendar c = Calendar.getInstance();
		date = String.format("%04d/%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
		amount = recordDao.getAmountOfMonth(date, expend);
		if (amount < 0) {
			amount = -(DB_EXCEPTION_RC);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}
}
