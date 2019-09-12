package com.cn.maitian.dev.constant;

public enum AwardsLevelEnum {
    ONE(1,"一等奖"),
    TWO(2,"二等奖"),
    THREE(3,"三等奖"),
    FOUR(4,"四等奖"),
    FIVE(5,"五等奖"),
    SEX(6,"谢谢参与"),
    ;
    private int id;
    private String description;
    AwardsLevelEnum(int id, String description){
        this.id = id;
        this.description=description;
    }
    public static AwardsLevelEnum getAwardsLevelEnum(int level){
        for(AwardsLevelEnum awardsLevel:AwardsLevelEnum.values()){
            if(awardsLevel.id == level){
                return awardsLevel;
            }
        }
        return SEX;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
