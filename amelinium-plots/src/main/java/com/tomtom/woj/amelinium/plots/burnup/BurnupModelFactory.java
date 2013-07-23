package com.tomtom.woj.amelinium.plots.burnup;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.trendline.PlotPoint;
import com.tomtom.woj.amelinium.trendline.PlotTrendLine;

public class BurnupModelFactory {

	public BurnupModel createModel(BacklogChunk chunk, double dailyVelocity, double dailyBlackMatter) {
		BurnupModel model = new BurnupModel();

		model.merged = chunk;
		model.minPoints = model.maxPoints = 0;
		model.minDate = model.maxDate = new DateTime();
		
		if(model.merged.dates.size()<1 || model.merged.cols.size()<1) {
			return model;
		}

		int lastIdx = chunk.dates.size()-1;
		DateTime lastDate = chunk.dates.get(lastIdx);
		int numColsWithTrends = findNumberOfColumnsWithTrends(chunk);
		
		// compute trend for burnup

		model.burnedTrend = new PlotTrendLine();
		model.burnedTrend.date = chunk.dates.get(lastIdx);
		model.burnedTrend.points = chunk.cols.get(0).get(lastIdx);
		model.burnedTrend.dailyVelocity = dailyVelocity;

		// compute trends for releases
		
		model.releasesTrends = new ArrayList<PlotTrendLine>();
		for(int i=1; i<numColsWithTrends; i++) {
			PlotTrendLine trend = new PlotTrendLine();
			trend.date = lastDate;
			trend.points = chunk.cols.get(i).get(lastIdx);
			trend.dailyVelocity = dailyBlackMatter;
			model.releasesTrends.add(trend);
		}
		
		// compute start and end dates by computing intersection points

		model.releasesStartDates = new ArrayList<DateTime>();
		model.releasesEndDates = new ArrayList<DateTime>();
		for(int i=1; i<numColsWithTrends; i++) {
			DateTime firstDate = findFirstNonNanDate(model.merged.dates, model.merged.cols.get(i));
			PlotPoint intersectionPoint = model.burnedTrend.intersection(model.releasesTrends.get(i-1));
			
			model.releasesStartDates.add(firstDate);
			model.releasesEndDates.add(intersectionPoint.date);
		}
		
		// compute start and end date for burnup

		model.burnedStartDate = model.merged.dates.get(0);
		if(numColsWithTrends>1) {
			model.burnedEndDate = model.releasesEndDates.get(numColsWithTrends-2);
		} else {
			model.burnedEndDate = model.merged.dates.get(lastIdx);
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
		for(int i=values.size()-1; i>=0; i--) {
			if(Double.isNaN(values.get(i))) {
				return date;
			}
			date = dates.get(i);
		}
		return date;
	}

}
