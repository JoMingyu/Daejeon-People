package com.planb.support.firebase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.networking.Config;
import com.planb.support.networking.HttpClient;
import com.planb.support.networking.HttpClientConfig;
import com.planb.support.networking.Response;

public class Firebase {
	private static final String SERVER_KEY = "AAAAhndBTOE:APA91bENhBImmt3bwwPvNYMcCanS5bl55zQ9W3-rpVJiCwPhSssuUyBWcbqL4FstfU8hhlMSmXS4qixQtaClDcT_0RJ5dh2q2pAVjM0pk8P8SyRPi0gC3xlRZbFXmpRE_FvaP4LjTizD";
	
	public Firebase() {
		// FireBase Read-Time DataBase를 사용하는 경우
//		FirebaseOptions options;
//		try {
//			options = new FirebaseOptions.Builder()
//				  .setServiceAccount(new FileInputStream("path/to/serviceAccountCredentials.json"))
//				  .setDatabaseUrl("https://databaseName.firebaseio.com/")
//				  .build();
//			FirebaseApp.initializeApp(options);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static Response createGroup(String notificationKeyName, String registrationId) {
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/gcm/notification");
		HttpClient client = new HttpClient(config);
		
		JSONObject requestObject = new JSONObject();
		requestObject.put("operation", "create");
		requestObject.put("notification_key_name", notificationKeyName);
		requestObject.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
		
		Map<String, Object> headers = createHeaders();
		
		Response response = client.post("/", headers, requestObject);
		return response;
	}
	
	public static Response enterGroup(String notificationKey, String notificationKeyName, String registrationId) {
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/gcm/notification");
		HttpClient client = new HttpClient(config);
		
		JSONObject requestObject = new JSONObject();
		requestObject.put("operation", "add");
		requestObject.put("notification_key", notificationKey);
		requestObject.put("notification_key_name", notificationKeyName);
		requestObject.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
		
		Map<String, Object> headers = createHeaders();
		
		Response response = client.post("/", headers, requestObject);
		return response;
	}
	
	public static Response exitGroup(String notificationKey, String notificationKeyName, String registrationId) {
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/gcm/notification");
		HttpClient client = new HttpClient(config);
		
		JSONObject requestObject = new JSONObject();
		requestObject.put("operation", "remove");
		requestObject.put("notification_key", notificationKey);
		requestObject.put("notification_key_name", notificationKeyName);
		requestObject.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
		
		Map<String, Object> headers = createHeaders();
		
		Response response = client.post("/", headers, requestObject);
		return response;
	}
	
	public static Response sendToGroup(String notificationKey, String title, String message) {
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/fcm/send");
		HttpClient client = new HttpClient(config);
		
		JSONObject body = new JSONObject();
		body.put(title, message);
		
		JSONObject requestObject = new JSONObject();
		requestObject.put("to", notificationKey);
		requestObject.put("data", body);
		
		Map<String, Object> headers = createHeaders();
		
		Response response = client.post("/", headers, requestObject);
		return response;
	}
	// sendByNotificationKey
	
	public static Response send(String message, String target) {
		// Send by registration id
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/fcm/send");
		HttpClient client = new HttpClient(config);
		
		JSONObject body = new JSONObject();
		body.put("body", message);
		
		JSONObject requestObject = new JSONObject();
		requestObject.put("notification", body);
		requestObject.put("to", target);
		
		Map<String, Object> headers = createHeaders();
		
		Response response = client.post("/", headers, requestObject);
		return response;
	}
	
	public static void sendByTopic() {
		// Send by topic
	}
	
	private static Map<String, Object> createHeaders() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "key=" + SERVER_KEY);
		headers.put("Content-Type", "application/json");
		
		return headers;
	}
}
