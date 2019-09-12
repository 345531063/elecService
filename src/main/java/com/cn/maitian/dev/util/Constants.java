/*
 * 文件名：Constants.java<br/>
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

import java.util.HashMap;
import java.util.Map;

/**
 * 常量<br/>
 * @author 任坚
 * @version 1.0,2016年12月22日
 */
public final class Constants
{
	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = ",";
	/**
	 * 10
	 */
	public static final int TEN = 10;

	/**
	 * 1000毫秒
	 */
	public static final int ONE_SECOND = 1000;

	/**
	 * 60秒
	 */
	public static final int SIXTY_SECOND = 60;

	/**
	 * 注销登录
	 */
	public static final int LOGOUT = 0;

	/**
	 * 登录
	 */
	public static final int LOGIN = 1;

	/**
	 * 40
	 */
	public static final int FOURTY = 40;

	/**
	 * 姓名长度
	 */
	public static final int NAME_LENGTH = 32;

	/**
	 * 中文格式
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * 日志配置文件
	 */
	public static final String FLLE_NAME = "log4j.properties";

	/**
	 * 文件配置文件
	 */
	public static final String FLLE_CONFIG_NAME = "file_config.properties";

	/**
	 * 文件处理线程配置文件
	 */
	public static final String FLLE_THREAD_CONFIG_NAME = "file_thread_config.properties";

	/**
	 * 短信配置文件
	 */
	public static final String SMS_CONFIG_NAME = "sms_config.properties";

	/**
	 * memcached配置文件
	 */
	public static final String MEMCACHED_CONFIG_NAME = "memcached.properties";

	/**
	 * 响应成功
	 */
	public static final String SUCCESS = "success";

	/**
	 * 响应失败
	 */
	public static final Object FAILED = null;

	/**
	 * 响应：正确
	 */
	public static final String YES = "正确";

	/**
	 * 安全校验，静态秘钥
	 */
	public static final String STATIC_KEY = "xjksecurity";

	/**
	 * 用户
	 */
	public static final String JDBC_USER = "jdbc.user";

	/**
	 * 密码
	 */
	public static final String JDBC_PWD = "jdbc.pwd";

	/**
	 * 年
	 */
	public static final String YEAR = "yyyy";

	/**
	 * 月
	 */
	public static final String MONTH = "yyyyMM";

	/**
	 * 日
	 */
	public static final String DAY = "yyyyMMdd";
	
	/**
	 * 时间
	 */
	public static final String INTTIME = "yyyyMMddHHmmss";
	
	/**
	 * 日期
	 */
	public static final String DATATIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 手机号正则表达式
	 */
	public static final String PHONE_REGULAR = "((^[1]([3|5|8|7|4][0-9]{1})[ ]{0,8}[0-9]{4}[ ]{0,8}[0-9]{4})|(^[1]([3|5|8|7|4][0-9]{2})[ ]{0,8}[0-9]{4}[ ]{0,8}[0-9]{3}))";

	/**
	 * 验证码正则表达式
	 */
	public static final String CODE_REGULAR = "^\\d{4}$";

	/**
	 * ID正则表达式
	 */
	public static final String ID_REGULAR = "^[0-9a-z]{32}$";

	/**
	 * 性别正则表达式
	 */
	public static final String SEX_REGULAR = "^(0|1|-1)$";

	/**
	 * 来源正则表达式
	 */
	public static final String TYPE_REGULAR = "^(0|1|2|3|4|5|6)$";

	/**
	 * 生日正则表达式
	 */
	public static final String BIRTHDAY_REGULAR = "^\\d{4}-\\d{2}-\\d{2}$";

	/**
	 * 身高正则表达式
	 */
	public static final String HEIGHT_REGULAR = "^(\\d{1,4}\\.\\d{1}|-1[.]0|\\d{1,4})$";

	/**
	 * 体重正则表达式
	 */
	public static final String WEIGHT_REGULAR = "^(\\d{1,4}\\.\\d{1}|-1[.]0|\\d{1,4})$";

