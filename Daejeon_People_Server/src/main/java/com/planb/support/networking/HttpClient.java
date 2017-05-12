package com.planb.support.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

public class HttpClient {
	private Config config = null;
	
	private URL url = null;
	private HttpURLConnection connection = null;
	private InputStream in = null;
	private OutputStream out = null;
	private OutputStreamWriter wr = null;
	
	public HttpClient(Config config) {
		this.config = config;
	}
	
	public HttpClient() {
		this.config = new HttpClientDefaultConfig();
	}
	
	public Response post(String uri, Map<String, Object> headers, Map<String, Object> params) {
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
			
			Response response = new Response();
			try {
				in = connection.getInputStream();
				String responseBody = NetworkingHelper.getResponse(in);
				// connection���� ���� InputStream���� ���� ������
				response.setResponseBody(responseBody);
			} catch(IOException e) {
				
			}
			response.setResponseCode(connection.getResponseCode());
			response.setResponseHeader(connection.getHeaderFields());
			
			connection.disconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response post(String uri, Map<String, Object> headers, JSONObject requestObject) {
		/*
		 * post ��û : ���� �����Ͱ� JSON
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
			
			wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(requestObject.toString());
			wr.flush();
			
			Response response = new Response();
			try {
				in = connection.getInputStream();
				String responseBody = NetworkingHelper.getResponse(in);
				// connection���� ���� InputStream���� ���� ������
				response.setResponseBody(responseBody);
			} catch(IOException e) {
				
			}
			response.setResponseCode(connection.getResponseCode());
			response.setResponseHeader(connection.getHeaderFields());
			
			connection.disconnect();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Response get(String uri, Map<String, Object> headers, Map<String, Object> params) {
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
			
			Response response = new Response();
			try {
				in = connection.getInputStream();
				String responseBody = NetworkingHelper.getResponse(in);
				// connection���� ���� InputStream���� ���� ������
				response.setResponseBody(responseBody);
			} catch(IOException e) {
				
			}
			response.setResponseCode(connection.getResponseCode());
			response.setResponseHeader(connection.getHeaderFields());
			
			connection.disconnect();
			return response;
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