package com.biyouche.service.impl;

import java.util.Date;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.biyouche.config.PropertiesConfig;
import com.biyouche.constants.SmsConstant;
import com.alibaba.fastjson.JSONObject;
import com.biyouche.constants.ConfigConstant;
import com.biyouche.constants.UserConstant;
import com.biyouche.dao.user.SmsMapper;
import com.biyouche.dao.user.UserMapper;
import com.biyouche.dao.user.UserTokenMapper;
import com.biyouche.domain.user.SmsCode;
import com.biyouche.domain.user.User;
import com.biyouche.domain.user.UserToken;
import com.biyouche.enums.SmsEnue;
import com.biyouche.exception.BussinessException;
import com.biyouche.exception.EduException;
import com.biyouche.message.SmsUtil;
import com.biyouche.utils.CommonUtils;
import com.biyouche.utils.DesUtils;
import com.biyouche.utils.TimeUtils;
import com.biyouche.utils.ValidatorUtils;
import com.biyouche.exception.BussinessException;
import com.biyouche.redis.utils.RedisTempleteUtils;
import com.biyouche.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.biyouche.dao.BlogTypeMapper;
import com.biyouche.domain.BlogType;
import com.biyouche.rabbitmq.producer.RabbitMQProducer;
import com.biyouche.redis.annotations.Cacheable;
import com.biyouche.redis.enums.ExpireTime;
import com.biyouche.service.UserService;
import com.biyouche.utils.CommonUtils;


