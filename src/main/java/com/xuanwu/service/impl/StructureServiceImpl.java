package com.xuanwu.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xuanwu.core.ScriptMode;
import com.xuanwu.dao.StructureDao;
import com.xuanwu.service.StructureService;
import com.xuanwu.utils.PropertiesUtil;
import com.xuanwu.utils.SqlUtil;

@Service
public class StructureServiceImpl extends BaseServiceImpl implements StructureService {

	@Resource
	private StructureDao structureDao;

	public String queryScript(String objectName, ScriptMode mode) {
		int objectId = structureDao.queryObjectId(objectName);
		String script = structureDao.queryScript(objectId);
		if (script.equals("")) {
			System.out.println("no script object is " + objectName);
		}
		StringBuffer buffer = new StringBuffer();
		if (mode.equal(ScriptMode.CREATE)) {
			String type = structureDao.queryStructureType(objectId);
			String drop = PropertiesUtil.commonProp.getProperty(type == null ? "" : type.trim());
			if (StringUtils.isNotEmpty(drop) && StringUtils.isNotEmpty(drop.trim())) {
				drop = SqlUtil.dealSql(drop, objectName);
				StringBuffer temp = new StringBuffer(drop).append(" ").append(objectName).append(";");
				buffer.append(SqlUtil.dealScript(temp.toString()));
			}
		} else {
			Pattern pattern = Pattern.compile(ScriptMode.CREATE.getRegex(), Pattern.CASE_INSENSITIVE);
			Pattern headPattern = Pattern.compile(ScriptMode.CREATE.getKeyword(), Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(script);
			if (matcher.find()) {
				String head = matcher.group();
				Matcher headMatcher = headPattern.matcher(head);
				head = headMatcher.replaceFirst(mode.getKeyword());
				script = matcher.replaceFirst(head);
			}
		}
		buffer.append(SqlUtil.dealScript(script)).append("\n");
		return buffer.toString();
	}

	@PostConstruct
	public void injectBaseDao() {
		super.baseDao = structureDao;
	}

}
