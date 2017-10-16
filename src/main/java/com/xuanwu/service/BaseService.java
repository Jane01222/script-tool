package com.xuanwu.service;

import javax.annotation.PostConstruct;

public interface BaseService {

	public void execScript(String script);
	
	/**
	 * 需要掉用execScript方法的实现类，必须实现这个方法，同时还得加上@PostConstruct
	 * spring容器实例化对象之后，执行的方法。
	 */
	@PostConstruct
	public void injectBaseDao();
}
