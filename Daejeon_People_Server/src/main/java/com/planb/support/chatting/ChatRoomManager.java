package com.planb.support.chatting;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.utilities.MySQL;

public class ChatRoomManager {
	public static int getUserCountInRoom(String topic) {
		ResultSet room = MySQL.executeQuery("SELECT COUNT(*) FROM travels WHERE topic=?", topic);
		try {
			if(room.next()) {
				return room.getInt(1);
			} else {
				return 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int getLastIndexInRoom(String topic) {
		ResultSet room = MySQL_Chat.executeQuery("SELECT idx FROM " + topic + " ORDER BY idx DESC limit 1");
		
		try {
			if(room.next()) {
				return room.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String createRoom(String clientId, String title) {
		String topic;
		while(true) {
			topic = UUID.randomUUID().toString().replaceAll("-", "");
			ResultSet rs = MySQL.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
			try {
				if(!rs.next()) {
					MySQL.executeUpdate("INSERT INTO travels VALUES(?, ?, ?)", topic, title, clientId);
					MySQL_Chat.executeUpdate("CREATE TABLE " + topic + "(idx INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, remaining_views INT(3) NOT NULL, type VARCHAR(20) NOT NULL, name VARCHAR(256), content VARCHAR(1024))");
					new File("chatting_resources/" + topic).mkdirs();
					return topic;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}
	}
	
	public static void deleteRoom(String topic) {
		MySQL.executeUpdate("DELETE * FROM travels WHERE topic=?", topic);
		MySQL_Chat.executeUpdate("DROP TABLE " + topic);
	}
	
	public static void enterRoom(String clientId, String topic) {
		MySQL.executeUpdate("DELETE FROM travel_invites WHERE dst_id=? AND topic=?", clientId, topic);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			rs.next();
			userInfoSet.next();
			
			MySQL.executeUpdate("INSERT INTO travels VALUES(?, ?, ?)", topic, rs.getString("title"), clientId);
			MySQL_Chat.executeUpdate("INSERT INTO " + topic +"(remaining_views, type, name) VALUES(?, ?, ?)", getUserCountInRoom(topic), "enter", userInfoSet.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static JSONArray getUnreadMessages(String topic, int startIndex) {
		ResultSet chatLogSet = MySQL_Chat.executeQuery("SELECT * FROM " + topic + " WHERE idx BETWEEN ? AND ?", startIndex, ChatRoomManager.getLastIndexInRoom(topic));
		
		JSONArray messages = new JSONArray();
		try {
			while(chatLogSet.next()) {
				JSONObject msg = new JSONObject();
				
				msg.put("idx", chatLogSet.getInt("idx"));
				msg.put("type", chatLogSet.getString("type"));
				msg.put("name", chatLogSet.getString("name"));
				if(chatLogSet.getString("content") != null) {
					msg.put("content", chatLogSet.getString("content"));
				}
				
				MySQL_Chat.executeUpdate("UPDATE " + topic + " SET remaining_views=? WHERE idx=?", chatLogSet.getInt("remaining_views") - 1, chatLogSet.getInt("idx"));
				
				messages.put("chatLogSet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return messages;
	}
}
