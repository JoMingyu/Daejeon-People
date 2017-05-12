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
		// FirebaseOptions options;
		// try {
		// options = new FirebaseOptions.Builder()
		// .setServiceAccount(new
		// FileInputStream("path/to/serviceAccountCredentials.json"))
		// .setDatabaseUrl("https://databaseName.firebaseio.com/")
		// .build();
		// FirebaseApp.initializeApp(options);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
	public static void send(String title, String body, String registrationId) {
		// Send by registration id
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/fcm/send");
		HttpClient client = new HttpClient(config);

		JSONObject noti = new JSONObject();
		noti.put("title", title);
		noti.put("body", body);

		JSONObject requestObject = new JSONObject();
		requestObject.put("notification", noti);
		requestObject.put("to", registrationId);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "key=" + SERVER_KEY);
		headers.put("Content-Type", "application/json");

		client.post("/", headers, requestObject);
	}

	public static void sendByTopic(String title, String body, String topicName) {
		// Send by registration id
		Config config = new HttpClientConfig();
		config.setTargetAddress("https://fcm.googleapis.com/fcm/send");
		HttpClient client = new HttpClient(config);

		JSONObject noti = new JSONObject();
		noti.put("title", title);
		noti.put("body", body);

		JSONObject requestObject = new JSONObject();
		requestObject.put("notification", noti);
		requestObject.put("to", "/topics/" + topicName);

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "key=" + SERVER_KEY);
		headers.put("Content-Type", "application/json");

		client.post("/", headers, requestObject);
	}

	public static void sendToGroup() {
		// 아마도 클라이언트에서 처리
	}
	// sendByNotificationKey
}