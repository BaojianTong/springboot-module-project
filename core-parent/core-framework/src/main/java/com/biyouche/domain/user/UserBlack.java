package com.biyouche.domain.user;

/**
 * 用户黑名单实体类
 */
public class UserBlack {

    private Integer blackId; //黑名单id

    private Integer userId; //用户id

    private Integer adminId; //添加黑名单的管理员id

    private Integer create_time; //加入黑名单时间

    private String remark; //被加入黑名单原因


    public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserBlack{" +
                "blackId=" + blackId +
                ", userId=" + userId +
                ", adminId=" + adminId +
                ", create_time=" + create_time +
                ", remark='" + remark + '\'' +
                '}';
    }
}
