package com.xuanwu.core;

public enum FactoryMode {

	SCRIPT(0), // 生产脚本模式
	EXECUTE(1); // 往目标数据库执行脚本模式

	private int mode;

	private FactoryMode(int mode) {
		this.mode = mode;
	}

	public int getMode() {
		return this.mode;
	}

	/**
	 * 通过模式的值获取枚举对象
	 * 
	 * @param mode
	 *            模式值
	 * @return 对应的枚举对象，默认是SCRIPT
	 */
	public static FactoryMode getMode(int mode) {
		for (FactoryMode m : FactoryMode.values()) {
			if (m.getMode() == mode) {
				return m;
			}
		}
		return FactoryMode.SCRIPT;
	}
}
