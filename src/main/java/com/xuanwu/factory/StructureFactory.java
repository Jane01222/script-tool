package com.xuanwu.factory;

import java.util.List;
import java.util.Queue;

import com.xuanwu.core.CoreParam;
import com.xuanwu.core.FactoryMode;
import com.xuanwu.core.ScriptMode;
import com.xuanwu.service.StructureService;
import com.xuanwu.utils.FileUtil;
import com.xuanwu.utils.PropertiesUtil;

public class StructureFactory {

	private StructureService structureService;

	private Queue<String> queue;

	private String rootPath;

	private String structureScriptPath;

	private ScriptMode scriptMode;

	private FactoryMode factoryMode;

	private String workPath;

	public StructureFactory(StructureService structureService, Queue<String> queue, ScriptMode scriptMode,
			FactoryMode factoryMode) {
		this.structureService = structureService;
		this.queue = queue;
		this.rootPath = StructureFactory.class.getClassLoader().getResource("").getPath();
		this.structureScriptPath = PropertiesUtil.tableConfProp.getProperty(CoreParam.STRUCTURE_SCRIPT_PATH_KEY);
		this.scriptMode = scriptMode;
		this.factoryMode = factoryMode;
		this.workPath = PropertiesUtil.tableConfProp.getProperty(CoreParam.WORK_PATH_KEY);
	}

	public void work() {
		int number = 1;
		if (queue.size() >= 2) {
			number = 2;
		}
		for (int i = 0; i < number; i++) {
			new Thread(new StructureWorker()).start();

		}
	}

	private class StructureWorker implements Runnable {
		public void run() {
			while (!queue.isEmpty()) {
				String source = queue.poll();
				System.out.println("start deal " + source + " ....");
				String path = new StringBuffer(workPath).append(source).toString();
				List<String> items = FileUtil.readItem(path);
				for (String item : items) {
					String structurePath = new StringBuffer(rootPath).append(structureScriptPath)
							.append(FileUtil.catSuffix(source)).append(CoreParam.SCRIPT_FILE_SUFFIX).toString();
					String[] i = item.trim().split(CoreParam.COL_SPLIT_REG);
					String script = structureService.queryScript(i[0], ScriptMode.getScriptMode(Integer.valueOf(i[1])));
					if (factoryMode.getMode() == FactoryMode.SCRIPT.getMode()) {
						FileUtil.write(structurePath, script);
					} else {
						structureService.execScript(script);
					}
				}
				System.out.println(source + " is over ....");
			}
		}
	}

}
