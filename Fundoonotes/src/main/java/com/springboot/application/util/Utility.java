package com.springboot.application.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	@Autowired
	private static JavaMailSender mailsender;
	
	public static void mailsend(String url,String token)
	{	String email=null;
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("gauravpreet.98@gmail.com");
		message.setTo(email);
		message.setSubject("accept your token");
		message.setText(url+token);
		mailsender.send(message);
	}
}
