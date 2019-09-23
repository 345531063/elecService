package com.cn.maitian.dev.entity;

public class ThemeActivity extends BaseEntity{
    private String id;

    private String name;

    private String startTime;

    private String endTime;

    private Integer lotteryStatus;

    private Integer answerTimes;

    private Integer lotteryAnswerLimit;

    private Integer testRandomNum;

    private Integer status = -1;
    private String  describe;//描述
    private String createTime;
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getLotteryStatus() {
        return lotteryStatus;
    }

    public void setLotteryStatus(Integer lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public Integer getAnswerTimes() {
        return answerTimes;
    }

    public void setAnswerTimes(Integer answerTimes) {
        this.answerTimes = answerTimes;
    }

    public Integer getLotteryAnswerLimit() {
        return lotteryAnswerLimit;
    }

    public void setLotteryAnswerLimit(Integer lotteryAnswerLimit) {
        this.lotteryAnswerLimit = lotteryAnswerLimit;
    }

    public Integer getTestRandomNum() {
        return testRandomNum;
    }

    public void setTestRandomNum(Integer testRandomNum) {
        this.testRandomNum = testRandomNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}