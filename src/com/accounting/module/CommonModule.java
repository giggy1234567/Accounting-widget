package com.accounting.module;

import java.util.Calendar;
import java.util.regex.Pattern;

public class CommonModule {
	public static final String NUMBER_ERROR_MSG = " �����Ʀr";
	public static final String DATE_ERROR_MSG = "����榡�μƭȿ��~!! ���� YYYY/MM/DD";
	public static final String PHONE_ERROR_MSG = "����榡���~!! ���� 10 ��Ʀr";
	public static final String EMAIL_ERROR_MSG = "Email �榡���~!!";
	public static final String QUIT_CONFIRM_MSG = "�T�w�n���}�b�Ȥp�ޮa��?";
	public static final String SUCC = "���\";
	public static final String ERROR = "���~";
	public static final String QUIT = "���}";
	
	
	public static boolean isNumeric(String str) {
		try {
			Integer.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isDate(String str) {
		boolean bool = true;
		String regex = "[0-9]{4}/[0-9]{2}/[0-9]{2}";
		String year = "";
		String month = "";
		String date = "";
		Calendar c = Calendar.getInstance();
		bool = Pattern.matches(regex, str);
		if (!bool) return bool;
		year = str.substring(0, 4);
		month = str.substring(5, 7);
		date = str.substring(8, 10);
		c.set(Calendar.YEAR, Integer.valueOf(year));
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		switch(Integer.valueOf(month)) {
		case 1:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 2:
			if (Integer.valueOf(date) > c.get(Calendar.DAY_OF_MONTH) || Integer.valueOf(date) < 1) bool = false;
			break;
		case 3:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 4:
			if (Integer.valueOf(date) > 30 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 5:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 6:
			if (Integer.valueOf(date) > 30 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 7:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 8:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 9:
			if (Integer.valueOf(date) > 30 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 10:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 11:
			if (Integer.valueOf(date) > 30 || Integer.valueOf(date) < 1) bool = false;
			break;
		case 12:
			if (Integer.valueOf(date) > 31 || Integer.valueOf(date) < 1) bool = false;
			break;
		default:
			bool = false;
			break;
		}
		return bool;
	}
	
	public static boolean isEmail(String str) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$";
		return Pattern.matches(regex, str);
	}
	
	public static boolean isPhone(String str) {
		String regex = "^09[0-9]{8}";
		return Pattern.matches(regex, str);
	}
}
