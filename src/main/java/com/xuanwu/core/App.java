package com.xuanwu.core;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xuanwu.factory.DataFactory;
import com.xuanwu.factory.StructureFactory;
import com.xuanwu.service.DataService;
import com.xuanwu.service.StructureService;
import com.xuanwu.service.impl.DataServiceImpl;
import com.xuanwu.service.impl.StructureServiceImpl;
import com.xuanwu.utils.FileUtil;
import com.xuanwu.utils.PropertiesUtil;

public class App {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		/** 初始化容器 **/
		ApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");
		DataService dataService = app.getBean(DataServiceImpl.class);
		StructureService structureService = app.getBean(StructureServiceImpl.class);

		/** 任务队列 **/
		Queue<String> dataQueue = new ConcurrentLinkedQueue<String>();
		Queue<String> structureQueue = new ConcurrentLinkedQueue<String>();

		/** 删除之前的脚本文件 **/
		String rootPath = App.class.getClassLoader().getResource("").getPath();
		FileUtil.deleteFiles(new StringBuffer(rootPath).append("script").toString());

		/** 读取任务 **/
		List<String> items = FileUtil.readItem("work/work-list.txt");
		for (String item : items) {
			String[] its = item.trim().split(CoreParam.COL_SPLIT_REG);
			if (its[1].equalsIgnoreCase("D")) {
				dataQueue.offer(its[0]);
			} else if (its[1].equalsIgnoreCase("S")) {
				structureQueue.offer(its[0]);
			}
		}

		// 默认为SCRIPT模式
		FactoryMode factoryMode = FactoryMode
				.getMode(Integer.valueOf(PropertiesUtil.tableConfProp.getProperty("factoryMode", "0").trim()));

		/** 开始工作 **/
		DataFactory dataFactory = new DataFactory(dataService, dataQueue, factoryMode);
		dataFactory.work();

		StructureFactory structureFactory = new StructureFactory(structureService, structureQueue, ScriptMode.CREATE,
				factoryMode);
		structureFactory.work();

	}
}
