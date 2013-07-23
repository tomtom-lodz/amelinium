package com.tomtom.amelinium.chartservice.builders;

import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.extractor.Extractor;
import com.tomtom.amelinium.chartservice.model.DoubleRow;
import com.tomtom.amelinium.chartservice.model.DoubleTable;
import com.tomtom.amelinium.chartservice.state.State;
import com.tomtom.amelinium.chartservice.state.Step;

/**
 * Builds model of the table which has values of double precision in the second
 * column.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class DoubleTableBuilder {
	public static Extractor extractor = new Extractor();

	/**
	 * Adds data from each given line to the table that is currently being
	 * built.
	 */
	public static void buildTable(State state, String line) {
		if (line.contains(MarkupConfig.IDEAL_TABLE_HEADER)) {
			startBuildingTable(state, line);
		} else if (line.isEmpty() || line.contains(MarkupConfig.CHART_MARKER)) {
			finishBuildingTable(state, line);
		} else {
			continueBuildingTable(state, line);
		}
	}

	static void continueBuildingTable(State state, String line) {
		line = line.replace(" ", "");
		line = line.replace("|", " ");
		String arr[] = line.split(" ");
		if (arr.length == 3) {
			int sprint = Integer.parseInt(arr[1]);
			double value = Double.parseDouble(arr[2]);
			DoubleRow row = new DoubleRow(sprint, value);
			state.getCurrentTableDouble().addRow(row);
		}
	}

	private static void finishBuildingTable(State state, String line) {
		state.getChartModel().setIdealTable((state.getCurrentTableDouble()));

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
		title = extractor.extractTitleFromTable(line);
		state.setCurrentTableDouble(new DoubleTable());
		state.getCurrentTableDouble().setTitle(title);
	}
}
