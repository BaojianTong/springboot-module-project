package com.biyouche.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.biyouche.domain.user.User;
import com.biyouche.mybatis.user.UserSqlProvider;

/**
 * mybatis高级应用示例
 * @author Administrator
 *
 */
public interface UserExampleMapper {

	/**
     * 使用注解指定某个工具类的方法来动态编写SQL.
     */
    @SelectProvider(type = UserSqlProvider.class, method = "listByUsername")
    List<User> listByUsername(String username);

 
    @SelectProvider(type = UserSqlProvider.class, method = "getBadUser")
    User getBadUser(@Param("username") String username, @Param("password") String password);


}
