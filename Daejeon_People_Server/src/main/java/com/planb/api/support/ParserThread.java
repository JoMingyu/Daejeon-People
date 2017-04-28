package com.planb.api.support;

import com.planb.api.parser.AdditionalImageParser;
import com.planb.api.parser.AreaBasedTourListParser;
import com.planb.api.parser.DetailCommonParser;
import com.planb.api.parser.DetailInfoParser;

public class ParserThread extends Thread {
	public void run() {
		while(true) {
			AreaBasedTourListParser.parse();
			DetailCommonParser.parse();
			DetailInfoParser.parse();
			AdditionalImageParser.parse();
			try {
				Thread.sleep(1000 * 300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
