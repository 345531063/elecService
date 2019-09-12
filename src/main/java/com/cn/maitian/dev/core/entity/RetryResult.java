package com.cn.maitian.dev.core.entity;

/**
 * Created by zxd on 2017/10/27.
 */
public class RetryResult {
    private boolean status;
    private Object   result;
    private  String  executeMethod;
    private  Object  executeParameter;
    /**execute count**/
    private  int     failCount; /**execute count**/

    private   int   successCount;

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
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

    public RetryResult(boolean status){
        this.status  = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
