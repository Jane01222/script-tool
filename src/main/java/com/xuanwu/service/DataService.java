package com.xuanwu.service;

import java.util.List;
import java.util.Map;

public interface DataService extends BaseService {

	/**
	 * 查询数据
	 * @param tableName 表名字
	 * @return 查询到的数据
	 */
	public List<Map<String, String>> queryData(String tableName);
	
	public String createScript(String tableName);
	
}
