package com.tomtom.woj.amelinium.plots.common;


public class PlotColors {
	
	private String colors[] = {"#000000", "#4bb2c5", "#EAA228", "#c5b47f", "#579575", "#839557", "#958c12",
	        "#953579", "#4b5de4", "#d8b83f", "#ff5800", "#0085cc"};

	public String getColor(int i) {
		return colors[i % colors.length];
	}
}
