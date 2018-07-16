package com.biyouche.dao.user;

import com.biyouche.domain.user.UserToken;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserTokenMapper {

    /**
     * 通过用户id与设备类型查询登录token
     * @param userId
     * @param deviceKey
     * @return
     */
    @Select("select access_id from user_token where user_id=#{userId} and device_type=#{deviceKey}")
    List<UserToken> selectLoginToken(@Param("userId") Integer userId, @Param("deviceKey") int deviceKey);

    /**
     * 通过用户id与设备类型删除登录token
     * @param userId
     * @param deviceKey
     */
    @Delete("delete from user_token where user_id=#{userId} and device_type=#{deviceKey}")
    void deleteUserToken(@Param("userId") Integer userId, @Param("deviceKey") int deviceKey);

    @Insert("INSERT into user_token(access_id,access_key,data_transfer_key,user_id,device_type,login_time,last_action_time,expire_time) " +
            "VALUES (#{accessId},#{accessKey},#{dataTransferKey},#{userId},#{deviceType}," +
            "#{loginTime},#{lastActionTime},#{expireTime})")
    int insertUserToken(UserToken userToken);

}
