package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.accounting.db.AssetDao;
import com.accounting.db.DBConnection;
import com.accounting.db.ExpenditureDao;
import com.accounting.db.Ledger;
import com.accounting.db.LedgerDao;
import com.accounting.db.RecordDao;

public class LedgerModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "無法連接到資料庫\n請稍後再試";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "資料庫指令執行錯誤!";
	public static final int LEDGER_NAME_EXISTED_RC = 3;
	public static final String LEDGER_NAME_EXISTED_MSG = "該帳本名稱已存在\n請重新輸入";
	public static final String SELECT_EMPTY_MSG = "請選擇帳本";
	public static final String ENTER_EMPTY_MSG = "請輸入帳本名稱";
	
	public ArrayList<Ledger> getLedgers(String user_id) {
		ArrayList<Ledger> ledger_List = new ArrayList<Ledger>();
		Connection conn = DBConnection.getConnection();
		if (conn == null) return null;
		LedgerDao ledgerDao = new LedgerDao(conn);
		ledger_List = ledgerDao.queryLedger(user_id);
		return ledger_List;
	}
	
	public Ledger getLedgerInfo(String ledger_name, String user_id) {
		Ledger ledger = new Ledger();
		Connection conn = DBConnection.getConnection();
		if (conn == null) return null;
		LedgerDao ledgerDao = new LedgerDao(conn);
		ledger = ledgerDao.getLegder(ledger_name, user_id);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ledger;
	}
	
	public int create(String ledger_name, String user_id) {
		int rc = 0;
		Ledger ledger = new Ledger();
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		LedgerDao ledgerDao = new LedgerDao(conn);
		AssetModule assetModule = new AssetModule();
		ExpenditureModule expendModule = new ExpenditureModule();
		while (true) {
			rc = ledgerDao.addLedger(new Ledger("", ledger_name, user_id));
			if (rc != 0) {
				if (rc == 2627) rc = LEDGER_NAME_EXISTED_RC;
				else rc = DB_EXCEPTION_RC;
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			ledger = ledgerDao.getLegder(ledger_name, user_id);
			if (ledger == null) {
				rc = DB_EXCEPTION_RC;
				System.err.println("Not handle error");
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			rc = assetModule.createDefault(ledger.getLedger_id());
			if (rc != 0) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			rc = expendModule.createDefault(ledger.getLedger_id());
			if (rc != 0) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
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
	
	public int modify(Ledger ledger) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		LedgerDao ledgerDao = new LedgerDao(conn);
		rc = ledgerDao.modifyLedger(ledger);
		if (rc != 0) {
			if (rc == 2627) rc = LEDGER_NAME_EXISTED_RC;
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
	
	public int remove(String ledger_id) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		LedgerDao ledgerDao = new LedgerDao(conn);
		AssetDao assetDao = new AssetDao(conn);
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		RecordDao recordDao = new RecordDao(conn);
		while (true) {
			rc = recordDao.deleteRecordWithLedger(ledger_id);
			if (rc != 0) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return DB_EXCEPTION_RC;
			}
			rc = expendDao.deleteExpendWithLedger(ledger_id);
			if (rc != 0) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rc = DB_EXCEPTION_RC;
				break;
			}
			rc = assetDao.deleteAssetWithLedger(ledger_id);
			if (rc != 0) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rc = DB_EXCEPTION_RC;
				break;
			}
			rc = ledgerDao.deleteLedger(ledger_id);
			if (rc != 0) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rc = DB_EXCEPTION_RC;
				break;
			}
			break;
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
	
	public int monthStat(String ledger_id) {
		int amount = 0;
		String date = "";
		Connection conn = DBConnection.getConnection();
		if (conn == null) return -(DB_CONNECT_FAIL_RC);
		RecordDao recordDao = new RecordDao(conn);
		Calendar c = Calendar.getInstance();
		date = String.format("%04d/%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
		amount = recordDao.getAmountOfDate(date, ledger_id);
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
