package com.accounting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LedgerDao {
	Connection conn;
	
	public LedgerDao(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<Ledger> queryLedger(String user_id) {
		String sql = "select * from [ledger] where user_id = ?";
		ArrayList<Ledger> ledger_List = new ArrayList<Ledger>();
		Ledger ledger;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				ledger = new Ledger();
				ledger.setLedger_id(rset.getString(1));
				ledger.setLedger_name(rset.getString(2));
				ledger.setUser_id(rset.getString(3));
				ledger_List.add(ledger);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ledger_List = null;
		}
		return ledger_List;
	}
	
	public int addLedger(Ledger ledger) {
		int rc = 0;
		int count = 0;
		String sql = "insert into [ledger] values(?, ?, ?)";
		String ledger_id;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			count = getMaxCount(ledger.getUser_id().trim());
			if (count == -1) {
				System.err.println("getMaxCount(ledger) Fail!");
				return count;
			}
			ledger_id = String.format("%s_l%02d", ledger.getUser_id().trim(), count + 1);
			pstmt.setString(1, ledger_id);
			pstmt.setString(2, ledger.getLedger_name());
			pstmt.setString(3, ledger.getUser_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int modifyLedger(Ledger ledger) {
		int rc = 0;
		String sql = "update [ledger] set ledger_name = ? where ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger.getLedger_name());
			pstmt.setString(2, ledger.getLedger_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			rc = -1;
		}
		return rc;
	}
	
	public int deleteLedger(String ledger_id) {
		int rc = 0;
		String sql = "delete from [ledger] where ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			rc = -1;
		}
		return rc;
	}
	
	public Ledger getLegder(String ledger_name, String user_id) {
		Ledger ledger = null;
		String sql = "select * from [ledger] where ledger_name = ? and user_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_name);
			pstmt.setString(2, user_id);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				ledger = new Ledger();
				ledger.setLedger_id(rset.getString(1));
				ledger.setLedger_name(rset.getString(2));
				ledger.setUser_id(rset.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ledger = null;
		}
		return ledger;
	}
	
	public int reorderLedgerId(String user_id) {
		int rc = 0;
		int count = 0;
		String sql = "select * from [ledger] where user_id = ? order by ledger_id asc";
		String asset_id;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rset = pstmt.executeQuery();
			sql = "update [ledger] set ledger_id = ? where ledger_id = ?";
			pstmt = conn.prepareStatement(sql);
			while (rset.next()) {
				count++;
				asset_id = String.format("%s_a%02d", user_id.trim(), count);
				if (!rset.getString(1).equals(asset_id)) {
					pstmt.setString(1, asset_id);
					pstmt.setString(2, rset.getString(1));
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	private int getMaxCount(String user_id) {
		int maxCount = 0;
		String sql = "select ledger_id from [ledger] where ledger_id like ? "
				+ "order by ledger_id asc";
		String count = "";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id + "%");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				count = rset.getString(1);
			}
			if (!count.equals("") && count != null) {
				count = count.trim();
				maxCount = Integer.valueOf(count.substring(count.length() - 2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			maxCount = -1;
		}
		return maxCount;
	}
}
