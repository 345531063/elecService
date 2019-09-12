package com.cn.maitian.dev.core.model;


import com.cn.maitian.dev.core.entity.RetryResult;
import net.sf.json.JSONObject;

/**
 * Created by zxd on 2017/10/30.
 */
public interface RetryCallI {
    public RetryResult call(JSONObject param);
}
