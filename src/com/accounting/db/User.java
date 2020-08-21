package com.accounting.db;

public class User {
	private String user_id;
	private String user_name;
	private String phone;
	private String email;
	private String birthday;
	private String password;
	
	public User(String user_id, String user_name, String phone, String email, String birthday, String password) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.phone = phone;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
	}
	
	public User() {
		
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
