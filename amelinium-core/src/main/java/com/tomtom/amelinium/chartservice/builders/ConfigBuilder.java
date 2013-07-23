package com.tomtom.amelinium.chartservice.builders;

import java.util.regex.Matcher;

import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.config.RegexConfig;
import com.tomtom.amelinium.chartservice.state.State;
import com.tomtom.amelinium.chartservice.state.Step;
/**
 * Builds a configuration of the application as specified in the input file.
 * 
 * @param state
 *            Current state of the state machine.
 * @param inputLine
 *            Line currently being processed.           
 * 
 * @author Natasza Nowak     
 */
public class ConfigBuilder {
	
	public static final void setConfig(String line, State state) {
		if (line.isEmpty()){
			state.setStep(Step.DECIDING);}else 
				
		if (line.contains(MarkupConfig.CHART_START_MARKER)) {
			state.getChartModel().setChartStartMarker(line);
			line = line.replace("}", "|");
			state.setStep(Step.DECIDING);
		}else{
			String str = "";
			ChartConfig chartConfig = state.getChartModel().getChartConfig();

			Matcher matcher = RegexConfig.CONFIG_VALUE_PATTERN.matcher(line);
			if (matcher.matches()) {
				str = matcher.group(1);
			}

			if (line.contains(chartConfig.getSprintLength_KEY())) {
				long sprintLength_VALUE=Long.parseLong(str) * chartConfig.getDayLength();
				chartConfig.setSprintLength_VALUE(sprintLength_VALUE);
			} else if (line.contains(chartConfig.getZeroethSprint_KEY())) {
				chartConfig.setZeroethSprint_VALUE(str);
			} else if (line.contains(chartConfig.getCurrentSprint_KEY())) {
				int currentSprint_VALUE = Integer.parseInt(str);
				chartConfig.setCurrentSprint_VALUE(currentSprint_VALUE);
			} else if (line.contains(chartConfig.getGoBack_KEY())) {
				if (!str.equals("false")) {
					int goBack_VALUE = Integer.parseInt(str);
					chartConfig.setGoBack_VALUE(goBack_VALUE);
					chartConfig.setComputeIdeal_VALUE(true);
				} else {
					chartConfig.setComputeIdeal_VALUE(false);
				}
			} else if (line.contains(chartConfig.getExtendIdeal_KEY())) {	
				boolean extendIdeal_VALUE = Boolean.valueOf(str);
				chartConfig.setExtendIdeal_VALUE(extendIdeal_VALUE); 
			} else if (line.contains(chartConfig.getAddMissingToChart_KEY())) {
				boolean addMissingToChart_VALUE = Boolean.valueOf(str);
				chartConfig.setAddMissingToChart_VALUE(addMissingToChart_VALUE);
			} else if (line.contains(chartConfig.getAddMissingToRoadMap_KEY())) {
				boolean addMissingToRoadMap_VALUE = Boolean.valueOf(str);
				chartConfig.setAddMissingToRoadMap_VALUE(addMissingToRoadMap_VALUE);
			} else if (line.contains(chartConfig.getNamesOnRoadmapFrom_KEY())) {
				chartConfig.setNamesOnRoadmapFrom_VALUE(str);
			} else if (line.contains(chartConfig.getShowSummary_KEY())) {
				boolean showSummary_VALUE = Boolean.valueOf(str);
				chartConfig.setShowSummary_VALUE(showSummary_VALUE);
			} else if (line.contains(chartConfig.getShowLog_KEY())) {
				boolean showLog_VALUE = Boolean.valueOf(str);
				chartConfig.setShowLog_VALUE(showLog_VALUE);
			} else if (line.contains(MarkupConfig.ROADMAP_HEADER)) {
				state.setStep(Step.BUILDING_ROADMAP);
				RoadMapBuilder.buildTable(state, line);
			} else if (line.contains(chartConfig.getDarkMatterPercentage_KEY())) {
				double darkMatterPercentage_VALUE = Double.parseDouble(str); 
				chartConfig.setDarkMatterPercentage_VALUE(darkMatterPercentage_VALUE);
				if(chartConfig.getDarkMatterPercentage_VALUE()<0) {
					chartConfig.setDarkMatterPercentage_VALUE(0);
				} else if(chartConfig.getDarkMatterPercentage_VALUE()>99) {
					chartConfig.setDarkMatterPercentage_VALUE(99);
				}
			}
		}
	}

}