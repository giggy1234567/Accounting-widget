package com.accounting.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.accounting.db.User;
import com.accounting.module.CommonModule;
import com.accounting.module.UserModule;

import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class Register {

	private JFrame mainFrame;
	private JTextField txt_Account;
	private JPasswordField passwd;
	private JTextField txt_Phone;
	private JTextField txt_Email;
	private JTextField txt_Birthday;
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
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
//		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/ledger.png")));
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("ledger.png"));
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6");
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, 706, 700);
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
		panel.setLayout(null);
		panel.setBounds(0, 0, 700, 660);
		mainFrame.getContentPane().add(panel);
		
		JLabel lbl_Title = new JLabel("\u8A3B\u518A\u4F7F\u7528\u8005");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 50));
		lbl_Title.setBounds(218, 64, 256, 77);
		panel.add(lbl_Title);
		
		JLabel lbl_Account = new JLabel("\u5E33\u865F :");
		lbl_Account.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Account.setBounds(80, 177, 81, 49);
		panel.add(lbl_Account);
		
		JLabel lbl_Name = new JLabel("\u59D3\u540D :");
		lbl_Name.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Name.setBounds(80, 233, 81, 49);
		panel.add(lbl_Name);
		
		JLabel lbl_Phone = new JLabel("\u624B\u6A5F :");
		lbl_Phone.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Phone.setBounds(80, 290, 81, 49);
		panel.add(lbl_Phone);
		
		JLabel lbl_Email = new JLabel("\u96FB\u5B50\u90F5\u4EF6 :");
		lbl_Email.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Email.setBounds(80, 350, 116, 49);
		panel.add(lbl_Email);
		
		JLabel lbl_Birthday = new JLabel("\u751F\u65E5 :");
		lbl_Birthday.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Birthday.setBounds(80, 409, 81, 49);
		panel.add(lbl_Birthday);
		
		JLabel lbl_Password = new JLabel("\u5BC6\u78BC :");
		lbl_Password.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		lbl_Password.setBounds(80, 473, 81, 49);
		panel.add(lbl_Password);
		
		txt_Account = new JTextField();
		txt_Account.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Account.setBounds(150, 184, 304, 41);
		panel.add(txt_Account);
		
		txt_Name = new JTextField();
		txt_Name.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Name.setBounds(150, 240, 304, 41);
		panel.add(txt_Name);
		
		txt_Phone = new JTextField();
		txt_Phone.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Phone.setBounds(150, 297, 304, 41);
		panel.add(txt_Phone);
		
		txt_Email = new JTextField();
		txt_Email.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		txt_Email.setBounds(198, 353, 256, 41);
		panel.add(txt_Email);
		
		txt_Birthday = new JTextField();
		txt_Birthday.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Birthday.setBounds(150, 416, 304, 41);
		panel.add(txt_Birthday);
		
		passwd = new JPasswordField();
		passwd.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		passwd.setBounds(150, 479, 304, 41);
		panel.add(passwd);
		
		JLabel lbl_Account_desc = new JLabel("\u6700\u591A20\u4F4D\u82F1\u6578\u5B57\u5143");
		lbl_Account_desc.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lbl_Account_desc.setBounds(469, 190, 171, 27);
		panel.add(lbl_Account_desc);
		
		JLabel lbl_Name_desc = new JLabel("\u6700\u591A30\u4F4D\u4E2D\u6587\u5B57\u5143");
		lbl_Name_desc.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lbl_Name_desc.setBounds(469, 246, 171, 27);
		panel.add(lbl_Name_desc);
		
		JLabel lbl_Phone_desc = new JLabel("09xxxxxxxx (10\u4F4D)");
		lbl_Phone_desc.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lbl_Phone_desc.setBounds(469, 303, 171, 27);
		panel.add(lbl_Phone_desc);
		
		JLabel lbl_Birthday_desc = new JLabel("\u897F\u5143\u5E74 (YYYY/MM/DD)");
		lbl_Birthday_desc.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lbl_Birthday_desc.setBounds(469, 422, 219, 27);
		panel.add(lbl_Birthday_desc);
		
		JLabel lbl_Password_desc = new JLabel("\u6700\u591A20\u4F4D\u82F1\u6578\u5B57\u5143");
		lbl_Password_desc.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 18));
		lbl_Password_desc.setBounds(469, 486, 171, 27);
		panel.add(lbl_Password_desc);
		
		JButton button_Confirm = new JButton("\u78BA\u8A8D\u9001\u51FA");
		button_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				UserModule userModule = new UserModule();
				while (true) {
					// 欄位空值檢核
					if (txt_Account.getText().equals("") || txt_Account.getText() == null ||
							txt_Name.getText().equals("") || txt_Name.getText() == null ||
							txt_Phone.getText().equals("") || txt_Phone.getText() == null ||
							txt_Email.getText().equals("") || txt_Email.getText() == null ||
							txt_Birthday.getText().equals("") || txt_Birthday.getText() == null ||
							String.valueOf(passwd.getPassword()).equals("") || 
							String.valueOf(passwd.getPassword()) == null) {
						errMsg = UserModule.REGISTER_EMPTY_MSG;
						break;
					}
					// 欄位長度檢核
					if (txt_Account.getText().length() > 20) {
						errMsg = lbl_Account.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} else if (txt_Name.getText().getBytes().length > 60 || txt_Name.getText().length() > 30) {
						errMsg = lbl_Name.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} else if (txt_Phone.getText().length() > 10) {
						errMsg = lbl_Phone.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} else if (txt_Email.getText().length() > 50) {
						errMsg = lbl_Email.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					} else if (txt_Birthday.getText().length() > 10) {
						errMsg = lbl_Birthday.getText() + " " + UserModule.TOO_LONG_MSG;
						break;
					}
					
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
					
					rc = userModule.register(new User(txt_Account.getText(), txt_Name.getText(), 
							txt_Phone.getText(), txt_Email.getText(), txt_Birthday.getText(), 
							String.valueOf(passwd.getPassword())));
					if (rc == UserModule.DB_CONNECT_FAIL_RC) {
						errMsg = CommonModule.REGISTER_FAIL_MSG + "\n" + UserModule.DB_CONNECT_FAIL_MSG;
						break;
					}
					if (rc == UserModule.DB_EXCEPTION_RC) {
						errMsg = CommonModule.REGISTER_FAIL_MSG + "\n" + UserModule.DB_EXCEPTION_MSG;
						break;
					} 
					if (rc == UserModule.USER_ID_EXISTED_RC) {
						errMsg = CommonModule.REGISTER_FAIL_MSG + "\n" + UserModule.USER_ID_EXISTED_MSG;
						break;
					}
					break;
				}
				if (errMsg.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, CommonModule.REGISTER_SUCC_MSG, 
							CommonModule.SUCC, JOptionPane.INFORMATION_MESSAGE);
					new Home().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		button_Confirm.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		button_Confirm.setBounds(137, 551, 170, 64);
		panel.add(button_Confirm);
		
		JButton button_Backhome = new JButton("\u8FD4\u56DE\u9996\u9801");
		button_Backhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Home().show();
				mainFrame.dispose();
			}
		});
		button_Backhome.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
		button_Backhome.setBounds(367, 551, 170, 64);
		panel.add(button_Backhome);
	}
}
