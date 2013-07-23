package com.tomtom.amelinium.chartservice.builders;

import java.util.regex.Matcher;

import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.config.RegexConfig;
import com.tomtom.amelinium.chartservice.state.State;
import com.tomtom.amelinium.chartservice.state.Step;

/**
 * Builds introductory lines that appear on page before the actual chart.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class IntroBuilder {
	/**
	 * Adds each given line to the intro as long as it encounters a tag
	 * indicating the start of the actual chart tables.
	 */
	public static void buildIntro(State state, String line) {
		if (line.contains(MarkupConfig.CHART_START_MARKER)) {
			state.getChartModel().setChartStartMarker(line);
			line = line.replace("}", "|");
			Matcher matcher = RegexConfig.CHART_TITLE_PATTERN.matcher(line);
			if (matcher.matches()) {
				state.getChartModel().setChartTitle("'"+matcher.group(1)+"'");
			}
			state.setStep(Step.DECIDING);
		} else if (line.contains(MarkupConfig.CONFIG_TABLE_HEADER)) {
			state.setStep(Step.SETTING_CONFIG);
		} else {
			state.getChartModel().addToIntro(line);
		}

	}

}
