package com.cn.maitian.dev.core.entity;

/**
 * Created by zxd on 2017/10/30.
 */
public enum TccEndEnum {
    END(1,"结束"),
    BEGIN(0,"未借宿"),;
    private  int  num;
    private String  text;
    private TccEndEnum(int value,String text){
            this.num = value;
            this.text = text;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
