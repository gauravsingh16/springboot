package com.springboot.application.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.springboot.application.model.RabbitDetails;

@Configuration
public class RabbitConfig {
	@Autowired
	private RabbitDetails rabbitdetails;
	

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
	DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
	factory.setMessageConverter(consumerJackson2MessageConverter());
	return factory;
}
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();

}
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	return new Jackson2JsonMessageConverter();
	}
	/* Creating a bean for the Message queue Exchange */

@Bean

public TopicExchange getApp1Exchange() {

return new TopicExchange(rabbitdetails.getApp1Exchange());

}

/* Creating a bean for the Message queue */

@Bean

public Queue getApp1Queue() {

return new Queue(rabbitdetails.getApp1Queue());

}

/* Binding between Exchange and Queue using routing key */

@Bean

public Binding declareBindingApp1() {

return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange()).with(rabbitdetails.getApp1RoutingKey());

}

/* Creating a bean for the Message queue Exchange */

@Bean
public TopicExchange getApp2Exchange() {

return new TopicExchange(rabbitdetails.getApp2Exchange());

}

/* Creating a bean for the Message queue */

@Bean

public Queue getApp2Queue() {

return new Queue(rabbitdetails.getApp2Queue());

}

/* Binding between Exchange and Queue using routing key */

@Bean

public Binding declareBindingApp2() {

return BindingBuilder.bind(getApp2Queue()).to(getApp2Exchange()).with(rabbitdetails.getApp2RoutingKey());

}

/* Bean for rabbitTemplate */

@Bean

public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {

final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

return rabbitTemplate;

}
}