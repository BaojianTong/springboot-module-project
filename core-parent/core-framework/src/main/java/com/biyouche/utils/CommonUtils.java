package com.biyouche.utils;


import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.biyouche.config.PropertiesConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class CommonUtils {
	private static final Log log = LogFactory.getLog(CommonUtils.class);

	private static final String LOGIN_PWD = "@byc2018";
	
	private static final String NAME_START = "用户";

	private static SecureRandom secureRand = null;

	private static Random rand = null;

	private static String localhost = "";

	private static final String USER_TOKEN = PropertiesConfig.getProperties("token", "user_token");

	static {
		secureRand = new SecureRandom();
		rand = new Random(secureRand.nextLong());
	}


	/**
	 * 登录密码加密
	 * @param redisKey
	 * @return
	 */
	public static String encodeLoginPwd(String redisKey) {
		return MD5.md5(redisKey+LOGIN_PWD);
	}

	/**
	 * 获取初始化昵称
	 * @return
	 */
	public static String getNickName() {
		StringBuffer num = new StringBuffer();
		for(int j = 0; j< 10; j++){
			num.append(((int)(Math.random()*10)*1));
		}
		return NAME_START+num;
	}
	
	public static String getTraceInfo() {
		StringBuffer stringBuffer = new StringBuffer();
		StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
		stringBuffer.append("(");
		stringBuffer.append("className:").append(
				stackTraceElement.getClassName());
		stringBuffer.append(";fieldName:").append(
				stackTraceElement.getFileName());
		stringBuffer.append(";methodName:").append(
				stackTraceElement.getMethodName());
		stringBuffer.append(";lineNumber:").append(
				stackTraceElement.getLineNumber());
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	// 获得随机数
	public static int getRandom(int accuracy) {
		return (int) (Math.random() * accuracy);
	}
	//获取uuid
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}


	/**
	 * 生成令牌id
	 * @param value 关键字
	 * @return
	 */
	public static String getRandom(String value) {
		String str = MD5.md5(getUUID() + System.currentTimeMillis()
				+ getRandom(999999999) + rand.nextLong() + localhost + value);
		str = str.toLowerCase();

		return str;
	}

	/**
	 * 生成数据key
	 * @return
	 */
	public static String getDesKey() {
		String md5 = MD5.md5((new Date()).getTime() + "");
		return md5.substring(8, 16).toUpperCase();
	}

	public static String encodeBase64String(String data) {
			return Base64.encode(data.getBytes()).trim();
	}


	public static Object encodeUserToken(String redisKey) {
		return DesUtils.encrypt(redisKey, USER_TOKEN);
	}
}
