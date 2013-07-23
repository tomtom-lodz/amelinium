package com.tomtom.amelinium.chartservice.model;

import java.util.ArrayList;

/**
 * Representation of the wiki markup table which has integer type values in the
 * second column.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class IntTable {
	/**
	 * Name of the feature group represented by this table.
	 */
	private String title = "";
	/**
	 * List of all the rows of the table.
	 */
	private ArrayList<IntRow> rows = new ArrayList<IntRow>();

	public int getFirstSprint() {
		return getRows().get(0).getSprint();
	}

	public int getFirstValue() {
		return getRows().get(0).getValue();
	}

	public int getLastSprint() {
		return getRows().get(getRows().size() - 1).getSprint();
	}

	public int getLastValue() {
		return getRows().get(getRows().size() - 1).getValue();
	}

	public int getLastSprint(int goBack) {
		if (goBack > this.getRows().size() || goBack == 0) {
			goBack = getRows().size();
		}
		return getRows().get(getRows().size() - goBack).getSprint();
	}

	public int getLastValue(int goBack) {
		if (goBack > this.getRows().size() || goBack == 0) {
			goBack = getRows().size();
		}
		return getRows().get(getRows().size() - goBack).getValue();
	}

	/** used for testing */
	public int getSecondLastSprint() {
		return getRows().get(getRows().size() - 2).getSprint();
	}

	/** used for testing */
	public int getSecondLastValue() {
		return getRows().get(getRows().size() - 2).getValue();
	}

	/** used for testing */
	public int getThirdLastValue() {
		return getRows().get(getRows().size() - 3).getValue();
	}

	public void setLastSprint(int sprint) {
		getRows().get(getRows().size() - 1).setSprint(sprint);
	}

	public void setLastValue(int value) {
		getRows().get(getRows().size() - 1).setValue(value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addRow(IntRow row) {
		this.rows.add(row);
	}

	public ArrayList<IntRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<IntRow> rows) {
		this.rows = rows;
	}

	public IntTable(String title) {
		this.title = title;
	}

	public IntTable() {

	}
}
