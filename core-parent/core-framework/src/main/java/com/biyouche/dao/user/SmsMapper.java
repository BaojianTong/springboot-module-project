package com.biyouche.dao.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biyouche.domain.user.SmsCode;

/**
 * @author hucong
 *
 */
public interface SmsMapper {
	
	
	@Insert("insert into sms_code(user_mobile,sms_code,sms_type,send_nums,create_time,reqip,device_type) values("
			+ "#{userMobile},#{smsCode},#{smsType},#{sendNums},UNIX_TIMESTAMP(),#{reqIp},#{deviceType})")
	@Options(useGeneratedKeys=true, keyProperty="smsId", keyColumn="sms_id") 
	int insert(SmsCode code);

	/**
	 * 根据主键查更新验证码状态
	 * @return
	 */
	@Update("update sms_code set send_time=UNIX_TIMESTAMP() where sms_id=#{smsId}")
	int updateSendTime(Integer smsId);
	
	/**
	 * 当天发送次数
	 * @return
	 */
	@Select("select sum(send_nums) as num from sms_code where user_mobile=#{userMobile} and sms_type=#{smsType} and create_time>=#{createTime} limit 1")
	Integer sendNumToday(SmsCode code);
	
}
