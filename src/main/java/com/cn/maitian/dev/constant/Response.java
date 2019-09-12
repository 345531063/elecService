/*
 * 文件名：Response.java<br/>
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
package com.cn.maitian.dev.constant;



/**
 * @author zhangxiaodong
 * @version 1.0,2019年12月22日
 */
public class Response
{
	/**
	 * 提示码
	 */
	private int code = ErrorCodeEnum.ERROR.getId();
	
	/**
	 * 原因
	 */
	private String reason = ErrorCodeEnum.ERROR.getCnText();
	
	/**
	 * 结果
	 */
	private Object result = ErrorCodeEnum.ERROR.getEnText();


	/**
	 * 获取提示码
	 *<br/>
	 * @return 提示码
	 */
	public int getCode()
	{
		return code;
	}

	/**
	 * 设置提示码
	 *<br/>
	 * @param code 提示码
	 */ 
	public void setCode(int code)
	{
		this.code = code;
	}

	/**
	 * 原因
	 *<br/>
	 * @return 原因
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * 设置原因
	 *<br/>
	 * @param reason 原因
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * 获取结果
	 *<br/>
	 * @return 结果
	 */
	public Object getResult()
	{
		return result;
	}

	/**
	 * 设置结果
	 *<br/>
	 * @param result 结果
	 */
	public void setResult(Object result)
	{
		this.result = result;
	}

	public void setResult(ErrorCodeEnum errorCodeEnum){
		this.setCode(Integer.valueOf(errorCodeEnum.getCode()));
		this.setResult(errorCodeEnum.getEnText());
		this.setReason(errorCodeEnum.getCnText());
	}
	/**
	 * toString()
	 */
	@Override
	public String toString()
	{
		return "Response [code=" + code + ", reason=" + reason + ", result=" + result + "]";
	}

	public void setResultV1(Object resultV1) {
		this.setCode(Integer.valueOf(ErrorCodeEnum.SUCCESS.getCode()));
		this.setReason(ErrorCodeEnum.SUCCESS.getCnText());
		this.setResult(resultV1);
	}
	/**特殊 临时用 用于产品检查**/
	public void setResultVTemp(Object resultV1) {
		this.setCode(Integer.valueOf(ErrorCodeEnum.NOTDREDGE.getCode()));
		this.setReason(ErrorCodeEnum.NOTDREDGE.getCnText());
		this.setResult(resultV1);
	}
}
