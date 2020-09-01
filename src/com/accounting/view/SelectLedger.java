package com.accounting.view;


import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import com.accounting.db.Ledger;
import com.accounting.db.User;
import com.accounting.module.AssetModule;
import com.accounting.module.CommonModule;
import com.accounting.module.ExpenditureModule;
import com.accounting.module.LedgerModule;

import java.util.ArrayList;
import java.awt.Choice;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class SelectLedger {
	
	private User user = LoginData.getUser();
	private ArrayList<Ledger> ledger_List;
	private JFrame mainFrame;

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
	public SelectLedger() {
		LedgerModule ledgerModule = new LedgerModule();
		ledger_List = ledgerModule.getLedgers(user.getUser_id());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u9078\u64C7\u5E33\u672C");
//		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/ledger.png")));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("ledger.png"));
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 402, 315);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}
			public void windowClosing(WindowEvent e) {
				int rc = 0;
				rc = JOptionPane.showConfirmDialog(mainFrame, CommonModule.QUIT_CONFIRM_MSG, CommonModule.QUIT, 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (rc == 0) {
					mainFrame.dispose();
					System.exit(0);
				} 
			}
			public void windowClosed(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}
		});
		mainFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 396, 275);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_Title = new JLabel("\u8ACB\u9078\u64C7\u6216\u65B0\u589E\u5E33\u672C");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));
		lbl_Title.setBounds(76, 35, 250, 33);
		panel.add(lbl_Title);
		
		Choice choice_Ledger = new Choice();
		choice_Ledger.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
		choice_Ledger.setBounds(76, 101, 240, 39);
		choice_Ledger.add(LedgerModule.SELECT_EMPTY_MSG);
		for (Ledger e : ledger_List) choice_Ledger.add(e.getLedger_name());
		panel.add(choice_Ledger);
		
		JButton btn_Confirm = new JButton("\u78BA\u5B9A");
		btn_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String errMsg = "";
				Ledger ledger = null;
				LedgerModule ledgerModule = new LedgerModule();
				while (true) {
					if (choice_Ledger.getSelectedItem().equals("") || choice_Ledger.getSelectedItem() == null || 
							choice_Ledger.getSelectedItem().equals(LedgerModule.SELECT_EMPTY_MSG)) {
						errMsg = LedgerModule.SELECT_EMPTY_MSG;
						break;
					} 
					ledger = ledgerModule.getLedgerInfo(choice_Ledger.getSelectedItem(), user.getUser_id()); 
					if (ledger == null) {
						errMsg = LedgerModule.DB_EXCEPTION_MSG;
						System.err.println("Not handle error");
						break;
					}
					break;
				}
				if (errMsg.equals("")) {
					ExpenditureModule expendModule = new ExpenditureModule();
					AssetModule assetModule = new AssetModule();
					LoginData.setExpend_List(expendModule.getExpends(ledger.getLedger_id()));
					LoginData.setAsset_List(assetModule.getAssets(ledger.getLedger_id()));
					LoginData.setLedger(ledger);
					new LedgerInfo().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_Confirm.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Confirm.setBounds(65, 180, 75, 40);
		panel.add(btn_Confirm);
		
		JButton btn_Create = new JButton("\u65B0\u589E");
		btn_Create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateLedger().show();
				mainFrame.dispose();
			}
		});
		btn_Create.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Create.setBounds(160, 180, 75, 40);
		panel.add(btn_Create);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Index().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Return.setBounds(257, 180, 75, 40);
		panel.add(btn_Return);
	}
}
