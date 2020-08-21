package com.accounting.db;

public class Ledger {
	private String ledger_id;
	private String ledger_name;
	private String user_id;
	
	public Ledger(String ledger_id, String ledger_name, String user_id) {
		this.ledger_id = ledger_id;
		this.ledger_name = ledger_name;
		this.user_id = user_id;
	}
	
	public Ledger() {
		
	}

	public String getLedger_id() {
		return ledger_id;
	}

	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}

	public String getLedger_name() {
		return ledger_name;
	}

	public void setLedger_name(String ledger_name) {
		this.ledger_name = ledger_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
