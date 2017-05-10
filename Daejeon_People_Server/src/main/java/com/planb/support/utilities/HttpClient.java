package com.planb.support.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
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
	
	public HashMap<String, Object> post(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * post 요청
		 * status code 리턴
		 */
		String requestAddress = NetworkingHelper.createRequestAddress(config, uri);
		// URI를 통해 요청 주소 얻어오기
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// POST 요청 시 DoOutput 활성화
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
				// Body 데이터가 있으면 바이트 형태의 데이터를 전송
				out.flush();
			}
			
			Map<String, Object> map = new HashMap<String, Object>(1);
			try {
				in = connection.getInputStream();
				String response = NetworkingHelper.getResponse(in);
				// connection으로 얻은 InputStream에서 응답 얻어오기
				map.put("code", connection.getResponseCode());
				map.put("response", response);
			} catch(IOException e) {
				map.put("code", 500);
			}
			
			connection.disconnect();
			return (HashMap<String, Object>) map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, Object> post(String uri, Map<String, Object> headers, JSONObject requestObject) {
		/*
		 * post 요청 : 본문 데이터가 JSON
		 * status code 리턴
		 */
		String requestAddress = NetworkingHelper.createRequestAddress(config, uri);
		// URI를 통해 요청 주소 얻어오기
		try {
			url = new URL(requestAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			// POST 요청 시 DoOutput 활성화
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
			
			Map<String, Object> map = new HashMap<String, Object>(1);
			try {
				in = connection.getInputStream();
				String response = NetworkingHelper.getResponse(in);
				// connection으로 얻은 InputStream에서 응답 얻어오기
				map.put("code", connection.getResponseCode());
				map.put("response", response);
			} catch(IOException e) {
				map.put("code", 500);
			}
			
			connection.disconnect();
			return (HashMap<String, Object>) map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, Object> get(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * get 요청
		 * status code와 응답 데이터 리턴
		 */
		String requestAddress = null;
		if(params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(config, uri, params);
			// URI와 파라미터를 통해 요청 주소 얻어오기
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
				// connection으로 얻은 InputStream에서 응답 얻어오기
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
		 * delete 요청
		 * status code 리턴
		 */
		String requestAddress = null;
		if(params.size() > 0) {
			requestAddress = NetworkingHelper.createRequestAddress(config, uri, params);
			// URI와 파라미터를 통해 요청 주소 얻어오기
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