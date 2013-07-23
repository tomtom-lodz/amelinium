package com.tomtom.amelinium.projectservice.visualisation;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder;
import com.tomtom.amelinium.backlogservice.corrector.BacklogModelCorrector;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.chartservice.builders.ChartModelBuilder;
import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.DoubleRow;
import com.tomtom.amelinium.chartservice.model.IntRow;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.model.Log;
import com.tomtom.amelinium.chartservice.model.LogRow;
import com.tomtom.amelinium.chartservice.model.Roadmap;
import com.tomtom.amelinium.chartservice.model.RoadmapRow;
import com.tomtom.amelinium.common.LineProducer;
import com.tomtom.amelinium.db.config.Config;

/**
 * Transforms chart model into Google Chart Tools JSP string.
 * 
 * @author Natasza Nowak
 * 
 */
public class JSPChartSerializer {

	/**
	 * Converts wiki markup content of a chart to its JSP Google Visualization
	 * API version. Before that the models of backlog and chart are built so
	 * that they can be corrected with respect to one another. Then the chart is
	 * serialized from backlog and chart models.
	 * 
	 * @return JSP serialized version of the model.
	 */

	public JSPChart serializeWikiMarkupContents(String wikiMarkupBacklog,
			String wikiMarkupChart) {
		LineProducer producer = new LineProducer();

		ChartModelBuilder chartModelBuilder = new ChartModelBuilder();
		ChartCorrector chartCorrector = new ChartCorrector();
		BacklogModelBuilder backlogModelBuilder = new BacklogModelBuilder();
		BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
		ArrayList<String> chartLines = producer
				.readLinesFromString(wikiMarkupChart);
		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);

		ArrayList<String> backlogLines = producer
				.readLinesFromString(wikiMarkupBacklog);
		BacklogModel backlogModel = backlogModelBuilder.buildBacklogModel(
				backlogLines, Config.allowingMultilineFeatures);

		backlogModelCorrector.correctModelPoints(backlogModel);
		chartCorrector.correctTables(backlogModel, chartModel);

