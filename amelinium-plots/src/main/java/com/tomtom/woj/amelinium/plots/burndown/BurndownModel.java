package com.tomtom.woj.amelinium.plots.burndown;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.trendline.PlotTrendLine;

public class BurndownModel {

	// envelope for plot
	public DateTime minDate;
	public DateTime maxDate;
	public double minPoints;
	public double maxPoints;

	// merged plot data with burned points subtracted
	public BacklogChunk merged;
	
	// trendlines for feature group burndowns
	public ArrayList<PlotTrendLine> releasesTrends;
	public ArrayList<DateTime> releasesStartDates;
	public ArrayList<DateTime> releasesEndDates;

}
