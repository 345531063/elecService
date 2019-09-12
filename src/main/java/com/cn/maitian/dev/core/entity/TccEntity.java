package com.cn.maitian.dev.core.entity;


import com.cn.maitian.dev.core.model.TccCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxd on 2017/10/27.
 */
public class TccEntity implements TccCallBack {
    private   TccContext    context;
    private List<TccCallBackEntity> tccEntityList = new ArrayList<>();

    public TccContext getContext() {
        return context;
    }

    public List<TccCallBackEntity> getTccEntityList() {
        return tccEntityList;
    }

    public void setTccEntityList(List<TccCallBackEntity> tccEntityList) {
        this.tccEntityList = tccEntityList;
    }

    public void setContext(TccContext context) {
        this.context = context;
    }
    public  void put(TccCallBackEntity  tccCallBackEntity){
        this.tccEntityList.add(tccCallBackEntity);
    }
    @Override
    public Object tccCall() {
       return null;
    }
    public void execute(){

    }
}
