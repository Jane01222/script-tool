package com.xuanwu.factory;

import java.util.List;
import java.util.Queue;

import com.xuanwu.core.CoreParam;
import com.xuanwu.core.FactoryMode;
import com.xuanwu.service.DataService;
import com.xuanwu.utils.FileUtil;
import com.xuanwu.utils.PropertiesUtil;

public class DataFactory {

	private DataService dataService;

	private Queue<String> queue;

	private String rootPath;

	private String dataScriptPath;

	private String workPath;

	private FactoryMode mode;

	public DataFactory(DataService dataService, Queue<String> queue, FactoryMode mode) {
		this.dataService = dataService;
		this.queue = queue;
		this.rootPath = DataFactory.class.getClassLoader().getResource("").getPath();
		this.dataScriptPath = PropertiesUtil.tableConfProp.getProperty(CoreParam.DATA_SCRIPT_PATH_KEY);
		this.workPath = PropertiesUtil.tableConfProp.getProperty(CoreParam.WORK_PATH_KEY);
		this.mode = mode;
	}
	
	public void work() {
		int number = 1;
		/*
		 * 暂时的结构只能跑一条线程。 if (queue.size() >= 2) { number = 2; }
		 */
		for (int i = 0; i < number; i++) {
			new Thread(new DataWorker()).start();
		}
	}

	private class DataWorker implements Runnable {

		public void run() {
			while (!queue.isEmpty()) {
				String source = queue.poll();
				System.out.println("start deal " + source + " ....");
				String path = new StringBuffer(workPath).append(source).toString();
				List<String> items = FileUtil.readItem(path);
				for (String item : items) {
					String dataPath = new StringBuffer(rootPath).append(dataScriptPath).append("data-script")
							.append(CoreParam.SCRIPT_FILE_SUFFIX).toString();
					String script = dataService.createScript(item);
					if (mode.getMode() == FactoryMode.SCRIPT.getMode()) {
						script = new StringBuffer(script).append("\n").toString();
						FileUtil.write(dataPath, script);
					} else {
						String disableCheck = PropertiesUtil.commonProp.getProperty(CoreParam.DISABLE_CHECK_KEY, "");
						String enableCheck = PropertiesUtil.commonProp.getProperty(CoreParam.ENABLE_CHECK_KEY, "");
						script = new StringBuffer(disableCheck).append(script).append(enableCheck).toString();
						script.replaceAll("##ENTERPRISE_NUMBER##", "1008970");
						dataService.execScript(script);
					}
				}
				System.out.println(source + " is over ....");
			}
		}
	}

}
