package com.tomtom.woj.amelinium.trendline;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class PlotTrendLine {

	public DateTime date;
	public double points;
	public double dailyVelocity;
	
	public double getPointsAt(DateTime dateTime) {
		int days = Days.daysBetween(date , dateTime ).getDays();
		return points + days * dailyVelocity;
	}

	public PlotPoint intersection(PlotTrendLine line) {
		PlotPoint point = new PlotPoint();

		int days = Days.daysBetween(date , line.date ).getDays();
		
		double a = dailyVelocity;
		double c = points;
		
		double b = line.dailyVelocity;
		double d = line.dailyVelocity * days + line.points;
		
		double x = Math.ceil((d-c) / (a-b));
		double y = a*x + c;

		point.date = date.plusDays((int)x);
		point.points = y;

		return point;
	}

	@Override
	public String toString() {
		return "PlotTrendLine [date=" + date.toString("yyyy-MM-dd") + ", points=" + points
				+ ", dailyVelocity=" + dailyVelocity + "]";
	}
}
