package com.cn.maitian.dev.core.entity;

/**
 * Created by zxd on 2017/10/27.
 */
public class TccResult {
    private boolean status;
    private Object   object;
    private  String  executeMethod;
    private  Object  executeParameter;
    private  String  resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public Object getExecuteParameter() {
        return executeParameter;
    }

    public void setExecuteParameter(Object executeParameter) {
        this.executeParameter = executeParameter;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void execute() {
    }
}
