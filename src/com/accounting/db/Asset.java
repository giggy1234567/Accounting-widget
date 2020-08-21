package com.accounting.db;

public class Asset {
	private String asset_name;
	private String ledger_id;
	
	public Asset(String asset_name, String ledger_id) {
		this.asset_name = asset_name;
		this.ledger_id = ledger_id;
	}
	
	public Asset() {
		
	}

	public String getAsset_name() {
		return asset_name;
	}

	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}

	public String getLedger_id() {
		return ledger_id;
	}

	public void setLedger_id(String ledger_id) {
		this.ledger_id = ledger_id;
	}
}
