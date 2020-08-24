package com.accounting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssetDao {
	Connection conn;
	
	public AssetDao(Connection conn) {
		this.conn = conn;
	}
	
	public int insertAssetDefault(String ledger_id) {
		int rc = 0;
		String sql = "insert into asset values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "現金");
			pstmt.setString(2, ledger_id.trim());
			pstmt.addBatch();
			pstmt.setString(1, "銀行存款");
			pstmt.setString(2, ledger_id.trim());
			pstmt.addBatch();
			pstmt.setString(1, "信用卡");
			pstmt.setString(2, ledger_id.trim());
			pstmt.addBatch();
			pstmt.setString(1, "悠遊卡");
			pstmt.setString(2, ledger_id.trim());
			pstmt.addBatch();
			pstmt.executeBatch();
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int addAsset(Asset asset) {
		int rc = 0;
		String sql = "insert into [asset] values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, asset.getAsset_name());
			pstmt.setString(2, asset.getLedger_id());
			pstmt.executeUpdate();
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public ArrayList<Asset> queryAsset(String ledger_id) {
		ArrayList<Asset> asset_List = new ArrayList<Asset>();
		Asset asset;
		String sql = "select * from asset where ledger_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				asset = new Asset();
				asset.setAsset_name(rset.getString(1));
				asset.setLedger_id(rset.getString(2));
				asset_List.add(asset);
			}
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
			asset_List = null;
		}
		return asset_List;
	}
	
	public int modifyAsset(String oldName, Asset asset) {
		int rc = 0;
		String sql = "update [asset] set asset_name = ? where ledger_id = ? and asset_name = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, asset.getAsset_name());
			pstmt.setString(2, asset.getLedger_id());
			pstmt.setString(3, oldName);
			pstmt.executeUpdate();
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int deleteAsset(Asset asset) {
		int rc = 0;
		String sql = "delete from asset where asset_name = ? and ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, asset.getAsset_name());
			pstmt.setString(2, asset.getLedger_id());
			pstmt.executeQuery();
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int deleteAssetWithLedger(String ledger_id) {
		int rc = 0;
		String sql = "delete from [asset] where ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			pstmt.executeQuery();
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			e.printStackTrace();
			rc = -1;
		}
		return rc;
	}
	
	public int reorderAssetId(String ledger_id) {
		int rc = 0;
		int count = 0;
		String sql = "select * from [asset] where ledger_id = ? order by asset_id asc";
		String asset_id;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			sql = "update [asset] set asset_id = ? where asset_id = ?";
			pstmt = conn.prepareStatement(sql);
			while (rset.next()) {
				count++;
				asset_id = String.format("%s_a%02d", ledger_id.trim(), count);
				if (!rset.getString(1).equals(asset_id)) {
					pstmt.setString(1, asset_id);
					pstmt.setString(2, rset.getString(1));
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			pstmt.closeOnCompletion();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
}
