package com.biyouche.domain.user;

/**
 * 用户登录token实体类
 */
public class UserToken {

    //令牌Id
    private String accessId;
    //令牌key
    private String accessKey;
    //数据key
    private String dataTransferKey;
    //用户id
    private Integer userId;
    //设备类型
    private Integer deviceType;
    //登录时间
    private Integer loginTime;
    //最后操作时间
    private Integer lastActionTime;
    //redis过期时间
    private Integer expireTime;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getDataTransferKey() {
        return dataTransferKey;
    }

    public void setDataTransferKey(String dataTransferKey) {
        this.dataTransferKey = dataTransferKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(Integer lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "accessId='" + accessId + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", dataTransferKey='" + dataTransferKey + '\'' +
                ", userId=" + userId +
                ", deviceType=" + deviceType +
                ", loginTime=" + loginTime +
                ", lastActionTime=" + lastActionTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
