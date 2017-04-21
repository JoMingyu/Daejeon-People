package com.planb.support.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	private static final String SMTP_HOST = "smtp.naver.com";
	private static final String SENDER_ID = "city7312";
	private static final String SENDER_PW = "uursty199";
	private static final int PORT = 587;
	
	private static Properties props;
	private static Session session;
	private static Message mimeMessage;
	
	static {
		props = System.getProperties();
		
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smpt.port", PORT);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", SMTP_HOST);
		
		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_ID, SENDER_PW);
			}
		});
		
		mimeMessage = new MimeMessage(session);
	}
	
	public static void sendMail(String recipient, String subject, String body) {
		try {
			mimeMessage.setFrom(new InternetAddress(SENDER_ID + "@naver.com"));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(body);
			
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
