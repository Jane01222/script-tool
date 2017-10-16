package com.xuanwu.service.impl;

import java.util.Properties;

import com.xuanwu.core.CoreParam;
import com.xuanwu.dao.BaseDao;
import com.xuanwu.service.BaseService;
import com.xuanwu.utils.PropertiesUtil;

public abstract class BaseServiceImpl implements BaseService {

	protected Properties tableConfProp = PropertiesUtil.tableConfProp;

	protected String paramPath;

	protected BaseDao baseDao;

	public BaseServiceImpl() {
		if (tableConfProp != null) {
			paramPath = tableConfProp.getProperty(CoreParam.QUERY_PARAM_PATH_KEY);
		}
	}

	public void execScript(String script) {
		baseDao.execScript(script);
	}

}
