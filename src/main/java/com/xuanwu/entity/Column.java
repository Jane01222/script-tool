package com.xuanwu.entity;

import com.xuanwu.core.CoreParam;

public class Column {

	public Column() {

	}

	public Column(String allColumn, String primaryColumn) {
		this.allColumn = allColumn;
		this.primaryColumn = primaryColumn;
	}

	private String allColumn;

	private String[] columns;

	private String primaryColumn;

	private String[] primarys;

	public String getAllColumn() {
		return allColumn;
	}

	public void setAllColumn(String allColumn) {
		this.allColumn = allColumn;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String getPrimaryColumn() {
		return primaryColumn;
	}

	public void setPrimaryColumn(String primaryColumn) {
		this.primaryColumn = primaryColumn;
	}

	public String[] getPrimarys() {
		return primarys;
	}

	public void setPrimarys(String[] primarys) {
		this.primarys = primarys;
	}

	public void strToArray() {
		this.columns = this.allColumn == null ? null : this.allColumn.trim().split(CoreParam.COL_SPLIT_REG);
		this.primarys = this.primaryColumn == null ? null : this.primaryColumn.trim().split(CoreParam.COL_SPLIT_REG);
	}

}
