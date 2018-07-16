package com.biyouche.response;

import java.util.Map;

/**
 * 自定义返回实体类
 * @author lgh
 *
 */
public class ResponseObject {

	public Map<String, Object> content = null;
	
	public Map<String, Object> info = null;

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
	
	
	
}
