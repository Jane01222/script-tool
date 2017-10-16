package com.xuanwu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuanwu.core.CoreParam;
import com.xuanwu.dao.DataDao;
import com.xuanwu.entity.Column;
import com.xuanwu.service.DataService;
import com.xuanwu.utils.FileUtil;
import com.xuanwu.utils.JsonUtil;
import com.xuanwu.utils.SqlUtil;

@Service
public class DataServiceImpl extends BaseServiceImpl implements DataService {

	@Resource
	private DataDao dataDao;

	public List<Map<String, String>> queryData(String tableName) {
		int tableId = dataDao.queryObjectId(tableName);
		String allColumn = dataDao.queryColumn(tableId, CoreParam.QUERY_ALL_COL_KEY);
		Column column = new Column(allColumn, null);
		Map<String, Object> conf = JsonUtil.getMap(tableConfProp.getProperty(tableName));
		allColumn = SqlUtil.dealColumn(allColumn, conf);
		String paramFileName;
		if (conf.get(CoreParam.TABLE_CONFIG_PARAM_PATH_NAME_KEY) == null) {
			paramFileName = tableName;
		} else {
			paramFileName = String.valueOf(conf.get(CoreParam.TABLE_CONFIG_PARAM_PATH_NAME_KEY));
		}
		Map<String, Object> paramVal = FileUtil
				.read(paramPath.concat(paramFileName).concat(CoreParam.PARAM_FILE_SUFFIX));
		if (conf.get(CoreParam.TABLE_CONFIG_PARAM_TITLE_KEY) != null) {
			paramVal.put("title", String.valueOf(conf.get(CoreParam.TABLE_CONFIG_PARAM_TITLE_KEY)));
		}
		List<Map<String, String>> param = SqlUtil.getQueryParam(paramVal);
		String sql = SqlUtil.getQuerySql(tableName, allColumn, param, conf);
		// System.out.println(sql);
		return dataDao.queryData(sql, column);
	}

	public String createScript(String tableName) {
		int tableId = dataDao.queryObjectId(tableName);
		String allColumn = dataDao.queryColumn(tableId, CoreParam.QUERY_ALL_COL_KEY);
		Column column = new Column(allColumn, null);
		Map<String, Object> conf = JsonUtil.getMap(tableConfProp.getProperty(tableName));
		allColumn = SqlUtil.dealColumn(allColumn, conf);
		String paramFileName;
		if (conf.get(CoreParam.TABLE_CONFIG_PARAM_PATH_NAME_KEY) == null) {
			paramFileName = tableName;
		} else {
			paramFileName = String.valueOf(conf.get(CoreParam.TABLE_CONFIG_PARAM_PATH_NAME_KEY));
		}
		Map<String, Object> paramVal = FileUtil
				.read(paramPath.concat(paramFileName).concat(CoreParam.PARAM_FILE_SUFFIX));
		if (conf.get(CoreParam.TABLE_CONFIG_PARAM_TITLE_KEY) != null) {
			paramVal.put("title", String.valueOf(conf.get(CoreParam.TABLE_CONFIG_PARAM_TITLE_KEY)));
		}
		List<Map<String, String>> param = SqlUtil.getQueryParam(paramVal);
		String sql = SqlUtil.getQuerySql(tableName, allColumn, param, conf);
		// System.out.println(sql);
		List<Map<String, String>> data = dataDao.queryData(sql, column);
		StringBuffer buffer = new StringBuffer(SqlUtil.getDeleteSql(tableName, param))
				.append(SqlUtil.assembleScript(data, tableName));
		return buffer.toString();
	}

	@PostConstruct
	public void injectBaseDao() {
		super.baseDao = this.dataDao;
	}

}
