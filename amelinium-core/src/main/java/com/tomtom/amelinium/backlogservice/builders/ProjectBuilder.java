package com.tomtom.amelinium.backlogservice.builders;

import java.util.ArrayList;
import java.util.regex.Matcher;

import com.tomtom.amelinium.backlogservice.config.RegexConfig;
import com.tomtom.amelinium.backlogservice.model.SubProject;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;
import com.tomtom.amelinium.backlogservice.state.WorkItem;

public class ProjectBuilder {
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
	public static void processProject(State state, String inputLine) {
		if (state.getBacklogModel().isDividedIntoSubProjects()==false){
			state.getBacklogModel().setProjects(new ArrayList<SubProject>());
		}
		state.getBacklogModel().setDividedIntoSubProjects(true);
		String line = inputLine;

		if (state.getCurrentProject() == null) {
			state.setCurrentProject(new SubProject());
		}

		if (!line.matches(RegexConfig.WORK_ITEM_LINE_CONTAINS_SP)) {
			if (state.getBacklogModel().isAllowingMulitilineFeatures() == true) {
				continueBuildingProject(state, inputLine);
				} else {
					finishBuildingProject_MultilineFeaturesDisallowed(state, inputLine);
				}	
		} else {
			finishBuildingProject(state, line);
		}
	}

	private static void finishBuildingProject_MultilineFeaturesDisallowed(State state, String line) {
		state.getCurrentProject().addToContentLeft(line);
		state.getCurrentProject().addToContentLeft(" - ");
		state.getCurrentProject().setContentRight("");
		state.getCurrentProject().setContentMiddle("0sp/0sp");
		addProjectToModel(state);
		state.setLastProject(state.getCurrentProject());
		state.setCurrentProject(null);
		state.setLastFeature(null);
		state.setStep(Step.DECIDING);	
	}

	/**
	 * Adds all text from the line to the content of the feature group.
	 */

	private static void continueBuildingProject(State state, String inputLine) {
		state.getCurrentProject().addToContentLeft(inputLine);
		state.setStep(Step.BUILDING_PROJECT);
	}

	/**
	 * Parses all data from the line and assigns to feature group, then adds
	 * feature group to the model.
	 */

	private static void finishBuildingProject(State state, String line) {
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

		state.getCurrentProject().addToContentLeft(contentLeft);
		state.getCurrentProject().setContentMiddle(contentMiddle);
		state.getCurrentProject().setContentRight(contentRight);

		addProjectToModel(state);

		state.setLastProject(state.getCurrentProject());
		state.setCurrentProject(null);
		state.setLastFeature(null);

		state.setStep(Step.DECIDING);
	}

	/**
	 * Adds feature to model.
	 */

	private static void addProjectToModel(State state) {
		state.getBacklogModel().getSubProjects().add(state.getCurrentProject());
		state.setLastAdded(WorkItem.PROJECT);
	}

}
