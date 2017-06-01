package com.planb.support.chatting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.utilities.MySQL;

public class ChatMessageSaver {
	private static JSONObject saveText;
	private String topic;
	
	static {
		File file = new File("chatting_resources");
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public ChatMessageSaver(String topic) {
		this.topic = topic;
	}
	
	public void createTopic() {
		File file = new File("chatting_resources" + "/" + this.topic);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void userEntered(String clientId) {
		File file = new File("chatting_resources" + "/" + this.topic);
		
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			userInfoSet.next();
			
			saveText.put("topic", this.topic);
			saveText.put("type", "enter");
			saveText.put("name", AES256.decrypt(userInfoSet.getString("name")));
//			saveText

			FileWriter fw = new FileWriter(file, true);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
