package com.biyouche.domain.user;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author hucong
 */
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;//用户ID

    private String loginMobile;//手机号

    private String userPassword;//登录密码

    private String recommendId;//推荐人ID

    private String recommendMobile;//推荐人手机号

    private Byte userLevel;//会员等级[0普通会员;1铜牌会员;2白银会员;3黄金会员;4钻石会员]

    private String userQrcode;//二维码

    private Byte messagePushFlag;//消息推送开关[1推送;2不推送;]

    private Integer loginNums;//累计登录次数

    private Integer createTime;//创建时间

    private Integer lastLoginTime;//最后登录时间

    private Byte loginLock;//登录锁定[0不锁定;1锁定;2已注销]

    private String nickName;//昵称

    private String avatarUrl;//头像

    private Byte deviceType;//登录设备类型[0微信;1android;2ios]

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginMobile() {
        return loginMobile;
    }

    public void setLoginMobile(String loginMobile) {
        this.loginMobile = loginMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public String getRecommendMobile() {
        return recommendMobile;
    }

    public void setRecommendMobile(String recommendMobile) {
        this.recommendMobile = recommendMobile;
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserQrcode() {
        return userQrcode;
    }

    public void setUserQrcode(String userQrcode) {
        this.userQrcode = userQrcode;
    }

    public Byte getMessagePushFlag() {
        return messagePushFlag;
    }

    public void setMessagePushFlag(Byte messagePushFlag) {
        this.messagePushFlag = messagePushFlag;
    }

    public Integer getLoginNums() {
        return loginNums;
    }

    public void setLoginNums(Integer loginNums) {
        this.loginNums = loginNums;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Byte getLoginLock() {
        return loginLock;
    }

    public void setLoginLock(Byte loginLock) {
        this.loginLock = loginLock;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", loginMobile=" + loginMobile + ", userPassword=" + userPassword
                + ", recommendId=" + recommendId + ", recommendMobile=" + recommendMobile + ", userLevel=" + userLevel
                + ", userQrcode=" + userQrcode + ", messagePushFlag=" + messagePushFlag + ", loginNums=" + loginNums
                + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime + ", loginLock=" + loginLock
                + ", nickName=" + nickName + ", avatarUrl=" + avatarUrl + ", deviceType=" + deviceType + "]";
    }


}
