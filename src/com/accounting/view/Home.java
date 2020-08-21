package com.accounting.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.accounting.db.User;
import com.accounting.module.CommonModule;
import com.accounting.module.UserModule;

import javax.swing.JPasswordField;
import java.awt.Toolkit;

public class Home {

	private JFrame mainFrame;
	private JTextField txt_Account;
	private JPasswordField passwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Home().show();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	public void show() {
		try {
			mainFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Workspace\\Accounting\\ledger.png"));
		mainFrame.setTitle("\u5E33\u52D9\u5C0F\u7BA1\u5BB6");
		mainFrame.setBounds(100, 100, 562, 576);
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
		panel.setBounds(0, 0, 556, 536);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lbl_Account = new JLabel("\u5E33\u865F :");
		lbl_Account.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 30));
		lbl_Account.setBounds(90, 177, 81, 49);
		panel.add(lbl_Account);
		
		JLabel lbl_Password = new JLabel("\u5BC6\u78BC :");
		lbl_Password.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 30));
		lbl_Password.setBounds(90, 297, 81, 49);
		panel.add(lbl_Password);
		
		JButton button_Login = new JButton("\u767B\u5165");
		button_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rc = 0;
				String errMsg = "";
				User user = new User();
				UserModule userModule = new UserModule();
				while (true) {
					if (txt_Account.getText().equals("") || txt_Account.getText() == null ||
							String.valueOf(passwd.getPassword()).equals("") || 
							String.valueOf(passwd.getPassword()) == null) {
						errMsg = UserModule.LOGIN_EMPTY_MSG;
						break;
					}
					rc = userModule.login(txt_Account.getText(),String.valueOf(passwd.getPassword()));
					if (rc == UserModule.DB_CONNECT_FAIL_RC) {
						errMsg = UserModule.DB_CONNECT_FAIL_MSG;
						break;
					}
					if (rc == UserModule.DB_EXCEPTION_RC) {
						errMsg = UserModule.DB_EXCEPTION_MSG;
						break;
					}
					if (rc == UserModule.LOGIN_FAIL_RC) {
						errMsg = UserModule.LOGIN_FAIL_MSG;
						break;
					}
					user = userModule.getUserInfo(txt_Account.getText());
					if (user == null) {
						errMsg = UserModule.DB_EXCEPTION_MSG;
						System.err.println("Not handle error");
						break;
					}
					break;
				}
				if (errMsg.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, UserModule.LOGIN_SUCC_MSG, CommonModule.SUCC, 
							JOptionPane.INFORMATION_MESSAGE);
					LoginData.setUser(user);
					new Index().show();
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame, errMsg, CommonModule.ERROR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		button_Login.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		button_Login.setBounds(90, 420, 140, 64);
		panel.add(button_Login);
		
		JButton button_Register = new JButton("\u8A3B\u518A");
		button_Register.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		button_Register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Register().show();
				mainFrame.dispose();
			}
		});
		button_Register.setBounds(305, 420, 140, 64);
		panel.add(button_Register);
		
		JLabel lbl_Title = new JLabel("\u5E33\u52D9\u5C0F\u7BA1\u5BB6");
		lbl_Title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 50));
		lbl_Title.setBounds(142, 42, 260, 72);
		panel.add(lbl_Title);
		
		txt_Account = new JTextField();
		txt_Account.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 24));
		txt_Account.setBounds(191, 186, 254, 41);
		panel.add(txt_Account);
		
		passwd = new JPasswordField();
		passwd.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 24));
		passwd.setBounds(191, 305, 254, 41);
		panel.add(passwd);
	}
}
