package com.biyouche.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.biyouche.dao.BlogTypeMapper;
import com.biyouche.domain.BlogType;
import com.biyouche.rabbitmq.producer.RabbitMQProducer;
import com.biyouche.redis.annotations.Cacheable;
import com.biyouche.redis.enums.ExpireTime;
import com.biyouche.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService  {

	private static final Logger log  = LoggerFactory.getLogger(BlogServiceImpl.class);
	
	@Autowired
    private BlogTypeMapper blogTypeMapper;
	
	@Value("${myqueue}")
	String queue;

	@Bean
	Queue queue(){
	    return new Queue(queue,false);
	}

	@Autowired
	RabbitMQProducer producer;
	
	public void sendMsg(String msg){
        producer.sendTo(queue,  msg+" at " + new Date());
    }

	@Cacheable(key = "queryBlogType", expire = ExpireTime.FIVE_MIN)
	public List<BlogType> queryBlogType() {
		sendMsg("rabbitmq test > .....");
		return blogTypeMapper.getAll();
	}
	
	@RabbitListener(queues="${myqueue}")
	public void handler(String message) {
		log.info("Consumer> " + message);
	}

}
