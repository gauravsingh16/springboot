package com.springboot.application.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	@Autowired
	private JavaMailSender mailsender;
	
	public void mailsend(String url,String token,String email)
	{	
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("gauravpreet.98@gmail.com");
		message.setTo(email);
		message.setSubject("verify your account");
		message.setText(url);
		mailsender.send(message);
	}
}
