package com.xuanwu.dao;

public interface BaseDao {

	/**
	 * 获取对象的id
	 * @param objectName 对象名字
	 * @return 对象id
	 */
	public int queryObjectId(String objectName);
	
	/**
	 * 将脚本在目标库库中执行。
	 * @param script
	 */
	public void execScript(String script);
	
}
