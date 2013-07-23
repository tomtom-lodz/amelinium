package com.tomtom.woj.amelinium.trendline;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tomtom.woj.amelinium.trendline.PlotPoint;
import com.tomtom.woj.amelinium.trendline.PlotTrendLine;

public class TrendTest {

	@Test
	public void testPointAt() {
		// given
		PlotTrendLine line = new PlotTrendLine();
		line.date = new DateTime(2012,1,1,0,0);
		line.points = 10.0;
		line.dailyVelocity = 1.;
		
		// when
		double points2 = line.getPointsAt(new DateTime(2012,1,2,0,0));
		
		// then
		assertEquals(11.0, points2, 1e-6);
	}

	@Test
	public void testIntersection() {
		// given
		PlotTrendLine line1 = new PlotTrendLine();
		line1.date = new DateTime(2012,1,1,0,0);
		line1.points = 10.0;
		line1.dailyVelocity = 1.;
		
		PlotTrendLine line2 = new PlotTrendLine();
		line2.date = new DateTime(2012,1,1,0,0);
		line2.points = 12.0;
		line2.dailyVelocity = 0.;

		// when
		PlotPoint line3 = line1.intersection(line2);
		
		//then
		assertEquals(new DateTime(2012,1,3,0,0), line3.date);
		assertEquals(12.0, line3.points, 1e-6);
	}
}
