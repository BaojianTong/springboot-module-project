package com.biyouche.redis.enums;

/**
 * redis key枚举类 
 * @author lgh
 *
 */
public enum RedisKeyEnum {

	RANDOM_4("random_4","4位随机数"),
	REFRESH("refresh","系统防刷"),
	LOGINIP_LIB("login_ip_lib","IP地址库"),
	ALARM("alarm","预警"),
	CONFIG("params_config","参数配置");

	
	public String KEY = "" ;
	public String REMARK = "" ;
	
	RedisKeyEnum(String key,String remark){
		KEY = key ;
		REMARK = remark ;
	}
	
}
