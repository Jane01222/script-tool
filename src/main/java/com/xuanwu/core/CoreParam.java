package com.xuanwu.core;

public class CoreParam {

	/*** 配置中的替换符号 ***/
	public static final String COL_SPLIT_REG = ",";
	public static final String PARAM_REPLACE = "#";
	
	/*** 脚本的必要配置文件 ***/
	public static final String TABLE_CONFIG_NAME = "table-config.properties";
	public static final String COMMON_SQL_NAME = "common-sql.properties";
	
	/*** 文件后缀名 ***/
	public static final String PARAM_FILE_SUFFIX = ".txt";
	public static final String SCRIPT_FILE_SUFFIX = ".sql";
	
	/*** 文件的路径key ***/
	public static final String QUERY_PARAM_PATH_KEY = "paramPath";
	public static final String DATA_SCRIPT_PATH_KEY = "scriptDataPath";
	public static final String STRUCTURE_SCRIPT_PATH_KEY = "scriptStructurePath";
	public static final String WORK_PATH_KEY = "workPath";
	
	/*** 公共查询方法的key ***/
	public static final String QUERY_ALL_COL_KEY = "queryAllColumn";
	public static final String QUERY_FILTER_COL_KEY = "queryColumn";
	public static final String QUERY_SCRIPT_KEY = "queryScript";
	public static final String QUERY_STRUCTURE_TYPE_KEY = "queryStructureType";
	public static final String DISABLE_CHECK_KEY = "disableCheck";
	public static final String ENABLE_CHECK_KEY = "enableCheck";
	
	/*** 表的配置属性 ***/
	public static final String[] TABLE_CONFIG_KEYS = {"replace"};
	public static final String TABLE_CONFIG_REPLACE_KEY = "replace";
	public static final String TABLE_CONFIG_PARAM_PATH_NAME_KEY = "paramPathName";
	public static final String TABLE_CONFIG_PARAM_TITLE_KEY = "paramTitle";
	public static final String TABLE_CONFIG_ORDER_BY_KEY = "orderBy";
}
