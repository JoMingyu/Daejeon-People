package com.planb.restful.user.common;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

@Function(name = "일반 모드", summary = "프로필 이미지 세팅")
@RESTful(requestBody = "png 이미지 파일", successCode = 201)
@Route(uri = "/profile-image", method = HttpMethod.POST)
public class SetProfileImage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String encryptedId = UserManager.getEncryptedIdFromSession(ctx);
		new File("profile-images/" + encryptedId + ".png").delete();
		// Refresh
		
		Set<FileUpload> files = ctx.fileUploads();
		
		for(FileUpload file : files) {
			File uploadedFile = new File(file.uploadedFileName());
			uploadedFile.renameTo(new File("profile-images/" + encryptedId + ".png"));
			try {
				uploadedFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			new File(file.uploadedFileName()).delete();
		}
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
