package com.springboot.application.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.springboot.application.config.UserToken;
import com.springboot.application.model.UserInfo;
import com.springboot.application.repositry.UserRepo;
import com.springboot.application.util.Utility;

@Component
public class MessageReciever {
	@Autowired
	private UserRepo repo;
	@Autowired
	private UserToken tokens;
	@Autowired
	private Utility util;

	public void messagerecieve(String queue) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			channel.queueDeclare(queue, true, false, false, null);
			DeliverCallback callback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				String message1 = message.substring(1, message.length() - 1);
				System.out.println("message" + message1);
				List<UserInfo> info = repo.sendemail(message1);
				System.out.println(info);
				for (UserInfo users : info) {
					System.out.println(users.getId());
					String token = tokens.tokengenerate(users.getId());
					System.out.println(token);
					String url = "http://localhost:8082/user/verify?token=" +token;
					util.mailsend(url,token,message1);

				}
			};
			channel.basicConsume(queue, true, callback, consumerTag -> {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
