package com.accounting.view;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import com.accounting.db.Ledger;
import com.accounting.module.CommonModule;
import com.accounting.module.LedgerModule;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyLedger {

	private Ledger ledger = LoginData.getLedger();
	private JFrame mainFrame;
	private JTextField txt_Ledger;

	/**
	 * Launch the application.
	 */
	public void show() {
		try {
			mainFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ModifyLedger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u7DE8\u8F2F\u5E33\u672C");
//		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/ledger.png")));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("ledger.png"));
		mainFrame.setBounds(100, 100, 490, 335);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		JLabel lbl_Title = new JLabel("\u4FEE\u6539\u5E33\u672C\u540D\u7A31");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		lbl_Title.setBounds(150, 56, 185, 37);
		mainFrame.getContentPane().add(lbl_Title);
		
		txt_Ledger = new JTextField();
		txt_Ledger.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		txt_Ledger.setBounds(94, 120, 272, 34);
		mainFrame.getContentPane().add(txt_Ledger);
		txt_Ledger.setColumns(10);
		
		JButton btn_Confirm = new JButton("\u78BA\u5B9A");
		btn_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				LedgerModule ledgerModule = new LedgerModule();
				while (true) {
					if (txt_Ledger.getText().equals("") || txt_Ledger.getText() == null) {
						errMsg = LedgerModule.ENTER_EMPTY_MSG;
						break;
					}
					rc = ledgerModule.modify(new Ledger(
							ledger.getLedger_id(), txt_Ledger.getText(), ledger.getUser_id()));
					if (rc == LedgerModule.DB_CONNECT_FAIL_RC) {
						errMsg = CommonModule.MODIFY_FAIL_MSG + "\n" + LedgerModule.DB_CONNECT_FAIL_MSG;
						break;
					}
					if (rc == LedgerModule.DB_EXCEPTION_RC) {
						errMsg = CommonModule.MODIFY_FAIL_MSG + "\n" + LedgerModule.DB_EXCEPTION_MSG;
						break;
					}
					if (rc == LedgerModule.LEDGER_NAME_EXISTED_RC) {
						errMsg = CommonModule.MODIFY_FAIL_MSG + "\n" + LedgerModule.DB_EXCEPTION_MSG;
						break;
					}
					break;
				}
				if (errMsg.equals("")) {
					LoginData.setLedger(new Ledger(ledger.getLedger_id(), txt_Ledger.getText(), ledger.getUser_id()));
					JOptionPane.showMessageDialog(mainFrame, CommonModule.MODIFY_SUCC_MSG, CommonModule.SUCC, 
							JOptionPane.INFORMATION_MESSAGE);
					new LedgerInfo().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_Confirm.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
		btn_Confirm.setBounds(94, 195, 82, 37);
		mainFrame.getContentPane().add(btn_Confirm);
		
		JButton btn_Delete = new JButton("\u522A\u9664");
		btn_Delete.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
		btn_Delete.setBounds(191, 195, 82, 37);
		mainFrame.getContentPane().add(btn_Delete);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LedgerInfo().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
		btn_Return.setBounds(288, 195, 82, 37);
		mainFrame.getContentPane().add(btn_Return);
	}
}
