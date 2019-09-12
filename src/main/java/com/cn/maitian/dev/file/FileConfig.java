/*
 * 文件名：FileConfig.java
 * 版权： Copyright 2016-2017 Chengdu CVHealth Tech. Co. Ltd. All Rights Reserved.  
 * 描述：
 * 修改人：聂臣圆
 * 修改时间：2017年1月4日
 * 修改内容：新增
 * 修改内容：
 * 走读人：
 * 走读时间：
 */
package com.cn.maitian.dev.file;

import com.cn.maitian.dev.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * <br/>
 * @author 聂臣圆
 * @version 1.0,2017年1月4日
 * @see
 * @since
 */
@Component
@Scope(value = "singleton")
public class FileConfig
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FileConfig.class);
	private String filePathHead;// 文件存储路径头
	private String cHttpUrlHead;//c写的http服务的访问路径
	private String cTXJServerHttpHead;//c写的贴心集服务http服务的访问路径
	private String fileHttpUrlHead;// 文件http下载地址头
	private String imgHttpUrlHead;// 图片http下载地址头
	private String deleteFilePathHead;// 被删除文件存储路径头
	private long timeOutTime;// 文件信息缓存超时时间
	private long qtTimeOutTime;// qt服务响应超时时间
	private int dynamicCodetimeoutTime;// 二维码超时时间
	private int handleCacheAddNum;// 每添加文件信息缓存该次数，处理一下文件信息缓存

	public FileConfig()
	{
		Properties properties = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.FLLE_CONFIG_NAME);
		try
		{
			properties.load(in);
		}
		catch (IOException e)
		{
			LOGGER.info("load properties file_config.properties failed" + e.getMessage());
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		filePathHead = properties.getProperty("file_save_path_head");
		fileHttpUrlHead = properties.getProperty("file_http_url_head");
		cHttpUrlHead = properties.getProperty("c_http_url_head");
		cTXJServerHttpHead = properties.getProperty("c_txjserver_http_url_head");
		imgHttpUrlHead = properties.getProperty("img_http_url_head");
		deleteFilePathHead = properties.getProperty("delete_file_save_path_head");
		timeOutTime = Long.parseLong(properties.getProperty("timeout_time", "86400000"));
		qtTimeOutTime = Long.parseLong(properties.getProperty("qt_timeout_time", "400000"));
		dynamicCodetimeoutTime = Integer.parseInt(properties.getProperty("dynamic_code_timeout_time", "28800000"));
		handleCacheAddNum = Integer.parseInt(properties.getProperty("handle_cache_addnum", "1000"));
	}

	/**
	 * 获取文件存储路径头 <br/>
	 * @return 文件存储路径头
	 * @since
	 */
	public String getFilePathHead()
	{
		return filePathHead;
	}

	/**
	 * 获取 文件http下载地址头 <br/>
	 * @return 文件http下载地址头
	 * @since
	 */
	public String getFileHttpUrlHead()
	{
		return fileHttpUrlHead;
	}

	/**
	 * 获取图片http下载地址头 <br/>
	 * @return 图片http下载地址头
	 * @since
	 */
	public String getImgHttpUrlHead()
	{
		return imgHttpUrlHead;
	}

	/**
	 * 获取被删除文件存储路径头 <br/>
	 * @return 被删除文件存储路径头
	 * @since
	 */
	public String getDeleteFilePathHead()
	{
		return deleteFilePathHead;
	}

	/**
	 * 获取文件信息缓存超时时间 <br/>
	 * @return 文件信息缓存超时时间
	 * @since
	 */
	public long getTimeOutTime()
	{
		return timeOutTime;
	}

	/**
	 * 获取 处理文件缓存需要的添加次数 <br/>
	 * @return 处理文件缓存需要的添加次数
	 * @since
	 */
	public int getHandleCacheAddNum()
	{
		return handleCacheAddNum;
	}

	/**
	 * 获取c编写的http服务的路径
	 *<br/>
	 * @return c编写的http服务的路径
	 */
	public String getcHttpUrlHead()
	{
		return cHttpUrlHead;
	}
	
	/**
	 * c写的贴心集服务http服务的访问路径
	 *<br/>
	 * @return c写的贴心集服务http服务的访问路径
	 */
	public String getcTXJServerHttpHead()
	{
		return cTXJServerHttpHead;
	}

	/**
	 * 获取qt服务超时时间
	 *<br/>
	 * @return qt服务超时时间
	 */
	public long getQtTimeOutTime()
	{
		return qtTimeOutTime;
	}

	/**
	 * 二维码超时时间
	 *<br/>
	 * @return 二维码超时时间
	 */
	public int getDynamicCodetimeoutTime()
	{
		return dynamicCodetimeoutTime;
	}
}
