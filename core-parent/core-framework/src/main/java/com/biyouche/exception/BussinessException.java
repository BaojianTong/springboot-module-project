package com.biyouche.exception;

/**
 * 自定义业务异常处理类
 * @author lgh
 *
 */
public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = -7707045733155933888L;

	public BussinessException() {
		super();
	}
	
	public BussinessException(int code) {
		super(String.valueOf(code));
	}
	
	public BussinessException(String message) {
		super(message);
	}

	public BussinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BussinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BussinessException(Throwable cause) {
		super(cause);
	}
	

}
