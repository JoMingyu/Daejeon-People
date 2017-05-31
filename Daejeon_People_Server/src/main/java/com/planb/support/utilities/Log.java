package com.planb.support.utilities;

import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	private static File file = null;
	private static FileWriter fw;
	private static Calendar cal;
	
	static {
		File dir = new File("logs");
		dir.mkdir();
		/*
		 * Create logs directory
		 * mkdir method contains exist check
		 */
		
		if(file == null) {
			cal = Calendar.getInstance();
			String year = String.format("%4d", cal.get(Calendar.YEAR));
			String month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
			String date = String.format("%02d", cal.get(Calendar.DATE));
			String logFileName = "logs/" + year + "-" + month + "-" + date + ".log";
			file = new File(logFileName);
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fw = new FileWriter(file, true);
				// Append true
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void Q(String sql) {
		writeToFile(getLogText("QUERY", sql));
	}
	
	public static void R(String s) {
		writeToFile(getLogText("ROUTING", s));
	}
	
	public static void I(String s) {
		writeToFile(getLogText("INFO", s));
	}
	
	public static void E(String s) {
		writeToFile(getLogText("ERROR", s));
	}
	
	private static String getLogText(String type, String s) {
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
		logText.append(s.toString());
		// [hh:mm:ss AM/PM - type] message
		
		return logText.toString();
	}
	
	private static void writeToFile(String logMsg) {
		try {
			System.out.println(logMsg);
			fw.write(logMsg);
			fw.write("\n");
			fw.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
