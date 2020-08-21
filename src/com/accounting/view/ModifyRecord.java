package com.accounting.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.accounting.db.Ledger;
import com.accounting.db.Record;
import com.accounting.db.User;

public class ModifyRecord {
	
	private Ledger ledger;
	private Record record;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void show() {
		try {
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ModifyRecord(Ledger ledger, Record record) {
		this.ledger = ledger;
		this.record = record;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

}
