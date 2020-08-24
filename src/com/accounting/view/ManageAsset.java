package com.accounting.view;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ManageAsset {

	private JFrame mainFrame;
	private JTextField txt_Asset;

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
	public ManageAsset() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6 - \u8CC7\u7522\u7BA1\u7406");
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Workspace\\Accounting\\ledger.png"));
		mainFrame.setBounds(100, 100, 570, 445);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 548, 389);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_Title = new JLabel("\u8CC7\u7522\u9805\u76EE\u7BA1\u7406");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		lbl_Title.setBounds(191, 40, 175, 43);
		panel.add(lbl_Title);
		
		JComboBox<String> comBox_Asset = new JComboBox<String>();
		comBox_Asset.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		comBox_Asset.setBounds(134, 116, 274, 34);
		panel.add(comBox_Asset);
		
		JLabel lbl_Asset = new JLabel("\u65B0\u589E\u6216\u4FEE\u6539\u8CC7\u7522:");
		lbl_Asset.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		lbl_Asset.setBounds(191, 187, 175, 29);
		panel.add(lbl_Asset);
		
		txt_Asset = new JTextField();
		txt_Asset.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		txt_Asset.setBounds(134, 244, 274, 34);
		panel.add(txt_Asset);
		txt_Asset.setColumns(10);
		
		JButton btn_Enter = new JButton("\u9001\u51FA");
		btn_Enter.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Enter.setBounds(134, 314, 111, 34);
		panel.add(btn_Enter);
		
		JButton btn_Return = new JButton("\u8FD4\u56DE");
		btn_Return.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
		btn_Return.setBounds(297, 314, 111, 34);
		panel.add(btn_Return);
	}
}
