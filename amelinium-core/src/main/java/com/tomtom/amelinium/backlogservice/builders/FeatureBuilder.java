package com.tomtom.amelinium.backlogservice.builders;

import java.util.regex.Matcher;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.config.RegexConfig;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;
import com.tomtom.amelinium.backlogservice.state.WorkItem;

public class FeatureBuilder {
	/**
	 * Builds a feature based on the line of text given as a parameter. If the
	 * line doesn't contain story points, the building process will be continued
	 * in the next line - the step of the state machine is set to
	 * <code>BUILDING_FEATURE</code>. If the line contains story points, the
	 * building process can be finished and feature added to the model.
	 * 
	 * @param state
	 *            Current state of the state machine.
	 * @param inputLine
	 *            Line currently being processed.
	 */
	public static void processFeature(State state, String inputLine) {
		if (state.getCurrentFeature() == null) {
			state.setCurrentFeature(new Feature());
		}

		if (!inputLine.matches(RegexConfig.WORK_ITEM_LINE_CONTAINS_SP)) {
			if (state.getBacklogModel().isAllowingMulitilineFeatures() == true) {
			continueBuildingFeature(state, inputLine);
			} else {
				finishBuildingFeature_MultilineFeaturesDisallowed(state, inputLine);
			}			
		} else {
			finishBuildingFeature(state, inputLine);
		}

	}
	
	/**
	 * Adds unvalued story points to the feature that doesn't contain story
	 * points, then adds feature to the model.
	 */
	
	private static void finishBuildingFeature_MultilineFeaturesDisallowed(State state, String line) {
		state.getCurrentFeature().addToContentLeft(line);
		state.getCurrentFeature().addToContentLeft(" - ");
		state.getCurrentFeature().setContentRight("");
		state.getCurrentFeature().setContentMiddle("0sp/0sp");
		addFeatureToModel(state);
		state.setCurrentFeature(null);
		state.setStep(Step.DECIDING);
		
	}

	/**
	 * Adds all text from the line to the content of the feature.
	 */
	private static void continueBuildingFeature(State state, String line) {
		state.getCurrentFeature().addToContentLeft(line);
		state.setStep(Step.BUILDING_FEATURE);
	}

	/**
	 * Parses all data from the line and assigns to feature, then adds feature
	 * to the model.
	 */

	private static void finishBuildingFeature(State state, String line) {
		String contentLeft;
		String contentRight;
		String contentMiddle = "";
		String[] array;
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

		state.getCurrentFeature().addToContentLeft(contentLeft);
		state.getCurrentFeature().setContentMiddle(contentMiddle);
		state.getCurrentFeature().setContentRight(contentRight);

		addFeatureToModel(state);
		state.setCurrentFeature(null);
		state.setStep(Step.DECIDING);
	}

	/**
	 * Adds story to model. If there is no feature group in the model first adds
	 * untitled feature group.
	 */

	private static void addFeatureToModel(State state) {
		// if there is no feature group in the model then add untitled feature
		// group
		if (state.getLastFeatureGroup() == null) {
			FeatureGroup featureGroup = new FeatureGroup(MarkupConfig.FEATURE_GROUP_MARKER + " UNTITLED FEATURE GROUP - ", "", "");
			state.getBacklogModel().getLastSubProject().getFeatureGroups().add(featureGroup);
			state.setLastFeatureGroup(featureGroup);
		}
		state.getLastFeatureGroup().getLastColumn().getFeatures().add(state.getCurrentFeature());
		state.setLastFeature(state.getCurrentFeature());
		state.setLastAdded(WorkItem.FEATURE);

	}

}
