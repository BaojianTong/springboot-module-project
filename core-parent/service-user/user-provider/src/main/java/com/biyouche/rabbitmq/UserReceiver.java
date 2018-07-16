package com.biyouche.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.biyouche.rabbitmq.model.MQLogModel;

/**
 * rabbit用户消息处理
 * @author lgh
 *
 */
@Component
@RabbitListener(queues = "user", containerFactory = "containerFactory")
public class UserReceiver {

	private static final Logger LOGGER  = LoggerFactory.getLogger(UserReceiver.class);
	
	@RabbitHandler
	public void handler(MQLogModel model) {
		LOGGER.info("Consumer> " + model.toString());
	}
	
}
