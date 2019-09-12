/*
 * 文件名：EncryptPropertyPlaceholderConfigurer.java<br/>
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

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <br/>
 * @author 任坚
 * @version 1.0,2016年12月28日
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
	private String[] encryptPropNames = {Constants.JDBC_USER,Constants.JDBC_PWD};

	@Override
	protected String convertProperty(String propertyName, String propertyValue)
	{
//		// 如果在加密属性名单中发现该属性
//		if (isEncryptProp(propertyName))
//		{
//			String decryptValue = DESUtils.getDecryptString(propertyValue);
//			return decryptValue;
//		}
		return propertyValue;
	}

	/**
	 * 判断是否在encryptPropNames中
	 *<br/>
	 * @param propertyName 键
	 * @return true or false
	 */
	private boolean isEncryptProp(String propertyName)
	{
		for (String encryptName : encryptPropNames)
		{
			if (encryptName.equals(propertyName))
			{
				return true;
			}
		}
		return false;
	}
}
