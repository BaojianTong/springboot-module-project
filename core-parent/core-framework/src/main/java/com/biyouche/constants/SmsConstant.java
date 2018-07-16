package com.biyouche.constants;

import com.biyouche.config.PropertiesConfig;

/**
 * 短信常量类
 * @author hucong
 *
 */
public class SmsConstant {
	
	/**短信请求参数常量*/
	public static final String REST_URL = PropertiesConfig.getProperties("sms", "REST_URL"); //短信请求url
	
    public static final String  PORT = PropertiesConfig.getProperties("sms", "SMS_PORT");//端口号
	
	public static final String ACCOUNT_SID = PropertiesConfig.getProperties("sms", "ACCOUNT_SID");//账号
	
	public static final String AUTH_TOKEN = PropertiesConfig.getProperties("sms", "AUTH_TOKEN");//令牌
	
	public static final String APP_ID = PropertiesConfig.getProperties("sms", "APP_ID");//应用id
	
	public static final String TIME = PropertiesConfig.getProperties("sms", "SEND_TIME");//时效
	
	/**短信参数常量*/
    public static final Integer TODAY_NUM_MAX = Integer.parseInt(PropertiesConfig.getProperties("sms", "TODAY_NUM_MAX"));//当天短信发送次数最大次数
	
	

}
