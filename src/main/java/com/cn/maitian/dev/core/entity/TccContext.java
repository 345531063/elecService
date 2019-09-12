package com.cn.maitian.dev.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.maitian.dev.core.model.CancelCallBack;
import com.cn.maitian.dev.core.model.ConfirmCallBack;
import com.cn.maitian.dev.core.model.RetryCallI;
import com.cn.maitian.dev.core.model.TryCallBack;
import com.cn.maitian.dev.util.ThreadUtil;

import net.sf.json.JSONObject;

/**
 * Created by zxd on 2017/10/30.
 */
public class TccContext implements TryCallBack,ConfirmCallBack,CancelCallBack {
	private static final Logger log = LoggerFactory.getLogger(TccContext.class);
	
    private List<TccCallBackEntity>  list = new ArrayList<>();
    private Map<String,Object> paramterMap = new HashMap<>();//参数
    private int retryTimes;//尝试次数
    private long intervalTime;//间隔尝试时间
    private void beforeInit(Map map){
       this.paramterMap  = map;
   }
    //加入到列表中
   public  TccResult   execute(Map<String,Object> paramterMap,TryCallBack tryCallBack, ConfirmCallBack confirmCallBack, CancelCallBack cancelCallBack,TccEndEnum tccEndEnum){
       if(null != paramterMap){
           beforeInit(paramterMap);
       }
        //执行取消 返回都是try里面的值
       Context    context  = new Context();
       context.setParamterMap(paramterMap);
       TccCallBackEntity  callBackList = new TccCallBackEntity();
       callBackList.setContext(context);
       callBackList.setTryCallBack(tryCallBack);
       callBackList.setConfirmCallBack(confirmCallBack);
       callBackList.setCancelCallBack(cancelCallBack);
       //预定方法执行成功 加入list
       list.add(callBackList);
       TccResult tccResult  =  tryCallBack.tryCallBack(context);
       if(tccResult.isStatus()){//表示成功 只有成功的才会加入到确认列表中,方便确认调用

       }else {//调用取消
           for(TccCallBackEntity tcbe : list){
               //网络异常 地址异常会出现 所以只需要重试即可
               new RetryContext().retry(this.retryTimes, this.intervalTime, new RetryCallI() {
                   @Override
                   public RetryResult call(JSONObject param) {
                       RetryResult  newx = new RetryResult(false);
                       try{
                    	   log.info(">>>取消操作 开始重试>>>");
                           TccResult  trx  = tcbe.getCancelCallBack().cancelCallBack(tcbe.getContext());
                           newx.setResult(trx.getObject());
                           newx.setStatus(trx.isStatus());
                           newx.setExecuteMethod(trx.getExecuteMethod());
                           newx.setExecuteParameter(trx.getExecuteParameter());
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                       return newx;
                   }
               }, ThreadUtil.getFixedThreadPool());
           }
           return tccResult;//表示执行失败 并返回错误信息
       }
       if(tccEndEnum.getNum() == TccEndEnum.END.getNum()){//表示已经结束了 如果预定值都执行完成 则将确认
           for(TccCallBackEntity tcbe : list){
               TccResult  tr  = null;
               try {
                          tr  = tcbe.getConfirmCallBack().confirmCallBack(tcbe.getContext());
               }catch (Exception e){
                   e.printStackTrace();
                   tr.setStatus(false);
               }
               if(tr.isStatus() == false){
                   //重试 只有网络连接不上的情况下 才会有重试
                    new RetryContext().retry(this.retryTimes, this.intervalTime, new RetryCallI() {
                       @Override
                       public RetryResult call(JSONObject param) {
                           RetryResult  newx = new RetryResult(false);
                           try{
                        	   log.info(">>>确认重试>>>");
                               TccResult  trx = tcbe.getConfirmCallBack().confirmCallBack(tcbe.getContext());
                               newx.setResult(trx.getObject());
                               newx.setStatus(trx.isStatus());
                               newx.setExecuteMethod(trx.getExecuteMethod());
                               newx.setExecuteParameter(trx.getExecuteParameter());
                           }catch (Exception e){
                               e.printStackTrace();
                           }
                           return newx;
                       }
                   }, ThreadUtil.getFixedThreadPool());
               }
           }
       }
       return tccResult;
   }
    //加入到列表中
    public  TccEntity   put(TryCallBack tryCallBack, ConfirmCallBack confirmCallBack, CancelCallBack cancelCallBack){
        TccEntity  tccEntity = new TccEntity();
        TccCallBackEntity  callBackList = new TccCallBackEntity();
        callBackList.setTryCallBack(tryCallBack);
        callBackList.setConfirmCallBack(confirmCallBack);
        callBackList.setCancelCallBack(cancelCallBack);
        tccEntity.put(callBackList);
        return tccEntity;
    }

    public Map<String, Object> getParamterMap() {
        return paramterMap;
    }

    @Override
    public TccResult cancelCallBack(Context context) {
        return null;
    }

    @Override
    public TccResult tryCallBack(Context context) {
        return null;
    }

    @Override
    public TccResult confirmCallBack(Context context) {
        return null;
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
}
