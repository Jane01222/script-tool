package com.xuanwu.service;

import com.xuanwu.core.ScriptMode;

public interface StructureService extends BaseService {

	/**
	 * 查询结构的脚本
	 * @param objectName 对象名字
	 * @param mode 模式（create或者alter）
	 * @return 脚本
	 */
	public String queryScript(String objectName, ScriptMode mode);
	
}
