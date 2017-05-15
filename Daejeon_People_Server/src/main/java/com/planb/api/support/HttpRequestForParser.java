package com.planb.api.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequestForParser {
	private static JSONObject request(String URL) {
		/*
		 * HttpURLConnection을 이용해 URL에 GET 요청
		 * 바이트 단위로 Json 데이터 전체를 읽어들여 리턴
		 */
		try {
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(60000);
			conn.setConnectTimeout(60000);
			
			InputStream in = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			byte[] buf = new byte[1024 * 16];
			int length;
			while((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}
			
			JSONObject responseEntire = new JSONObject(new String(out.toByteArray(), "UTF-8"));
			/*
			 * Response 전체 : response를 key로 header와 body가 갈라짐
			 * response -> body -> items -> item 순서로 깊어지는 구조
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
		 * page와 numOfRows라는 파라미터로 응답 아이템 페이지와 갯수를 정하는 경우가 있음
		 * 1. TotalCount를 그대로 numOfRows에 적용하여 array를 얻어내거나
		 * 2. TotalCount에 맞춰서 page를 넘겨가며 array를 얻어내는 등의 방법으로 활용 가능
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
		 * 파싱을 위해 array들을 점차 쌓아가는 과정에서 이를 확인해야 함
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
	
	public static JSONObject getItem(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 1개라면 object로 묶여 있음
		 * 아이템이 1개 = numOfRows가 1
		 */
		JSONObject responseEntire = request(URL);
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		JSONObject items = responseBody.getJSONObject("items");
		JSONObject item = items.getJSONObject("item");
		
		return item;
	}
}
