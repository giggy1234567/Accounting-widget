package com.accounting.view;

import javax.swing.JFrame;

import com.accounting.db.User;
import com.accounting.module.CommonModule;
import com.accounting.module.UserModule;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Index {

	private User user = LoginData.getUser();
	private JFrame mainFrame;
	private JTextField txt_Account;
	private JTextField txt_Name;
	private JTextField txt_Phone;
	private JTextField txt_Email;
	private JTextField txt_Birthday;
	private JPasswordField passwd;

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
	public Index() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Workspace\\Accounting\\ledger.png"));
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 610, 712);
		mainFrame.setTitle(String.format("帳務小管家 - %s", user.getUser_name()));
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
		
		if (user == null) {
			JOptionPane.showMessageDialog(mainFrame, CommonModule.SYSTEM_ERROR_MSG, 
					CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
			new Home().show();
			mainFrame.dispose();
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 604, 692);
		mainFrame.getContentPane().add(panel);
		
		JButton button_Backhome = new JButton("\u767B\u51FA");
		button_Backhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginData.setUser(null);
				new Home().show();
				mainFrame.dispose();
			}
		});
		button_Backhome.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
		button_Backhome.setBounds(461, 46, 110, 49);
		panel.add(button_Backhome);
		
		JLabel lbl_Title = new JLabel("");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 50));
		lbl_Title.setBounds(59, 39, 401, 67);
		lbl_Title.setText(String.format("歡迎  %s", user.getUser_name().trim()));
		panel.add(lbl_Title);
		
		JLabel lbl_Account = new JLabel("\u5E33\u865F :");
		lbl_Account.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Account.setBounds(108, 165, 81, 49);
		panel.add(lbl_Account);
		
		JLabel lbl_Name = new JLabel("\u59D3\u540D :");
		lbl_Name.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Name.setBounds(108, 221, 81, 49);
		panel.add(lbl_Name);
		
		JLabel lbl_Phone = new JLabel("\u624B\u6A5F :");
		lbl_Phone.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Phone.setBounds(108, 278, 81, 49);
		panel.add(lbl_Phone);
		
		JLabel lbl_Email = new JLabel("\u96FB\u5B50\u90F5\u4EF6 :");
		lbl_Email.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Email.setBounds(108, 338, 116, 49);
		panel.add(lbl_Email);
		
		JLabel lbl_Birthday = new JLabel("\u751F\u65E5 :");
		lbl_Birthday.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Birthday.setBounds(108, 397, 81, 49);
		panel.add(lbl_Birthday);
		
		JLabel lbl_Password = new JLabel("\u5BC6\u78BC:");
		lbl_Password.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Password.setBounds(108, 461, 81, 49);
		panel.add(lbl_Password);
		
		txt_Account = new JTextField();
		txt_Account.setEditable(false);
		txt_Account.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Account.setBounds(178, 172, 304, 41);
		txt_Account.setText(user.getUser_id().trim());
		panel.add(txt_Account);
		
		txt_Name = new JTextField();
		txt_Name.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Name.setBounds(178, 228, 304, 41);
		txt_Name.setText(user.getUser_name().trim());
		panel.add(txt_Name);
		
		txt_Phone = new JTextField();
		txt_Phone.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Phone.setBounds(178, 285, 304, 41);
		txt_Phone.setText(user.getPhone().trim());
		panel.add(txt_Phone);
		
		txt_Email = new JTextField();
		txt_Email.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		txt_Email.setBounds(226, 341, 256, 41);
		txt_Email.setText(user.getEmail().trim());
		panel.add(txt_Email);
		
		txt_Birthday = new JTextField();
		txt_Birthday.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Birthday.setBounds(178, 404, 304, 41);
		txt_Birthday.setText(user.getBirthday().trim());
		panel.add(txt_Birthday);
		
		passwd = new JPasswordField();
		passwd.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		passwd.setBounds(178, 464, 304, 41);
		panel.add(passwd);
		
		JButton button_Modify = new JButton("\u4FEE\u6539\u8CC7\u6599");
		button_Modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				User modifiedUser = new User();
				UserModule userModule = new UserModule();
				while (true) {
					// 欄位長度檢核
					if (txt_Account.getText().length() > 20) {
						errMsg = lbl_Account.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					}  
					if (txt_Name.getText().getBytes().length > 60 || txt_Name.getText().length() > 30) {
						errMsg = lbl_Name.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} 
					if (txt_Phone.getText().length() > 10) {
						errMsg = lbl_Phone.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} 
					if (txt_Email.getText().length() > 50) {
						errMsg = lbl_Email.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} 
					if (txt_Birthday.getText().length() > 10) {
						errMsg = lbl_Birthday.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					}
					// 欄位長度檢核結束
					
					// 欄位輸入格式檢核
					if (!CommonModule.isPhone(txt_Phone.getText())) {
						errMsg = CommonModule.PHONE_ERROR_MSG;
						break;
					}
					if (!CommonModule.isEmail(txt_Email.getText())) {
						errMsg = CommonModule.EMAIL_ERROR_MSG;
						break;
					}
					if (!CommonModule.isDate(txt_Birthday.getText())) {
						errMsg = CommonModule.DATE_ERROR_MSG;
						break;
					}
					
					// 欄位空值檢核 (不報錯)
					modifiedUser.setUser_id(user.getUser_id());
					if (txt_Name.getText().equals(user.getUser_name()) || txt_Name.getText().equals("") || 
							txt_Name.getText() == null) {
						modifiedUser.setUser_name(user.getUser_name());
					} else modifiedUser.setUser_name(txt_Name.getText());
					if (txt_Phone.getText().equals(user.getPhone()) || txt_Phone.getText().equals("") || 
							txt_Phone.getText() == null) {
						modifiedUser.setPhone(user.getPhone());
					} else modifiedUser.setPhone(txt_Phone.getText());
					if (txt_Email.getText().equals(user.getEmail()) || txt_Email.getText().equals("") || 
							txt_Email.getText() == null) {
						modifiedUser.setEmail(user.getEmail());
					} else modifiedUser.setEmail(txt_Email.getText());
					if (txt_Birthday.getText().equals(user.getBirthday()) || txt_Birthday.getText().equals("") || 
							txt_Birthday.getText() == null) {
						modifiedUser.setBirthday(user.getBirthday());
					} else modifiedUser.setBirthday(txt_Birthday.getText());
					if (String.valueOf(passwd.getPassword()).equals("") || 
							String.valueOf(passwd.getPassword()) == null) {
						modifiedUser.setPassword(user.getPassword());
					} else modifiedUser.setPassword(String.valueOf(passwd.getPassword()));
					// 欄位空值檢核結束
					
					rc = userModule.modify(modifiedUser);
					if (rc == UserModule.DB_CONNECT_FAIL_RC) {
						errMsg = CommonModule.MODIFY_FAIL_MSG + "\n" + UserModule.DB_CONNECT_FAIL_MSG;
						break;
					}
					else if (rc == UserModule.DB_EXCEPTION_RC) {
						errMsg = CommonModule.MODIFY_FAIL_MSG + "\n" + UserModule.DB_EXCEPTION_MSG;
						break;
					}
					break;
				}
				
				if (errMsg.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, CommonModule.MODIFY_SUCC_MSG, CommonModule.SUCC, 
							JOptionPane.INFORMATION_MESSAGE);
					LoginData.setUser(modifiedUser);
					new Index().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, 
							CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		button_Modify.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		button_Modify.setBounds(95, 576, 170, 64);
		panel.add(button_Modify);
		
		JButton button_Ledger = new JButton("\u67E5\u770B\u5E33\u52D9");
		button_Ledger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectLedger().show();
				mainFrame.dispose();
			}
		});
		button_Ledger.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		button_Ledger.setBounds(350, 576, 170, 64);
		panel.add(button_Ledger);
		
		JLabel lblNewLabel = new JLabel("\u203B\u6B04\u4F4D\u70BA\u7A7A\u503C\u8868\u793A\u4E0D\u505A\u4FEE\u6539");
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		lblNewLabel.setBounds(178, 530, 248, 23);
		panel.add(lblNewLabel);
	}
}
