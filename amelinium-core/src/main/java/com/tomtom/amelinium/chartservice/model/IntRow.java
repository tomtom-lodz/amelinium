package com.tomtom.amelinium.chartservice.model;
/**
 * Representation of the wiki markup table row which has an integer value in the
 * second column.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class IntRow {
	/**
	 * First column of the row (horizontal axis on the chart).
	 */
	private int sprint;
	/**
	 * Second column of the row (vertical axis on the chart).
	 */
	private int value;

	public int getSprint() {
		return sprint;
	}

	public void setSprint(int sprint) {
		this.sprint = sprint;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public IntRow(int sprint, int value) {
		super();
		this.sprint = sprint;
		this.value = value;
	}

}
