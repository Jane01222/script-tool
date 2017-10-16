package com.xuanwu.dao;

public interface StructureDao extends BaseDao {

	/**
	 * 获取结构（视图、函数、存储过程）的创建脚本
	 * @param objectId 对象的id
	 * @return 脚本
	 */
	public String queryScript(int objectId);
	
	/**
	 * 查询结构的类型
	 * @param objectId 对象id
	 * @return 返回xtype字段
	 */
	public String queryStructureType(int objectId);
	
}
