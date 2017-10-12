package com.planb.parser.support;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.networking.HttpClient;

public class HttpClientForParser {
	// 파싱을 위한 HTTP Client
	
	private static JSONObject request(String url) {
		try {
			HttpClient client = new HttpClient(url, 80, 60000, 60000);
			
			/*
			 * Response 전체 : response를 key로 header와 body가 갈라짐
			 * response -> body -> items -> item 순서로 깊어지는 구조
			 */
			return new JSONObject(client.get("/", new HashMap<>(), new HashMap<>()).getResponseBody());
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
		assert responseEntire != null;
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");

		return responseBody.getInt("totalCount");
	}
	
	@SuppressWarnings("unused")
	public static int getNumOfRows(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 복수 개라면 array로 묶여 있고
		 * 아이템이 1개라면 object로 묶여 있음
		 * 요청에 대한 아이템의 갯수는 numOfRows라는 key로 표현함
		 * 파싱을 위해 array들을 점차 쌓아가는 과정에서 이를 확인해야 함
		 */
		JSONObject responseEntire = request(URL);
		assert responseEntire != null;
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");

		return responseBody.getInt("numOfRows");
	}

	public static JSONArray getItems(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 복수 개라면 array로 묶여 있음
		 * 아이템이 복수 개 = numOfRows > 1
		 */
		JSONObject responseEntire = request(URL);
		assert responseEntire != null;
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		JSONObject items = responseBody.getJSONObject("items");

		return items.getJSONArray("item");
	}
	
	public static JSONObject getItem(String URL) {
		/*
		 * 요청에 대한 응답에서 아이템이 1개라면 object로 묶여 있음
		 * 아이템이 1개 = numOfRows가 1
		 */
		JSONObject responseEntire = request(URL);
		assert responseEntire != null;
		JSONObject inResponse = responseEntire.getJSONObject("response");
		JSONObject responseBody = inResponse.getJSONObject("body");
		if(responseBody.getInt("totalCount") != 0) {
			JSONObject items = responseBody.getJSONObject("items");
			return items.getJSONObject("item");
		} else {
			return null;
		}
	}
}
