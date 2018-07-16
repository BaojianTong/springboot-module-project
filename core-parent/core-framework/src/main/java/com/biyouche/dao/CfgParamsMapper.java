package com.biyouche.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.biyouche.domain.CfgParams;

@Mapper
public interface CfgParamsMapper {
	
	@Select("select * from cfg_params")
	@Results({
		@Result(property = "pmCate", column = "pm_cate" ),
		@Result(property = "pmKey", column = "pm_key"),
		@Result(property = "pmValue", column = "pm_value"),
		@Result(property = "pmRemark", column = "pm_remark")
	})
	public List<CfgParams> getValues();
	
	@Select("select pm_value from cfg_params where pm_cate=#{pmCate} and pm_key=#{pmKey}")
	@Results({
		@Result(property = "pmCate", column = "pm_cate" ),
		@Result(property = "pmKey", column = "pm_key"),
		@Result(property = "pmValue", column = "pm_value"),
		@Result(property = "pmRemark", column = "pm_remark")
	})
	String getValue(@Param("pmCate") String pmCate, @Param("pmKey") String pmKey);
	
}
