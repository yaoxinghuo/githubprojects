package com.terrynow.springtest.util;

import java.util.Date;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.terrynow.springtest.serializer.CustDateSerializer;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @date 2014年4月16日 下午12:42:47
 * @description
 */
public class CustomObjectMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8859083943056178252L;

	public CustomObjectMapper() {
		SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0,
				0, null, null, null));
		module.addSerializer(Date.class, new CustDateSerializer());
		// module.addDeserializer(Date.class, new DateSerializer());
		// Add more here ...
		registerModule(module);
	}
}
