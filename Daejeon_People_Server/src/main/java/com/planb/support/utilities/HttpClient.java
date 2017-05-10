package com.planb.support.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class HttpClient {
	private HttpClientConfig config = null;
	
	private URL url = null;
	private HttpURLConnection connection = null;
	private InputStream in = null;
	private OutputStream out = null;
	private OutputStreamWriter wr = null;
	
	public HttpClient(HttpClientConfig config) {
		this.config = config;
	}
	
	public int post(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * post 요청
		 * status code 리턴
		 */
		String requestAddress = createRequestAddress(uri);
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
				out.write(createParamBytes(params));
				out.flush();
			}
			
			connection.disconnect();
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int post(String uri, Map<String, Object> headers, JSONObject requestObject) {
		/*
		 * post 요청 : 본문 데이터가 JSON
		 * status code 리턴
		 */
		String requestAddress = createRequestAddress(uri);
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
			
			connection.disconnect();
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public HashMap<String, Object> get(String uri, Map<String, Object> headers, Map<String, Object> params) {
		/*
		 * get 요청
		 * status code와 응답 데이터 리턴
		 */
		String requestAddress = null;
		if(params.size() > 0) {
			requestAddress = createRequestAddress(uri, params);
			// URI와 파라미터를 통해 요청 주소 얻어오기
		} else {
			requestAddress = createRequestAddress(uri);
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
				String response = getResponse(in);
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
	
	private String createRequestAddress(String uri) {
		// POST 요청 또는 파라미터가 없는 GET 요청에서의 request address
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// 비정상 URI 방지
		
		return config.getTargetAddress() + ":" + config.getTargetPort() + uri;
	}
	
	private String createRequestAddress(String uri, Map<String, Object> params) {
		/*
		 * 파라미터가 있는 GET 요청에서의 request address
		 * URI?key=value&key=value 형태
		 */
		if(config.getTargetAddress().endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		} else if(!config.getTargetAddress().endsWith("/") && !uri.startsWith("/")) {
			uri = "/" + uri;
		}
		// 비정상 URI 방지
		
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
	
	private byte[] createParamBytes(Map<String, Object> params) {
		// POST 메소드에서 사용하는 byte 타입의 body 데이터
		StringBuilder requestData = new StringBuilder();
		
		for(String key : params.keySet()) {
			String value = String.valueOf(params.get(key));
			requestData.append(key).append("=").append(value).append("&");
		}
		
		String requestAddressStr = requestData.toString();
		requestAddressStr = requestAddressStr.substring(0, requestAddressStr.length() - 1);
		return requestAddressStr.getBytes();
	}
	
	private String getResponse(InputStream in) {
		if(in == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024 * 8];
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