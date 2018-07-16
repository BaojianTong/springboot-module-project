package com.biyouche.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biyouche.controller.BaseController;
import com.biyouche.response.ResponseObject;
import com.biyouche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * @param content 登录请求数据
     * @return
     * @autor pht
     */
    @PostMapping("/login")
    public String login(@RequestBody String content) {
    	LOGGER.info("登录请求参数内容:" + content);
        //解析
        userService.login(content);
        return "10000";
    }

    /**
     * 注册接口
     *@return hucong
     *@param content
     *@return code
     */
    @PostMapping("/register")
    public ResponseObject register(@RequestBody String content) {
    	LOGGER.info("注册地请求参数内容:" + content);
    	try {
    		 userService.register(content);
    	        return dealSuccess();
    	}catch (Exception e) {
			return dealException(e);
		}
       
    }
    /**
     * 忘记密码
     * 
     * @author lgh
     * @param phone
     * @param password
     * @param validCode
     * @return
     */
    @PostMapping("/forgetPassword")
    public ResponseObject forgetPassword(String phone, String password, String validCode) {
    	LOGGER.info("忘记密码请求参数内容: phone = " + phone + ", password = " + password + ",validCode = " + validCode);
        try {
        	
        	//TODO
        	
			return dealSuccess();
		} catch (Exception e) {
			return dealException(e);
		}
        
    }
    

}
