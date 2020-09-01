package com.accounting.module;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.accounting.db.Asset;
import com.accounting.db.AssetDao;
import com.accounting.db.DBConnection;
import com.accounting.db.Expenditure;
import com.accounting.db.ExpenditureDao;
import com.accounting.db.Record;
import com.accounting.db.RecordDao;

public class RecordModule {
	public static final int DB_CONNECT_FAIL_RC = 1;
	public static final String DB_CONNECT_FAIL_MSG = "無法連接到資料庫\n請稍後再試";
	public static final int DB_EXCEPTION_RC = 2;
	public static final String DB_EXCEPTION_MSG = "資料庫指令執行錯誤!";
	public static final int REPORT_FAIL_RC = 3;
	public static final String REPORT_FAIL_MSG = "報表匯出失敗";
	public static final String REPORT_SUCC_MSG = "報表匯出成功";
	public static final String ENTER_EMPTY_MSG = "請輸入所有欄位資料 (備註紀錄可為空)";
	public static final String EMPTY_RECORD_MSG = "無帳務紀錄";
	public static final String DELETE_CONFIRM_MSG = "確定要刪除此筆帳務紀錄?";
	public static final String REPORT_TYPE_M = "月報表";
	public static final String REPORT_TYPE_Y = "年報表";
	public static final String REPORT_DETAIL = "帳務明細";
	public static final String REPORT_STAT = "項目統計";
	public static final String STAT_ASSET = "資產項目統計";
	public static final String STAT_EXPEND = "支出項目統計";

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
	
	public ArrayList<Record> getRecords(String ledger_id, String date, String expend_name, String asset_name, String amount, String memo) {
		ArrayList<Record> record_List = new ArrayList<Record>();
		Connection conn = DBConnection.getConnection();
		if (conn == null) record_List = null;
		RecordDao recordDao = new RecordDao(conn);
		record_List = recordDao.queryRecord(ledger_id, date, expend_name.trim(), asset_name.trim(), amount.trim(), memo.trim());
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return record_List;
	}
	