	/**
	 * 年正则表达式
	 */
	public static final String YEAR_REGULAR = "^\\d{4}$";

	/**
	 * 月正则表达式
	 */
	public static final String MONTH_REGULAR = "^\\d{6}$";

	/**
	 * 日正则表达式
	 */
	public static final String DAY_REGULAR = "^\\d{8}$";

	/**
	 * 页数正则表达式
	 */
	public static final String PAGE_REGULAR = "^[1-9]\\d{0,4}$";

	/**
	 * 条数正则表达式
	 */
	public static final String SIZE_REGULAR = "^[1-9]\\d{0,2}$";

	/**
	 * 正整数正则表达式
	 */
	public static final String POSITIVE_INTEGER = "^[1-9][0-9]*$";

	/**
	 * 非负整数正则表达式
	 */
	public static final String NON_NEGATIVE_NUMBER = "^(0|[1-9][0-9]*)$";

	/**
	 * 日期正则表达式
	 */
	public static final String DATE_REGULAR = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";

	/**
	 * 证件编号正则
	 */
	public static final String CARD_REGULAR = "^[\\s\\S]{0,30}$";

	/**
	 * 职业格式正则
	 */
	public static final String PROFESSION_REGULAR = "^[\\s\\S]{0,30}$";

	/**
	 * 病史格式
	 */
	public static final String DISEASE_REGULAR = "^[\\s\\S]{0,2000}$";

	/**
	 * 证件类型正则
	 */
	public static final String CARDTYPE_REGULAR = "^[\\s\\S]{0,30}$";

	/**
	 * 工作单位格式
	 */
	public static final String WORK_REGULAR = "^[\\s\\S]{0,100}$";

	/**
	 * 职务格式
	 */
	public static final String PRO_REGULAR = "^[\\s\\S]{0,30}$";

	/**
	 * 个人简介格式
	 */
	public static final String INTRODUCTION_REGULAR = "^[\\s\\S]{0,500}$";

	/**
	 * 标签名称
	 */
	public static final String TAG_NAME_REGULAR = "^[\\s\\S]{0,20}$";

	/**
	 * 嘱咐内容验证
	 */
	public static final String CONTEXT_REGULAR = "^[\\s\\S]{0,500}$";

	/**
	 * 咨询问题正则
	 */
	public static final String ISSUE_REGULAR = "^[\\s\\S]{0,255}$";

	/**
	 * 查询类型表达式
	 */
	public static final String CLIENT_TYPE_REGULAR = "^(0|1|2|3|4|5|6)$";

	/**
	 * 未被处理
	 */
	public static final int NOT_DEAL = 0;

	/**
	 * 已处理
	 */
	public static final int HAD_DEAL = 1;

	/**
	 * 待添加
	 */
	public static final Object WAIT_ADD = 1;

	/**
	 * 未添加
	 */
	public static final Object NOT_ADD = 0;

	/**
	 * 未关注
	 */
	public static final Object NOT_ATTENTION = 1;

	/**
	 * 已关注
	 */
	public static final Object HAD_ATTENTION = 1;

	/**
	 * 数字ID正则表达式
	 */
	public static final String SID_REGULAR = "^[0-9]*$";

	/**
	 * 症状添加正则
	 */
	public static final String SYS_REGULAR = "^[\\s\\S]{0,20}$";

	/**
	 * 用户IOS手机APP的ID
	 */
	public static final String IOS_USER_APP = "026e5fd301354c7db55d8bc60eff4d4c";

	/**
	 * 用户ANDRIOD手机APP的ID
	 */
	public static final String ANDRIOD_USER_APP = "319cdaa667cc486a9c67b83b7b0b882d";

	/**
	 * 企业ANDRIOD手机APP的ID
	 */
	public static final String ANDRIOD_BUSINESS_APP = "808a85fafaf1477b8b083c8e9f26d0f0";

	/**
	 * ANDRIOD蓝牙APP的ID
	 */
	public static final String ANDRIOD_BLE_APP = "951f533c1e13468a8710c9c1026f0bc5";

