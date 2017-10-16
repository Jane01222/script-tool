package com.xuanwu.core;

public enum ScriptMode {

	ALTER(0, "ALTER", "ALTER[\\s]+(PROC|FUNCTION)+?"), CREATE(1, "CREATE", "CREATE[\\s]+(PROC|FUNCTION)+?");
	private Integer mode;
	private String keyword;
	private String regex;

	private ScriptMode(int mode, String keyword, String regex) {
		this.mode = mode;
		this.keyword = keyword;
		this.regex = regex;
	}

	public int getMode() {
		return this.mode;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getRegex() {
		return regex;
	}
	
	public boolean equal(ScriptMode mode) {
		return this.mode == mode.getMode();
	}
	
	public static ScriptMode getScriptMode(int mode){
		for (ScriptMode m : ScriptMode.values()) {
			if(m.getMode() == mode){
				return m;
			}
		}
		return ScriptMode.CREATE;
	}
}
