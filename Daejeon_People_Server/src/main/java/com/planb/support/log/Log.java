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
}
