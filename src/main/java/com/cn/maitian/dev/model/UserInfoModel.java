package com.cn.maitian.dev.model;

public class UserInfoModel extends BaseModel {
    private String jobNum;//工号
    private String wxNickname;//用户名称

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getWxNickname() {
        return wxNickname;
    }

    public void setWxNickname(String wxNickname) {
        this.wxNickname = wxNickname;
    }
}
