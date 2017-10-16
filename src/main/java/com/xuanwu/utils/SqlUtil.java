package com.xuanwu.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.xuanwu.core.CoreParam;

public class SqlUtil {

	/**
	 * 处理sql，将originalSql中的#替换为对应参数。
	 * 
	 * @param originalSql
	 *            需要处理的sql
	 * @param params
	 *            参数
	 * @return 处理后的sql
	 */
	public static String dealSql(String originalSql, String... params) {
		String[] parts = originalSql.trim().split(CoreParam.PARAM_REPLACE);
		if (params.length == parts.length - 1) {
			StringBuffer buffer = new StringBuffer(parts[0]);
			for (int i = 0; i < params.length; i++) {
				buffer.append(params[i]);
				buffer.append(parts[i + 1]);
			}
			return buffer.toString();
		} else {
			return null;
		}
	}

	/**
	 * 根据配置，处理需要查询的字段。
	 * 
	 * @param allColumn
	 *            需要查询的字段（逗号分隔）
	 * @param conf
	 *            配置
	 * @return 返回处理后的字段
	 */
	@SuppressWarnings("unchecked")
	public static String dealColumn(String allColumn, Map<String, Object> conf) {
		String[] columns = allColumn.trim().split(CoreParam.COL_SPLIT_REG);
		if (conf.get(CoreParam.TABLE_CONFIG_REPLACE_KEY) != null) {
			Map<String, String> replace = (Map<String, String>) conf.get(CoreParam.TABLE_CONFIG_REPLACE_KEY);
			for (int i = 0; i < columns.length; i++) {
				if (replace.get(columns[i]) != null) {
					columns[i] = new StringBuffer("'").append(replace.get(columns[i])).append("' AS ")
							.append(columns[i]).toString();
				}
			}
		}
		return StringUtils.join(columns, CoreParam.COL_SPLIT_REG);
	}

	/**
	 * 将查询的数据，转换为脚本。
	 * 
	 * @param data
	 *            数据
	 * @param tableName
	 *            表的名字
	 * @return 脚本字符串
	 */
	public static String assembleScript(List<Map<String, String>> data, String tableName) {
		StringBuffer result = new StringBuffer();
		if (data != null && data.size() > 0) {
			boolean isFirst = true;
			StringBuffer frontPartBuffer = new StringBuffer("INSERT INTO ").append(tableName).append("(");
			StringBuffer endPartBuffer, oneBuffer;
			for (Map<String, String> record : data) {
				endPartBuffer = new StringBuffer();
				for (Entry<String, String> entry : record.entrySet()) {
					if (isFirst == true) {
						frontPartBuffer.append(entry.getKey()).append(",");
					}
					if (entry.getValue() != null) {
						// 需要将字段中的单引号替换为两个单引号。
						endPartBuffer.append("'").append(entry.getValue().replaceAll("'", "''")).append("'").append(",");
					} else {
						endPartBuffer.append(entry.getValue()).append(",");
					}
				}
				if (isFirst == true) {
					frontPartBuffer.deleteCharAt(frontPartBuffer.length() - 1);
					frontPartBuffer.append(") VALUES(");
				}
				endPartBuffer.deleteCharAt(endPartBuffer.length() - 1);
				oneBuffer = new StringBuffer();
				oneBuffer.append(frontPartBuffer).append(endPartBuffer).append(");\n");
				result.append(oneBuffer);
				isFirst = false;
			}

		}
		return result.toString();
	}

	/**
	 * 获取查询语句sql
	 * 
	 * @param tableName
	 *            表名字
	 * @param queryColumn
	 *            需要查询的字段
	 * @param param
	 *            筛选字段及其值
	 * @return 组装好的sql语句
	 */
	public static String getQuerySql(String tableName, String queryColumn, List<Map<String, String>> param, Map<String, Object> conf) {
		StringBuffer buffer = new StringBuffer("SELECT ");
		buffer.append(queryColumn);
		buffer.append(" FROM ");
		buffer.append(tableName);
		buffer.append(assembleWhere(param));
		if(conf.get(CoreParam.TABLE_CONFIG_ORDER_BY_KEY) != null){
			buffer.append(" ORDER BY ").append(String.valueOf(conf.get(CoreParam.TABLE_CONFIG_ORDER_BY_KEY)));
		}
		return buffer.toString();
	}
	
	public static String getDeleteSql(String tableName, List<Map<String, String>> param){
		StringBuffer buffer = new StringBuffer("DELETE FROM ").append(tableName).append(assembleWhere(param)).append("\n");
		return buffer.toString();
	}
	
	private static String assembleWhere(List<Map<String, String>> param){
		StringBuffer buffer = new StringBuffer(" WHERE 1=2");
		for (Map<String, String> pr : param) {
			buffer.append(" OR\n (1=1");
			for (Map.Entry<String, String> entry : pr.entrySet()) {
				buffer.append(" AND ");
				buffer.append(entry.getKey());
				buffer.append("='");
				buffer.append(entry.getValue());
				buffer.append("'");
			}
			buffer.append(")");
		}
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getQueryParam(Map<String, Object> param) {
		List<Map<String, String>> result = new LinkedList<Map<String, String>>();
		if (param != null && param.get("data") != null) {
			List<String> value = (List<String>) param.get("data");
			String title = (String) param.get("title");
			String[] titles = title.trim().split(CoreParam.COL_SPLIT_REG);
			String[] values = null;
			Map<String, String> data = null;
			for (String str : value) {
				values = str.trim().split(CoreParam.COL_SPLIT_REG);
				data = new HashMap<String, String>();
				for (int i = 0; i < titles.length; i++) {
					data.put(titles[i], values[i]);
				}
				result.add(data);
			}
		}
		return result;
	}

	public static String dealScript(String script){
		script = script.replaceAll("'", "''");
		StringBuffer buffer = new StringBuffer("EXEC('\n").append(script).append("\n')\n");
		return buffer.toString();
	}
}
