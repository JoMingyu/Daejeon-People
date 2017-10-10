package com.planb.support.chatting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.utilities.MySQL;

public class ChatRoomManager {
//	public static int getUserCountInRoom(String topic) {
//		ResultSet room = MySQL.executeQuery("SELECT COUNT(*) FROM travel_clients WHERE topic=?", topic);
//		try {
//			if(room != null ? room.next() : false) {
//				return room.getInt(1);
//			} else {
//				return 0;
//			}
//		} catch(SQLException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
	
//	public static int getLastIndexInRoom(String topic) {
//		ResultSet room = MySQL_Chat.executeQuery("SELECT idx FROM " + topic + " ORDER BY idx DESC limit 1");
//		
//		try {
//			if(room != null ? room.next() : false) {
//				return room.getInt(1);
//			} else {
//				return 0;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
	
//	public static String getLastMessageInRoom(String topic) {
//		ResultSet room = MySQL_Chat.executeQuery("SELECT content FROM " + topic + " WHERE idx=?", getLastIndexInRoom(topic));
//		
//		try {
//			if(room != null ? room.next() : false) {
//				return room.getString(1);
//			} else {
//				return "새로운 메시지가 있습니다.";
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return "새로운 메시지가 있습니다.";
//		}
//	}
}
