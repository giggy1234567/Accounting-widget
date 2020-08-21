package com.accounting.view;

import java.util.ArrayList;

import com.accounting.db.Asset;
import com.accounting.db.Expenditure;
import com.accounting.db.Ledger;
import com.accounting.db.User;

public class LoginData {
	private static User user = null;
	private static Ledger ledger = null;
	private static ArrayList<Expenditure> expend_List = null;
	private static ArrayList<Asset> asset_List = null;
	
	static User getUser() {
		return user;
	}
	static void setUser(User user) {
		LoginData.user = user;
	}
	static Ledger getLedger() {
		return ledger;
	}
	static void setLedger(Ledger ledger) {
		LoginData.ledger = ledger;
	}
	static ArrayList<Expenditure> getExpend_List() {
		return expend_List;
	}
	static void setExpend_List(ArrayList<Expenditure> expend_List) {
		LoginData.expend_List = expend_List;
	}
	static ArrayList<Asset> getAsset_List() {
		return asset_List;
	}
	static void setAsset_List(ArrayList<Asset> asset_List) {
		LoginData.asset_List = asset_List;
	}
}
