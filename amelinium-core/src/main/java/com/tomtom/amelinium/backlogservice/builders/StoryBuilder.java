package com.tomtom.amelinium.backlogservice.builders;

import java.util.regex.Matcher;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.config.RegexConfig;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.Story;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;
import com.tomtom.amelinium.backlogservice.state.WorkItem;

/**
 * A class for building stories and adding them to a model.
 */

public class StoryBuilder {

	/**
	 * Builds a story based on the line of text given as a parameter. If the
	 * line doesn't contain story points, the building process will be continued
	 * in the next line - the step of the state machine is set to
	 * <code>BUILDING_STORY</code>. If the line contains story points, the
	 * building process can be finished and story added to the model.
	 * 
	 * @param state
	 *            Current state of the state machine.
	 * @param inputLine
	 *            Line currently being processed.
	 */

	public static void processStory(State state, String inputLine) {

		if (state.getCurrentStory() == null) {
			state.setCurrentStory(new Story());
		}

		if (!inputLine.matches(RegexConfig.STORY_LINE_CONTAINS_SP)) {
			if (state.getBacklogModel().isAllowingMulitilineFeatures() == true) {
				continueBuildingStory(state, inputLine);
			} else {
				finishBuildingStory_MultilineFeaturesDisallowed(state, inputLine);
			}
		} else {
			// ends with "sp"
			finishBuildingStory(state, inputLine);
		}

	}

	/**
	 * Adds all text from the line to the content of the story.
	 */
	private static void continueBuildingStory(State state, String line) {
		state.getCurrentStory().addToContentLeft(line);
		state.setStep(Step.BUILDING_STORY);

	}

	/**
	 * Adds unvalued story points to the story that doesn't contain story
	 * points, then adds story to the model.
	 */
	private static void finishBuildingStory_MultilineFeaturesDisallowed(State state, String line) {
		state.getCurrentStory().addToContentLeft(line);
		state.getCurrentStory().addToContentLeft(" - ");
		boolean isCrossedOut = checkIfDone(state.getCurrentStory().getContentLeft());
		state.getCurrentStory().setContentRight("");
		state.getCurrentStory().setContentMiddle("?sp");
		state.getCurrentStory().setEstimated(false);
		state.getCurrentStory().setDone(isCrossedOut);
		state.getCurrentStory().setPoints(0);
		addStoryToModel(state);
		state.setCurrentStory(null);
		state.setStep(Step.DECIDING);
	}

	/**
	 * Parses all data from the line and assigns to story, then adds story to
	 * the model.
	 */
	private static void finishBuildingStory(State state, String line) {
		String contentLeft;
		String contentMiddle = "";
		String contentRight;
		String str;
		String[] array;
		int points;
		boolean isEstimated = true;
		boolean isCertain = true;
		boolean isCrossedOut;

		Matcher matcher = RegexConfig.STORY_CONTENT_MIDDLE_PATTERN.matcher(line);

		while (matcher.find()) {
			contentMiddle = matcher.group();
		}
		str = contentMiddle;
		str = str.replace(" ", "");
		str = str.replace("sp", "");
		if (str.equals("?")) {
			isEstimated = false;
			points = 0;
		} else if (str.contains("?")) {
			isCertain = false;
			str = str.replace("?", "");
			try {
				points = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				points = 0;
			}
		} else {
			try {
				points = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				points = 0;
			}
		}
		
		String contentMiddleRegex=contentMiddle.replace("?", "\\?");
		
		array = line.split(contentMiddleRegex);
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

		isCrossedOut = checkIfDone(contentLeft);

		state.getCurrentStory().addToContentLeft(contentLeft);
		state.getCurrentStory().setContentRight(contentRight);
		state.getCurrentStory().setContentMiddle(contentMiddle);
		state.getCurrentStory().setPoints(points);
		state.getCurrentStory().setEstimated(isEstimated);
		state.getCurrentStory().setCertain(isCertain);
		state.getCurrentStory().setDone(isCrossedOut);

		addStoryToModel(state);

		state.setCurrentStory(null);
		state.setStep(Step.DECIDING);
	}

	/**
	 * Adds story to model. If there is no feature in the model first adds
	 * untitled feature to last feature group. If there is no feature group in
	 * the model first adds untitled feature group.
	 */
	private static void addStoryToModel(State state) {

		// if there is no feature group in the model then add untitled feature
		// group
		if (state.getLastFeatureGroup() == null) {
			FeatureGroup featureGroup = new FeatureGroup(MarkupConfig.FEATURE_GROUP_MARKER + " UNTITLED FEATURE GROUP - ", "", "");
			state.getBacklogModel().getLastSubProject().getFeatureGroups().add(featureGroup);
			state.setLastFeatureGroup(featureGroup);
		}

		// if there is no feature in the model then add untitled feature to last
		// feature group
		if (state.getLastFeature() == null) {
			Feature feature = new Feature("UNTITLED FEATURE - ", "", "");
			state.getLastFeatureGroup().getLastColumn().getFeatures().add(feature);
			state.setLastFeature(feature);
		}

		state.getLastFeature().getStories().add(state.getCurrentStory());
		state.setLastStory(state.getCurrentStory());
		state.setLastAdded(WorkItem.STORY);
	}

	/**
	 * Checks if a story is marked as done. Checks if a given line is crossed
	 * out in WikiMarkup.
	 * 
	 * @param leftContent
	 *            line content located between the beginning of the line and the
	 *            story points
	 * @return <code>true</code> if the story is done and <code>false</code> if
	 *         the story is not done
	 */

	private static boolean checkIfDone(String leftContent) {
		boolean isDone = false;
		String str = leftContent;
		str = str.replace("*", "");
		str = str.trim();
		if (str.startsWith("-")) {
			isDone = true;
		}
		if (str.startsWith("_{-}")) {
			isDone = true;
		}
		if (str.matches("^\\{color:#......\\} ?\\{?-.*")) {
			isDone = true;
		}
		return isDone;
	}

}
