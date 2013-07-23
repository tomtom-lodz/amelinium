package com.tomtom.amelinium.chartservice.model;

/**
 * Representation of a line function of the form y=ax+b. Can be used while manipulating with the
 * "ideal line".
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class Line {
	/**
	 * Bias (b coefficient) of the line function.
	 */
	private double slope;	

	/**
	 * Slope (a coefficient) of the line function.
	 */
	private double bias;
	
	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public Line(double slope, double bias) {
		super();
		this.slope = slope;
		this.bias = bias;
	}

}
