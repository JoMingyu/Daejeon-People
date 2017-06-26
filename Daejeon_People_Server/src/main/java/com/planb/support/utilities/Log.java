package com.planb.support.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	private static File logFile = null;
	private static FileWriter fw;
	private static Calendar cal;
	
	private static void check() {
		File dir = new File("logs");
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		cal = Calendar.getInstance();
		String year = String.format("%4d", cal.get(Calendar.YEAR));
		String month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		String date = String.format("%02d", cal.get(Calendar.DATE));
		String logFileName = "logs/" + year + "-" + month + "-" + date + ".log";
		logFile = new File(logFileName);
		try {
			if(!logFile.exists()) {
				logFile.createNewFile();
				fw = new FileWriter(logFile, true);
				// Append
			} else {
				fw = new FileWriter(logFile, true);
			}
			} catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void request(String s) {
		writeToFile(getLogText("REQUEST", s));
	}
	
	public static void query(String sql) {
		writeToFile(getLogText("QUERY", sql));
	}
	
	public static void routing(String s) {
		writeToFile(getLogText("ROUTING", s));
	}
	
	public static void info(String s) {
		writeToFile(getLogText("INFO", s));
	}
	
	public static void error(String s) {
		writeToFile(getLogText("ERROR", s));
	}
	
	private static String getLogText(String type, String msg) {
		cal = Calendar.getInstance();
		String ampm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String hour = String.format("%02d", cal.get(Calendar.HOUR));
		String minute = String.format("%02d", cal.get(Calendar.MINUTE));
		String second = String.format("%02d", cal.get(Calendar.SECOND));
		
		StringBuilder logText = new StringBuilder();
		logText.append("[").append(hour).append(":");
		logText.append(minute).append(":");
		logText.append(second).append(" ");
		logText.append(ampm).append(" - ");
		logText.append(type).append("] ");
		logText.append(msg);
		// [hh:mm:ss AM/PM - type] message
		
		return logText.toString();
	}
	
	private static void writeToFile(String logMsg) {
		// This method is called every logging
		
		check();
		
		try {
			System.out.println(logMsg);
			fw.write(logMsg);
			fw.write("\n");
			fw.flush();
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
