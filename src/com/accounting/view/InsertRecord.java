package com.accounting.view;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.accounting.db.Asset;
import com.accounting.db.Expenditure;
import com.accounting.db.Ledger;
import com.accounting.db.Record;
import com.accounting.module.AssetModule;
import com.accounting.module.CommonModule;
import com.accounting.module.ExpenditureModule;
import com.accounting.module.RecordModule;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class InsertRecord {
	
	private Ledger ledger = LoginData.getLedger();
	private ArrayList<Asset> asset_List = LoginData.getAsset_List();
	private ArrayList<Expenditure> expend_List = LoginData.getExpend_List();
	private JFrame mainFrame;
	private JTextField txt_Date;
	private JTextField txt_Amount;
	private JTextField txt_Memo;

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
	public InsertRecord() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u65B0\u589E\u5E33\u52D9");
//		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/ledger.png")));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("ledger.png"));
		mainFrame.setBounds(100, 100, 585, 550);
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
		
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
		          "Microsoft JhengHei", Font.BOLD, 18)));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font(
				"Microsoft JhengHei",Font.PLAIN,18)));
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 563, 494);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_Title = new JLabel();
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 40));
		lbl_Title.setBounds(69, 34, 421, 54);
		lbl_Title.setText(String.format("新增帳務 - %s", ledger.getLedger_name().trim()));
		panel.add(lbl_Title);
		
		JLabel lbl_Date = new JLabel("\u5E33\u52D9\u65E5\u671F:");
		lbl_Date.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Date.setBounds(83, 119, 111, 39);
		panel.add(lbl_Date);
		
		Calendar c = Calendar.getInstance();
		
		txt_Date = new JTextField();
		txt_Date.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		txt_Date.setBounds(199, 119, 253, 39);
		txt_Date.setText(String.format("%04d/%02d/%02d", 
				c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE)));
		panel.add(txt_Date);
		txt_Date.setColumns(10);
		
		JLabel lbl_Expend = new JLabel("\u652F\u51FA\u9805\u76EE:");
		lbl_Expend.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Expend.setBounds(83, 173, 111, 39);
		panel.add(lbl_Expend);
		
		Choice choice_Expend = new Choice();
		choice_Expend.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		choice_Expend.setBounds(199, 173, 253, 39);
		choice_Expend.add(ExpenditureModule.SELECT_EMPTY_MSG);
		for (Expenditure e : expend_List) choice_Expend.add(e.getExpend_name());
		panel.add(choice_Expend);
		
		JLabel lbl_Asset = new JLabel("\u8CC7\u7522\u9805\u76EE:");
		lbl_Asset.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Asset.setBounds(83, 229, 111, 39);
		panel.add(lbl_Asset);
		
		Choice choice_Asset = new Choice();
		choice_Asset.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		choice_Asset.setBounds(199, 229, 253, 39);
		choice_Asset.add(AssetModule.SELECT_EMPTY_MSG);
		for (Asset e : asset_List) choice_Asset.add(e.getAsset_name());
		panel.add(choice_Asset);
		
		JLabel lbl_Amount = new JLabel("\u82B1\u8CBB\u91D1\u984D:");
		lbl_Amount.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Amount.setBounds(83, 288, 111, 39);
		panel.add(lbl_Amount);
		
		txt_Amount = new JTextField();
		txt_Amount.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		txt_Amount.setColumns(10);
		txt_Amount.setBounds(199, 288, 253, 39);
		panel.add(txt_Amount);
		
		JLabel lbl_Memo = new JLabel("\u5099\u8A3B\u7D00\u9304:");
		lbl_Memo.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Memo.setBounds(83, 348, 111, 39);
		panel.add(lbl_Memo);
		
		txt_Memo = new JTextField();
		txt_Memo.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		txt_Memo.setColumns(10);
		txt_Memo.setBounds(199, 348, 253, 39);
		panel.add(txt_Memo);
		
		JButton btn_Confirm = new JButton("\u78BA\u5B9A");
		btn_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				RecordModule recordModule = new RecordModule();
				while (true) {
					// 欄位空值檢核
					if (txt_Date.getText().equals("") || txt_Date.getText() == null || 
							choice_Expend.getSelectedItem().equals("") || choice_Expend.getSelectedItem() == null ||
							choice_Asset.getSelectedItem().equals("") || choice_Asset.getSelectedItem() == null || 
							txt_Amount.getText().equals("") || txt_Amount.getText() == null) {
						errMsg = RecordModule.ENTER_EMPTY_MSG;
						break;
					}
					if (choice_Expend.getSelectedItem().equals(ExpenditureModule.SELECT_EMPTY_MSG)) {
						errMsg = ExpenditureModule.SELECT_EMPTY_MSG;
						break;
					}
					if (choice_Asset.getSelectedItem().equals(AssetModule.SELECT_EMPTY_MSG)) {
						errMsg = AssetModule.SELECT_EMPTY_MSG;
						break;
					}
					
					// 欄位輸入格式檢核
					if (!CommonModule.isDate(txt_Date.getText())) {
						errMsg = CommonModule.DATE_ERROR_MSG;
						break;
					}
					if (!CommonModule.isNumeric(txt_Amount.getText())) {
						errMsg = lbl_Amount.getText() + " " + CommonModule.NUMBER_ERROR_MSG;
						break;
					}
					
					rc = recordModule.add(new Record("", txt_Date.getText(), choice_Expend.getSelectedItem(), 
							choice_Asset.getSelectedItem(), txt_Amount.getText(), txt_Memo.getText(), 
							ledger.getLedger_id()));
					if (rc == RecordModule.DB_CONNECT_FAIL_RC) {
						errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + RecordModule.DB_CONNECT_FAIL_MSG;
						break;
					}
					if (rc == RecordModule.DB_EXCEPTION_RC) {
						errMsg = CommonModule.INSERT_FAIL_MSG + "\n" + RecordModule.DB_EXCEPTION_MSG;
						break;
					}
					break;
				}
				if (errMsg.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, CommonModule.INSERT_SUCC_MSG, CommonModule.SUCC, 
							JOptionPane.INFORMATION_MESSAGE);
					new InsertRecord().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, 
							CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_Confirm.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		btn_Confirm.setBounds(131, 418, 111, 42);
		panel.add(btn_Confirm);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LedgerInfo().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setBounds(295, 418, 111, 42);
		panel.add(btn_Return);
	}
}
