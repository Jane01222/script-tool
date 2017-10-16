package com.xuanwu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.xuanwu.core.CoreParam;

public class PropertiesUtil {

	public static Properties commonProp;

	public static Properties tableConfProp;

	static {
		commonProp = new Properties();
		tableConfProp = new Properties();
		InputStream commonPropIn = PropertiesUtil.class.getClassLoader().getResourceAsStream(CoreParam.COMMON_SQL_NAME);
		InputStream tableConfPropIn = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream(CoreParam.TABLE_CONFIG_NAME);
		try {
			commonProp.load(commonPropIn);
			tableConfProp.load(tableConfPropIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
