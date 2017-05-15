package com.planb.api.support;

import com.planb.api.parser.AdditionalImageParser;
import com.planb.api.parser.TourListBasicParser;
import com.planb.api.parser.DetailCommonParser;
import com.planb.api.parser.EnhancedDetailInfoParser;

public class ParserThread extends Thread {
	public void run() {
		while(true) {
			TourListBasicParser.parse();
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
