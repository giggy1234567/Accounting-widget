package com.accounting.view;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.accounting.db.Expenditure;
import com.accounting.module.CommonModule;
import com.accounting.module.ExpenditureModule;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ManageExpend {

	private ArrayList<Expenditure> expend_List = LoginData.getExpend_List();
	private JFrame mainFrame;
	private JTextField txt_Expend;

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
	public ManageExpend() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
//		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/ledger.png")));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("ledger.png"));
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u652F\u51FA\u7BA1\u7406");
		mainFrame.setBounds(100, 100, 570, 445);
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
		
		if (expend_List == null) {
			JOptionPane.showMessageDialog(mainFrame, CommonModule.SYSTEM_ERROR_MSG, 
					CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 548, 389);
		panel.setLayout(null);
		mainFrame.getContentPane().add(panel);
		
		JLabel lbl_Title = new JLabel("\u652F\u51FA\u9805\u76EE\u7BA1\u7406");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		lbl_Title.setBounds(191, 40, 175, 43);
		panel.add(lbl_Title);
		
		JComboBox<String> comBox_Expend = new JComboBox<String>();
		comBox_Expend.setBackground(Color.WHITE);
		comBox_Expend.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		comBox_Expend.setBounds(134, 116, 274, 34);
		comBox_Expend.addItem(ExpenditureModule.INSERT_EXPEND_MSG);
		for (Expenditure e : expend_List) comBox_Expend.addItem(e.getExpend_name());
		panel.add(comBox_Expend);
		
		JLabel lbl_Expend = new JLabel("\u65B0\u589E\u6216\u4FEE\u6539\u652F\u51FA:");
		lbl_Expend.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Expend.setBounds(191, 187, 175, 29);
		panel.add(lbl_Expend);
		
		txt_Expend = new JTextField();
		txt_Expend.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		txt_Expend.setColumns(10);
		txt_Expend.setBounds(134, 244, 274, 34);
		panel.add(txt_Expend);
		
		JButton btn_Enter = new JButton("\u9001\u51FA");
		btn_Enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String option = (String) comBox_Expend.getSelectedItem();
				String errMsg = "";
				ExpenditureModule expendModule = new ExpenditureModule();
				while (true) {
					if (txt_Expend.getText().equals("") || txt_Expend.getText() == null) {
						errMsg = ExpenditureModule.ENTER_EMPTY_MSG;
						break;
					}
					if (option.equals(ExpenditureModule.INSERT_EXPEND_MSG)) {
						rc = expendModule.add(txt_Expend.getText(), LoginData.getLedger().getLedger_id());
						if (rc == ExpenditureModule.DB_CONNECT_FAIL_RC) {
							errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + ExpenditureModule.DB_CONNECT_FAIL_MSG;
							break;
						}
						if (rc == ExpenditureModule.DB_EXCEPTION_RC) {
							errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + ExpenditureModule.DB_EXCEPTION_MSG;
							break;
						}
						if (rc == ExpenditureModule.EXPEND_NAME_EXISTED_RC) {
							errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + ExpenditureModule.EXPEND_NAME_EXISTED_MSG;
							break;
						}
					} else {
						rc = expendModule.modify((String) comBox_Expend.getSelectedItem(), 
								LoginData.getLedger().getLedger_id(), txt_Expend.getText());
						if (rc == ExpenditureModule.DB_CONNECT_FAIL_RC) {
							errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + ExpenditureModule.DB_CONNECT_FAIL_MSG;
							break;
						}
						if (rc == ExpenditureModule.DB_EXCEPTION_RC) {
							errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + ExpenditureModule.DB_EXCEPTION_MSG;
							break;
						}
						if (rc == ExpenditureModule.EXPEND_NAME_EXISTED_RC) {
							errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + ExpenditureModule.EXPEND_NAME_EXISTED_MSG;
							break;
						}
					}
					break;
				}
				if (errMsg.equals("")) {
					if (option.equals(ExpenditureModule.INSERT_EXPEND_MSG)) {
						JOptionPane.showMessageDialog(mainFrame, CommonModule.INSERT_SUCC_MSG, CommonModule.SUCC, 
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(mainFrame, CommonModule.MODIFY_SUCC_MSG, CommonModule.SUCC, 
								JOptionPane.INFORMATION_MESSAGE);
					}
					LoginData.setExpend_List(expendModule.getExpends(LoginData.getLedger().getLedger_id()));
					new ManageExpend().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_Enter.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Enter.setBounds(134, 314, 111, 34);
		panel.add(btn_Enter);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LedgerInfo().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Return.setBounds(297, 314, 111, 34);
		panel.add(btn_Return);
	}

}
