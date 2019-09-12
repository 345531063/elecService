package com.cn.maitian.dev.core.entity;


import com.cn.maitian.dev.core.model.CancelCallBack;
import com.cn.maitian.dev.core.model.ConfirmCallBack;
import com.cn.maitian.dev.core.model.TryCallBack;

import java.util.Map;

/**
 * Created by zxd on 2017/10/30.
 */
public class TccCallBackEntity {
    private TryCallBack tryCallBack;
    private ConfirmCallBack confirmCallBack;
    private CancelCallBack cancelCallBack;
    private Map<String,Object> paramterMap;
    private  Context   context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Map<String, Object> getParamterMap() {
        return paramterMap;
    }

    public void setParamterMap(Map<String, Object> paramterMap) {
        this.paramterMap = paramterMap;
    }

    public TryCallBack getTryCallBack() {
        return tryCallBack;
    }

    public void setTryCallBack(TryCallBack tryCallBack) {
        this.tryCallBack = tryCallBack;
    }

    public ConfirmCallBack getConfirmCallBack() {
        return confirmCallBack;
    }

    public void setConfirmCallBack(ConfirmCallBack confirmCallBack) {
        this.confirmCallBack = confirmCallBack;
    }

    public CancelCallBack getCancelCallBack() {
        return cancelCallBack;
    }

    public void setCancelCallBack(CancelCallBack cancelCallBack) {
        this.cancelCallBack = cancelCallBack;
    }
}
