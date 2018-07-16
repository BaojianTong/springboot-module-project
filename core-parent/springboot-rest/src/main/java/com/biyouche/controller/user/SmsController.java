package com.biyouche.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biyouche.config.PropertiesConfig;
import com.biyouche.controller.BaseController;
import com.biyouche.exception.BussinessException;
import com.biyouche.exception.EduException;
import com.biyouche.response.ResponseObject;
import com.biyouche.service.UserService;

/**
 * @author hucong
 *
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private UserService userService;

	 /**
	 * 获取验证码
	 * @author hucong
	 * @param type
	 * @return code
	 * @throws EduException 
	 */
	@PostMapping("/getCode")
	public ResponseObject getCode(@RequestBody String content) throws Exception {
		
		LOGGER.info("获取验证码请求参数:" + content);
    	try {
    		
    		    userService.send(content);
    	        return dealSuccess();
    	        
    	}catch (Exception e) {
    		System.out.println(e.getClass());
			return dealException(e);
		}
	 }
	
	
}
