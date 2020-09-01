package com.accounting.view;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.accounting.db.Ledger;
import com.accounting.module.CommonModule;
import com.accounting.module.RecordModule;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;

public class SelectReport {
	private Ledger ledger = LoginData.getLedger();
	private ArrayList<String> month_List;
	private ArrayList<String> year_List;
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
	public SelectReport() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u532F\u51FA\u5831\u8868");
//		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/ledger.png")));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("ledger.png"));
		mainFrame.setBounds(100, 100, 402, 460);
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
		month_List = recordModule.getDates(ledger.getLedger_id());
		if (month_List == null) {
			JOptionPane.showMessageDialog(mainFrame, CommonModule.SYSTEM_ERROR_MSG, 
					CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
			new QueryRecord().show();
			mainFrame.dispose();
		}
		year_List = new ArrayList<String>();
		for (String e : month_List) {
			String year = e.substring(0, 4);
			if (!year_List.contains(year)) year_List.add(year); 
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 380, 404);
		mainFrame.getContentPane().add(panel);
		
		JLabel lbl_Title = new JLabel("\u8ACB\u9078\u64C7\u5831\u8868");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));
		lbl_Title.setBounds(110, 35, 160, 33);
		panel.add(lbl_Title);
		
		JComboBox<String> comBox_Report = new JComboBox<String>();
		comBox_Report.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		comBox_Report.setBackground(Color.WHITE);
		comBox_Report.setBounds(89, 102, 200, 35);
		comBox_Report.addItem(RecordModule.REPORT_TYPE_M);
		comBox_Report.addItem(RecordModule.REPORT_TYPE_Y);
		comBox_Report.setSelectedIndex(0);
		panel.add(comBox_Report);
		
		JLabel lbl_Time = new JLabel("\u8ACB\u9078\u64C7\u6642\u9593");
		lbl_Time.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));
		lbl_Time.setBounds(110, 171, 160, 33);
		panel.add(lbl_Time);
		
		JComboBox<String> comBox_Time = new JComboBox<String>();
		comBox_Time.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		comBox_Time.setBackground(Color.WHITE);
		comBox_Time.setBounds(89, 230, 200, 35);
		for (String e : month_List) comBox_Time.addItem(e);
		panel.add(comBox_Time);

		JButton btn_Confirm = new JButton("\u78BA\u5B9A");
		btn_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				RecordModule recordModule = new RecordModule();
				String fileName = "";
				if (comBox_Report.getSelectedItem().equals(RecordModule.REPORT_TYPE_M)) {
					fileName = String.format("%s_%s_%s.xlsx", 
							ledger.getLedger_name().trim(), 
							comBox_Time.getSelectedItem(), 
							RecordModule.REPORT_TYPE_M);
					fileName = fileName.replace('/', '-');
				}
				if (comBox_Report.getSelectedItem().equals(RecordModule.REPORT_TYPE_Y)) {
					fileName = String.format("%s_%s_%s.xlsx", 
							ledger.getLedger_name().trim(), 
							comBox_Time.getSelectedItem(), 
							RecordModule.REPORT_TYPE_Y);
				}
				JFileChooser fileChooser = new JFileChooser(System.getenv("USERPROFILE") + "/Desktop");
				fileChooser.setFileFilter(
						new FileNameExtensionFilter("Excel ÀÉ®×(xls, xlsx)", "xls", "xlsx"));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setSelectedFile(new File(fileName));
				rc = fileChooser.showSaveDialog(mainFrame);
				if (rc == JFileChooser.APPROVE_OPTION) {
					fileName = fileChooser.getSelectedFile().getAbsolutePath();
					rc = recordModule.exportExcel(fileName, 
							ledger.getLedger_id(), 
							(String) comBox_Report.getSelectedItem(), 
							(String) comBox_Time.getSelectedItem());
					if (rc != 0) {
						while (true) {
							if (rc == RecordModule.DB_EXCEPTION_RC) {
								errMsg = RecordModule.DB_EXCEPTION_MSG;
								break;
							}
							if (rc == RecordModule.REPORT_FAIL_RC) {
								errMsg = RecordModule.REPORT_FAIL_MSG;
								break;
							}
							if (rc == RecordModule.REPORT_FAIL_RC) {
								errMsg = RecordModule.REPORT_FAIL_MSG;
								break;
							}
							break;
						}
					}
					if (errMsg.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, RecordModule.REPORT_SUCC_MSG, 
								CommonModule.SUCC, JOptionPane.INFORMATION_MESSAGE);
						new QueryRecord().show();
						mainFrame.dispose();
					} else {
						JOptionPane.showMessageDialog(mainFrame, errMsg, CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btn_Confirm.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Confirm.setBounds(89, 308, 80, 40);
		panel.add(btn_Confirm);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new QueryRecord().show();
				mainFrame.dispose();
			}
		});
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Return.setBounds(209, 308, 80, 40);
		panel.add(btn_Return);
		
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					comBox_Time.removeAllItems();
					if (comBox_Report.getSelectedItem().equals(RecordModule.REPORT_TYPE_M)) {
						for (String s : month_List) comBox_Time.addItem(s);
					}
					if (comBox_Report.getSelectedItem().equals(RecordModule.REPORT_TYPE_Y)) {
						for (String s : year_List) comBox_Time.addItem(s);
					}
					comBox_Time.setSelectedIndex(0);
				}
			}
		};
		comBox_Report.addItemListener(itemListener);
	}
}
