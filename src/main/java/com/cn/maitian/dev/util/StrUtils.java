/*
 * 文件名：StrUtils.java<br/>
 * 版权： Copyright 2016-2017 Chengdu CVHealth Tech. Co. Ltd. All Rights Reserved. <br/> 
 * 描述：<br/>
 * 修改人：任坚<br/>
 * 修改时间：2016年12月22日<br/>
 * 修改内容：新增<br/>
 * 修改内容：<br/>
 * 走读人：<br/>
 * 走读时间：<br/>
 * 走读备注：<br/>
 */
package com.cn.maitian.dev.util;

import com.alibaba.fastjson.JSON;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.*;

/**
 * <br/>
 * @author 任坚
 * @version 1.0,2016年12月22日
 */
public class StrUtils
{
	/**
	 * LOGGER
	 */
	private static final Logger log = LoggerFactory.getLogger(StrUtils.class);

	/**
	 * 讲内容进行MD5加密
	 * @param content 加密内容
	 * @return MD5字符串
	 */
	public static String md5(String content)
	{
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(content.getBytes());
			byte tmp[] = md.digest();
			char str[] = new char[tmp.length * 2];
			int k = 0;
			for (int i = 0; i < tmp.length; i++)
			{
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		}
		catch (Exception e)
		{
			log.error("security error " + e.getMessage());
		}
		return s;
	}
	public static String generate(String phone)
	{
		return md5(phone + UUID.randomUUID().toString().replace("-", ""));
	}
	public static<T> List<T>  fromResponseJsonToListBean(String resultJson, Class<T> cls)
	{
		String code = JSON.parseObject(resultJson).getString("code");
		if("10000".equals(code)) {
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseObject(resultJson).getJSONArray("result");
			List<T> parseArray = JSON.parseArray(JSON.toJSONString(jsonArray), cls);
			return parseArray;
		}
		return null;
	}

    public static String floatScaleToInt(float am) {
		BigDecimal b = new BigDecimal(am);
		int s = b.setScale(2,BigDecimal.ROUND_HALF_EVEN).intValue();
		log.info(">>>am1>>>"+String.valueOf(s));
		return String.valueOf(s);
    }
	public static String floatScaleToFloat(float am) {
		BigDecimal b = new BigDecimal(am);
		float s = b.setScale(2,BigDecimal.ROUND_HALF_EVEN).floatValue();
		log.info(">>>am1>>>"+String.valueOf(s));
		return String.valueOf(s);
	}
    public static void main(String args[]){
		String dm = md5("appid=wx5ed6c80d5b3b29a6&bank_type=CFT&cash_fee=1&fee_type=CNY&is_subscribe=N&mch_id=1507960231&nonce_str=153292974351812971066&openid=oGZfE0xhQ2RQILlTIFlPKIvfke5c&out_trade_no=2bea80788c3328cde670531d824008f8&result_code=SUCCESS&return_code=SUCCESS&time_end=20180730134910&total_fee=1&trade_type=APP&transaction_id=4200000147201807302141367215&key=c6d5122b6d1148128bf74ce4f4282ef1");
		log.info(dm);
	}

}
