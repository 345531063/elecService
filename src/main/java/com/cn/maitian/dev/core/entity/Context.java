package com.cn.maitian.dev.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxd on 2017/11/2.
 */
public class Context {
    private Map<String,Object> paramterMap = new HashMap<>();//参数

    public Map<String, Object> getParamterMap() {
        return paramterMap;
    }

    public void setParamterMap(Map<String, Object> paramterMap) {
        this.paramterMap = paramterMap;
    }
}
