package com.cn.maitian.dev.core.model;


import com.cn.maitian.dev.core.entity.Context;
import com.cn.maitian.dev.core.entity.TccResult;

/**
 * Created by zxd on 2017/10/27.
 */
public interface CancelCallBack {
    public TccResult cancelCallBack(Context context);
}
