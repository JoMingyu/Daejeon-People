package com.planb.support.utilities;

import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	private static File file = null;
	private static FileWriter fw;
	private static Calendar cal;
	
	private static void check() {
		if(file == null) {
			cal = Calendar.getInstance();
			String year = String.format("%4d", cal.get(Calendar.YEAR));
			String month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
			String date = String.format("%02d", cal.get(Calendar.DATE));
			String logFileName = year + "-" + month + "-" + date + ".log";
			file = new File(logFileName);
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fw = new FileWriter(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void I(String s) {
		check();
		String ampm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String hour = String.format("%02d", cal.get(Calendar.HOUR));
		String minute = String.format("%02d", cal.get(Calendar.MINUTE));
		String second = String.format("%02d", cal.get(Calendar.SECOND));
		
		StringBuilder logText = new StringBuilder();
		logText.append("[").append(hour).append(":");
		logText.append(minute).append(":");
		logText.append(second).append(" ");
		logText.append(ampm).append("] ");
		logText.append(s);
		
		try {
			System.out.println(logText.toString());
			fw.write(logText.toString());
			fw.write("\n");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
