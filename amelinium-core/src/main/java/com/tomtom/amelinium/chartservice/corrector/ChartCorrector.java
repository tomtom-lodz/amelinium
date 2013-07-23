package com.tomtom.amelinium.chartservice.corrector;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.DoubleTable;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.model.Line;
import com.tomtom.amelinium.chartservice.model.Roadmap;

public class ChartCorrector {

	public static FeatureGroupsTablesCorrector fgTablesCorrector = new FeatureGroupsTablesCorrector();
	public static BurnedPointsTableCorrector bpTableCorrector = new BurnedPointsTableCorrector();
	public static IdealTableCorrector idealTableCorrector = new IdealTableCorrector();
	public static RoadMapCorrector roadMapCorrector = new RoadMapCorrector();

	/**
	 * Method for correcting all the tables that the chart is based on, which
	 * are: table with points burned so far, table with ideal trend, road map
	 * table and tables with all the feature groups.
	 */
	public ChartModel correctTables(BacklogModel backlogModel,
			ChartModel chartModel) {
		DoubleTable idealTable = chartModel.getIdealTable();
		if (idealTable.getRows().size() < 1) {
			idealTable = idealTableCorrector.generateMockIdealTable();
			String msg = "Automatically added required 'ideal' trend line table.";
			chartModel.setIdealTable(idealTable);
			chartModel.getLog().setMessage(msg);
		}
		ArrayList<IntTable> featureGroupsTables = chartModel
				.getFeatureGroupsTables();
		IntTable burnedPointsTable = chartModel.getBurnedPointsTable();
		Roadmap roadMap = chartModel.getRoadmap();
		ArrayList<FeatureGroup> featureGroups = backlogModel
				.getFeatureGroupsFromAllSubProjects();
		int overallBurnedStoryPoints = backlogModel
				.getOverallBurnedStoryPoints();
		ChartConfig chartConfig = chartModel.getChartConfig();

		bpTableCorrector.correctBurnedPointsTable(burnedPointsTable,
				overallBurnedStoryPoints, chartConfig);
		Line line = calculateIdealLineCoefficients(idealTable);
		if (chartConfig.isComputeIdeal_VALUE()) {
			line = idealTableCorrector.computeIdeal(burnedPointsTable,
					idealTable, chartConfig);
		}

		fgTablesCorrector.correctFeatureGroupsTables(backlogModel, chartModel,
				line);
		if (chartConfig.isAddMissingToChart_VALUE()) {
			fgTablesCorrector.addMissingTablesFromBacklogToChart(backlogModel,
					chartModel, line);
		}

		if (chartConfig.isExtendIdeal_VALUE()) {
			idealTableCorrector.correctIdealTable(featureGroupsTables,
					idealTable, line);
		}
		roadMapCorrector.correctRoadmap(featureGroupsTables, line, roadMap,
				featureGroups, chartConfig);
		return chartModel;
	}

	/**
	 * Method for calculating the ideal trend for the chart.
	 */
	private Line calculateIdealLineCoefficients(DoubleTable table) {
		int x1 = table.getFirstSprint();
		double y1 = table.getFirstValue();

		int x2 = table.getLastSprint();
		double y2 = table.getLastValue();
		double slope = 0;
		// to protect from division by 0
		try {
			slope = (y2 - y1) / (x2 - x1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		double bias = table.getFirstValue() - slope * table.getFirstSprint();

		return new Line(slope, bias);
	}
}
