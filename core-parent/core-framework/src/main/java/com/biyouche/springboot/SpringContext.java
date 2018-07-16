package com.biyouche.springboot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import com.biyouche.dao.CfgParamsMapper;
import com.biyouche.domain.CfgParams;
import com.biyouche.redis.enums.RedisKeyEnum;
import com.biyouche.redis.utils.RedisTempleteUtils;

/**
 * 启动配置初始化
 * @author lgh
 *
 */
public class SpringContext implements ApplicationContextAware, DisposableBean, ApplicationListener<ApplicationContextEvent> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SpringContext.class);
	
	private static ApplicationContext applicationContext = null;
	
	
	public SpringContext() {
		LOGGER.info("SpringContext initialize completion >");
	}
	
	/** 
     * 取得存储在静态变量中的ApplicationContext. 
     */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
        return applicationContext;  
    }
	
	/**
     * 实现ApplicationContextAware接口, 注入Context到静态变量. 
     */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		LOGGER.debug("注入ApplicationContext到SpringContext:{}", applicationContext);  
        if (SpringContext.applicationContext != null) {  
            LOGGER.info("SpringContext中的ApplicationContext被覆盖, 原有ApplicationContext为:{}" , SpringContext.applicationContext);  
        } 
		
		SpringContext.applicationContext = applicationContext;
		
	}
	
	/** 
     * 获取对象 
     *  
     * @param name 
     * @return Object
     * @throws BeansException 
     */  
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
    	assertContextInjected();
        return (T)getApplicationContext().getBean(name);
    }
    
    public static <T> T getBean(Class<T> classes){
    	assertContextInjected();
    	return getApplicationContext().getBean(classes);
    }
	
    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量. 
     */
	@Override
	public void destroy() throws Exception {
		LOGGER.debug("清除SpringContext的ApplicationContext:{}", applicationContext);
		
		SpringContext.applicationContext = null;
		
	}
	
	/**
	 * 检查applicationContext是否为空
	 */
	private static void assertContextInjected() {
        if(applicationContext == null) {  
            throw new InstantiationError("applicaitonContext属性未注入");  
        }  
    }

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		
		LOGGER.info("加载配置初始化 ... ...");
		
		CfgParamsMapper mapper = event.getApplicationContext().getBean(CfgParamsMapper.class);
		setConfigParamsCache(mapper);
		
		
		LOGGER.info("加载配置初始化完成 ... ...");
		
	}
	
	private void setConfigParamsCache(CfgParamsMapper mapper) {
		
		//预加载cfg_params表数据到缓存
		Map<String, String> params = new LinkedHashMap<String, String>();
		List<CfgParams> cfgParamsList = mapper.getValues();
		for(CfgParams param : cfgParamsList) {
			
			String cate = param.getPmCate();
			String key = param.getPmKey();
			String value = param.getPmValue();
			
			params.put(cate +"_"+ key , value);
		}
		
		RedisTempleteUtils.addMap(RedisKeyEnum.CONFIG.KEY, params);
		LOGGER.info("params_config 参数配置已加载进缓存......");
	}

	
}
