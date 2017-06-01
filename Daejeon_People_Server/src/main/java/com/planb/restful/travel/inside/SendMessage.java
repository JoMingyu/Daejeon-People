package com.planb.restful.travel.inside;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

import com.planb.support.chatting.ChatManager;
import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "메시지 전송")
@REST(requestBody = "topic : String, type : String, (text or image), content : String(type is text)", successCode = 201)
@Route(uri = "/chat", method = HttpMethod.POST)
public class SendMessage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		String type = ctx.request().getFormAttribute("type");
		
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			userInfoSet.next();
			
			if(type == "text") {
				String content = ctx.request().getFormAttribute("content");
				MySQL_Chat.executeQuery("INSERT INTO ?(remaining_views, type, name, content VALUES(?, ?, ?, ?)", topic, ChatManager.getUserCountInRoom(topic), "text", userInfoSet.getString("name"), content);
			} else if(type == "image") {
				Set<FileUpload> uploads = ctx.fileUploads();
				for(FileUpload upload : uploads) {
					String identifier = createIdentifier(topic);
					MySQL_Chat.executeQuery("INSERT INTO ?(remaining_views, type, name, content VALUES(?, ?, ?, ?)", topic, ChatManager.getUserCountInRoom(topic), "image", userInfoSet.getString("name"), identifier);
					
					File uploadedFile = new File(upload.uploadedFileName());
					
					uploadedFile.renameTo(new File("chatting_resources/" + topic + "/" + identifier));
					new File(upload.uploadedFileName()).delete();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
	
	private String createIdentifier(String topic) {
		String identifier = null;
		
		while(true) {
			identifier = UUID.randomUUID().toString();
			ResultSet rs = MySQL_Chat.executeQuery("SELECT * FROM ? WHERE content=?", identifier);
			
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
}
