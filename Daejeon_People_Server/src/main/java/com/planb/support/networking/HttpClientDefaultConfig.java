package com.planb.support.networking;

public class HttpClientDefaultConfig extends Config {
	private static String targetAddress = null;
	private static int targetPort = 80;

	private static int readTimeout = 3000;
	private static int connectTimeout = 3000;

	public void setTargetAddress(String address) {
		HttpClientDefaultConfig.targetAddress = address;
	}

	public String getTargetAddress() {
		return targetAddress;
	}

	public void setTargetPort(int port) {
		HttpClientDefaultConfig.targetPort = port;
	}

	public int getTargetPort() {
		return targetPort;
	}

	public void setReadTimeout(int readTimeout) {
		HttpClientDefaultConfig.readTimeout = readTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		HttpClientDefaultConfig.connectTimeout = connectTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}
}
