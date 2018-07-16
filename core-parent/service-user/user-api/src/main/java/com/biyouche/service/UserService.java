package com.biyouche.service;

import java.util.Map;

import com.biyouche.exception.EduException;

public interface UserService {


    void login(String content);
    
    /**
     * 注册
     * @author hucong
     * @param content
     */
    void register(String content) ;
    
    /**
     * 获取验证码
     * @author hucong
     * @param params
     */
    void send(String content)throws Exception;
}
