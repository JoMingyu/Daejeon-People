package com.planb.tourapi.support;

import com.planb.tourapi.parser.AreaBasedTourListParser;

public class ParserThread extends Thread {
	public void run() {
		while(true) {
			AreaBasedTourListParser.parse();
			
			try {
				Thread.sleep(1000 * 300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
