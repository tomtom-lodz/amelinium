package com.tomtom.amelinium.chartservice.serializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.DoubleRow;
import com.tomtom.amelinium.chartservice.model.DoubleTable;
import com.tomtom.amelinium.chartservice.model.IntRow;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.model.LogRow;
import com.tomtom.amelinium.chartservice.model.Log;
import com.tomtom.amelinium.chartservice.model.Roadmap;
import com.tomtom.amelinium.chartservice.model.RoadmapRow;

/**
 * Transforms chart model into WikiMarkup string.
 * 
 * @author Natasza Nowak
 * 
 */

public class ChartModelSerializer {
	/**
	 * @return serialized version of the model.
	 */
	public String serializeChartModel(ChartModel chartModel,
			BacklogModel backlogModel) {
		ChartConfig chartConfig = chartModel.getChartConfig();
		String result = "";
		result += chartModel.getIntro();
		result += serializeConfigTable(chartModel.getChartConfig());
		result += chartModel.getChartStartMarker();
		result += "\n\n";
		result += serializeIntTable(chartModel.getBurnedPointsTable());
		result += serializeFeatureGroupsTables(chartModel
				.getFeatureGroupsTables());
		result += serializeDoubleTable(chartModel.getIdealTable());
		result += serializeRoadMap(chartModel.getRoadmap(),
				chartModel.getFeatureGroupsTables());
		if (chartConfig.isShowSummary_VALUE()) {
			result += serializeSummary(backlogModel);
		}
		if (chartConfig.isShowLog_VALUE()) {
			result += serializeLog(chartModel.getLog());
		}
		return result;
	}

	private String serializeFeatureGroupsTables(ArrayList<IntTable> tables) {
		String result = "";
		for (IntTable t : tables) {
			result += serializeIntTable(t);
		}
		return result;
	}

	private String serializeIntTable(IntTable t) {
		String result = "";
		result += "|| || ";
		result += t.getTitle();
		result += " ||";
		for (IntRow r : t.getRows()) {
			result += serializeIntRow(r);
		}
		result += "\n\n";
		return result;
	}

	private String serializeIntRow(IntRow r) {
		String result = "";
		result += "\n| ";
		result += r.getSprint();
		result += " | ";
		result += r.getValue();
		result += " |";
		return result;
	}

	private String serializeDoubleTable(DoubleTable t) {
		String result = "";
		result += "|| || ";
		result += t.getTitle();
		result += " ||";
		for (DoubleRow r : t.getRows()) {
			result += serializeDoubleRow(r);
		}
		result += "\n";
		return result;
	}

	private String serializeDoubleRow(DoubleRow r) {
		String result = "";
		result += "\n| ";
		result += r.getSprint();
		result += " | ";
		result += r.getValue();
		result += " |";
		return result;
	}

	private String serializeRoadMap(Roadmap roadMap, ArrayList<IntTable> tables) {
		String result = "";
		result += "{chart}\n\nh3. Roadmap\n";
		result += MarkupConfig.ROADMAP_HEADER;
		ArrayList<RoadmapRow> roadMapRows = roadMap.getRows();

		Collections.sort(roadMapRows, new Comparator<RoadmapRow>() {
			public int compare(RoadmapRow dr1, RoadmapRow dr2) {
				return dr1.getSprint() - dr2.getSprint();
			}
		});

		for (RoadmapRow roadMapRow : roadMapRows) {
			result += serializeDateRow(roadMapRow);
		}

		result += "\n";
		return result;
	}

	private String serializeDateRow(RoadmapRow roadMapRow) {
		String result = "";
		result += "\n| ";
		result += roadMapRow.getFeatureGroup();
		result += " | ";
		result += roadMapRow.getSprint();
		result += " | ";
		result += roadMapRow.getEndOfDevelopment();
		result += " | ";
		result += roadMapRow.getDeploymentToProduction();
		result += " |";
		return result;
	}

	private String serializeSummary(BacklogModel backlogModel) {
		String result = "";
		String name = "";
		ArrayList<FeatureGroup> featureGroups = backlogModel
				.getFeatureGroupsFromAllSubProjects();
		result += "\nSUMMARY\n";
		result += "|| Burned story points | "
				+ backlogModel.getOverallBurnedStoryPoints() + " ||\n\n";
		result += "|| Feature Group || Story Points ||\n";
		for (FeatureGroup fg : featureGroups) {
			name = fg.getContentLeft();
			name = name.replace("h3. ", "");
			name = name.replaceAll(" - $", "");
			name += fg.getContentRight();
			result += "|" + name + "|" + fg.getCummulativePoints() + "|\n";
		}

		return result;
	}

	private String serializeLog(Log log) {
		String result = "";
		result += "\n";
		result += MarkupConfig.LOG_START_MARKER;
		result += "\n";
		result += MarkupConfig.LOG_TABLE_HEADER;
		for (LogRow r : log.getRows()) {
			result += "\n| ";
			result += r.getTable();
			result += " | ";
			result += (r.isMatched()) ? "(/)" : "(!)";
			result += " | ";
			result += (r.isCorrected()) ? "(/)" : "(!)";
			result += " | ";
			result += (r.getFgFromBacklog());
			result += " |";
		}
		result += "\n";
		result += log.getMessage();
		return result;
	}

	private String serializeConfigTable(ChartConfig chartConfig) {
		String result = "";
		String goBack_value = ((chartConfig.isComputeIdeal_VALUE() == true) ? String
				.valueOf(chartConfig.getGoBack_VALUE()) : String
				.valueOf(chartConfig.isComputeIdeal_VALUE()));
		result += "\n";
		result += MarkupConfig.CONFIG_TABLE_HEADER;
		result += "\n| ";
		result += chartConfig.getCurrentSprint_KEY() + " | "
				+ chartConfig.getCurrentSprint_VALUE();
		result += " |\n| ";
		result += chartConfig.getSprintLength_KEY() + " | "
				+ chartConfig.getSprintLength_VALUE()
				/ chartConfig.getDayLength();
		result += " |\n| ";
		result += chartConfig.getZeroethSprint_KEY() + " | "
				+ chartConfig.getZeroethSprint_VALUE();
		result += " |\n| ";
		result += chartConfig.getGoBack_KEY() + " | " + goBack_value;
		result += " |\n| ";
		result += chartConfig.getExtendIdeal_KEY() + " | "
				+ chartConfig.isExtendIdeal_VALUE();
		result += " |\n| ";
		result += chartConfig.getAddMissingToChart_KEY() + " | "
				+ chartConfig.isAddMissingToChart_VALUE();
		result += " |\n| ";
		result += chartConfig.getAddMissingToRoadMap_KEY() + " | "
				+ chartConfig.isAddMissingToRoadMap_VALUE();
		result += " |\n| ";
		result += chartConfig.getNamesOnRoadmapFrom_KEY() + " | "
				+ chartConfig.getNamesOnRoadmapFrom_VALUE();
		result += " |\n| ";
		result += chartConfig.getDarkMatterPercentage_KEY() + " | "
				+ (int) chartConfig.getDarkMatterPercentage_VALUE();
		result += " |\n| ";
		result += chartConfig.getShowSummary_KEY() + " | "
				+ chartConfig.isShowSummary_VALUE();
		result += " |\n| ";
		result += chartConfig.getShowLog_KEY() + " | "
				+ chartConfig.isShowLog_VALUE();
		result += " |\n\n";

		return result;
	}

}
