package com.biyouche.dao.user;

import com.biyouche.domain.user.User;

import com.biyouche.domain.user.UserToken;
import org.apache.ibatis.annotations.*;
import com.biyouche.domain.user.UserBlack;

import java.util.List;

public interface UserMapper {

    /**
     * 通过登录手机号查询用户信息
     * @param loginMobile
     * @return
     */
    @Select("select user_id,login_mobile,user_password,avatar_url,user_level,user_qrcode,login_lock,nick_name from user where login_mobile=#{loginMobile}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "loginMobile", column = "login_mobile"),
            @Result(property = "userPassword",column = "user_password"),
            @Result(property = "avatarUrl",column = "avatar_url"),
            @Result(property = "userLevel",column = "user_level"),
            @Result(property = "userQrcode",column = "user_qrcode"),
            @Result(property = "loginLock",column = "login_lock"),
            @Result(property = "nickName",column = "nick_name")
    })
    User selectByloginMobile(String loginMobile);

    /**
     * 通过用户id查黑名单
     * @param userId
     * @return
     */
    @Select("select count(0) from user_black where user_id = #{userId}")
    int checkUserBlackByuserId(Integer userId);


    /**
     * 注册插入用户
     * @param user
     * @return
     */
    @Insert("insert into user(login_mobile,user_password,recommend_id,recommend_mobile,user_qrcode,create_time,last_login_time,nick_name,avatar_url,device_type)"
    		+ "values(#{loginMobile},#{userPassword},#{recommendId},#{recommendMobile},#{userQrcode},UNIX_TIMESTAMP(),UNIX_TIMESTAMP(),#{nickName},#{avatarUrl},#{deviceType})")
    @Results({
        @Result(property = "loginMobile", column = "login_mobile"),
        @Result(property = "userPassword",column = "user_password"),
        @Result(property = "recommendId",column = "recommend_id"),
        @Result(property = "recommendMobile",column = "recommend_mobile"),
        @Result(property = "userQrcode",column = "user_qrcode"),
        @Result(property = "nickName",column = "nick_name"),
        @Result(property = "avatarUrl",column = "avatar_url"),
        @Result(property = "deviceType",column = "device_type")
    })
    int register(User user);
    
    
    /**
     * 通过电话号码查询用户数量
     * @param mobile
     * @return
     */
    @Select("select count(0) from user where login_mobile = #{mobile}")
    int selectNumByMobile(String mobile);
}
