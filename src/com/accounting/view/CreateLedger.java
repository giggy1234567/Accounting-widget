package com.accounting.view;

import javax.swing.JFrame;

import com.accounting.db.User;
import com.accounting.module.CommonModule;
import com.accounting.module.LedgerModule;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class CreateLedger {

	private User user = LoginData.getUser();
	private JFrame mainFrame;
	private JTextField txt_Name;

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
	public CreateLedger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Workspace\\Accounting\\ledger.png"));
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u65B0\u589E\u5E33\u672C");
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 402, 315);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}
			public void windowClosing(WindowEvent e) {
				int rc = 0;
				rc = JOptionPane.showConfirmDialog(mainFrame, "確定要離開帳務小管家嗎?", "離開", 
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
		panel.setLayout(null);
		panel.setBounds(0, 0, 396, 275);
		mainFrame.getContentPane().add(panel);
		
		JLabel lbl_Title = new JLabel("\u8ACB\u8F38\u5165\u5E33\u672C\u540D\u7A31");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		lbl_Title.setBounds(97, 36, 203, 33);
		panel.add(lbl_Title);
		
		txt_Name = new JTextField();
		txt_Name.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		txt_Name.setBounds(97, 100, 203, 41);
		panel.add(txt_Name);
		txt_Name.setColumns(10);
		
		JButton btn_Confirm = new JButton("\u78BA\u5B9A");
		btn_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				LedgerModule ledgerModule = new LedgerModule();
				while (true) {
					if (txt_Name.getText().equals("") || txt_Name.getText() == null) {
						errMsg = LedgerModule.ENTER_EMPTY_MSG;
						break;
					}
					rc = ledgerModule.create(txt_Name.getText(), user.getUser_id());
					if (rc == LedgerModule.DB_CONNECT_FAIL_RC) {
						errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + LedgerModule.DB_CONNECT_FAIL_MSG;
						break;
					}
					if (rc == LedgerModule.DB_EXCEPTION_RC) {
						errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + LedgerModule.DB_EXCEPTION_MSG;
						break;
					}
					if (rc == LedgerModule.LEDGER_NAME_EXISTED_RC) {
						errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + LedgerModule.LEDGER_NAME_EXISTED_MSG;
						break;
					}
					break;
				}
				if (errMsg.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, CommonModule.INSERT_SUCC_MSG, "成功", 
							JOptionPane.INFORMATION_MESSAGE);
					new SelectLedger().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, "錯誤", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_Confirm.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		btn_Confirm.setBounds(86, 180, 100, 47);
		panel.add(btn_Confirm);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectLedger().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		btn_Return.setBounds(216, 180, 100, 47);
		panel.add(btn_Return);
	}
}
