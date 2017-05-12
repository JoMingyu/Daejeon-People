package com.planb.api.support;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.networking.Config;
import com.planb.support.networking.HttpClient;
import com.planb.support.networking.HttpClientConfig;

public class HttpRequestForParser {
	private static JSONObject request(String url) {
		/*
		 * HttpURLConnection을 이용해 URL에 GET 요청
		 * 바이트 단위로 Json 데이터 전체를 읽어들여 리턴
		 */
		try {
			Config config = new HttpClientConfig();
			config.setTargetAddress(url);
			HttpClient client = new HttpClient(config);
			
			JSONObject responseEntire = new JSONObject(client.get("/", new HashMap<String, Object>(), new HashMap<String, Object>()).getResponseBody());
			/*
			 * Response 전체 : response를 key로 header와 body가 갈라짐
			 * response -> body -> items -> item 순서로 깊어지는 구조
			 */
			return responseEntire;
		} catch (JSONException e) {
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
