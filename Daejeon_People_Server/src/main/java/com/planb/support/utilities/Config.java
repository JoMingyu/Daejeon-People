package com.planb.support.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(new FileReader(new File("config.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String attribute) {
		return props.get(attribute).toString();
	}
	
	public static int getIntValue(String attribute) {
		return Integer.parseInt(props.get(attribute).toString());
	}
}
