package com.biyouche.enums;


/**
 * @author hucong
 * @time 2018年7月12日 
 * @type SmsEnue.java
 */
public enum  SmsEnue {

	/*定义了8个短信发送类型*/
    CODE_01("01258251", "拒绝申请"),
    CODE_02("257372", "拒绝"),
    CODE_03("257370", "通过"),
    CODE_04("214690", "新能源汽车管理通知"),
    CODE_05("157622", "注册"),
    CODE_06("157424", "验证码"),
    CODE_07("157423", "修改密码"),
	CODE_08("157422", "身份验证");
	
	/*两个属性*/
    private String code;
    private String msg;
   /*构造函数*/
    private SmsEnue(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
   /*属性的setter和getter*/
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