	/**
	 * ANDRIOD桌面APP的ID
	 */
	public static final String ANDRIOD_DESKTOP_APP = "951f533c1e13468a8710c9c1026f0bc5";

	/**
	 * ANDRIOD医生APP的ID
	 */
	public static final String ANDRIOD_DOCTOR_APP = "d1194b086ba6405c9119b13cba610495";

	/**
	 * 企业IOS手机APP的ID
	 */
	public static final String IOS_BUSINESS_APP = "d4f78bceafbc4c61a82bc33b5c7edabd";

	/**
	 * IOS医生APP的ID
	 */
	public static final String IOS_DOCTOR_APP = "d583c80b1a844596bc40dd8073daadd3";

	/**
	 * ANDRIOD测量APP的ID
	 */
	public static final String ANDRIOD_TEST_APP = "fecd8d1ff1ab4dc7ab22029a4566bcbd";

	/**
	 * 密码校验规则
	 */
	public static final String PWD_REGULAR = "^[0-9a-z]{32}$";
	
	/**
	 * 绑定参数正则规则
	 */
	public static final String BOUND_REGULAR = "^(0|1)$";
	
	/**
	 * 科室格式正则
	 */
	public static final String DEPARTMENT_REGULAR = "^[\\s\\S]{0,20}$";
	
	/**
	 * 设备编号正则
	 */
//	public static final String NUMBER_REGULAR = "^([a-zA-Z]{3})([0-9]{5})([1-9]{1})([0-9]{4})$";
//	public static final String NUMBER_REGULAR = "^([a-zA-Z]{3})([A-Fa-f0-9]{5})([1-9]{1})([0-9]{4})$";
	public static final String NUMBER_REGULAR = "^([a-zA-Z]{2})([0-9A-Z]{1})([A-Fa-f0-9]{5})([1-9]{1})([0-9]{4})$";
	
	
	/**
	 * 登录安全业务IP限流次数
	 */
	public static final long LIMIT=60;
	/**
	 * 未登录业务IP限流次数
	 */
	public static final long LIMIT_NO_LOGIN=60;
	/**
	 * 生成TOKEN业务IP限流次数
	 */
	public static final long LIMIT_TOKEN=1;

    /**
     * 原始文件已经分析过
     */
	public static final int HAD_ANALYZED = 1;
	
	/**
	 * qt配置文件
	 */
	public static final String QT_CONFIG_NAME = "qt_config.properties";

	/**
	 * 是否为子账号正则
	 */
	public static final String SON_REGULAR = "^(0|1)$";

	/**
	 * 备注的正则表达式
	 */
	public static final String COMMENT_REGULAR = "^[\\s\\S]{1,20}$";
	
	/**
	 * 备注的正则表达式
	 */
	public static final String BLOOD_PRESSURE_REGULAR = "^[1-9][0-9]{0,2}$";
	
	/**
	 * 就诊医院正则表达式
	 */
	public static final String HOSPITAL_REGULAR = "^[\\s\\S]{2,40}$";

	/**
	 * 查询分组患者正则表达式
	 */
	public static final String SATUS_REGULAR = "^(0|1)$";

	/**
	 * 来源正则表达式
	 */
	public static String SOURCE_REGULAR = "^(0|1)$";

	/**
	 * 分组名称正则表达式
	 */
	public static final String GROUP_NAME_REGULAR = "^[\\s\\S]{1,20}$";

	/**
	 * 文件类型
	 */
	public static final String FILE_TYPE = "^(log|scope|air|PPG|result|abnormal|ggt|img|apk|languagepack|bin|data)$";
	
	/**
	 * AES加密与前端约定加密密钥
	 */
	public static final String AES_KEY = "xjkxdapplication";
	
	/**
	 * AES加密16进制字符串
	 */
	public static final String AES_IV_STRING = "A-16-Byte-String";

	//没有redis 暂时用这个
	public static final Map<String,String>  TOKENMAP = new HashMap<>();
}