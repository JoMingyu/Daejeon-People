package com.planb.support.networking;

public class Config {
	private String targetAddress;
	private int readTimeout = 3000;
	private int connectTimeout = 3000;
	
	public Config(String targetAddress, int port, int readTimeout, int connectTimeout) {
		// Constructor with address, port, timeouts
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		this.readTimeout = readTimeout;
		this.connectTimeout = connectTimeout;
	}
	
	public Config(String targetAddress, int port) {
		// Constructor with address, port
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = port == 80 ? targetAddress : targetAddress + ":" + port;
	}
	
	public Config(String targetAddress) {
		// Constructor with address
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = targetAddress;
	}
	
	public String getTargetAddress() {
		return targetAddress;
	}
	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}
	
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
}