@Service("userService")
@SuppressWarnings({ "unused" })
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private BlogTypeMapper blogTypeMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private SmsMapper smsMapper;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Value("${userqueue}")
    String queue;

    @Bean
    Queue queue() {
        return new Queue(queue,false);
    }

    @Autowired
    RabbitMQProducer producer;

    public void sendMsg(String msg) {
        producer.sendTo(queue, msg + " at " + new Date());
    }

    @Cacheable(key = "queryBlogType", expire = ExpireTime.FIVE_MIN)
    public List<BlogType> queryBlogType() {
        sendMsg("rabbitmq test > .....");
        return blogTypeMapper.getAll();
    }

    @RabbitListener(queues = "${userqueue}")
    public void handler(String message) {
        LOGGER.info("Consumer> " + message);
    }


    /**
     * 登录操作
     *
     * @param content
     * @autor pht
     */
    @SuppressWarnings("unchecked")
	@Cacheable(key = "login", expire = ExpireTime.FIVE_MIN)
    public void login(String content) {
        sendMsg("rabbitmq 登录操作");
        //解析客户端传递的json
        Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
        if (ValidatorUtils.isEmpty(contentMap)) {
            throw new BussinessException("请求参数解析失败");
        }

        String contentString = contentMap.get("content") + "";
        if (ValidatorUtils.isNull(contentString)) {
            throw new BussinessException("请求参数为空");
        }
        //content包含的数据
        Map<String, Object> loginMap = JSON.parseObject(contentString, Map.class);
        if (ValidatorUtils.isEmpty(loginMap)) {
            throw new BussinessException("请求参数为空");
        }
        //获取登录手机号
        String userMobile = loginMap.get("user_mobile") + "";
        //校验手机号
        ValidatorUtils.isLoginMobile(userMobile);
        //获取密码(客户端加密过的密码)
        String userPassword = loginMap.get("user_password") + "";
        //解密
        try {
            //deskey  与客户端约定的密钥
            userPassword = DesUtils.decrypt(userPassword, "ABCDEFGH");
        } catch (Exception e) {
            throw new BussinessException("密码解密失败");
        }
        //密码校验
        if (!ValidatorUtils.isPassword(userPassword)) {
            throw new BussinessException("密码格式无效");
        }
        //通过用户名查询数据库
        User user = userMapper.selectByloginMobile(userMobile);

        //登录校验
        checkLogin(user, userPassword);

        LOGGER.info("用户校验成功,登录成功:" + content);

        //生成令牌信息
        //通行证(令牌)id
        String accessId = CommonUtils.getRandom(userMobile);
        //保存数据key
        String dataKey = CommonUtils.getDesKey();
        //令牌key
        String accessKey = CommonUtils.encodeBase64String(CommonUtils.getRandom(dataKey));
        //设备类型
        int devicePort = Integer.parseInt(loginMap.get("devicePort") + "");
        DeviceTypeEnum enums = DeviceTypeEnum.getEnumType(devicePort);
        //登陆时间
        int loginTime = Integer.parseInt(TimeUtils.getCurrentTimeStamp() + "");
        //将令牌token存入数据库与redis中
        addToken(accessId, accessKey, dataKey, user.getUserId(), enums, loginTime);
        //添加登录日志
        addLoginLog(user,enums,loginTime);
    }

    /**
     * 添加登录日志
     * @param user
     * @param enums
     * @param loginTime
     */
    private void addLoginLog(User user, DeviceTypeEnum enums, int loginTime) {

    }

    /**
     * 生成令牌信息
     *
     * @param accessId  令牌id
     * @param accessKey 令牌key
     * @param dataKey   数据key
     * @param userId    用户id
     * @param enums     登录设备
     * @param loginTime 登陆时间
     */
    private void addToken(String accessId, String accessKey, String dataKey, Integer userId, DeviceTypeEnum enums, Integer loginTime) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("userId", userId + "");
        //先执行登出的动作
        if (DeviceTypeEnum.isApp(enums.KEY)) {
            params.put("devicePort", DeviceTypeEnum.ANDROID.KEY + "");
            logout(params);
            params.put("devicePort", DeviceTypeEnum.IOS.KEY + "");
            logout(params);
        } else {
            params.put("devicePort", enums.KEY + "");
            logout(params);
        }
        //重新添加登录信息
        UserToken userToken = new UserToken();
        userToken.setAccessId(accessId);
        userToken.setAccessKey(accessKey);
        userToken.setDataTransferKey(dataKey);
        userToken.setUserId(userId);
        userToken.setDeviceType(enums.KEY);
        userToken.setLoginTime(loginTime);
        userToken.setLastActionTime(loginTime);
        userToken.setExpireTime(loginTime + ConfigConstant.getInstance().LOGIN_EXPIRE_MINUTE * 60);
        //添加到数据库
        int t = userTokenMapper.insertUserToken(userToken);
        if (t != 1) {
            throw new BussinessException("登录异常");
        }
        //添加到redis队列中，并设置过期时间
        LinkedHashMap<String, Object> tokenMap = new LinkedHashMap<String, Object>();
        tokenMap.put("share_id",accessId);
        tokenMap.put("access_key",accessKey);
        tokenMap.put("data_transfer_key",dataKey);
        tokenMap.put("user_id",userId);
        tokenMap.put("last_action_time",loginTime);
        RedisTempleteUtils.set(accessId,CommonUtils.encodeUserToken(new JSONObject(tokenMap).toString()), ConfigConstant.getInstance().LOGIN_EXPIRE_MINUTE * 60);
    }

    private void logout(Map<String, String> params) {
        Integer userId = Integer.valueOf(params.get("userId"));
        int deviceKey = Integer.parseInt(params.get("devicePort"));
        //查询已存在令牌(通过用户id 和设备类型)
        List<UserToken> userTokens = userTokenMapper.selectLoginToken(userId, deviceKey);
        //不为空就删除
        if (!ValidatorUtils.isEmpty(userTokens)){
            for (UserToken userToken : userTokens) {
                //redis中删除令牌信息
                RedisTempleteUtils.del(userToken.getAccessId());
            }
        }
        //数据库存在数据并删除
        userTokenMapper.deleteUserToken(userId,deviceKey);
    }

    /**
     * 检查登录
     * @param user
     * @param userPassword
     */
    private void checkLogin(User user, String userPassword) {
        if (ValidatorUtils.isNull(user) || user.getUserId() == null) {
            throw new BussinessException("用户不存在");
        }
        //校验密码
        if (!CommonUtils.encodeLoginPwd(userPassword).equals(user.getUserPassword())) {
            //密码不正确
            throw new BussinessException("用户名或密码不正确");
        }
        //登录状态判断
        if (user.getLoginLock() == UserConstant.LOGIN_LOCK_YES) {
            throw new BussinessException("用户已被锁定");
        }
        if (user.getLoginLock() == UserConstant.LOGIN_LOCK_DELETED) {
            throw new BussinessException("用户已被注销");
        }
        //查询用户是否存在于黑名单
        int count = userMapper.checkUserBlackByuserId(user.getUserId());
        if (count > 0) {
            throw new BussinessException("用户已被加入黑名单");
        }

    }

    /**
     * 注册
     *
     * @param content
     * @autor hucong
     */
    @SuppressWarnings("rawtypes")
    public void register(String content) {
        Map map = JSON.parseObject(content, Map.class);
        User user = new User();
        LOGGER.info("注册请求参数" + map);
        String code = map.get("code") + "";
        String recommecd_mobile = map.get("recommecd_mobile") + "";
        String user_mobile = map.get("user_mobile") + "";
        String pwd = map.get("pwd") + "";
        String device_type = map.get("device_type") + "";
        //更新验证码
        int recommendId = 0;
        //检验推荐人
        user.setLoginMobile(user_mobile);
        user.setUserPassword(CommonUtils.encodeLoginPwd(pwd));//需要加密
        user.setRecommendMobile(recommecd_mobile);//需要校验
        User user2 = userMapper.selectByloginMobile(recommecd_mobile);
        if(user2!=null) {
        	recommendId = user2.getUserId();
        }
        user.setRecommendId(recommendId + "");
        user.setNickName(CommonUtils.getNickName());//生成用户初始化昵称
        user.setDeviceType(Byte.valueOf(device_type));
        user.setAvatarUrl("");
        user.setUserQrcode("");
        LOGGER.info("用户参数" + user);
        int i = userMapper.register(user);
        LOGGER.info("插入数据库返回状态" + i);

    }

	/* 
	 * 验证码发送
	 * @param params
	 * @autor hucong
	 */
	public void send(String content) throws BussinessException {
		@SuppressWarnings("unchecked")
		Map<String, String> params = JSON.parseObject(content, Map.class);
		//获取参数 用户手机号  验证码类型
    	String device_type = params.get("device_type");
		String type = params.get("type");
		String user_mobile = params.get("user_mobile");
		String code =(int)((Math.random()*9+1)*1000)+"";
		Integer sms_type = 0;
		String type_code = "";//短信验证码发送类型编码
		if(type.equals(SmsEnue.CODE_05+"")) {
			//注册的话先判断用户是否存在
			int i = userMapper.selectNumByMobile(user_mobile);
			if(i>0) {
				throw new BussinessException(200000);
			}
			sms_type = 0;//注册验证码
			type_code = SmsEnue.CODE_05.getCode();
		}
		else if(type.equals(SmsEnue.CODE_07+"")) {
			sms_type = 1;//修改密码
			type_code = SmsEnue.CODE_07.getCode();
		}
		else {
			sms_type = 2;//验证码
			type_code = SmsEnue.CODE_06.getCode();
		}
		//生成传参对象
		SmsCode coder = new SmsCode();
		coder.setUserMobile(user_mobile);
		coder.setSmsCode(code);
		coder.setSmsType(sms_type);
		coder.setSendNums(1);
		coder.setDeviceType(Integer.parseInt(device_type));
		coder.setReqIp("");
		coder.setCreateTime(TimeUtils.getTimeStampByDate(TimeUtils.getSysdateTimeStart(), TimeUtils.yyyyMMddHHmmss));
		//判定当日是否超过次数限制
		Integer sendNum = smsMapper.sendNumToday(coder);
		if(sendNum!=null) {
			if(sendNum>SmsConstant.TODAY_NUM_MAX) {
				throw new BussinessException(200001);
			}
		}

		//短信消息不存在，新增发送记录
		int insert = smsMapper.insert(coder);
		int smsId = coder.getSmsId();
		//更新验证码
		if(SmsUtil.send(user_mobile,type_code, code)) {
			smsMapper.updateSendTime(smsId);
		}
	}
}
