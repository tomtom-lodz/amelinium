package com.tomtom.amelinium.chartservice.model;

import java.util.ArrayList;
/**
 * Model representation of the log table.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class Log {
	/**
	 * List of the rows of the log table.
	 */
	private ArrayList<LogRow> rows = new ArrayList<LogRow>();
	private String message = "";
	public Log() {
		super();
	}
	public ArrayList<LogRow> getRows() {
		return rows;
	}
	public void setRows(ArrayList<LogRow> rows) {
		this.rows = rows;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
