package com.biyouche.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biyouche.domain.BlogType;


public interface BlogTypeMapper {

	@Select("select * from blog_type")
	@Results({
		@Result(property = "id", column = "id" ),
		@Result(property = "btId", column = "bt_id"),
		@Result(property = "typeTxt", column = "type_txt"),
		@Result(property = "userId", column = "user_id"),
		@Result(property = "crtTime", column = "crt_time")
	})
	List<BlogType> getAll();
	
	@Select("select * from blog_type where bt_id=#{btId}")
	@Results({
		@Result(property = "id", column = "id" ),
		@Result(property = "btId", column = "bt_id"),
		@Result(property = "typeTxt", column = "type_txt"),
		@Result(property = "userId", column = "user_id"),
		@Result(property = "crtTime", column = "crt_time")
	})
	BlogType getOne(String btId);
	
	@Insert("insert into blog_type(bt_id, type_txt, user_id, crt_time) values(#{btId},#{typeTxt},#{userId},#{crtTime})")
	void insert(BlogType type);
	
	@Update("update blog_type set type_txt=#{typeTxt},user_id=#{userId},crt_time=#{crtTime} where bt_id=#{btId}")
	void update(BlogType type);
	
	@Delete("delete from blog_type where bt_id=#{btId}")
	void delete(String btId);
}
