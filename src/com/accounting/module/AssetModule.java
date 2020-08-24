package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.accounting.db.Asset;
import com.accounting.db.AssetDao;
import com.accounting.db.DBConnection;
import com.accounting.db.RecordDao;

public class AssetModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "無法連接到資料庫\n請稍後再試";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "資料庫指令執行錯誤!";
	public static final int ASSET_NAME_EXISTED_RC = 3;
	public static final String ASSET_NAME_EXISTED_MSG = "該資產項目已存在\n請重新輸入";
	public static final String SELECT_EMPTY_MSG = "請選擇資產項目";
	
	public int createDefault(String ledger_id) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		AssetDao assetDao = new AssetDao(conn);
		rc = assetDao.insertAssetDefault(ledger_id);
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
	
	public ArrayList<Asset> getAssets(String ledger_id) {
		ArrayList<Asset> asset_List = new ArrayList<Asset>();
		Connection conn = DBConnection.getConnection();
		if (conn == null) return null;
		AssetDao assetDao = new AssetDao(conn);
		asset_List = assetDao.queryAsset(ledger_id);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return asset_List;
	}
	
	public int add(String asset_name, String ledger_id) {
		int rc  = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		AssetDao assetDao = new AssetDao(conn);
		rc = assetDao.addAsset(new Asset(asset_name, ledger_id));
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
	
	public int modify(String oldName, Asset asset) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		AssetDao assetDao = new AssetDao(conn);
		rc = assetDao.modifyAsset(oldName, asset);
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
	
	public int remove(Asset asset) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		AssetDao assetDao = new AssetDao(conn);
		rc = assetDao.deleteAsset(asset);
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
	
	public int monthStat(Asset asset) {
		int amount = 0;
		String date = "";
		Connection conn = DBConnection.getConnection();
		if (conn == null) return -(DB_CONNECT_FAIL_RC);
		RecordDao recordDao = new RecordDao(conn);
		Calendar c = Calendar.getInstance();
		date = String.format("%04d/%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
		amount = recordDao.getAmountOfMonth(date, asset);
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
