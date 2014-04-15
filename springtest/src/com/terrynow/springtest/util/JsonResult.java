package com.terrynow.springtest.util;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 15, 2014 2:17:34 PM
 * @description
 */
public class JsonResult {
	private boolean result = false;
	private String message;

	public JsonResult(boolean result, String message) {
		this.result = result;
		this.message = message;
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
}
