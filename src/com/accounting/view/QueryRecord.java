package com.accounting.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.accounting.db.Asset;
import com.accounting.db.Expenditure;
import com.accounting.db.Ledger;
import com.accounting.db.Record;
import com.accounting.module.CommonModule;
import com.accounting.module.RecordModule;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;

public class QueryRecord {

	private Ledger ledger = LoginData.getLedger();
	private ArrayList<Expenditure> expend_List = LoginData.getExpend_List();
	private ArrayList<Asset> asset_List = LoginData.getAsset_List();
	private ArrayList<Record> record_List;
	private Object record_tbl_data[][] = new Object[200][];
	private ItemListener itemListener;
	private JFrame mainFrame;
	private JTextField txt_Amount;
	private JTextField txt_Memo;
	private JTable tbl_Record;

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
	public QueryRecord() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u5E33\u52D9\u67E5\u8A62");
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Workspace\\Accounting\\ledger.png"));
		mainFrame.setBounds(100, 100, 1550, 910);
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
		
		RecordModule recordModule = new RecordModule();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1528, 854);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_Date = new JLabel("\u5E33\u52D9\u6642\u9593:");
		lbl_Date.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		lbl_Date.setBounds(54, 49, 103, 33);
		panel.add(lbl_Date);
		
		ArrayList<String> date_List = recordModule.getDates(ledger.getLedger_id());
		if (date_List == null) {
			JOptionPane.showMessageDialog(mainFrame, CommonModule.SYSTEM_ERROR_MSG, 
					CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
			new LedgerInfo().show();
			mainFrame.dispose();
		}
		
		JComboBox<String> comBox_Date = new JComboBox<String>();
		comBox_Date.setBackground(Color.WHITE);
		comBox_Date.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		comBox_Date.setBounds(163, 50, 133, 34);
		for (String str : date_List) comBox_Date.addItem(str);
		panel.add(comBox_Date);
		
		JLabel lbl_Asset = new JLabel("\u8CC7\u7522\u9805\u76EE:");
		lbl_Asset.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		lbl_Asset.setBounds(310, 49, 111, 33);
		panel.add(lbl_Asset);
		
		JComboBox<String> comBox_Asset = new JComboBox<String>();
		comBox_Asset.setBackground(Color.WHITE);
		comBox_Asset.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		comBox_Asset.setBounds(427, 50, 160, 34);
		comBox_Asset.addItem("");
		comBox_Asset.setSelectedIndex(0);
		for (Asset e : asset_List) comBox_Asset.addItem(e.getAsset_name());
		panel.add(comBox_Asset);
		
		JLabel lbl_Expend = new JLabel("\u652F\u51FA\u9805\u76EE:");
		lbl_Expend.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		lbl_Expend.setBounds(606, 49, 103, 33);
		panel.add(lbl_Expend);
		
		JComboBox<String> comBox_Expend = new JComboBox<String>();
		comBox_Expend.setBackground(Color.WHITE);
		comBox_Expend.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		comBox_Expend.setBounds(715, 50, 160, 34);
		comBox_Expend.addItem("");
		comBox_Expend.setSelectedIndex(0);
		for (Expenditure e : expend_List) comBox_Expend.addItem(e.getExpend_name());
		panel.add(comBox_Expend);
		
		JLabel lbl_Amount = new JLabel("\u91D1\u984D:");
		lbl_Amount.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		lbl_Amount.setBounds(881, 50, 64, 33);
		panel.add(lbl_Amount);
		
		txt_Amount = new JTextField();
		txt_Amount.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		txt_Amount.setColumns(10);
		txt_Amount.setBounds(955, 50, 136, 34);
		panel.add(txt_Amount);
		
		JLabel lbl_Memo = new JLabel("\u5099\u8A3B\u7D00\u9304:");
		lbl_Memo.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		lbl_Memo.setBounds(1106, 49, 111, 33);
		panel.add(lbl_Memo);
		
		txt_Memo = new JTextField();
		txt_Memo.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		txt_Memo.setColumns(10);
		txt_Memo.setBounds(1218, 50, 153, 34);
		panel.add(txt_Memo);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LedgerInfo().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
		btn_Return.setBounds(1393, 50, 80, 34);
		panel.add(btn_Return);
		
		JLabel lbl_Record = new JLabel("\u5E33\u52D9\u7D00\u9304");
		lbl_Record.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		lbl_Record.setBounds(54, 125, 136, 33);
		panel.add(lbl_Record);
		
		record_List = recordModule.getRecords(ledger.getLedger_id(), (String) comBox_Date.getSelectedItem(), 
				(String) comBox_Expend.getSelectedItem(), (String) comBox_Asset.getSelectedItem(), 
				txt_Amount.getText(), txt_Memo.getText());
		if (record_List == null) {
			JOptionPane.showMessageDialog(mainFrame, CommonModule.SYSTEM_ERROR_MSG, 
					CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
			new LedgerInfo().show();
			mainFrame.dispose();
		}
		
		String record_tbl_title[] = new String[] {
				"\u65E5\u671F", "\u652F\u51FA\u9805\u76EE", "\u8CC7\u7522\u9805\u76EE", "\u91D1\u984D", "\u5099\u8A3B"
		};
		if (record_List.size() == 0) {
			record_tbl_data[0] = new Object[] {"", "", RecordModule.EMPTY_RECORD_MSG, "", ""};
		}
		for (int i = 0; i < record_List.size(); i++) {
			Record rec = record_List.get(i);
			record_tbl_data[i] = new Object[] {
					rec.getRecord_date(), rec.getExpend_name(), rec.getAsset_name(), rec.getAmount(), rec.getMemo()
					};
			if (i == 200) break;
		}
		
		tbl_Record = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbl_Record);
		scrollPane.setBounds(54, 200, 1317, 612);
		tbl_Record.setModel(new DefaultTableModel(record_tbl_data, record_tbl_title) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tbl_Record.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		tbl_Record.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
		tbl_Record.setRowHeight(26);
		tbl_Record.setBounds(71, 571, 834, 612);
		panel.add(scrollPane);
		
		itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					record_List = recordModule.getRecords(ledger.getLedger_id(), (String) comBox_Date.getSelectedItem(), 
							(String) comBox_Expend.getSelectedItem(), (String) comBox_Asset.getSelectedItem(), 
							txt_Amount.getText(), txt_Memo.getText());
					record_tbl_data = new Object[200][];
					if (record_List.size() == 0) {
						record_tbl_data[0] = new Object[] {"", "", RecordModule.EMPTY_RECORD_MSG, "", ""};
					}
					for (int i = 0; i < record_List.size(); i++) {
						Record rec = record_List.get(i);
						record_tbl_data[i] = new Object[] {
								rec.getRecord_date(), rec.getExpend_name(), rec.getAsset_name(), rec.getAmount(), rec.getMemo()
								};
						if (i == 200) break;
					}
					tbl_Record.setModel(new DefaultTableModel(record_tbl_data, record_tbl_title) {
						private static final long serialVersionUID = 1L;
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
				}
			}
		};
		comBox_Date.addItemListener(itemListener);
		comBox_Asset.addItemListener(itemListener);
		comBox_Expend.addItemListener(itemListener);
	}
}
