package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.accounting.db.DBConnection;
import com.accounting.db.Record;
import com.accounting.db.RecordDao;

public class RecordModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "無法連接到資料庫\n請稍後再試";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "資料庫指令執行錯誤!";
	public static final String ENTER_EMPTY_MSG = "請輸入所有欄位資料 (備註紀錄可為空)";
	public static final String EMPTY_RECORD_MSG = "無帳務紀錄";
	public static final String INSERT_SUCC_MSG = "新增成功!!";
	public static final String INSERT_FAIL_MSG = "新增失敗!!";

	public ArrayList<Record> getRecords(String ledger_id) {
		ArrayList<Record> record_List = new ArrayList<Record>();
		Connection conn = DBConnection.getConnection();
		if (conn == null) record_List = null;
		RecordDao recordDao = new RecordDao(conn);
		record_List = recordDao.queryRecord(ledger_id);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return record_List;
	}
	
	public int add(Record record) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) rc = DB_CONNECT_FAIL_RC;
		RecordDao recordDao = new RecordDao(conn);
		rc = recordDao.addRecord(record);
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
		}
		return rc;
	}
	
	public int modify(Record record) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) rc = DB_CONNECT_FAIL_RC;
		RecordDao recordDao = new RecordDao(conn);
		rc = recordDao.modifyRecord(record);
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
		}
		return rc;
	}
	
	public int remove(String record_id) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) rc = DB_CONNECT_FAIL_RC;
		RecordDao recordDao = new RecordDao(conn);
		rc = recordDao.deleteRecord(record_id);
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
		}
		return rc;
	}
} 
