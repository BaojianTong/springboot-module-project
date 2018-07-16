package com.biyouche.config;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.biyouche.constants.Constants;
import com.biyouche.dao.CfgParamsMapper;
import com.biyouche.redis.enums.RedisKeyEnum;
import com.biyouche.redis.utils.RedisTempleteUtils;
import com.biyouche.springboot.SpringContext;
import com.biyouche.utils.ValidatorUtils;

/**
 * 资源读取工具类
 * @author lgh
 *
 */
public class PropertiesConfig {

	private final static Map<String, ResourceBundle> PROPERTY_POOL = new HashMap<String, ResourceBundle>();
	public  static CfgParamsMapper paramsDao = null;

	
	public static final String getProperties(String fileName,String key){
		try{
			int index = fileName.indexOf(".") ;
			if(index>0){
				fileName = fileName.substring(0,index > 0 ? index : fileName.length());
				if(!PROPERTY_POOL.containsKey(fileName)){
					PROPERTY_POOL.put(fileName,ResourceBundle.getBundle(fileName));
				}			
				return PROPERTY_POOL.get(fileName).getString(key);
			}else{
				
				String value = RedisTempleteUtils.getMapField(RedisKeyEnum.CONFIG.KEY, fileName+"_"+key, String.class);
				
				if(ValidatorUtils.isNotNull(value)){
					return value;
				}
				
				if(paramsDao==null){
					paramsDao = (CfgParamsMapper)SpringContext.getBean("paramsDao");
				}
				value = paramsDao.getValue(fileName,key);
				RedisTempleteUtils.addMap(RedisKeyEnum.CONFIG.KEY, fileName+"_"+key, value);
				
				return value;
			}			
		}catch(Exception e){
			return Constants.UNKNOWN ;
		}		
	}
	
	public static final ResourceBundle getProperties(String fileName){
		try{
			int index = fileName.indexOf(".") ;
			fileName = fileName.substring(0,index > 0 ? index : fileName.length());
			if(!PROPERTY_POOL.containsKey(fileName)){
				PROPERTY_POOL.put(fileName,ResourceBundle.getBundle(fileName));
			}
			return PROPERTY_POOL.get(fileName);
		}catch(Exception e){
			return null ;
		}
	}

	public final static void reload() {
		clear();
	}
	
	public final static void clear() {
		PROPERTY_POOL.clear();
	}
}
