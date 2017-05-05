package com.planb.support.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	private static File file;
	private static FileWriter fw;
	private static Calendar cal;
	
	public static void initialize() {
		cal = Calendar.getInstance();
		String year = String.format("%4d", cal.get(Calendar.YEAR));
		String month = String.format("%02d", cal.get(Calendar.MONTH));
		String date = String.format("%02d", cal.get(Calendar.DATE));
		String logFileName = year + "-" + month + "-" + date + ".txt";
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
	
	public static void I(String s) {
		String ampm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String hour = String.format("%2d", cal.get(Calendar.HOUR));
		String minute = String.format("%2d", cal.get(Calendar.MINUTE));
		String second = String.format("%2d", cal.get(Calendar.SECOND));
		
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
