package com.biyouche.rabbitmq.model;

/**
 * rabbitMQ操作类型基类
 * @author lgh
 *
 */
public class MQBaseModel {

	protected String operation;
	
	public MQBaseModel() {
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
