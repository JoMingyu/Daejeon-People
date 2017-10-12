package com.planb.support.utilities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.planb.support.networking.HttpClient;

public class Translator {
	public static String translate(String koreanText) {
		HttpClient client = new HttpClient("https://openapi.naver.com");
		Map<String, Object> data = new HashMap<>();
		data.put("source", "ko");
		data.put("target", "en");
		data.put("text", koreanText);
		
		Map<String, Object> headers = new HashMap<>();
		headers.put("X-Naver-Client-Id", "QY9nwyKyEotjQQFD1fdG");
		headers.put("X-Naver-Client-Secret", "iC1sNZ8Yjt");
		
		JSONObject response = new JSONObject(client.post("/v1/language/translate", headers, data).getResponseBody());
		
		return response.getJSONObject("message").getJSONObject("result").getString("translatedText");
	}
}
