package com.accounting.module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.accounting.db.Asset;
import com.accounting.db.AssetDao;
import com.accounting.db.DBConnection;
import com.accounting.db.RecordDao;

public class AssetModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "�L�k�s�����Ʈw\n�еy��A��";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "��Ʈw���O������~!";
	public static final int ASSET_NAME_EXISTED_RC = 3;
	public static final String ASSET_NAME_EXISTED_MSG = "�Ӹ겣���ؤw�s�b\n�Э��s��J";
	public static final String SELECT_EMPTY_MSG = "�п�ܸ겣����";
	public static final String ENTER_EMPTY_MSG = "�п�J�겣���ئW��";
	public static final String INSERT_ASSET_MSG = "�s�W�겣";
	
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
			if (rc == 2627) rc = AssetModule.ASSET_NAME_EXISTED_RC;
			else rc = AssetModule.DB_EXCEPTION_RC;
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
	
	public int modify(String oldName, String ledger_id, String newName) {
		int rc = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		AssetDao assetDao = new AssetDao(conn);
		rc = assetDao.modifyAsset(oldName, new Asset(newName, ledger_id));
		if (rc != 0) {
			if (rc == 2627) rc = AssetModule.ASSET_NAME_EXISTED_RC;
			else rc = AssetModule.DB_EXCEPTION_RC;
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
	
	public int monthStat(String date, Asset asset) {
		int amount = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return -(DB_CONNECT_FAIL_RC);
		RecordDao recordDao = new RecordDao(conn);
		amount = recordDao.getAmountOfDate(date, asset);
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
	
	public int yearStat(String date, Asset asset) {
		int amount = 0;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return -(DB_CONNECT_FAIL_RC);
		RecordDao recordDao = new RecordDao(conn);
		amount = recordDao.getAmountOfDate(date, asset);
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
