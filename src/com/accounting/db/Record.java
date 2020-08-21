package com.accounting.db;

public class Record {
	private String record_id;
	private String record_date;
	private String expend_name;
	private String asset_name;
	private String amount;
	private String memo;
	private String ledger_id;
	
	public Record(String record_id, String record_date, String expend_name, String asset_name, String amount, String memo, String ledger_id) {
		this.record_id = record_id;
		this.record_date = record_date;
		this.expend_name = expend_name;
		this.asset_name = asset_name;
		this.amount = amount;
		this.memo = memo;
		this.ledger_id = ledger_id;
	}
	
	public Record() {
		
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public String getExpend_name() {
		return expend_name;
	}

	public void setExpend_name(String expend_id) {
		this.expend_name = expend_id;
	}

	public String getAsset_name() {
		return asset_name;
	}

	public void setAsset_name(String asset_id) {
		this.asset_name = asset_id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLedger_id() {
		return ledger_id;
	}

	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}
}
