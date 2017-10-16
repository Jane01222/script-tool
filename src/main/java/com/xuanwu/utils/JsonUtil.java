package com.xuanwu.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	/**
	 * 
	 * 自定义时间格式
	 * 
	 */
	private static SimpleDateFormat format;
	/**
	 * 
	 * ObjectMapper对象
	 * 
	 */
	private static ObjectMapper objectMapper;

	/**
	 * 
	 * 初始化
	 * 
	 */
	static {
		format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(format);
	}

	/**
	 * 
	 * 将obj对象转化成json
	 * 
	 * 
	 * 
	 * @param obj
	 * 
	 *            对象
	 * 
	 * @return 返回json
	 * 
	 */
	public static String getJson(Object obj) {
		String json = null;
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			json = null;
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将json转换为Map集合
	 * 
	 * @param json
	 *            json数据
	 * @return Map集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) {
		Map<String, Object> result = null;
		try {
			result = objectMapper.readValue(json, HashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
