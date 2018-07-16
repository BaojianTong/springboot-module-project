package com.biyouche.rabbitmq.model;

import java.util.Date;

/**
 * rabbitMQ操作类型日志类
 * @author lgh
 *
 */
public class MQLogModel extends MQBaseModel {

	private String information;
	
	private Date logTime;

	public MQLogModel() {
		
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	@Override
	public String toString() {
		return "MQLogModel [information=" + information + ", logTime=" + logTime + "]";
	}
	
	
}
