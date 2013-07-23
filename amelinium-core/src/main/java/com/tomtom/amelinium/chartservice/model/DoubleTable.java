package com.tomtom.amelinium.chartservice.model;

import java.util.ArrayList;
/**
 * Representation of the wiki markup table which has double type values in the
 * second column.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class DoubleTable {
	/**
	 * Name of the feature group represented by this table.
	 */
	private String title = "";
	/**
	 * List of all the rows of the table.
	 */
	private ArrayList<DoubleRow> rows = new ArrayList<DoubleRow>();

	public int getFirstSprint(){
		return getRows().get(0).getSprint();
	}
	public double getFirstValue(){
		return getRows().get(0).getValue();
	}
	public int getLastSprint(){
		return getRows().get(getRows().size()-1).getSprint();
	}
	public double getLastValue(){
		return getRows().get(getRows().size()-1).getValue();
	}
	/**used for testing*/
	public int getSecondLastSprint(){
		return getRows().get(getRows().size()-2).getSprint();
	}
	/**used for testing*/
	public double getSecondLastValue(){
		return getRows().get(getRows().size()-2).getValue();
	}
	/**used for testing*/
	public double getThirdLastValue(){
		return getRows().get(getRows().size()-3).getValue();
	}
	public void setLastSprint(int sprint){
		getRows().get(getRows().size()-1).setSprint(sprint);
	}
	public void setLastValue(double value){
		getRows().get(getRows().size()-1).setValue(value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addRow(DoubleRow row){
		this.rows.add(row);
	}
	
	public ArrayList<DoubleRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<DoubleRow> rows) {
		this.rows = rows;
	}

}
