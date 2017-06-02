package com.planb.support.chatting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.utilities.MySQL;

public class ChatManager {
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
}
