package com.planb.tourapi.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Request {
	private static JSONObject request(String URL) {
		try {
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 8];
			int length = 0;
			while((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}
			
			JSONObject responseEntire = new JSONObject(new String(out.toByteArray(), "UTF-8"));
			/*
			 * Response 전체 : response를 key로 header와 body가 갈라짐
			 * response -> body -> items -> item 순서로 깊어지는 파싱
			 */
			return responseEntire;
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getTotalCount(String URL) {
		/*
		 * 현재 요청에 대한 전체 카운트
		 * 기본적으로 page와 numOfRows라는 파라미터로 응답 아이템 위치와 갯수를 정함
		 */
		JSONObject responseEntire = request(URL);
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		int totalCount = responseBody.getInt("totalCount");
		
		return totalCount;
	}
	
	public static int getNumOfRows(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 복수 개라면 array로 묶여 있고
		 * 아이템이 1개라면 object로 묶여 있음
		 * 요청에 대한 아이템의 갯수는 numOfRows라는 key로 표현함
		 * 파싱을 위한 array들을 점차 put하는 과정에서 이를 확인해야 함
		 */
		JSONObject responseEntire = request(URL);
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		int numOfRows = responseBody.getInt("numOfRows");
		
		return numOfRows;
	}
	
	public static JSONArray getItems(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 복수 개라면 array로 묶여 있음
		 * 아이템이 복수 개 = numOfRows > 1
		 */
		JSONObject responseEntire = request(URL);
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		JSONObject items = responseBody.getJSONObject("items");
		JSONArray itemsArray = items.getJSONArray("item");
		
		return itemsArray;
	}
	
	public static JSONArray getItem(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 1개라면 object로 묶여 있음
		 * 아이템이 1개 = numOfRows가 1
		 */
		JSONObject responseEntire = request(URL);
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		JSONObject items = responseBody.getJSONObject("items");
		JSONObject item = items.getJSONObject("item");
		
		return new JSONArray().put(item);
	}
}