		JSPChart jspChart = serializeChartModel(backlogModel, chartModel);
		return jspChart;

	}

	private JSPChart serializeChartModel(BacklogModel backlogModel,
			ChartModel chartModel) {

		String configuration = serializeConfiguration(chartModel
				.getChartConfig());
		String chart = serializeChart(chartModel);
		String roadmap = serializeRoadmap(chartModel.getRoadmap());
		String burnedTable = serializeBurnedTable(backlogModel
				.getOverallBurnedStoryPoints());
		String summary = serializeSummary(backlogModel
				.getFeatureGroupsFromAllSubProjects());
		String log = serializeLogTable(chartModel.getLog());
		String logMessage = chartModel.getLog().getMessage();

		JSPChart jspChart = new JSPChart(configuration, chart, roadmap,
				burnedTable, summary, log, logMessage);

		return jspChart;
	}

	/**
	 * @return JSP-serialized version of the chart configuration.
	 */
	public String serializeConfiguration(ChartConfig chartConfig) {
		String result = "";
		String goBack_value = ((chartConfig.isComputeIdeal_VALUE() == true) ? String
				.valueOf(chartConfig.getGoBack_VALUE()) : String
				.valueOf(chartConfig.isComputeIdeal_VALUE()));
		result += "\n";
		result += "[ 'Configuration' , ' '],";
		result += "\n[ '";
		result += chartConfig.getCurrentSprint_KEY().replace("*", "") + "' , '"
				+ chartConfig.getCurrentSprint_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getSprintLength_KEY() + "' , '"
				+ chartConfig.getSprintLength_VALUE()
				/ chartConfig.getDayLength();
		result += "' ],\n[ '";
		result += chartConfig.getZeroethSprint_KEY() + "' , '"
				+ chartConfig.getZeroethSprint_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getGoBack_KEY().replace("'", "\"") + "' , '"
				+ goBack_value;
		result += "' ],\n[ '";
		result += chartConfig.getExtendIdeal_KEY() + "' , '"
				+ chartConfig.isExtendIdeal_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getAddMissingToChart_KEY() + "' , '"
				+ chartConfig.isAddMissingToChart_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getAddMissingToRoadMap_KEY() + "' , '"
				+ chartConfig.isAddMissingToRoadMap_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getNamesOnRoadmapFrom_KEY() + "' , '"
				+ chartConfig.getNamesOnRoadmapFrom_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getDarkMatterPercentage_KEY() + "' , '"
				+ (int) chartConfig.getDarkMatterPercentage_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getShowSummary_KEY() + "' , '"
				+ chartConfig.isShowSummary_VALUE();
		result += "' ],\n[ '";
		result += chartConfig.getShowLog_KEY() + "' , '"
				+ chartConfig.isShowLog_VALUE();
		result += "' ]]);\n\n";
		return result;

	}

	/**
	 * @return JSP-serialized version of the chart.
	 */
	public String serializeChart(ChartModel chartModel) {
		int tablesTotal = 0;
		if (chartModel.getBurnedPointsTable().getRows().size() != 0) {
			tablesTotal += 1;
		}
		if (chartModel.getIdealTable().getRows().size() != 0) {
			tablesTotal += 1;
		}
		tablesTotal += chartModel.getFeatureGroupsTables().size();

		ArrayList<Double[]> temp = new ArrayList<Double[]>();

		for (IntRow row : chartModel.getBurnedPointsTable().getRows()) {
			Double[] tab = new Double[tablesTotal + 1];
			tab[0] = (double) row.getSprint();
			tab[1] = (double) row.getValue();
			temp.add(tab);
		}

		for (int i = 0; i < chartModel.getFeatureGroupsTables().size(); i++) {
			IntTable table = chartModel.getFeatureGroupsTables().get(i);
			for (IntRow row : table.getRows()) {
				Double[] tab = new Double[tablesTotal + 1];
				tab[0] = (double) row.getSprint();
				tab[i + 2] = (double) row.getValue();
				temp.add(tab);
			}
		}

		for (DoubleRow row : chartModel.getIdealTable().getRows()) {
			Double[] tab = new Double[tablesTotal + 1];
			tab[0] = (double) row.getSprint();
			tab[tablesTotal] = (double) row.getValue();
			temp.add(tab);
		}

		String result = "[ ' '";
		if (chartModel.getBurnedPointsTable().getRows().size() != 0) {
			result += ", '";
			result += chartModel.getBurnedPointsTable().getTitle();
			result += "'";
		}

		for (IntTable table : chartModel.getFeatureGroupsTables()) {
			result += ", '";
			result += table.getTitle();
			result += "'";
		}
		if (chartModel.getIdealTable().getRows().size() != 0) {
			result += ", '";
			result += chartModel.getIdealTable().getTitle();
			result += "'";
		}
		result += " ]";

		for (Double[] tab : temp) {
			result += ",\n[ ";
			result += tab[0];
			for (int i = 1; i < tab.length; i++) {
				result += ", ";
				result += tab[i];
			}
			result += " ]";
		}
		return result;
	}

	public String serializeRoadmap(Roadmap t) {
		String result = "";
		result += "[ 'Feature Group' , 'Sprint' , 'End of development' , 'Deployment to production' ]";
		for (RoadmapRow r : t.getRows()) {
			result += serializeDateRow(r);
		}
		result += "\n";
		// roadmapSerialized = result;
		return result;
	}

	public String serializeDateRow(RoadmapRow r) {
		String result = "";
		result += ",\n[ '";
		result += r.getFeatureGroup();
		result += "' , ";
		result += r.getSprint();
		result += " , '";
		result += r.getEndOfDevelopment();
		result += "' , '";
		result += r.getDeploymentToProduction();
		result += "' ]";
		return result;
	}

	public String serializeBurnedTable(int burnedPoints) {
		String result = "";
		result = "[ 'Burned story points' , '" + burnedPoints + "' ]";
		// burnedTableSerialized = result;
		return result;
	}

	public String serializeSummary(ArrayList<FeatureGroup> featureGroups) {
		String result = "";
		String name = "";
		result += "[ 'Feature Group' , 'Story Points' ]";
		for (FeatureGroup fg : featureGroups) {
			name = fg.getContentLeft();
			name = name.replace("h3. ", "");
			name = name.replaceAll(" - $", "");
			name += fg.getContentRight();
			result += ",\n[ '" + name + "' , " + fg.getCummulativePoints()
					+ " ]";
		}
		// summarySerialized = result;
		return result;
	}

	public String serializeLogTable(Log log) {
		String result = "";
		result += "[ 'Table' , 'matched?' , 'corrected?' , 'Feature Group from backlog' ]";
		for (LogRow row : log.getRows()) {
			result += serializeLogRow(row);
		}
		// log = result;
		return result;
	}

	private String serializeLogRow(LogRow r) {
		String result = "";
		result += ",\n[ '";
		result += r.getTable();
		result += "' , ";
		result += r.isMatched();
		result += " , ";
		result += r.isCorrected();
		result += " , '";
		result += r.getFgFromBacklog();
		result += "' ]";
		return result;
	}

}
