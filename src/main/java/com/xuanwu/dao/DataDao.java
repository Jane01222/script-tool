package com.xuanwu.dao;

import java.util.List;
import java.util.Map;

import com.xuanwu.entity.Column;

public interface DataDao extends BaseDao {

	/**
	 * 查询表的字段
	 * 
	 * @param tableId
	 *            表的id
	 * @param commonQuery
	 *            查询语句的Key
	 * @return 逗号分隔的字段名字
	 */
	public String queryColumn(int tableId, String commonQuery);

	/**
	 * 查询数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param column
	 *            字段
	 * @return map集合
	 */
	public List<Map<String, String>> queryData(String sql, Column column);

}
