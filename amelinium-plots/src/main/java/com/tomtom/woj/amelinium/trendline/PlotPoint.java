package com.tomtom.woj.amelinium.trendline;

import org.joda.time.DateTime;

public class PlotPoint {

	public DateTime date;
	public double points;
	
	@Override
	public String toString() {
		return "PlotPoint [date=" + date.toString("yyyy-MM-dd") + ", points=" + points + "]";
	}
}
