package com.tomtom.woj.amelinium.plots.burnup;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.trendline.PlotTrendLine;

public class BurnupModel {

	// envelope for plot
	public DateTime minDate;
	public DateTime maxDate;
	public double minPoints;
	public double maxPoints;

	// merged plot data
	public BacklogChunk merged;
	
	// trendline for burnup
	public PlotTrendLine burnedTrend;
	public DateTime burnedStartDate;
	public DateTime burnedEndDate;
	
	// trendlines for feature groups
	public ArrayList<PlotTrendLine> releasesTrends;
	public ArrayList<DateTime> releasesStartDates;
	public ArrayList<DateTime> releasesEndDates;

}
