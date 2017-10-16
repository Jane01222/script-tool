package com.xuanwu.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.Resource;

import com.mchange.v2.c3p0.PooledDataSource;
import com.xuanwu.dao.BaseDao;
import com.xuanwu.utils.PropertiesUtil;
import com.xuanwu.utils.SqlUtil;

public abstract class BaseDaoImpl implements BaseDao {

	@Resource(name = "originalDataSource")
	protected PooledDataSource originalDataSource;

	@Resource(name = "destinationDataSource")
	protected PooledDataSource destionationDataSource;

	protected Properties commonProp = PropertiesUtil.commonProp;

	public int queryObjectId(String objectName) {

		String originalSql = commonProp.getProperty("queryObjectId");
		String sql = SqlUtil.dealSql(originalSql, objectName);
		Connection connection = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			connection = originalDataSource.getConnection();
			stat = connection.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(connection, stat, rs);
		}
		return 0;
	}

	public void execScript(String script) {
		Connection connection = null;
		Statement stat = null;
		try {
			connection = destionationDataSource.getConnection();
			stat = connection.createStatement();
			stat.execute(script);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(connection, stat, null);
		}
	}
	
	public void close(Connection connection, Statement stat, ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
			if(stat != null){
				stat.close();
			}
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
