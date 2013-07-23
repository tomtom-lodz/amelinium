package com.tomtom.amelinium.chartservice.model;

/**
 * Representation of the wiki markup table row which has a double value in the
 * second column.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class DoubleRow {
	/**
	 * First column of the row (horizontal axis on the chart).
	 */
	private int sprint;
	/**
	 * Second column of the row (vertical axis on the chart).
	 */
	private double value;

	public int getSprint() {
		return sprint;
	}

	public void setSprint(int sprint) {
		this.sprint = sprint;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DoubleRow(int sprint, double value) {
		super();
		this.sprint = sprint;
		this.value = value;
	}

}
