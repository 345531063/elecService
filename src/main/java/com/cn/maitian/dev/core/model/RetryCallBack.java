package com.cn.maitian.dev.core.model;


import com.cn.maitian.dev.core.entity.RetryResult;

/**
 * Created by zxd on 2017/10/26.
 */
public interface RetryCallBack {
    public RetryResult retryCall(Object object);
}
