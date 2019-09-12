package com.cn.maitian.dev.model;

public class UserLotteryModel extends BaseModel {
    private int lotteryType;//中奖类型 1等奖 2 等奖 3等奖等
    private String jobNum;//员工编号

    public int getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(int lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
}
