package com.googlecode.jsonplugin.missing;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Aug 15, 2013 8:56:48 PM
 */
public class TextUtils {
	public static final boolean stringSet(String string) {
		return (string != null) && !"".equals(string);
	}

	public static final String noNull(String string, String defaultString) {
		return (stringSet(string)) ? string : defaultString;
	}
}
