package com.biyouche.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ消息提供者
 * @author lgh
 *
 */
@Component
public class RabbitMQProducer {
	private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public void sendTo(String routingkey, String message) {
		log.info("Sending> ...");
        this.rabbitTemplate.convertAndSend(routingkey,message);
        
	}
}
