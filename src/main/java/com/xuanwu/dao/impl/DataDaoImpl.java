package com.xuanwu.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xuanwu.core.CoreParam;
import com.xuanwu.dao.DataDao;
import com.xuanwu.entity.Column;
import com.xuanwu.utils.SqlUtil;

@Repository
public class DataDaoImpl extends BaseDaoImpl implements DataDao {

	public String queryColumn(int tableId, String commonQuery) {
		String sql = SqlUtil.dealSql(commonProp.getProperty(commonQuery), String.valueOf(tableId));
		StringBuffer buffer = new StringBuffer();
		Connection connection = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			connection = originalDataSource.getConnection();
			stat = connection.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				buffer.append(rs.getString(1));
				buffer.append(CoreParam.COL_SPLIT_REG);
			}
			if (buffer.length() > 1) {
				buffer.deleteCharAt(buffer.length() - 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(connection, stat, rs);
		}
		return buffer.toString();
	}


	public List<Map<String, String>> queryData(String sql, Column column) {
		if(column.getColumns() == null){
			column.strToArray();
		}
		List<Map<String, String>> result = new LinkedList<Map<String,String>>();
		Connection connection = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			connection = originalDataSource.getConnection();
			stat = connection.createStatement();
			rs = stat.executeQuery(sql);
			Map<String, String> param = null;
			while(rs.next()){
				param = new HashMap<String, String>();
				for (String col : column.getColumns()) {
					param.put(col, rs.getString(col));
				}
				result.add(param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(connection, stat, rs);
		}
		return result;
	}
}
