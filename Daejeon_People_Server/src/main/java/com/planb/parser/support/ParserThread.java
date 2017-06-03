package com.planb.parser.support;

import com.planb.parser.AdditionalImageParser;
import com.planb.parser.DetailCommonParser;
import com.planb.parser.EnhancedDetailInfoParser;
import com.planb.parser.TourListParser;

public class ParserThread extends Thread {
	public void run() {
		while(true) {
			TourListParser.parse();
			DetailCommonParser.parse();
			EnhancedDetailInfoParser.parse();
			AdditionalImageParser.parse();
			try {
				Thread.sleep(1000 * 300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
