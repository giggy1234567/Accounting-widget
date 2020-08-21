package com.accounting.db;

public class Expenditure {
	private String expend_name;
	private String ledger_id;
	
	public Expenditure(String expend_name, String ledger_id) {
		this.expend_name = expend_name;
		this.ledger_id = ledger_id;
	}
	
	public Expenditure() {
		
	}

	public String getExpend_name() {
		return expend_name;
	}

	public void setExpend_name(String expend_name) {
		this.expend_name = expend_name;
	}

	public String getLedger_id() {
		return ledger_id;
	}

	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}
}
