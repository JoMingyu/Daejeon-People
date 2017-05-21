package com.planb.support.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class NetworkingHelper {
	private static String validateUri(String targetAddress, String uri) {
		if(uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		
		if(!uri.startsWith("/")) {
			uri = "/" + uri;
		}
		
		return targetAddress + uri;
	}
	
	static String createRequestAddress(String targetAddress, String uri) {
		// POST request or GET request with no parameter
		
		return validateUri(targetAddress, uri);
	}
	
	static String createRequestAddress(String targetAddress, String uri, Map<String, Object> params) {
		/*
		 * GET request with parameter
		 * URI?key=value&key=value
		 */
		
		StringBuilder requestAddress = new StringBuilder();
		requestAddress.append(validateUri(targetAddress, uri)).append("?");
		for(String key : params.keySet()) {
			String value = (String) params.get(key);
			try {
				requestAddress.append(key).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String requestAddressStr = requestAddress.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr;
	}
	
	static byte[] createParamBytes(Map<String, Object> params) {
		// Body data to byte[]
		
		StringBuilder requestData = new StringBuilder();
		
		for(String key : params.keySet()) {
			String value = String.valueOf(params.get(key));
			try {
				requestData.append(key).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String requestAddressStr = requestData.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr.getBytes();
	}
	
	static Response getResponse(HttpURLConnection connection) {
		// Get response from response
		
		InputStream in = null;
		try {
			in = connection.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(in == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024 * 16];
		int length;
		try {
			while((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Response response = new Response();;
		
		try {
			String responseBody = new String(out.toByteArray(), "UTF-8");
			response.setResponseBody(responseBody);
			response.setResponseHeader(connection.getHeaderFields());
			response.setResponseCode(connection.getResponseCode());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		connection.disconnect();
		return response;
	}
}
