package com.xuanwu.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.xuanwu.core.CoreParam;
import com.xuanwu.dao.StructureDao;
import com.xuanwu.utils.SqlUtil;

@Repository
public class StructureDaoImpl extends BaseDaoImpl implements StructureDao {

	public String queryScript(int objectId) {
		Connection connection = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			String sql = commonProp.getProperty(CoreParam.QUERY_SCRIPT_KEY);
			sql = SqlUtil.dealSql(sql, String.valueOf(objectId));
			connection = originalDataSource.getConnection();
			stat = connection.createStatement();
			rs = stat.executeQuery(sql);
			StringBuffer buffer = new StringBuffer();
			while (rs.next()) {
				buffer.append(rs.getString(1));
			}
			return buffer.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stat, rs);
		}
		return null;
	}

	public String queryStructureType(int objectId) {
		Connection connection = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			String sql = commonProp.getProperty(CoreParam.QUERY_STRUCTURE_TYPE_KEY);
			sql = SqlUtil.dealSql(sql, String.valueOf(objectId));
			connection = originalDataSource.getConnection();
			stat = connection.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stat, rs);
		}
		return null;
	}

}
