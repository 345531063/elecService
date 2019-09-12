package com.cn.maitian.dev.core.model;


import com.cn.maitian.dev.core.entity.RetryResult;

/**
 * Created by Administrator on 2017/10/26.
 */
public interface FailCallBack {
    public RetryResult failCall(RetryResult retryResult);
}
