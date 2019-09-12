package com.cn.maitian.dev.core.entity;

import com.cn.maitian.dev.core.model.EntityCallBack;
import com.cn.maitian.dev.core.model.FailCallBack;
import com.cn.maitian.dev.core.model.RetryCallBack;
import com.cn.maitian.dev.core.model.SuccessCallBack;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zxd on 2017/10/26.
 */
public class RetryEntity implements EntityCallBack {
    private final static Logger log = Logger.getLogger(RetryEntity.class);
    private  RetryTemplate      oRetryTemplate   = new RetryTemplate();
    private   SimpleRetryPolicy oRetryPolicy     = new SimpleRetryPolicy();
    private   int               retryTimes       = 10;//重拾次数 默认为2
    private   long              intervalTime     = 100 ;//毫秒单位 间隔重拾时间 默认为100毫秒
    private   Object             retryParamter      = null;//重拾参数 包括地址,参数
    private   Object             successResult        = null;//执行成功结果

    public RetryEntity(){
    }

    public Object getSuccessResult() {
        return successResult;
    }

    public void setSuccessResult(Object successResult) {
        this.successResult = successResult;
    }

    public Object getRetryParamter() {
        return retryParamter;
    }

    public void setRetryParamter(Object retryParamter) {
        this.retryParamter = retryParamter;
    }
    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }
    private void before(int retryTimes,long intervalTime){
        this.intervalTime   = intervalTime;
        this.retryTimes     = retryTimes;
        this.oRetryPolicy.setMaxAttempts(retryTimes);
        this.oRetryTemplate.setRetryPolicy(oRetryPolicy);
    }
    private Object execute(int retryTimes, long intervalTime, RetryCallBack retryCallBack, SuccessCallBack successCallBack , FailCallBack failCallBack) {
        this.before(retryTimes,intervalTime);
        Object obj   = null;
        try {
            obj = oRetryTemplate.execute(new RetryCallback<Object, Exception>() {
                @Override
                public Object doWithRetry(org.springframework.retry.RetryContext context) throws Exception {// begin retry execute 开始重试
                    context.setAttribute("status",false);//默认为失败

                    Thread.sleep(RetryEntity.this.intervalTime);
                    RetryResult                 resultRetry      = retryCallBack.retryCall(null);
                    Map<String,Object>          result           = new HashMap<>();
                    result.put("param",resultRetry.getExecuteParameter());
                    result.put("executeMethod",resultRetry.getExecuteMethod());
                    context.setAttribute("result",result);//失败的返回值
                    if(resultRetry.isStatus()){// execute success == over
                        context.setAttribute("status"   ,true);//表示最后结果执行成功
                        context.setAttribute("result"   ,resultRetry.getResult());//表示最后结果执行成功
                        return successCallBack.successCall(resultRetry);
                    }else{//抛异常会重试调用
                        throw new  RuntimeException();
                    }
                }
            }, new RecoveryCallback<Object>() {
                @Override
                public Object recover(org.springframework.retry.RetryContext context) throws Exception { // 重试多次后都失败了
                    Object      result       = context.getAttribute("result");
                    RetryResult  retryResult = new RetryResult(false);
                    retryResult.setResult(result);
                    if(failCallBack != null){
                        return failCallBack.failCall(retryResult);
                    }
                    return result;
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Object retry(JSONObject  returnResult,int retryTimes,long intervalTime, RetryCallBack  retryCallBack, SuccessCallBack successCallBack, FailCallBack failCallBack, ExecutorService executorService) {
        //请求的不能加线程池 会导致不能即使返回给客户端 其他情况可以 比如发短信 推送消息等
        Object  returnObj = null;
        if(null == executorService){
            returnObj  =(RetryResult)execute(retryTimes,intervalTime,retryCallBack,successCallBack,failCallBack);
        }else{
            ThreadPoolExecutor  poolExecutor = (ThreadPoolExecutor)executorService;
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        // Thread.sleep(100000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    execute(retryTimes,intervalTime,retryCallBack,successCallBack,failCallBack);


                    //Integer.valueOf("AF");

                }
            });
        }
        if(returnObj == null){
            return new RetryResult(false);
        }
        return  returnObj;
    }
}
