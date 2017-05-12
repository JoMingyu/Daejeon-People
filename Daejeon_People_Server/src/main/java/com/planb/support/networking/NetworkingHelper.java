package com.planb.support.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class NetworkingHelper {
	static String createRequestAddress(Config config, String uri) {
		// POST ��û �Ǵ� �Ķ���Ͱ� ���� GET ��û������ request address
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// ������ URI ����
		
		return config.getTargetAddress() + ":" + config.getTargetPort() + uri;
	}
	
	static String createRequestAddress(Config config, String uri, Map<String, Object> params) {
		/*
		 * �Ķ���Ͱ� �ִ� GET ��û������ request address
		 * URI?key=value&key=value ����
		 */
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// ������ URI ����
		
		StringBuilder requestAddress = new StringBuilder();
		requestAddress.append(config.getTargetAddress());
		requestAddress.append(":");
		requestAddress.append(config.getTargetPort());
		requestAddress.append(uri);
		requestAddress.append("?");
		
		for(String key : params.keySet()) {
			String value = (String) params.get(key);
			requestAddress.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestAddress.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr;
	}
	
	static byte[] createParamBytes(Map<String, Object> params) {
		// POST �޼ҵ忡�� ����ϴ� byte Ÿ���� body ������
		StringBuilder requestData = new StringBuilder();
		
		for(String key : params.keySet()) {
			String value = String.valueOf(params.get(key));
			requestData.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestData.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr.getBytes();
	}
	
	static String getResponse(InputStream in) {
		if(in == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024 * 128];
		int length;
		
		try {
			while((length = in.read(buf)) != 1) {
				out.write(buf, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			return new String(out.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
