package com.accounting.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecordDao {
	Connection conn;
	
	public RecordDao(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<Record> queryRecord(String ledger_id) {
		ArrayList<Record> record_List = new ArrayList<Record>();
		Record record;
		String sql = "select * from [record] where ledger_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				record = new Record();
				record.setRecord_id(rset.getString(1));
				record.setRecord_date(rset.getString(2));
				record.setExpend_name(rset.getString(3));
				record.setAsset_name(rset.getString(4));
				record.setAmount(rset.getString(5));
				record.setMemo(rset.getString(6));
				record.setLedger_id(rset.getString(7));
				record_List.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			record_List = null;
		}
		return record_List;
	}
	
	public Record queryOneRecord(String record_id) {
		Record record = new Record();
		String sql = "select * from [record] where record_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, record_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				record.setRecord_id(rset.getString(1));
				record.setRecord_date(rset.getString(2));
				record.setExpend_name(rset.getString(3));
				record.setAsset_name(rset.getString(4));
				record.setAmount(rset.getString(5));
				record.setMemo(rset.getString(6));
				record.setLedger_id(rset.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			record = null;
		}
		return record;
	}
	
	public int addRecord(Record record) {
		int rc = 0;
		int count;
		String record_id;
		String sql = "insert into [record] values(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			count = getMaxCount(record.getLedger_id().trim());
			if (count == -1) {
				System.err.println("getMaxCount(record) Fail!");
				return count;
			}
			record_id = String.format("%s_r%02d", record.getLedger_id().trim(), count + 1);
			pstmt.setString(1, record_id);
			pstmt.setString(2, record.getRecord_date());
			pstmt.setString(3, record.getExpend_name());
			pstmt.setString(4, record.getAsset_name());
			pstmt.setString(5, record.getAmount());
			pstmt.setString(6, record.getMemo());
			pstmt.setString(7, record.getLedger_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int modifyRecord(Record record) {
		int rc = 0;
		String sql = "update [record] set record_date = ?,  expend_id = ?, asset_id = ?, "
				+ "amount = ?, memo = ? where record_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, record.getRecord_date());
			pstmt.setString(2, record.getExpend_name());
			pstmt.setString(3, record.getAsset_name());
			pstmt.setString(4, record.getAmount());
			pstmt.setString(5, record.getMemo());
			pstmt.setString(6, record.getRecord_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int deleteRecord(String record_id) {
		int rc = 0;
		String sql = "delete from [record] where record_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, record_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	public int deleteRecordWithLedger(String ledger_id) {
		int rc = 0;
		String sql = "delete from [record] where ledger_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			rc = e.getErrorCode() == 0 ? -1 : e.getErrorCode();
		}
		return rc;
	}
	
	private int getMaxCount(String ledger_id) {
		int maxCount = 0;
		String sql = "select record_id from [record] where record_id like ? "
				+ "order by record_id asc";
		String count = "";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id + "%");
			rset = pstmt.executeQuery();
			while (rset.next())	count = rset.getString(1);
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
	
	public int reorderRecordId(String ledger_id) {
		int rc = 0;
		int count = 0;
		String sql = "select record_id from [record] where ledger_id = ? order by record_id asc";
		String record_id;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			sql = "update [record] set record_id = ? where record_id = ?";
			pstmt = conn.prepareStatement(sql);
			while (rset.next()) {
				count++;
				record_id = String.format("%s_r%02d", ledger_id.trim(), count);
				if (!rset.getString(1).equals(record_id)) {
					pstmt.setString(1, record_id);
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
	
	public int getAmountOfMonth(String date, String ledger_id) {
		int amount = 0;
		String sql = "select sum(cast(amount as int)) from [record] where ledger_id = ? and record_date like ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			pstmt.setString(2, date.substring(0, 7) + "%");
			rset = pstmt.executeQuery();
			while (rset.next()) amount = rset.getInt(1);
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			amount = -(e.getErrorCode());
		}
		return amount;
	}
	
	public int getAmountOfMonth(String date, Expenditure expend) {
		int amount = 0;
		String sql = "select sum(cast(amount as int)) from [record] where "
				+ "ledger_id = ? and expend_name = ? and record_date like ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, expend.getLedger_id());
			pstmt.setString(2, expend.getExpend_name());
			pstmt.setString(3, date.substring(0, 7) + "%");
			rset = pstmt.executeQuery();
			while (rset.next()) amount = rset.getInt(1);
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			amount = -(e.getErrorCode());
		}
		return amount;
	}
	
	public int getAmountOfMonth(String date, Asset asset) {
		int amount = 0;
		String sql = "select sum(cast(amount as int)) from [record] where "
				+ "ledger_id = ? and asset_name = ? and record_date like ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, asset.getLedger_id());
			pstmt.setString(2, asset.getAsset_name());
			pstmt.setString(3, date.substring(0, 7) + "%");
			rset = pstmt.executeQuery();
			while (rset.next()) amount = rset.getInt(1);
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			amount = -(e.getErrorCode());
		}
		return amount;
	}
	
	public ArrayList<String> queryDate(String ledger_id) {
		ArrayList<String> date_List = new ArrayList<String>();
		String sql = "select record_date from [record] where ledger_id = ? order by record_date desc";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				if (!date_List.contains(rset.getString(1).substring(0, 7))) {
					date_List.add(rset.getString(1).substring(0, 7));
				}
			}
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			date_List = null;
		}
		return date_List;
	}
	
	public ArrayList<Record> queryRecord(String ledger_id, String date, String expend_name, String asset_name, String amount, String memo) {
		ArrayList<Record> record_List = new ArrayList<Record>();
		String sql = "select * from [record] where ledger_id = ? and record_date like ? "
				+ "and expend_name like ? and asset_name like ? and amount like ? and memo like ?";
		Record record;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ledger_id);
			pstmt.setString(2, date + "%");
			pstmt.setString(3, "%" + expend_name + "%");
			pstmt.setString(4, "%" + asset_name + "%");
			pstmt.setString(5, amount.equals("") ? "%" : amount);
			pstmt.setString(6, "%" + memo + "%");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				record = new Record();
				record.setRecord_id(rset.getString(1));
				record.setRecord_date(rset.getString(2));
				record.setExpend_name(rset.getString(3));
				record.setAsset_name(rset.getString(4));
				record.setAmount(rset.getString(5));
				record.setMemo(rset.getString(6));
				record.setLedger_id(rset.getString(7));
				record_List.add(record);
			}
		} catch (SQLException e) {
			System.err.printf("Error Code [%d]\n", e.getErrorCode());
			e.printStackTrace();
			record_List = null;
		}
		return record_List;
	}
}
