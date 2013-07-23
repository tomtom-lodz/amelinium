package com.tomtom.amelinium.chartservice.builders;

import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.model.Roadmap;
import com.tomtom.amelinium.chartservice.model.RoadmapRow;
import com.tomtom.amelinium.chartservice.state.State;
import com.tomtom.amelinium.chartservice.state.Step;

/**
 * Builds a road map model, being the information about feature groups and their
 * finish sprints and dates.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class RoadMapBuilder {
	/**
	 * Adds rows to the road map table according to the input file lines.
	 */
	public static void buildTable(State state, String line) {
		if (line.contains(MarkupConfig.ROADMAP_HEADER)) {
			startBuildingTable(state, line);
		} else if (line.contains(MarkupConfig.LOG_START_MARKER)) {
			state.setStep(Step.BUILDING_LOG_TABLE);
		} else if (line.contains(MarkupConfig.SUMMARY_START_MARKER)) {
			state.setStep(Step.STOP);
			return;
		} else {
			continueBuildingTable(state, line);
		}
	}

	private static void startBuildingTable(State state, String line) {
		state.getChartModel().setRoadmap(new Roadmap());
	}

	private static void continueBuildingTable(State state, String line) {
		String str = line;
		str = str.replaceAll("^\\| ", "");
		str = str.replaceAll(" \\|$", "");
		String arr[] = str.split(" \\| ");
		if (arr.length == 4) {
			String featureGroup = arr[0];
			int sprint = Integer.parseInt(arr[1]);
			String date = arr[2];
			String deploymentToProduction = arr[3];

			RoadmapRow row = new RoadmapRow(featureGroup, sprint, date,
					deploymentToProduction);
			state.getChartModel().getRoadmap().getRows().add(row);
		}
	}

}
