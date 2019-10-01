package com.springboot.application;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.application.config.RabbitConfig;

@EnableRabbit
@SpringBootApplication
public class FundooApplication implements RabbitListenerConfigurer{
	@Autowired
	private RabbitConfig rabbitconfig;

	public static void main(String[] args) {
		SpringApplication.run(FundooApplication.class,args);
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(rabbitconfig.messageHandlerMethodFactory());
	}

}
