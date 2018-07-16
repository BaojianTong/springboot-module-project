package com.biyouche.rabbitmq.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;  
import org.springframework.amqp.rabbit.connection.ConnectionFactory;  
import org.springframework.amqp.rabbit.core.RabbitTemplate;  
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;  
import org.springframework.amqp.support.converter.MessageConverter;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ配置类
 * @author lgh
 *
 */
@Configuration
public class RabbitConfig {

	@Bean  
    public MessageConverter messageConverter() {  
        return new Jackson2JsonMessageConverter();  
    }  
      
    @Bean  
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactory factory) {  
        factory.setMessageConverter(messageConverter());  
        return factory;  
    }  
  
    @Bean  
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory);  
        template.setMessageConverter(messageConverter());  
        return template;  
    }
	
}
