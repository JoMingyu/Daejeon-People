package com.planb.support.networking;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

public class HttpClient {
	private String targetAddress;
	private URL url;
	
	private int readTimeout = 3000;
	private int connectTimeout = 3000;
	
	private HttpURLConnection connection = null;
	private OutputStream out = null;
	private OutputStreamWriter wr = null;
	
	public HttpClient(String targetAddress, int port, int readTimeout, int connectTimeout) {
		// Constructor with address, port, timeouts
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		this.readTimeout = readTimeout;
		this.connectTimeout = connectTimeout;
	}
	
	public HttpClient(String targetAddress, int port) {
		// Constructor with address, port
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = port == 80 ? targetAddress : targetAddress + ":" + port;
	}
	
	public HttpClient(String targetAddress) {
		// Constructor with address
		
		if(targetAddress.endsWith("/")) {
			targetAddress = targetAddress.substring(0, targetAddress.length() - 1);
		}
		
		this.targetAddress = targetAddress;
	}
	
	public HttpClient(Config config) {
		// Constructor with config
		
		this.targetAddress = config.getTargetAddress();
		this.readTimeout = config.getReadTimeout();
		this.connectTimeout = config.getConnectTimeout();
	}
	
	public Response post(String uri, Map<String, Object> headers, Map<String, Object> params) {
		// POST request with parameter map
		
		String requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
		// Request address with uri
		
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// Enable do output
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			if(params != null && params.size() > 0) {
				out = connection.getOutputStream();
				out.write(NetworkingHelper.createParamBytes(params));
				// Send byte[] data if body data is exists
				out.flush();
			}
			
			return NetworkingHelper.getResponse(connection);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response post(String uri, Map<String, Object> headers, JSONObject requestObject) {
		// POST request with JSON data
		
		String requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
		// Request address with uri
		
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// Enable do output
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(requestObject.toString());
			wr.flush();
			
			return NetworkingHelper.getResponse(connection);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response get(String uri, Map<String, Object> headers, Map<String, Object> params) {
		// GET request
		
		String requestAddress = null;
		if(params != null && params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri, params);
			// Request address with uri and parameter
		} else {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
			// Request address with uri
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			return NetworkingHelper.getResponse(connection);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response delete(String uri, Map<String, Object> headers, Map<String, Object> params) {
		// GET request
		
		String requestAddress = null;
		if(params != null && params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri, params);
			// Request address with uri and parameter
		} else {
			requestAddress = NetworkingHelper.createRequestAddress(this.targetAddress, uri);
			// Request address with uri
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setReadTimeout(this.readTimeout);
			connection.setConnectTimeout(this.connectTimeout);
			
			if(headers != null && headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			return NetworkingHelper.getResponse(connection);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}