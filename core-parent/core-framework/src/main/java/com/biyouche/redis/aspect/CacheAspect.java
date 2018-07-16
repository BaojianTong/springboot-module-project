package com.biyouche.redis.aspect;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.biyouche.redis.annotations.CacheEvict;
import com.biyouche.redis.annotations.Cacheable;
import com.biyouche.redis.prefix.RedisKeyPrefix;
import com.biyouche.utils.ValidatorUtils;

/**
 * redis缓存切面
 * @author lgh
 *
 */
@Aspect
@Component
public class CacheAspect {

	private final static char DOT = '.';
    private final static char SHARP = '#';
    private final static String PREFIX = RedisKeyPrefix.DEMOPREFIX;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Around("@annotation(cache)")
    public Object cacheable(final ProceedingJoinPoint pjp, Cacheable cache) throws Throwable {
  
		String key = getCacheKey(pjp, cache.key());
		key = PREFIX + key;

        ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
        Object value =  valueOper.get(key); // 从缓存获取数据
        if (value != null) {
            return value; // 如果有数据,则直接返回
        }
        
        value = pjp.proceed(); // 缓存,到后端查询数据
        if (cache.expire().getTime() <= 0) { // 如果没有设置过期时间,则无限期缓存
            valueOper.set(key, value);
        } else { // 否则设置缓存时间
            valueOper.set(key, value, cache.expire().getTime(), TimeUnit.SECONDS);
        }
        return value;
    }  
    
	
    @Around("@annotation(evict)")
    public Object cacheEvict(final ProceedingJoinPoint pjp, CacheEvict evict) throws Throwable {
        Object value = pjp.proceed(); // 执行方法
        String key = getCacheKey(pjp, evict.key());

        if (evict.key().equals(key)) {// 支持批量删除
            Set<String> keys = redisTemplate.keys(key.concat("*"));
            redisTemplate.delete(keys);
        }else{
            redisTemplate.delete(key);
        }
        return value;
    }  
    
    /**
     * 获取缓存的key对象
     *
     * @param pjp
     * @param key
     * @return
     * @throws Exception
     */
    private String getCacheKey(ProceedingJoinPoint pjp, String key) throws Exception {
    	int index = -1;
    	String prefix = "";
    	
    	index = key.indexOf(SHARP);
    	if( key.length() > 0 && index > -1 ) {
    		
    		if( index > 0 ) {
    			prefix = key.substring(0,index);
    			key = key.substring(index);
    		}
    		
    		// 去掉#
            key = key.substring(1);
            // 将key分割成属性和参数名，第一个“.”之前是参数名，之后是属性名称
            int dotIdx = key.indexOf(DOT);
            String argName = key;
            if (dotIdx > 0) {
                argName = key.substring(0, dotIdx);
                key = key.substring(dotIdx + 1); // 剩下的属性
            }

            // 取参数值
            Object argVal = getArg(pjp, argName);

            // 获取参数的属性值
            Object objectKey = argVal;
            if (dotIdx > 0) {
                objectKey = String.valueOf( getObjectKey(argVal, key) );
            }

            return prefix + String.valueOf( objectKey );
    		
    		
    	}else{ // 不是以#开头的就以其值作为参数key
    		return key;
    	}
    	
        /*// 以#开头
        if (key.length() > 0 && key.charAt(0) == SHARP) {
            // 去掉#
            key = key.substring(1);
            // 将key分割成属性和参数名，第一个“.”之前是参数名，之后是属性名称
            int dotIdx = key.indexOf(DOT);
            String argName = key;
            if (dotIdx > 0) {
                argName = key.substring(0, dotIdx);
                key = key.substring(dotIdx + 1); // 剩下的属性
            }

            // 取参数值
            Object argVal = getArg(pjp, argName);

            // 获取参数的属性值
            Object objectKey = argVal;
            if (dotIdx > 0) {
                objectKey = String.valueOf( getObjectKey(argVal, key) );
            }

            return String.valueOf( objectKey );

        } else { // 不是以#开头的就以其值作为参数key
            return key;
        }*/
    }
    
    /**
     * 获取参数对象
     *
     * @param pjp 连接点
     * @param parameterName 参数名称
     * @return
     */
    private Object getArg(ProceedingJoinPoint pjp, String parameterName) throws NoSuchMethodException {

        Method method = getAdvicedMethod(pjp);
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null) {
            int idx = 0;
            for (String name : parameterNames) {
                if (name.equals(parameterName)) {
                    return pjp.getArgs()[idx];
                }
                idx++;
            }
        }

        throw new IllegalArgumentException("no such parameter name: [" + parameterName + "] in method: " + method);
    }

    /**
     * 获取拦截的方法
     *
     * @param pjp 连接点
     * @return
     * @throws NoSuchMethodException
     */
    private Method getAdvicedMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("annotation can only use to method.");
        }
        msig = (MethodSignature) sig;
        Object target = pjp.getTarget();
        return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    }

    /**
     * 获取从object上获取key所对应的属性对象
     * 例如：key: country.province.city.town
     * 就相当于调用：object.getCountry().getProvince().getCity.getTown()
     *
     * @param object
     * @param key
     * @return
     * @throws Exception
     */
    private Object getObjectKey(Object object, String key) throws Exception {
        // 如果key已经是空了就直接返回
        if (ValidatorUtils.isEmpty(key)) {
            return object;
        }

        int dotIdx = key.indexOf(DOT);
        // 形如key=aa.bb**的情况
        if (dotIdx > 0) {
            // 取第一个属性值
            String propertyName = key.substring(0, dotIdx);
            // 取剩下的key
            key = key.substring(dotIdx + 1);

            Object property = getProperty(object, getterMethod(propertyName));
            return getObjectKey(property, key);
        } else { // key=aa
            return getProperty(object, getterMethod(key));
        }
    }

    /**
     * 获取name的getter方法名称
     *
     * @param name
     * @return
     */
    private String getterMethod(String name) {
        return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * 调用obj对象上的getterMethodName
     *
     * @param obj
     * @param getterMethodName
     * @return
     * @throws Exception
     */
    private Object getProperty(Object obj, String getterMethodName) throws Exception {
        return obj.getClass().getMethod(getterMethodName).invoke(obj);
    }
    
	
}
