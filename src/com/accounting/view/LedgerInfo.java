package com.accounting.view;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;

import com.accounting.db.Asset;
import com.accounting.db.Expenditure;
import com.accounting.db.Ledger;
import com.accounting.db.Record;
import com.accounting.db.User;
import com.accounting.module.AssetModule;
import com.accounting.module.CommonModule;
import com.accounting.module.ExpenditureModule;
import com.accounting.module.LedgerModule;
import com.accounting.module.RecordModule;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class LedgerInfo {

	private User user;
	private Ledger ledger;
	private ArrayList<Asset> asset_List;
	private ArrayList<Expenditure> expend_List;
	private ArrayList<Record> record_List;
	private JFrame mainFrame;
	private JTable tbl_Asset;
	private JTable tbl_Expend;
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
	public LedgerInfo(User user, Ledger ledger) {
		this.user = user;
		this.ledger = ledger;
		AssetModule assetModule = new AssetModule();
		ExpenditureModule expendModule = new ExpenditureModule();
		RecordModule recordModule = new RecordModule();
		asset_List = assetModule.getAssets(ledger.getLedger_id());
		expend_List = expendModule.getExpends(ledger.getLedger_id());
		record_List = recordModule.getRecords(ledger.getLedger_id());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Workspace\\Accounting\\ledger.png"));
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u5E33\u52D9\u7E3D\u89BD");
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 1470, 910);
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
		
		LedgerModule ledgerModule = new LedgerModule();
		int month_total_amount = ledgerModule.monthStat(ledger.getLedger_id());
		if (month_total_amount < 0) {
			System.err.printf("Err %d\n", month_total_amount);
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1464, 870);
		mainFrame.getContentPane().add(panel);
		
		JLabel lbl_Title = new JLabel("");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 36));
		lbl_Title.setBounds(71, 42, 859, 54);
		lbl_Title.setText(String.format("%s - %s", user.getUser_name().trim(), ledger.getLedger_name().trim()));
		panel.add(lbl_Title);
		
		Calendar c = Calendar.getInstance();
		JLabel lbl_Date = new JLabel("");
		lbl_Date.setFont(new Font("Microsoft JhengHei", Font.BOLD, 32));
		lbl_Date.setBounds(1197, 118, 191, 43);
		lbl_Date.setText(String.format("%04d/%02d/%02d", 
				c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE)));
		panel.add(lbl_Date);
		
		JButton button_Return = new JButton("\u8FD4\u56DE\u5E33\u672C\u9078\u64C7");
		button_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectLedger(user).show();
				mainFrame.dispose();
			}
		});
		button_Return.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		button_Return.setBounds(1226, 42, 162, 41);
		panel.add(button_Return);
		
		JLabel lbl_Month = new JLabel("");
		lbl_Month.setFont(new Font("Microsoft JhengHei", Font.BOLD, 32));
		lbl_Month.setBounds(71, 115, 505, 48);
		lbl_Month.setText(String.format("%d月份支出總金額  %d", c.get(Calendar.MONTH) + 1, month_total_amount));
		panel.add(lbl_Month);
		
		JLabel lbl_Asset_Stat = new JLabel("\u8CC7\u7522\u9805\u76EE\u7D71\u8A08");
		lbl_Asset_Stat.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 28));
		lbl_Asset_Stat.setBounds(71, 190, 181, 48);
		panel.add(lbl_Asset_Stat);
		
		JLabel lbl_Expend_Stat = new JLabel("\u652F\u51FA\u9805\u76EE\u7D71\u8A08");
		lbl_Expend_Stat.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 28));
		lbl_Expend_Stat.setBounds(788, 190, 181, 48);
		panel.add(lbl_Expend_Stat);
		
		AssetModule assetModule = new AssetModule();
		String asset_tbl_title[] = new String[] {
			"\u8CC7\u7522\u9805\u76EE", "\u82B1\u8CBB\u91D1\u984D", "\u5360\u6BD4"
		};
		Object asset_tbl_data[][] = new Object[asset_List.size() == 0 ? 4 : asset_List.size()][];
		for (int i = 0; i < asset_List.size(); i++) {
			Asset e = asset_List.get(i);
			int asset_month_amount = assetModule.monthStat(e);
			double rate = 0.0;
			if (asset_month_amount < 0) {
				System.err.printf("Stat Error [%d]", asset_month_amount);
				asset_month_amount = 0;
			}
			if (month_total_amount == 0) rate = 0.0;
			else rate = (double) asset_month_amount / (double) month_total_amount;
			asset_tbl_data[i] = new Object[] {
					e.getAsset_name().trim(), 
					String.format("%d", asset_month_amount), 
					String.format("%.0f%%", rate * 100)
					};
		}
		tbl_Asset = new JTable();
		tbl_Asset.setModel(new DefaultTableModel(asset_tbl_data, asset_tbl_title) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		JScrollPane scrollPane_Asset = new JScrollPane(tbl_Asset);
		scrollPane_Asset.setSize(600, 144);
		scrollPane_Asset.setLocation(71, 265);
		tbl_Asset.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		tbl_Asset.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
		tbl_Asset.setRowHeight(26);
		tbl_Asset.setBounds(71, 335, 380, 121);
		panel.add(scrollPane_Asset);
		
		JButton button_Asset = new JButton("\u8CC7\u7522\u7BA1\u7406");
		button_Asset.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		button_Asset.setBounds(531, 192, 140, 48);
		panel.add(button_Asset);
		
		JButton button_Expend = new JButton("\u652F\u51FA\u7BA1\u7406");
		button_Expend.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		button_Expend.setBounds(1248, 192, 140, 48);
		panel.add(button_Expend);
		
		ExpenditureModule expendModule = new ExpenditureModule();
		String expend_tbl_title[] = new String[] {
				"\u8CC7\u7522\u9805\u76EE", "\u82B1\u8CBB\u91D1\u984D", "\u5360\u6BD4"
			};
		Object expend_tbl_data[][] = new Object[expend_List.size() == 0 ? 4 : expend_List.size()][];
		for (int i = 0; i < expend_List.size(); i++) {
			Expenditure e = expend_List.get(i);
			int expend_month_amount = expendModule.monthStat(e);
			double rate = 0.0;
			if (expend_month_amount < 0) {
				System.err.printf("Stat Error [%d]", expend_month_amount);
				expend_month_amount = 0;
			}
			if (month_total_amount == 0) rate = 0.0;
			else rate = (double) expend_month_amount / (double) month_total_amount;
			expend_tbl_data[i] = new Object[] {
					e.getExpend_name().trim(), 
					String.format("%d", expend_month_amount), 
					String.format("%.0f%%", rate * 100)
					};
		}
		tbl_Expend = new JTable();
		tbl_Expend.setModel(new DefaultTableModel(expend_tbl_data, expend_tbl_title) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		JScrollPane scrollPane_Expend = new JScrollPane(tbl_Expend);
		scrollPane_Expend.setSize(600, 144);
		scrollPane_Expend.setLocation(788, 265);
		tbl_Expend.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		tbl_Expend.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
		tbl_Expend.setRowHeight(26);
		tbl_Expend.setBounds(525, 335, 380, 121);
		panel.add(scrollPane_Expend);
		
		JLabel lbl_Record = new JLabel("\u8FD110\u7B46\u5E33\u52D9\u7D00\u9304");
		lbl_Record.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 28));
		lbl_Record.setBounds(71, 459, 213, 48);
		panel.add(lbl_Record);
		
		JButton button_Add = new JButton("\u65B0\u589E\u5E33\u52D9\u7D00\u9304");
		button_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InsertRecord(user, ledger).show();
				mainFrame.dispose();
			}
		});
		button_Add.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		button_Add.setBounds(636, 467, 222, 41);
		panel.add(button_Add);
		
		JButton button_More = new JButton("\u67E5\u770B\u66F4\u591A\u5E33\u52D9\u7D00\u9304");
		button_More.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		button_More.setBounds(1166, 466, 222, 41);
		panel.add(button_More);
		
		String record_tbl_title[] = new String[] {
				"\u65E5\u671F", "\u652F\u51FA\u9805\u76EE", "\u8CC7\u7522\u9805\u76EE", "\u91D1\u984D", "\u5099\u8A3B"
		};
		Object record_tbl_data[][] = new Object[10][];
		if (record_List.size() == 0) {
			record_tbl_data[0] = new Object[] {"", "", RecordModule.EMPTY_RECORD_MSG, "", ""};
		}
		for (int i = 0; i < record_List.size(); i++) {
			Record rec = record_List.get(i);
			record_tbl_data[i] = new Object[] {
					rec.getRecord_date(), rec.getExpend_name(), rec.getAsset_name(), rec.getAmount(), rec.getMemo()
					};
			if (i == 10) break;
		}
		tbl_Record = new JTable();
		JScrollPane scrollPane_Record = new JScrollPane(tbl_Record);
		scrollPane_Record.setSize(1317, 306);
		scrollPane_Record.setLocation(71, 533);
		tbl_Record.setModel(new DefaultTableModel(record_tbl_data, record_tbl_title) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tbl_Record.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		tbl_Record.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
		tbl_Record.setRowHeight(26);
		tbl_Record.setBounds(71, 571, 834, 306);
		panel.add(scrollPane_Record);
	}
}
