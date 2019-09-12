package com.cn.maitian.dev.core.model;


import net.sf.json.JSONObject;

import java.util.concurrent.ExecutorService;

/**
 * Created by zxd on 2017/10/26.
 */
public interface EntityCallBack {
    public Object retry(JSONObject returnResult, int retryTimes, long intervalTime, RetryCallBack retryCallBack, SuccessCallBack successCallBack, FailCallBack failCallBack, ExecutorService executorService);
}
