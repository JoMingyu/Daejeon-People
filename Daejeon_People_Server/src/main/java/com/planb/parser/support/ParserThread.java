package com.planb.parser.support;

import com.planb.parser.AdditionalImageParser;
import com.planb.parser.DetailCommonParser;
import com.planb.parser.EnhancedDetailParser;
import com.planb.parser.TourListParser;

public class ParserThread extends Thread {
	// 매일 TourAPI를 파싱하기 위한 스레드
	
	public void run() {
		while(true) {
			new TourListParser().parse();
			new DetailCommonParser().parse();
			new EnhancedDetailParser().parse();
			new AdditionalImageParser().parse();
			try {
				Thread.sleep(1000 * 3600 * 24);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
