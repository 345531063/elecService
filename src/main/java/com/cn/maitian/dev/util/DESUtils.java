/*
 * 文件名：DESUtils.java<br/>
 * 版权： Copyright 2016-2017 Chengdu CVHealth Tech. Co. Ltd. All Rights Reserved. <br/> 
 * 描述：<br/>
 * 修改人：任坚<br/>
 * 修改时间：2016年12月28日<br/>
 * 修改内容：新增<br/>
 * 修改内容：<br/>
 * 走读人：<br/>
 * 走读时间：<br/>
 * 走读备注：<br/>
 */
package com.cn.maitian.dev.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 加密解密算法 <br/>
 * @author 任坚
 * @version 1.0,2016年12月28日
 */
public class DESUtils
{
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DESUtils.class);
	
	/**
	 * 密匙
	 */
	private static Key key;
	
	/**
	 * 私钥
	 */
	private static final String KEY_STR = "xjk_security";

	static
	{
		try
		{
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY_STR.getBytes());
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 对字符串进行加密，返回BASE64的加密字符串 <功能详细描述>
	 * @param str
	 * @return string
	 * @see [类、类#方法、类#成员]
	 */
	public static String getEncryptString(String str)
	{
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try
		{
			byte[] strBytes = str.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64Encoder.encode(encryptStrBytes);
		}
		catch (Exception e)
		{
			LOGGER.error("getEncryptString failed " + e.getMessage());
			return null;
		}
	}

	/**
	 * 对BASE64加密字符串进行解密 <功能详细描述>
	 * @param str
	 * @return string
	 * @see [类、类#方法、类#成员]
	 */
	public static String getDecryptString(String str)
	{
//		BASE64Decoder base64Decoder = new BASE64Decoder();
//		try
//		{
//			byte[] strBytes = base64Decoder.decodeBuffer(str);
//			Cipher cipher = Cipher.getInstance("DES");
//			cipher.init(Cipher.DECRYPT_MODE, key);
//			byte[] encryptStrBytes = cipher.doFinal(strBytes);
//			return new String(encryptStrBytes, "UTF-8");
//		}
//		catch (Exception e)
//		{
//			LOGGER.error("getDecryptString failed " + e.getMessage());
//			return null;
//		}
		return null;
	}
}
