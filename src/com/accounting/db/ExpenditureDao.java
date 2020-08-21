package com.accounting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenditureDao {
	Connection conn;
	
	public ExpenditureDao(Connection conn) {
		this.conn = conn;
	}
	
	public int insertExpenditureDefault(String ledger_id) {
		int rc = 0;
		String sql = "insert into [expenditure] values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "飲食");
			pstmt.setString(2, ledger_id);
			pstmt.addBatch();
			pstmt.setString(1, "衣物");
			pstmt.setString(2, ledger_id);
			pstmt.addBatch();
			pstmt.setString(1, "生活用品");
			pstmt.setString(2, ledger_id);
			pstmt.addBatch();
			pstmt.setString(1, "交通");
			pstmt.setString(2, ledger_id);
			pstmt.addBatch();
			pstmt.executeBatch();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int addExpenditure(Expenditure expend) {
		int rc = 0;
		String sql = "insert into [expenditure] values(?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, expend.getExpend_name());
			pstmt.setString(2, expend.getLedger_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public ArrayList<Expenditure> queryExpenditure(String ledger_id) {
		ArrayList<Expenditure> expenditure_List = new ArrayList<Expenditure>();
		Expenditure expend;
		String sql = "select * from [expenditure] where ledger_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				expend = new Expenditure();
				expend.setExpend_name(rset.getString(1));
				expend.setLedger_id(rset.getString(2));
				expenditure_List.add(expend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			expenditure_List = null;
		}
		return expenditure_List;
	} 
	
	public int modifyExpend(String oldName, Expenditure expend) {
		int rc = 0;
		String sql = "update [expenditure] set expend_name = ? where ledger_id = ? and expend_name = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, expend.getExpend_name());
			pstmt.setString(2, expend.getLedger_id());
			pstmt.setString(3, oldName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int deleteExpend(Expenditure expend) {
		int rc = 0;
		String sql = "delete from [expend] where expend_name = ? and ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, expend.getExpend_name());
			pstmt.setString(2, expend.getLedger_id());
			pstmt.executeQuery();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int deleteExpendWithLedger(String ledger_id) {
		int rc = 0;
		String sql = "delete from [expend] where ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			rc = -1;
		}
		return rc;
	}
	
	public int reorderExpendId(String ledger_id) {
		int rc = 0;
		int count = 0;
		String sql = "select * from [expend] where ledger_id = ? order by expend_id asc";
		String asset_id;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			sql = "update [expend] set expend_id = ? where expend_id = ?";
			pstmt = conn.prepareStatement(sql);
			while (rset.next()) {
				count++;
				asset_id = String.format("%s_e%02d", ledger_id.trim(), count);
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
}