	public ArrayList<String> getDates(String ledger_id) {
		ArrayList<String> date_List = new ArrayList<String>();
		Connection conn = DBConnection.getConnection();
		if (conn == null) date_List = null;
		RecordDao recordDao = new RecordDao(conn);
		date_List = recordDao.queryDate(ledger_id);
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return date_List;
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
	
	public int remove(String record_id, String ledger_id) {
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
		rc = recordDao.reorderRecordId(ledger_id);
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
	
	public int exportExcel(String fileName, String ledger_id, String report_Type, String date) {
		int rc = 0;
		Workbook wb = null;
		FileOutputStream fos = null;
		Connection conn = DBConnection.getConnection();
		if (conn == null) return DB_CONNECT_FAIL_RC;
		AssetDao assetDao = new AssetDao(conn);
		ExpenditureDao expendDao = new ExpenditureDao(conn);
		RecordDao recordDao = new RecordDao(conn);
		ArrayList<Record> record_List = getRecords(ledger_id, date, "", "", "", "");
		ArrayList<Expenditure> expend_List = expendDao.queryExpenditure(ledger_id);
		ArrayList<Asset> asset_List = assetDao.queryAsset(ledger_id);
		int total = recordDao.getAmountOfDate(date, ledger_id);
		if (record_List == null) return DB_EXCEPTION_RC;
		if (expend_List == null) return DB_EXCEPTION_RC;
		if (asset_List == null) return DB_EXCEPTION_RC;
		if (fileName.substring(fileName.indexOf(".")).equals(".xlsx")) {
			wb = new XSSFWorkbook();
		}
		else {
			wb = new HSSFWorkbook();
		}
		Sheet detail_sheet = wb.createSheet(REPORT_DETAIL);
		Sheet stat_sheet = wb.createSheet(REPORT_STAT);
		Row header_detail = detail_sheet.createRow(0);
		Row header_stat = stat_sheet.createRow(0);
		CellStyle header_style = wb.createCellStyle();
		CellStyle table_style = wb.createCellStyle();
		Font header_font = wb.createFont();
		Font table_font = wb.createFont();
		header_font.setBold(true);
		header_font.setFontName("Microsoft JhengHei");
		header_font.setFontHeightInPoints((short) 18);
		table_font.setFontName("Microsoft JhengHei");
		table_font.setFontHeightInPoints((short) 12);
		header_style.setAlignment(HorizontalAlignment.CENTER);
		header_style.setVerticalAlignment(VerticalAlignment.CENTER);
		header_style.setFont(header_font);
		table_style.setAlignment(HorizontalAlignment.LEFT);
		table_style.setVerticalAlignment(VerticalAlignment.CENTER);
		table_style.setFont(table_font);
		Cell header_cell_detail = header_detail.createCell(0);
		Cell header_cell_stat_a = header_stat.createCell(0);
		Cell header_cell_stat_e = header_stat.createCell(3);
		header_cell_detail.setCellStyle(header_style);
		header_cell_detail.setCellValue(String.format("%s %s", date, report_Type));
		header_cell_stat_a.setCellStyle(header_style);
		header_cell_stat_a.setCellValue(STAT_ASSET);
		header_cell_stat_e.setCellStyle(header_style);
		header_cell_stat_e.setCellValue(STAT_EXPEND);
		detail_sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		stat_sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		stat_sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
		for (int i = 0; i < record_List.size(); i++) {
			Record record = record_List.get(i);
			Row row = detail_sheet.createRow(i + 1);
			row.setRowStyle(table_style);
			Cell cell_date = row.createCell(0);
			Cell cell_expend = row.createCell(1);
			Cell cell_asset = row.createCell(2);
			Cell cell_amount = row.createCell(3);
			Cell cell_memo = row.createCell(4);
			cell_date.setCellValue(record.getRecord_date());
			cell_expend.setCellValue(record.getExpend_name().trim());
			cell_asset.setCellValue(record.getAsset_name().trim());
			cell_amount.setCellValue(Integer.valueOf(record.getAmount().trim()));
			cell_amount.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
			cell_memo.setCellValue(record.getMemo().trim());
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				detail_sheet.autoSizeColumn(j);
				if (j == 0) continue;
				detail_sheet.setColumnWidth(j, detail_sheet.getColumnWidth(j) * 17 / 10);
			}
		}
		if (expend_List.size() >= asset_List.size()) {
			for (int i = 0; i < expend_List.size(); i++) {
				Row row = stat_sheet.createRow(i + 1);
				row.setRowStyle(table_style);
			}
		} else {
			for (int i = 0; i < asset_List.size(); i++) {
				Row row = stat_sheet.createRow(i + 1);
				row.setRowStyle(table_style);
			}
		}
		for (int i = 0; i < asset_List.size(); i++) {
			Asset asset = asset_List.get(i);
			int asset_total = recordDao.getAmountOfDate(date, asset);
			Row row = stat_sheet.getRow(i + 1);
			row.createCell(0).setCellValue(asset.getAsset_name().trim());
			row.createCell(1).setCellValue(asset_total);
			row.createCell(2).setCellValue(String.format("%.2f%%", (double) asset_total / (double) total));
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				stat_sheet.autoSizeColumn(j);
				stat_sheet.setColumnWidth(j, stat_sheet.getColumnWidth(j) * 17 / 10);
			}
		}
		for (int i = 0; i < expend_List.size(); i++) {
			Expenditure expend = expend_List.get(i);
			int expend_total = recordDao.getAmountOfDate(date, expend);
			Row row = stat_sheet.getRow(i + 1);
			row.createCell(3).setCellValue(expend.getExpend_name().trim());
			row.createCell(4).setCellValue(expend_total);
			row.createCell(5).setCellValue(String.format("%.2f%%", (double) expend_total / (double) total));
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				stat_sheet.autoSizeColumn(j);
				stat_sheet.setColumnWidth(j, stat_sheet.getColumnWidth(j) * 17 / 10);
			}
		}
		try {
			fos = new FileOutputStream(fileName);
			wb.write(fos);
			wb.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			rc = REPORT_FAIL_RC;
		}
		return rc;
	}
} 
