package com.biyouche.constants;

/**
 * 用户常用常量
 * @author Administrator
 *
 */
public class UserConstant {

	/**代理商标志*/
	public static final int AGENT_STEP_NOT_APPLY=0; //未申请
	public static final int AGENT_STEP_WAIT_CHECK=1;//申请中
	public static final int AGENT_STEP_CHECK_SUCCESS=2;//审核通过
	public static final int AGENT_STEP_CHECK_FAILURE=3;//审核不通过
	public static final int AGENT_STEP_AUTO_CLOSED=4;//自动关闭
	
	/**代理商审核状态*/
	public static final int AGENT_STATUS_WAIT=0; //待审核
	public static final int AGENT_STATUS_SUCCESS=1;//审核通过
	public static final int AGENT_STATUS_FAILURE=2;//审核不通过
		
	/**消息推送设置*/
	public static final int MESSAGE_PUSH_FLAG_NO=0; //不推送
	public static final int MESSAGE_PUSH_FLAG_YES=1;//推送
		
	/**是否会员*/
	public static final int MEMBER_FLAG_FALSE=0; //不是
	public static final int MEMBER_FLAG_TRUE=1;//是
	
	/**登录锁定设置*/
	public static final int LOGIN_LOCK_NO=0; //不锁定
	public static final int LOGIN_LOCK_YES=1;//锁定
	public static final int LOGIN_LOCK_DELETED=2;//已注销
	
	/**逾期标志*/
	public static final int OVERDUE_FLAG_FALE=0;//无逾期记录
	public static final int OVERDUE_FLAG_YES=1;//有逾期记录
	
	/**短信使用状态*/
	public static final int SMS_STATUS_UNUSE=0; //未使用
	public static final int SMS_STATUS_USED=1;//已使用
	public static final int SMS_STATUS_DISABLE=2;//主动废除
	
	/**审核状态*/
	public static final int AUTH_STATUS_UNAPPLY=0; //未申请
	public static final int AUTH_STATUS_CHECKING=1;//审核中
	public static final int AUTH_STATUS_PASS=2;//审核通过
	public static final int AUTH_STATUS_UNPASS=3;//审核不通过
	
	/**审核步骤*/
	public static final int AUTH_STEP_WAIT_SUBMIT=0;//等待提交审核
	public static final int AUTH_STEP_WAIT_MACHINE_CHECK=1;//等待机器审核
	public static final int AUTH_STEP_MACHINE_PASS=2;//机器审核通过
	public static final int AUTH_STEP_MACHINE_UNPASS=3;//机器审核不通过
	public static final int AUTH_STEP_ARTI_CHECKING=4;//人工审核中
	public static final int AUTH_STEP_ARTI_MATERIAL_PASS=5;//材料审核通过
	public static final int AUTH_STEP_ARTI_AGAIN_PASS=6;//复审通过
	public static final int AUTH_STEP_ARTI_FINAL_PASS=7;//终审通过
	public static final int AUTH_STEP_ARTI_MATERIAL_NOT_PASS=8;//材料审核未通过
	public static final int AUTH_STEP_ARTI_AGAIN_NOT_PASS=9;//复审未通过
	public static final int AUTH_STEP_ARTI_FINAL_NOT_PASS=10;//终审未通过
	public static final int AUTH_STEP_LOAN_AGAIN=11;//再次借款申请未提交
	//人工审核失败状态
	public static final int AUTH_ARTI_CHECK_PASS = 1;//审核通过
	public static final int AUTH_ARTI_CHECK_FAILURE = 2;//审核不通过
	public static final int AUTH_ARTI_CHECK_SUPPLEMENT = 3;//补充材料
	
	/**
	 * 机器自动审核状态
	 */
	public static final int MACHINE_AUTH_UNPASS=0;//审核未通过
	public static final int MACHINE_AUTH_PASS=1;//审核通过
	
	public static final String MACHINE_AUTH_ITEM_AGE = "身份证年龄";
	public static final String MACHINE_AUTH_ITEM_ZHIMA = "芝麻信用";
	public static final String MACHINE_AUTH_ITEM_MOBILE_TIME = "入网时间";
	public static final String MACHINE_AUTH_ITEM_CALL_NUMS = "通话记录数";
	public static final String MACHINE_AUTH_ITEM_TONGDUN = "同盾分数";
	public static final String MACHINE_AUTH_ITEM_MOBILE_CONSUME = "手机消费";
	public static final String MACHINE_AUTH_ITEM_WHITE = "白名单";
	public static final String MACHINE_AUTH_AREA = "地区检查";
	
	
	//错误理由
	public static final String REJECT_REG= "拒绝申请，您申请暂不符合我们要求";
	public static final String CREDIT_UNABLE = "信用体系得分过低，请180天后重新申请";
	public static int getMachineReasonKey(String key){
		if(key.equals(CREDIT_UNABLE)){
			return 200318;
		}
		return 200317;
	}
	
}
