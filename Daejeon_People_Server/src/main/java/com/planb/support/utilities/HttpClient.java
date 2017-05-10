package com.planb.support.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
	private Config config = null;
	
	private URL url = null;
	private HttpURLConnection connection = null;
	private InputStream in = null;
	private OutputStream out = null;
	
	public HttpClient(HttpClientConfig config) {
		this.config = config;
	}
	
	public HttpClient() {
		this.config = new HttpClientDefaultConfig();
	}
	
	public int post(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * post ��û
		 * status code ����
		 */
		String requestAddress = NetworkingHelper.createRequestAddress(config, uri);
		// URI�� ���� ��û �ּ� ������
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// POST ��û �� DoOutput Ȱ��ȭ
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			if(headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			if(params.size() > 0) {
				out = connection.getOutputStream();
				out.write(NetworkingHelper.createParamBytes(params));
				// Body �����Ͱ� ������ ����Ʈ ������ �����͸� ����
				out.flush();
			}
			
			connection.disconnect();
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public HashMap<String, Object> get(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * get ��û
		 * status code�� ���� ������ ����
		 */
		String requestAddress = null;
		if(params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(config, uri, params);
			// URI�� �Ķ���͸� ���� ��û �ּ� ������
		} else {
			requestAddress = NetworkingHelper.createRequestAddress(config, uri);
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			if(headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>(1);
			try {
				in = connection.getInputStream();
				String response = NetworkingHelper.getResponse(in);
				// connection���� ���� InputStream���� ���� ������
				map.put("code", connection.getResponseCode());
				map.put("response", response);
			} catch(IOException e) {
				map.put("code", 500);
			}
			
			connection.disconnect();
			return (HashMap<String, Object>) map;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int delete(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * delete ��û
		 * status code ����
		 */
		String requestAddress = null;
		if(params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(config, uri, params);
			// URI�� �Ķ���͸� ���� ��û �ּ� ������
		} else {
			requestAddress = NetworkingHelper.createRequestAddress(config, uri);
		}
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setReadTimeout(config.getReadTimeout());
			connection.setConnectTimeout(config.getConnectTimeout());
			
			if(headers.size() > 0) {
				for(String key : headers.keySet()) {
					connection.setRequestProperty(key, (String) headers.get(key));
				}
			}
			
			connection.disconnect();
			return connection.getResponseCode();
		} catch(IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
}