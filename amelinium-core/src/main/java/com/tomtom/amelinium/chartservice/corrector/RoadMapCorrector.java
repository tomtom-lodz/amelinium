package com.tomtom.amelinium.chartservice.corrector;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.model.Line;
import com.tomtom.amelinium.chartservice.model.Roadmap;
import com.tomtom.amelinium.chartservice.model.RoadmapRow;

public class RoadMapCorrector {
	/**
	 * Method for correcting "under the chart table" with newly calculated
	 * sprint numbers and dates of development end.
	 */
	public void correctRoadmap(ArrayList<IntTable> featureGroupsTables, Line line, Roadmap roadMap, ArrayList<FeatureGroup> featureGroups, ChartConfig chartConfig) {
		boolean matched;
		
		for (FeatureGroup fg : featureGroups) {
			matched = false;
			for (RoadmapRow row : roadMap.getRows()) {
				if (fg.getContentLeft().toLowerCase().contains(row.getFeatureGroup().toLowerCase())) {
					correctRoadmapRow(fg, row, line, chartConfig);
					matched = true;
				}
			}
			if (matched == false && chartConfig.isAddMissingToRoadMap_VALUE()) {
				createAndAddNewRoadMapRow(roadMap, fg, line, chartConfig);
			}
		}
		if (chartConfig.getNamesOnRoadmapFrom_VALUE().equals("backlog")) {
			setNamesOnRoadMapFromBacklog(roadMap, featureGroups);

		} else if (chartConfig.getNamesOnRoadmapFrom_VALUE().equals("chart")) {
			setNamesOnRoadMapFromChart(roadMap, featureGroupsTables);
		}

	}

	private void correctRoadmapRow(FeatureGroup fg, RoadmapRow row, Line line, ChartConfig chartConfig) {
		row.setEndOfDevelopment(calculateEndOfDevelopment(fg, line, chartConfig));
		row.setSprint(calculateLastSprint(fg, line, chartConfig));

	}

	private String calculateEndOfDevelopment(FeatureGroup fg, Line line, ChartConfig chartConfig) {
		long sprint;
		String newEndOfDevelopment = "";
		sprint = calculateLastSprint(fg, line, chartConfig);

		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

		try {
			Date date = formatter.parse(chartConfig.getZeroethSprint_VALUE());
			Date newDate = new Date(date.getTime() + chartConfig.getSprintLength_VALUE() * sprint);
			newEndOfDevelopment = formatter.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newEndOfDevelopment;
	}

	private int calculateLastSprint(FeatureGroup fg, Line line, ChartConfig chartConfig) {
//		int sprint = (int) Math.ceil((fg.getCummulativePoints() - line.getBias()) / line.getSlope());
		
		double slope1 = line.getSlope()*chartConfig.getDarkMatterPercentage_VALUE()/100.;
		Line line1 = new Line(slope1, fg.getCummulativePoints()-slope1*chartConfig.getCurrentSprint_VALUE());

		// compute intersection of two lines
		int sprint = (int) Math.ceil( (line1.getBias() - line.getBias()) / (line.getSlope() - line1.getSlope()));
		
		return sprint;
	}

	/**
	 * Method for creating new rows of the date table, in case not all of the
	 * Feature Group tables on the chart were matched to the date table.
	 */
	private void createAndAddNewRoadMapRow(Roadmap roadMap, FeatureGroup fg, Line line, ChartConfig chartConfig) {
		String fgTitle = fg.getContentLeft() + fg.getContentRight();
		fgTitle = fgTitle.replace("h3. ", "");
		fgTitle = fgTitle.replaceAll(" - $", "");
		int sprint = calculateLastSprint(fg, line, chartConfig);
		String endOfDevelopment = calculateEndOfDevelopment(fg, line, chartConfig);
		String deploymentToProduction = "unknown";
		RoadmapRow roadMapRow = new RoadmapRow(fgTitle, sprint, endOfDevelopment, deploymentToProduction);
		if (sprint >= chartConfig.getCurrentSprint_VALUE()) {
			roadMap.getRows().add(roadMapRow);
		}
	}

	private void setNamesOnRoadMapFromChart(Roadmap roadMap, ArrayList<IntTable> featureGroupsTables) {
		for (IntTable table : featureGroupsTables) {
			for (RoadmapRow row : roadMap.getRows()) {
				if (row.getFeatureGroup().contains(table.getTitle())) {
					row.setFeatureGroup(table.getTitle());
				}
			}
		}
	}

	private void setNamesOnRoadMapFromBacklog(Roadmap roadMap, ArrayList<FeatureGroup> featureGroups) {
		String fgTitle;
		for (FeatureGroup fg : featureGroups) {
			for (RoadmapRow row : roadMap.getRows()) {
				if (fg.getContentLeft().contains(row.getFeatureGroup())) {
					fgTitle = fg.getContentLeft() + fg.getContentRight();
					fgTitle = fgTitle.replace("h3. ", "");
					fgTitle = fgTitle.replaceAll(" - $", "");
					row.setFeatureGroup(fgTitle);
				}
			}
		}

	}
}
