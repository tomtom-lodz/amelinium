package com.tomtom.amelinium.chartservice.builders;

import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.extractor.Extractor;
import com.tomtom.amelinium.chartservice.model.IntRow;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.state.State;
import com.tomtom.amelinium.chartservice.state.Step;
/**
 * Builds model of the table which has integer values in the second
 * column.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class IntTableBuilder {
	public static Extractor extractor = new Extractor();
	/**
	 * Adds data from each given line to the table that is currently being
	 * built.
	 */
	public static void buildTable(State state, String line) {
		if (line.contains(MarkupConfig.HEADER_MARKER)) {
			startBuildingTable(state, line);
		} else if (line.isEmpty() || line.contains(MarkupConfig.CHART_MARKER)) {
			finishBuildingTable(state, line); 
		} else {
			continueBuildingTable(state, line);
		}
	}

private static void continueBuildingTable(State state, String line) {
		line = line.replace(" ", "");
		line = line.replace("|", " ");
		String arr[] = line.split(" ");
		if (arr.length==3) {
			int sprint = Integer.parseInt(arr[1]);
			int value = Integer.parseInt(arr[2]);
			IntRow row = new IntRow(sprint, value);
			state.getCurrentIntTable().addRow(row);
		}
	}

	private static void finishBuildingTable(State state, String line) {
		
		if (state.getCurrentIntTable().getTitle().equals(MarkupConfig.BURNED_SP_TABLE_TITLE)){
			state.getChartModel().setBurnedPointsTable(state.getCurrentIntTable());
		}
		else{
			state.getChartModel().getFeatureGroupsTables().add(state.getCurrentIntTable());
		}
		state.setStep(Step.DECIDING);
		
		if (line.contains(MarkupConfig.CHART_MARKER)) {
			state.setStep(Step.SETTING_CONFIG);
		}
		if (line.contains(MarkupConfig.ROADMAP_HEADER)) {
			state.setStep(Step.BUILDING_ROADMAP);
			RoadMapBuilder.buildTable(state, line);
		}
	}

	private static void startBuildingTable(State state, String line) {
		String title;
		title=extractor.extractTitleFromTable(line);
		state.setCurrentTableInt(new IntTable());
		state.getCurrentIntTable().setTitle(title);
	}
}
