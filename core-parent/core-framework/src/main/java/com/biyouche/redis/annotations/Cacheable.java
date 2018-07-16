package com.biyouche.redis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.biyouche.redis.enums.ExpireTime;

/**
 * redis自定义回设缓存
 * @author lgh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheable {

	public String key() default "";
	
	public ExpireTime expire() default ExpireTime.NONE;
	
}
