package com.cn.maitian.dev.core.entity;

import com.cn.maitian.dev.core.model.FailCallBack;
import com.cn.maitian.dev.core.model.RetryCallBack;
import com.cn.maitian.dev.core.model.RetryCallI;
import com.cn.maitian.dev.core.model.SuccessCallBack;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;

/**
 * Created by zxd on 2017/10/30.
 */
public class RetryContext {
    private final static Logger log = Logger.getLogger(RetryContext.class);
    public RetryResult retry(int retryTimes, long intervalTime, RetryCallI retryCallI, ExecutorService service){

        RetryResult  result          = (RetryResult)new RetryEntity().retry(null,retryTimes,intervalTime,
                new RetryCallBack() {
                    @Override
                    public RetryResult retryCall(Object object) {
                        //execute code
                        //只有异常才会继续执行
                        RetryResult  result = new RetryResult(false);
                        try{
                             result =  retryCallI.call(null);
                             if(result.isStatus()){
                                 result.setResult(result.getResult());
                                 result.setStatus(true);
                             }else{
                                 result.setStatus(false);
                             }
                        }catch (Exception e){
                            result.setStatus(false);
                        }
                        return result;
                    }
                },new SuccessCallBack(){
                    @Override
                    public RetryResult successCall(RetryResult retryResult) {
                        //记录一条日志 执行成功需要记录日志
                        log.info(">>>>>>>>retry.>>执行成功了 paremterString..."+ JSONObject.fromObject(retryResult).toString());
                        return retryResult;
                    }
                },new FailCallBack() {
                    @Override
                    public RetryResult failCall(RetryResult result) {
                        //记录一条日志 执行失败也需要记录日志
                        JSONObject  jsonObjectMap = JSONObject.fromObject(result.getResult());
                        log.error(">>>>>>>>retry.>重试失败>>>>>>>"+jsonObjectMap.toString());
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("param",jsonObjectMap.getString("param"));
                        jsonObject.put("methodName",jsonObjectMap.getString("executeMethod"));
                        result.setStatus(false);
                        //插入执行失败的日志进入到数据库 调用用户服务写入日志
//                        ExcuteLogClient excuteLogClient = (ExcuteLogClient) SpringUtil.getTyeBean(ExcuteLogClient.class);
//                        excuteLogClient.createLog(jsonObject);
                        return  result;
                    }
                },service);

        return result;
    }
}
