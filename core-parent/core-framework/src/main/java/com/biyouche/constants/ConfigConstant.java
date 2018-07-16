package com.biyouche.constants;

import com.biyouche.config.PropertiesConfig;

import java.util.Random;

/**
 * 配置型常量
 * @author Administrator
 *
 */
public class ConfigConstant {
	
	private   String cate = "business";
	

	public static ConfigConstant getInstance(){
		return new ConfigConstant();
	}
	

	//短信验证码防刷次数
	public   int SMS_REFRESH_IP_NUMS=Integer.valueOf(PropertiesConfig.getProperties(cate, "SMS_REFRESH_IP_NUMS")); 
	
	//短信验证码当天发送最大限制
	public   int SMS_SEND_MAX=Integer.valueOf(PropertiesConfig.getProperties(cate, "SMS_SEND_MAX"));
	
	//短信验证码有效期
	public   int SMS_EXPIRE_TERM=Integer.valueOf(PropertiesConfig.getProperties(cate, "SMS_EXPIRE_TERM")); 
	
	
	//登录过期时间
	public   int LOGIN_EXPIRE_MINUTE = Integer.valueOf(PropertiesConfig.getProperties(cate, "LOGIN_EXPIRE_MINUTE"));	
	
	//借款额度
	public   int LOAN_LIMIT_MAX=Integer.valueOf(PropertiesConfig.getProperties(cate, "LOAN_LIMIT_MAX"));//最大额度
	public   int LOAN_LIMIT_DEFAULT=Integer.valueOf(PropertiesConfig.getProperties(cate, "LOAN_LIMIT_DEFAULT"));//默认额度
	public   int LOAN_LIMIT_MIN=Integer.valueOf(PropertiesConfig.getProperties(cate, "LOAN_LIMIT_MIN"));//最低借款额
	public   int LOAN_BASE=Integer.valueOf(PropertiesConfig.getProperties(cate, "LOAN_BASE"));//借款基数
	
	
	//借款时间
	public   int LOAN_TIME_MAX=Integer.valueOf(PropertiesConfig.getProperties(cate, "LOAN_TIME_MAX"));//最多天数
	public   int LOAN_TIME_MIN=Integer.valueOf(PropertiesConfig.getProperties(cate, "LOAN_TIME_MIN"));//最少天数
	
	//每天的服务费
	public   double INTEREST_RATE=Double.valueOf(PropertiesConfig.getProperties(cate, "INTEREST_RATE"));
	//每天展期服务费
	public double RENEWAL_INTEREST_RATE=Double.valueOf(PropertiesConfig.getProperties(cate, "RENEWAL_INTEREST_RATE"));//9
	public   double OVERDUE_ONE_DAY_RATE=Double.valueOf(PropertiesConfig.getProperties(cate, "OVERDUE_ONE_DAY_RATE"));
	public   double OVERDUE_OTHER_DAY_RATE=Double.valueOf(PropertiesConfig.getProperties(cate, "OVERDUE_OTHER_DAY_RATE"));
	public   double OVERDUE_AGENT_FEE=Double.valueOf(PropertiesConfig.getProperties(cate, "OVERDUE_AGENT_FEE"));
	
	
	
	//官方微信
	public   String WECHAT_OFFICIAL=PropertiesConfig.getProperties(cate, "WECHAT_OFFICIAL");//  1档递增
	
	//客服电话
	public   String CUSTOME_TELEPHONE=PropertiesConfig.getProperties(cate, "CUSTOME_TELEPHONE");//  1档递增


	

	

	//消息发送时间
	public   String MESSAGE_SEND_TIME = PropertiesConfig.getProperties(cate, "MESSAGE_SEND_TIME");
	//联系电话
	public   String PHONE_CONTACT = PropertiesConfig.getProperties(cate, "PHONE_CONTACT");
	//管理电话
	public   String PHONE_MANAGE = PropertiesConfig.getProperties(cate, "PHONE_MANAGE");
	
	public  String random(){
		Random random=new Random();
		int result=random.nextInt(10);
		if(result==0){
			result=1;
		}
		return (result/100.0)+"";
	}

	public  boolean TESTING = ("product".equals(PropertiesConfig.getProperties("config", "environment")))?false:true;


}
