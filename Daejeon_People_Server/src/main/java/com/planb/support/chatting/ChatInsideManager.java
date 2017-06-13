package com.planb.support.chatting;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.utilities.MySQL;

import io.vertx.ext.web.FileUpload;

public class ChatInsideManager {
	public static JSONArray getRoomInsideInfo(String requesterId, String topic) {
		JSONArray response = new JSONArray();
		ResultSet travelInfoSet = MySQL.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
		
		try {
			while(travelInfoSet.next()) {
				String clientId = travelInfoSet.getString("client_id");
				ResultSet clientInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
				clientInfoSet.next();

				JSONObject clientInfo = new JSONObject();
				clientInfo.put("id", clientId);
				clientInfo.put("phone_number", clientInfoSet.getString("phone_number") == null ? "전화번호 없음" : AES256.decrypt(clientInfoSet.getString("phone_number")));
				clientInfo.put("email", AES256.decrypt(clientInfoSet.getString("email")));
				clientInfo.put("name", requesterId == clientId ? "나" : AES256.decrypt(clientInfoSet.getString("name")));
				response.put(clientInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static int addAttraction(String clientId, String topic, int contentId) {
		ResultSet travelPinSet = MySQL.executeQuery("SELECT * FROM travel_pins WHERE topic=? AND content_id=?", topic, contentId);
		try {
			if(travelPinSet.next()) {
				// 이미 공유된 여행지
				return 204;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		ResultSet attractionInfoSet = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
		try {
			userInfoSet.next();
			attractionInfoSet.next();
			
			String title = attractionInfoSet.getString("title");
			String owner = userInfoSet.getString("name");
			double mapX = attractionInfoSet.getDouble("mapx");
			double mapY = attractionInfoSet.getDouble("mapy");
			
			MySQL.executeUpdate("INSERT INTO travel_pins VALUES(?, ?, ?, ?, ?, ?)", topic, contentId, title, owner, mapX, mapY);
			MySQL_Chat.executeUpdate("INSERT INTO" + topic + "(remaning_views, type, name, content) VALUES(?, ?, ?, ?)", ChatRoomManager.getUserCountInRoom(topic), "add_att", owner, title);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 201;
	}
	
	public static void sendTextMessage(String clientId, String topic, String content) {
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		
		try {
			userInfoSet.next();
			MySQL_Chat.executeUpdate("INSERT INTO " + topic + "(remaining_views, type, name, content) VALUES(?, ?, ?, ?)", ChatRoomManager.getUserCountInRoom(topic), "text", userInfoSet.getString("name"), content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendImage(String clientId, String topic, Set<FileUpload> uploads) {
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		
		try {
			userInfoSet.next();
			
			for(FileUpload upload : uploads) {
				String identifier = createIdentifier(topic);
				MySQL_Chat.executeUpdate("INSERT INTO " + topic + "(remaining_views, type, name, content) VALUES(?, ?, ?, ?)", ChatRoomManager.getUserCountInRoom(topic), "image", userInfoSet.getString("name"), identifier);
				
				File uploadedFile = new File(upload.uploadedFileName());
				
				uploadedFile.renameTo(new File("chatting_resources/" + topic + "/" + identifier + ".png"));
				new File(upload.uploadedFileName()).delete();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String createIdentifier(String topic) {
		String identifier = null;
		
		while(true) {
			identifier = UUID.randomUUID().toString();
			ResultSet rs = MySQL_Chat.executeQuery("SELECT * FROM " + topic + " WHERE content=?", identifier);
			
			try {
				if(!rs.next()) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return identifier;
	}
	
	public static void quitTravel(String clientId, String topic) {
		MySQL.executeUpdate("DELETE FROM travels WHERE client_id=? AND topic=?", clientId, topic);
		
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			userInfoSet.next();
			MySQL_Chat.executeUpdate("INSERT INTO " + topic + "(remaining_views, type, name) VALUES(?, ?, ?)", ChatRoomManager.getUserCountInRoom(topic), "quit", userInfoSet.getString("name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
