package com.biyouche.domain.user;

import java.io.Serializable;

/**
 * @author hucong
 * 
 * 验证码实体类
 *
 */
public class SmsCode implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 短信ID
	 */
	private Integer smsId;
	
	/**
	 * 手机号
	 */
	private String userMobile;
	
	/**
	 * 验证码
	 */
	private String smsCode;
	
	/**
	 * 验证码类型[0注册;1修改密码;]
	 */
	private Integer smsType;
	
	/**
	 * 发送次数
	 */
	private Integer sendNums;
	
	/**
	 * 使用状态[0未使用;1已使用;2主动废除]
	 */
	private Integer smsStatus;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 最后发送时间
	 */
	private Integer sendTime;
	
	/**
	 * 使用时间
	 */
	private Integer usedTime;
	
	/**
	 *过期时间 
	 */
	private Integer expireTime;
	
	/**
	 * 请求IP
	 */
	private String reqIp;
	
	/**
	 * 登录设备类型[0微信;1android;2ios]
	 */
	private Integer deviceType;
	
	/**
	 *渠道ID 
	 */
	private Integer channelId;

	public Integer getSmsId() {
		return smsId;
	}

	public void setSmsId(Integer smsId) {
		this.smsId = smsId;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public Integer getSmsType() {
		return smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public Integer getSendNums() {
		return sendNums;
	}

	public void setSendNums(Integer sendNums) {
		this.sendNums = sendNums;
	}

	public Integer getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
}
