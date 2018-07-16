package com.biyouche.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biyouche.config.PropertiesConfig;
import com.biyouche.exception.BussinessException;
import com.biyouche.response.ResponseObject;
import com.biyouche.utils.ValidatorUtils;

/**
 * 控制器基类
 * @author lgh
 *
 */
public class BaseController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	protected static ResponseObject dealSuccess() {
		
		return dealSuccess(null);
		
	}
	
	protected static ResponseObject dealSuccess(Map<String, Object> content) {
		
		ResponseObject response = new ResponseObject();
		
		Map<String, Object> info = new LinkedHashMap<String, Object>();
		
		String code = "100000";
		info.put("code", code);
		info.put("msg", PropertiesConfig.getProperties("message.properties", code));
		
		if( ValidatorUtils.isEmpty(content) ) {
			content = new LinkedHashMap<String, Object>();
			
		}
		response.setContent(content);
		response.setInfo(info);
		
		return response;
		
	}
	
	protected static ResponseObject dealException(Exception e) {
		
		ResponseObject response = new ResponseObject();
		
		Map<String, Object> info = new LinkedHashMap<String, Object>();
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		response.setContent(content);
		
		if( e instanceof BussinessException) {
			
			LOGGER.error("BussinessException: >" + e.getMessage());
			e.printStackTrace();
			
			String message = e.getMessage();
			
			if( ValidatorUtils.isInfoCode(message) ) {
				
				info.put("code", message);
				info.put("msg", PropertiesConfig.getProperties("message.properties", message));
			}else {
				String code = "100001";
				info.put("code", code);
				info.put("msg", PropertiesConfig.getProperties("message.properties", code));
			}
			
			response.setInfo(info);
			
		}else if( e instanceof RuntimeException ) {
			LOGGER.error("RuntimeException: >" + e.getMessage());
			e.printStackTrace();
			String code = "100002";
			info.put("code", code);
			info.put("msg", PropertiesConfig.getProperties("message.properties", code));
			response.setInfo(info);
			
		}else if( e instanceof Exception) {
			
			LOGGER.error("Exception: >" + e.getMessage());
			e.printStackTrace();
			String code = "100002";
			info.put("code", code);
			info.put("msg", PropertiesConfig.getProperties("message.properties", code));
			response.setInfo(info);
		}
		return response;
		
	}
	
	public static void main(String[] args) throws IOException {
		try {
			throw new RuntimeException("aaa");
		} catch (Exception e) {
			
			System.out.println(JSON.toJSON(dealException(e)));
		}
		
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("stu", "student");
		content.put("sex", "male");
		System.out.println(JSON.toJSON(dealSuccess(content)));
		
		
		System.out.println(JSON.toJSON(dealSuccess()));
	
	}
	
}
