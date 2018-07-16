package com.biyouche.redis.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biyouche.springboot.SpringContext;
import com.biyouche.utils.ValidatorUtils;

/**
 * redis 工具类
 * @author lgh
 *
 */
public class RedisTempleteUtils {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RedisTempleteUtils.class);

	public static RedisTemplate<String, Object> redisTemplate = SpringContext.getBean("redisTemplate");  
    public static StringRedisTemplate stringRedisTemplate = SpringContext.getBean("stringRedisTemplate");  
  
//    private static String redisKeyPrefix = PropertiesUtil.getValueByKey(CacheUtils.class.getResource("/").getPath() + "config/redis.properties", "redis.keyPrefix");  
      
    private RedisTempleteUtils() {  
    }  
      
    /** 
     * 删除缓存<br> 
     * 根据key精确匹配删除 
     *  
     * @param key 
     */  
    public static void del(String... key) {  
        LOGGER.warn("delete cache, keys in ({})", merge(key));  
        for (String k : key) {  
            redisTemplate.delete(k);  
        }  
    }  
  
    /** 
     * 批量删除<br> 
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删） 
     *  
     * @param pattern 
     */  
    public static void batchDel(String... pattern) {  
        LOGGER.warn("batchDel cache, pattern in ({})", merge(pattern));  
        for (String kp : pattern) {  
            redisTemplate.delete(redisTemplate.keys(kp + "*"));  
        }  
    }  
  
    /** 
     * 取得缓存（int型） 
     *  
     * @param key 
     * @return 
     */  
    public static Integer getInt(String key) {  
        String value = stringRedisTemplate.boundValueOps(key).get();  
        if (ValidatorUtils.isNotEmpty(value)) {  
            return Integer.valueOf(value);  
        }  
        return 0;  
    }  
      
    /** 
     * 取得缓存（long型） 
     *  
     * @param key 
     * @return 
     */  
    public static Long getLong(String key) {  
        String value = stringRedisTemplate.boundValueOps(key).get();  
        if (ValidatorUtils.isNotEmpty(value)) {  
            return Long.valueOf(value);  
        }  
        return 0l;  
    }  
  
    /** 
     * 取得缓存（字符串类型） 
     *  
     * @param key 
     * @return 
     */  
    public static String getStr(String key) {  
        return stringRedisTemplate.boundValueOps(key).get();  
    }  
  
    /** 
     * 取得缓存（字符串类型） 
     *  
     * @param key 
     * @return 
     */  
    public static String getStr(String key, boolean retain) {  
        String value = stringRedisTemplate.boundValueOps(key).get();  
        if (!retain) {  
            stringRedisTemplate.delete(key);  
        }  
        return value;  
    }  
  
    /** 
     * 获取缓存<br> 
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值 
     *  
     * @param key 
     * @return 
     */  
    public static Object getObj(String key) {  
        return redisTemplate.boundValueOps(key).get();  
    }  
  
    /** 
     * 获取缓存<br> 
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值 
     *  
     * @param key 
     * @param retain 
     *            是否保留 
     * @return 
     */  
    public static Object getObj(String key, boolean retain) {  
        Object obj = redisTemplate.boundValueOps(key).get();  
        if (!retain && obj != null) {  
            redisTemplate.delete(key);  
        }  
        return obj;  
    }  
  
    /** 
     * 获取缓存<br> 
     * 注：慎用java基本数据类型进行转换（可能会出现空值，转换报错） 
     *  
     * @param key 
     *            key 
     * @param clazz 
     *            类型 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T get(String key, Class<T> clazz) {
        if (clazz.equals(String.class)) {  
            return (T) stringRedisTemplate.boundValueOps(key).get();  
        } else if (clazz.equals(Integer.class) || clazz.equals(Long.class)) {  
            return (T) stringRedisTemplate.boundValueOps(key).get();  
        } else if (clazz.equals(Double.class) || clazz.equals(Float.class)) {  
            return (T) stringRedisTemplate.boundValueOps(key).get();  
        } else if (clazz.equals(Short.class) || clazz.equals(Boolean.class)) {  
            return (T) stringRedisTemplate.boundValueOps(key).get();  
        }  
        return (T) redisTemplate.boundValueOps(key).get();  
    }  
  
      
    /** 
     * 将value对象写入缓存 
     *  
     * @param key 
     * @param value 
     * @param seconds 
     *            失效时间(秒) 
     */  
    public static void set(String key, Object value, long seconds) {  
        if (null == key || null == value) {  
            throw new RuntimeException("key or value must not null");  
        }
        if (value instanceof String) {  
            stringRedisTemplate.opsForValue().set(key, value.toString());  
        } else if (value instanceof Integer || value instanceof Long) {  
            stringRedisTemplate.opsForValue().set(key, value.toString());  
        } else if (value instanceof Double || value instanceof Float) {  
            stringRedisTemplate.opsForValue().set(key, value.toString());  
        } else if (value instanceof Short || value instanceof Boolean) {  
            stringRedisTemplate.opsForValue().set(key, value.toString());  
        } else {  
            redisTemplate.opsForValue().set(key, value);  
        }  
        if (seconds > 0) {  
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);  
        }  
    }  
  
  
    /** 
     * 更新key对象field的值 
     *  
     * @param key 
     *            缓存key 
     * @param field 
     *            缓存对象field 
     * @param value 
     *            缓存对象field值 
     */  
    public static void setJsonField(String key, String field, String value) {  
        JSONObject obj = JSON.parseObject(stringRedisTemplate.boundValueOps(key).get());  
        obj.put(field, value);  
        stringRedisTemplate.opsForValue().set(key, obj.toJSONString());  
    }  
  
    /** 
     * 递减操作 
     *  
     * @param key 
     * @param by 
     * @return 
     */  
    public static double decr(String key, double by) {  
        return redisTemplate.opsForValue().increment(key, -by);  
    }  
  
    /** 
     * 递增操作 
     *  
     * @param key 
     * @param by 
     * @return 
     */  
    public static double incr(String key, double by) {  
        return redisTemplate.opsForValue().increment(key, by);  
    }  
  
    /** 
     * 递减操作 
     *  
     * @param key 
     * @param by 
     * @return 
     */  
    public static long decr(String key, long by) {  
        return redisTemplate.opsForValue().increment(key, -by);  
    }  
  
    /** 
     * 递增操作 
     *  
     * @param key 
     * @param by 
     * @return 
     */  
    public static long incr(String key, long by) {  
        return redisTemplate.opsForValue().increment(key, by);  
    }  
  
    /** 
     * 获取double类型值 
     *  
     * @param key 
     * @return 
     */  
    public static double getDouble(String key) {  
        String value = stringRedisTemplate.boundValueOps(key).get();  
        if (ValidatorUtils.isNotEmpty(value)) {  
            return Double.valueOf(value);  
        }  
        return 0d;  
    }  
  
    /** 
     * 设置double类型值 
     *  
     * @param key 
     * @param value 
     * @param seconds 
     *            失效时间(秒) 
     */  
    public static void setDouble(String key, double value, long seconds) {  
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));  
        if (seconds > 0) {  
            stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);  
        }  
    }  
  
    /** 
     * 将map写入缓存 
     *  
     * @param key 
     * @param map 
     */  
    public static <T> void setMap(String key, Map<String, T> map) {  
        redisTemplate.opsForHash().putAll(key, map);  
    }  
  
  
    /** 
     * 向key对应的map中添加缓存对象 
     *  
     * @param key 
     * @param map 
     */  
    public static <T> void addMap(String key, Map<String, T> map) {  
        redisTemplate.opsForHash().putAll(key, map);  
    }  
  
    /** 
     * 向key对应的map中添加缓存对象 
     *  
     * @param key 
     *            cache对象key 
     * @param field 
     *            map对应的key 
     * @param value 
     *            值 
     */  
    public static void addMap(String key, String field, String value) {  
        redisTemplate.opsForHash().put(key, field, value);  
    }  
  
    /** 
     * 向key对应的map中添加缓存对象 
     *  
     * @param key 
     *            cache对象key 
     * @param field 
     *            map对应的key 
     * @param obj 
     *            对象 
     */  
    public static <T> void addMap(String key, String field, T obj) {  
        redisTemplate.opsForHash().put(key, field, obj);  
    }  
  
    /** 
     * 获取map缓存 
     *  
     * @param key 
     * @param clazz 
     * @return 
     */  
    public static <T> Map<String, T> mget(String key, Class<T> clazz) {  
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);      
        return boundHashOperations.entries();  
    }  
  
    /** 
     * 获取map缓存中的某个对象 
     *  
     * @param key 
     * @param field 
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T getMapField(String key, String field, Class<T> clazz) {  
        return (T) redisTemplate.boundHashOps(key).get(field);  
    }  
  
    /** 
     * 删除map中的某个对象 
     *  
     * @author lh 
     * @date 2016年8月10日 
     * @param key 
     *            map对应的key 
     * @param field 
     *            map中该对象的key 
     */  
    public static void delMapField(String key, String... field) {  
        redisTemplate.opsForHash().delete(key, field);  
    }  
  
    /** 
     * 为哈希表key中的域field的值 
     *  
     * @param key 
     *            键 
     * @param field 
     *            map域 
     * @param value 
     *            增量值 
     * @return 
     */  
    public static long hincr(String key, String field, long value) {  
        return redisTemplate.opsForHash().increment(key, field, value);  
    }  
      
    public static void hset(String key, String field, Object value){  
        redisTemplate.opsForHash().put(key, field, value);  
    }  
      
    public static Object hget(String key, String field){  
        return redisTemplate.boundHashOps(key).get(field);  
    }  
      
    public static void hdel(String key, String...fields){  
        if (fields == null || fields.length == 0) {  
            redisTemplate.delete(key);  
        }else{  
            redisTemplate.opsForHash().delete(key, fields);              
        }  
    }  
      
    public static Long hlen(String key){  
        return redisTemplate.boundHashOps(key).size();  
    }  
    @SuppressWarnings("unchecked")
	public static <T> Set<T> hkeys(String key){  
        return (Set<T>)redisTemplate.boundHashOps(key).keys();  
    }  
    @SuppressWarnings("unchecked")
	public static <T> List<T> hvals(String key){  
        return (List<T>)redisTemplate.boundHashOps(key).values();  
    }  
  
    /** 
     *  
     * @param key 
     * @param value 
     * @param seconds 
     * @return 
     */  
    public static boolean setnx(String key, String value, long seconds) {  
        boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, value);  
        if (seconds > 0) {  
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);  
        }  
        return flag;  
    }  
  
    /** 
     * 指定缓存的失效时间 
     *  
     * @author FangJun 
     * @date 2016年8月14日 
     * @param key 
     *            缓存KEY 
     * @param seconds 
     *            失效时间(秒) 
     */  
    public static void expire(String key, long seconds) {  
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);  
    }  
  
    /** 
     * 指定缓存的失效时间 
     *  
     * @author FangJun 
     * @date 2016年8月14日 
     * @param key 
     *            缓存KEY 
     * @param seconds 
     *            失效时间(秒) 
     */  
    public static void expire(String key, int seconds) {  
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);  
    }  
  
    /** 
     * 添加set 
     *  
     * @param key 
     * @param value 
     */  
    public static void sadd(String key, String... value) {  
        redisTemplate.boundSetOps(key).add(value);  
    }  
  
    /** 
     * 删除set集合中的对象 
     *  
     * @param key 
     * @param value 
     */  
    public static void srem(String key, String... value) {  
        redisTemplate.boundSetOps(key).remove(value);  
    }  
  
  
    /** 
     * 判断key对应的缓存是否存在 
     *  
     * @param key 
     * @return 
     */  
    public static boolean exists(String key) {  
        return redisTemplate.hasKey(key);  
    }  
      
     /**  
     * 模糊查询keys  
     * @param pattern  
     * @return  
     */    
    public static Set<String> keys(String pattern){    
        return redisTemplate.keys(pattern);     
    }    
  
    /** 
     * 合并数组为字符串 
     * @param strings 
     * @return 
     */  
    private static String merge(String...strings){  
        if (strings == null || strings.length == 0) {  
            return null;  
        }  
        StringBuffer sb = new StringBuffer();  
        int len = strings.length;  
        for (int i = 0; i < len; i++) {  
            sb.append(strings[i]);  
            if(len != i+1){  
                sb.append(",");  
            }  
        }  
        return sb.toString();  
    }
	
}
