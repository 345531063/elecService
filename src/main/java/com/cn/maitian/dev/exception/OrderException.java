package com.cn.maitian.dev.exception;

public class OrderException extends  RuntimeException {
    public OrderException(String message,Class<?> clazz){
        super("class:"+clazz.getName()+">>>>>>"+message);
    }
}
