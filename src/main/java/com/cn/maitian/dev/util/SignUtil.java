package com.cn.maitian.dev.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtil {
	private static final Logger log = LoggerFactory.getLogger(SignUtil.class);
	
    public static String sign(Map<String, Object> info, String key) {
        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(info.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> arg0, Map.Entry<String, Object> arg1) {
                return (arg0.getKey()).compareTo(arg1.getKey());
            }
        });
        String ret = "";

        for (Map.Entry<String, Object> entry : infoIds) {
            ret += entry.getKey();
            ret += "=";
            ret += String.valueOf(entry.getValue());
            ret += "&";
        }
        ret = ret.substring(0, ret.length() - 1) + "&key=" + key;
        log.info("排序前ret:" + ret);
        String md5 = StrUtils.md5(ret);
        md5 = md5.toUpperCase();
        return md5;
    }
    public static void main(String args[]){
       Map<String,Object> param = new  HashMap<>();
        param.put("accessToken","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        param.put("nonce","20191320253632");
        param.put("timestamp","20180809113030");
        param.put("userId","sdafsdfsdf");
        param.put("outTradeNo","2125502d8f23fb0fd1b4ab3eeadd2bd0");
        param.put("clientType","android");
        log.info(">>>>>>>>>>..sign:"+sign(param,"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+"sign"+"20191320253632"));
    }
}
