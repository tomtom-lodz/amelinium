package com.tomtom.amelinium.backlogservice.builders;

import java.util.regex.Matcher;

import com.tomtom.amelinium.backlogservice.config.RegexConfig;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;
import com.tomtom.amelinium.backlogservice.state.WorkItem;

public class FeatureGroupBuilder {
	/**
	 * Builds a feature group based on the line of text given as a parameter. If
	 * the line doesn't contain story points, the building process will be
	 * continued in the next line - the step of the state machine is set to
	 * <code>BUILDING_FEATURE_GROUP</code>. If the line contains story points,
	 * the building process can be finished and feature group added to the
	 * model.
	 * 
	 * @param state
	 *            Current state of the state machine.
	 * @param inputLine
	 *            Line currently being processed.
	 */
	public static void processFeatureGroup(State state, String inputLine) {

		String line = inputLine;

		if (state.getCurrentFeatureGroup() == null) {
			state.setCurrentFeatureGroup(new FeatureGroup());
		}

		if (!line.matches(RegexConfig.WORK_ITEM_LINE_CONTAINS_SP)) {
			if (state.getBacklogModel().isAllowingMulitilineFeatures() == true) {
				continueBuildingFeatureGroup(state, inputLine);
				} else {
					finishBuildingFeatureGroup_MultilineFeaturesDisallowed(state, inputLine);
				}	
		} else {
			finishBuildingFeatureGroup(state, line);
		}
	}

	private static void finishBuildingFeatureGroup_MultilineFeaturesDisallowed(State state, String line) {
		state.getCurrentFeatureGroup().addToContentLeft(line);
		state.getCurrentFeatureGroup().addToContentLeft(" - ");
		state.getCurrentFeatureGroup().setContentRight("");
		state.getCurrentFeatureGroup().setContentMiddle("0sp/0sp");
		addFeatureGroupToModel(state);
		state.setLastFeatureGroup(state.getCurrentFeatureGroup());
		state.setCurrentFeatureGroup(null);
		state.setLastFeature(null);
		state.setStep(Step.DECIDING);	
	}

	/**
	 * Adds all text from the line to the content of the feature group.
	 */

	private static void continueBuildingFeatureGroup(State state, String inputLine) {
		state.getCurrentFeatureGroup().addToContentLeft(inputLine);
		state.setStep(Step.BUILDING_FEATURE_GROUP);
	}

	/**
	 * Parses all data from the line and assigns to feature group, then adds
	 * feature group to the model.
	 */

	private static void finishBuildingFeatureGroup(State state, String line) {
		String contentLeft;
		String contentRight;
		String[] array;
		String contentMiddle = "";
		Matcher matcher = RegexConfig.WORK_ITEM_CONTENT_MIDDLE_PATTERN.matcher(line);
		if (matcher.find()) {
			contentMiddle = matcher.group();
		}

		array = line.split(RegexConfig.WORK_ITEM_CONTENT_MIDDLE);
		if (array.length >= 2) {
			contentLeft = array[0];
			contentRight = array[1];
		} else if (array.length == 1) {
			contentLeft = array[0];
			contentRight = "";
		} else {
			contentLeft = "";
			contentRight = "";
		}

		state.getCurrentFeatureGroup().addToContentLeft(contentLeft);
		state.getCurrentFeatureGroup().setContentMiddle(contentMiddle);
		state.getCurrentFeatureGroup().setContentRight(contentRight);

		addFeatureGroupToModel(state);

		state.setLastFeatureGroup(state.getCurrentFeatureGroup());
		state.setCurrentFeatureGroup(null);
		state.setLastFeature(null);

		state.setStep(Step.DECIDING);
	}

	/**
	 * Adds feature to model.
	 */

	private static void addFeatureGroupToModel(State state) {
		state.getBacklogModel().getLastSubProject().getFeatureGroups().add(state.getCurrentFeatureGroup());
		state.setLastAdded(WorkItem.FEATURE_GROUP);
	}

}
