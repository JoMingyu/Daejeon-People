package com.planb.support.networking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
	private Map<String, List<String>> responseHeader = new HashMap<String, List<String>>();
	private String responseBody;
	private int responseCode;
	
	public Map<String, List<String>> getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(Map<String, List<String>> responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
