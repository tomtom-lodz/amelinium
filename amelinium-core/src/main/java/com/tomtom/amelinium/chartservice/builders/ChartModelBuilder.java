package com.tomtom.amelinium.chartservice.builders;

import java.util.ArrayList;

import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.state.State;
import com.tomtom.amelinium.chartservice.state.Step;

/**
 * Builds model of the chart. Consumes array of lines and converts it into model
 * of chart.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ChartModelBuilder {
	/**
	 * Builds chart model out of the given array of lines. Implements state
	 * machine. Performs {@link ChartModelBuilder#consumeLine(State, String)} on
	 * each line that is updating the state and incrementally building the
	 * model.
	 */
	public ChartModel buildChartModel(ArrayList<String> lines) {
		State state = new State();

		for (String line : lines) {
			if (state.getStep().equals(Step.STOP)) {
				return state.getChartModel();
			}
			consumeLine(state, line);
		}
		return state.getChartModel();
	}

	/**
	 * Implements one step of state machine that builds chart model. Consumes a
	 * line and updates a model according to the current state of the state
	 * machine.
	 * 
	 * @param state
	 *            current state of the state machine
	 * @param inputLine
	 *            a line to be consumed
	 * @return current state of the state machine
	 */
	private void consumeLine(State state, String inputLine) {
		inputLine = inputLine.replace("&nbsp;", " ");
		inputLine = inputLine.replaceAll("\r", "");
		inputLine = inputLine.replace("\\", "");
		inputLine = inputLine.replaceAll(" +", " ");
		updateMachineStep(state, inputLine);
		updateModel(state, inputLine);
	}

	private void updateMachineStep(State state, String line) {
		if (state.getStep().equals(Step.DECIDING)) {
			if (line.isEmpty()) {
				// skip empty line
			} else if (line.trim().startsWith("|")) {
				if (line.contains(MarkupConfig.IDEAL_TABLE_HEADER)) {
					state.setStep(Step.BUILDING_DOUBLE_TABLE);
				} else if (line.contains(MarkupConfig.ROADMAP_HEADER)) {
					state.setStep(Step.BUILDING_ROADMAP);
				} else if (line.contains(MarkupConfig.CONFIG_TABLE_HEADER)) {
					state.setStep(Step.SETTING_CONFIG);
				} else if (line.contains(MarkupConfig.LOG_TABLE_HEADER)) {
					state.setStep(Step.STOP);
					return;
				} else {
					state.setStep(Step.BUILDING_INT_TABLE);
				}
			} else if (line.contains(MarkupConfig.CHART_MARKER)) {
				state.setStep(Step.SETTING_CONFIG);
			} else if (line.contains(MarkupConfig.CHART_START_MARKER)) {
				state.getChartModel().setChartStartMarker(line);
			} else if (line.contains(MarkupConfig.SUMMARY_START_MARKER)) {
				state.setStep(Step.STOP);
				return;
			}
		}
	}

	private void updateModel(State state, String line) {
		if (state.getStep().equals(Step.DECIDING)) {
			// skip empty lines
		} else if (state.getStep().equals(Step.BUILDING_INTRO)) {
			IntroBuilder.buildIntro(state, line);
		} else if (state.getStep().equals(Step.BUILDING_INT_TABLE)) {
			IntTableBuilder.buildTable(state, line);
		} else if (state.getStep().equals(Step.BUILDING_DOUBLE_TABLE)) {
			DoubleTableBuilder.buildTable(state, line);
		} else if (state.getStep().equals(Step.SETTING_CONFIG)) {
			ConfigBuilder.setConfig(line, state);
		} else if (state.getStep().equals(Step.BUILDING_ROADMAP)) {
			RoadMapBuilder.buildTable(state, line);
		} else if (state.getStep().equals(Step.BUILDING_LOG_TABLE)) {
			LogTableBuilder.buildTable(state, line);
		}
	}

}
