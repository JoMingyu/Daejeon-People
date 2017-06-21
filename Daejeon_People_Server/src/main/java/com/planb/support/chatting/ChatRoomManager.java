package com.planb.support.chatting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.utilities.MySQL;

public class ChatRoomManager {
	public static int getUserCountInRoom(String topic) {
		ResultSet room = MySQL.executeQuery("SELECT COUNT(*) FROM travel_clients WHERE topic=?", topic);
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
}
