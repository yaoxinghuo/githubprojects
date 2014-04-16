package com.terrynow.springtest.util;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.terrynow.springtest.serializer.CustDateSerializer;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 15, 2014 2:17:34 PM
 * @description 返回给前端的时候一个Object,会被spring转成JSON的
 */
public class JsonResult {
	private boolean result = false;
	private String message;
	private HashMap<String, Object> extra = new HashMap<String, Object>();

	public JsonResult(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public JsonResult() {
	}

	public HashMap<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(HashMap<String, Object> extra) {
		this.extra = extra;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void addKeyValue(String key, Object value) {
		this.extra.put(key, value);
	}

	/**
	 * 
	 * @author xinghuo.yao
	 * @date 2014年4月16日 下午12:53:43
	 * @description 可以在这里用注解来让他特意生成时间格式，或者spring-servlet.xml中去配置ObjectMapper
	 * 
	 * @return
	 */
	@JsonSerialize(using = CustDateSerializer.class)
	public Date getTime() {
		return new Date();
	}
}
