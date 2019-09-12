/*
 * 文件名：ConfigUtil.java<br/>
 * 版权： Copyright 2016-2017 Chengdu CVHealth Tech. Co. Ltd. All Rights Reserved. <br/> 
 * 描述：<br/>
 * 修改人：任坚<br/>
 * 修改时间：2017年2月24日<br/>
 * 修改内容：新增<br/>
 * 修改内容：<br/>
 * 走读人：<br/>
 * 走读时间：<br/>
 * 走读备注：<br/>
 */
package com.cn.maitian.dev.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>
 * @author 任坚
 * @version 1.0,2017年2月24日
 */
public class ConfigUtil
{
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

	/**
	 * 版本控制文件
	 */
	private static String fileName = "config.properties";

	/**
	 * 加载键值对内容
	 */
	private static Properties p = getProperties(fileName);

	/**版本*/
	public static final String VERSION = p.getProperty("VERSION").trim();
	
	/**资源文件 */
	public static final String RESOURCE_URL = p.getProperty("RESOURCE_URL").trim();

	/**
	 * 读取propertity文件的方法
	 * @author haojian Apr 27, 2012 3:00:56 PM
	 * @param fileName
	 * @return
	 */
	public static Properties getProperties(String fileName)
	{

		LOGGER.info("开始读取文件【{}】...", new Object[] { fileName });

		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);
		Properties properties = new Properties();
		try
		{
			properties.load(is);
		}
		catch (IOException e)
		{
			LOGGER.error("Exception:【{}】" + e.getMessage());
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					is = null;
					LOGGER.error("Exception:【{}】" + e.getMessage());
				}
			}
		}

		LOGGER.info("读取文件【{}】结束...", new Object[] { fileName });

		return properties;
	}
}
