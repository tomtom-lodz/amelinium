package com.tomtom.woj.amelinium.plots.burndown;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.trendline.PlotPoint;
import com.tomtom.woj.amelinium.trendline.PlotTrendLine;

public class BurndownModelFactory {

	public BurndownModel createModel(BacklogChunk chunk, double effecticeVelocity) {
		BurndownModel model = new BurndownModel();
		model.merged = chunk;
		
		if(model.merged.dates.size()<1 || model.merged.cols.size()<1) {
			// no data in model
			model.minPoints = model.maxPoints = 0;
			return model;
		}
		
		int lastIdx = chunk.dates.size()-1;
		DateTime lastDate = chunk.dates.get(lastIdx);
		int numColsWithTrends = findNumberOfColumnsWithTrends(chunk);
		
		PlotTrendLine line0 = new PlotTrendLine();
		line0.date = lastDate;
		line0.dailyVelocity = 0;
		line0.points = 0;
		
		// compute trends for releases
		
		model.releasesTrends = new ArrayList<PlotTrendLine>();
		for(int i=0; i<numColsWithTrends; i++) {
			PlotTrendLine trend = new PlotTrendLine();
			trend.date = lastDate;
			trend.points = chunk.cols.get(i).get(lastIdx);
			trend.dailyVelocity = -effecticeVelocity;
			model.releasesTrends.add(trend);
		}
		
		// compute start and end dates by computing intersection points

		model.releasesStartDates = new ArrayList<DateTime>();
		model.releasesEndDates = new ArrayList<DateTime>();
		for(int i=0; i<numColsWithTrends; i++) {
			DateTime firstDate = findFirstNonNanDate(model.merged.dates, model.merged.cols.get(i));
			PlotPoint intersectionPoint = line0.intersection(model.releasesTrends.get(i));
			
			model.releasesStartDates.add(firstDate);
			model.releasesEndDates.add(intersectionPoint.date);
		}
		
		return model;
	}

	private int findNumberOfColumnsWithTrends(BacklogChunk chunk) {
		int lastIdx = chunk.dates.size()-1;
		int i;
		for(i=1; i<chunk.cols.size(); i++) {
			if(Double.isNaN(chunk.cols.get(i).get(lastIdx))) {
				break;
			}
		}
		return i;
	}

	private DateTime findFirstNonNanDate(ArrayList<DateTime> dates, ArrayList<Double> values) {
		DateTime date = dates.get(values.size()-1);
		for(int i=0; i<values.size(); i++) {
			if(!Double.isNaN(values.get(i))) {
				return dates.get(i);
			}
		}
		return date;
	}
	
}
