package com.terrynow.springtest.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @date 2014年4月16日 下午12:21:10
 * @description 自定义返回JSON 数据格中日期格式化处理
 */
public class CustDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jsonGenerator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jsonGenerator.writeString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(value));
	}

}
