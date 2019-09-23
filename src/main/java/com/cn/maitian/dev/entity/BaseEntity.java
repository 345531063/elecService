package com.cn.maitian.dev.entity;

public class BaseEntity {
    private int startSize = 0 ;
    private int endSize   = 5;

    public int getStartSize() {
        return startSize;
    }

    public void setStartSize(int startSize) {
        this.startSize = startSize;
    }

    public int getEndSize() {
        return endSize;
    }

    public void setEndSize(int endSize) {
        this.endSize = endSize;
    }
}
