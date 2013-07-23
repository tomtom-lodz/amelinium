package com.tomtom.amelinium.chartservice.model;
/**
 * Represents the row if the log table.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class LogRow {
	/**
	 * Title of the feature group table represented on the chart.
	 */
	private String table = "";
	/**
	 * Information whether the 'table' field matched to any of the feature
	 * groups from backlog.
	 */
	private boolean matched = false;
	/**
	 * Information whether the table that corresponds to the 'table' string was
	 * corrected by this application. A 'matched' table is not corrected if its
	 * finish sprint number was smaller than the current sprint.
	 */
	private boolean corrected = false;
	/**
	 * Title of the feature group from backlog that matched the one represented
	 * by the 'table' string.
	 */
	private String fgFromBacklog = "";

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public boolean isCorrected() {
		return corrected;
	}

	public void setCorrected(boolean corrected) {
		this.corrected = corrected;
	}

	public String getFgFromBacklog() {
		return fgFromBacklog;
	}

	public void setFgFromBacklog(String fgFromBacklog) {
		this.fgFromBacklog = fgFromBacklog;
	}

	public LogRow(String table) {
		super();
		this.table = table;
	}

	public LogRow(String table, boolean matched, String fgFromBacklog) {
		super();
		this.table = table;
		this.matched = matched;
		this.fgFromBacklog = fgFromBacklog;
	}

	public LogRow() {
		super();
	}

	public LogRow(String table, boolean matched, boolean corrected,
			String fgFromBacklog) {
		super();
		this.table = table;
		this.matched = matched;
		this.corrected = corrected;
		this.fgFromBacklog = fgFromBacklog;
	}
}
