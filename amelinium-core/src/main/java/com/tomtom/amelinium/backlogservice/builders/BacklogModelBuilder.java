package com.tomtom.amelinium.backlogservice.builders;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;

/**
 * Builds model of the backlog. Consumes array of lines and converts it into
 * model of backlog.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class BacklogModelBuilder {

	/**
	 * Builds backlog model out of the given array of lines. Implements state
	 * machine. Performs {@link BacklogModelBuilder#consumeLine(State, String)}
	 * on each line that is updating the state and incrementally building the
	 * model.
	 */

	public BacklogModel buildBacklogModel(ArrayList<String> lines, boolean allowingMultilineFeatures) {
		State state = new State();
		state.getBacklogModel().setAllowingMulitilineFeatures(allowingMultilineFeatures);

		for (String line : lines) {
			consumeLine(state, line);
		}

		return state.getBacklogModel();
	}

	/**
	 * Implements one step of state machine that builds backlog model. Consumes
	 * a line and updates a model according to the current state of the state
	 * machine.
	 * 
	 * @param state
	 *            current state of the state machine
	 * @param inputLine
	 *            a line to be consumed
	 * @return current state of the state machine
	 */
	private void consumeLine(State state, String inputLine) {
		String line = inputLine.replaceAll(" +", " ");
		line = inputLine.replace("&nbsp;", " ");

		updateMachineStep(state, line);
		updateModel(state, line);
	}

	private void updateMachineStep(State state, String line) {
		if (state.getStep().equals(Step.DECIDING)) {
			if (line.isEmpty()) {
				state.setPreviousLineEmpty(true);
			} else {
				if (line.trim().startsWith(MarkupConfig.COLUMN_MARKER)) {
					state.setStep(Step.BUILDING_COLUMN);
				} else if (line.trim().startsWith(MarkupConfig.SECTION_TAG)) {
					state.setStep(Step.DECIDING);
				} else if (line.trim().startsWith(MarkupConfig.PROJECT_MARKER)) {
					state.setStep(Step.BUILDING_PROJECT);
				} else if (line.trim().startsWith(MarkupConfig.FEATURE_GROUP_MARKER)) {
					state.setStep(Step.BUILDING_FEATURE_GROUP);
				} else if (line.trim().startsWith(MarkupConfig.FEATURE_MARKER)) {
					state.setStep(Step.BUILDING_FEATURE);
				} else if (line.trim().startsWith(MarkupConfig.STORY_MARKER)) {
					state.setStep(Step.BUILDING_STORY);
				} else { // no markup
					if (state.isPreviousLineEmpty()) {
						state.setStep(Step.BUILDING_FEATURE);
					} else {
						state.setStep(Step.ADDING_COMMENT);
					}
				}
				state.setPreviousLineEmpty(false);
			}
		}
	}

	private void updateModel(State state, String line) {
		if (state.getStep().equals(Step.DECIDING)) {
			// skip empty lines
		} else if (state.getStep().equals(Step.BUILDING_INTRO)) {
			IntroBuilder.buildIntro(state, line);
		} else if (state.getStep().equals(Step.SKIPPING_SUMMARY)) {
			IntroBuilder.skipSummary(state, line);
		} else if (state.getStep().equals(Step.BUILDING_PROJECT)) {
			ProjectBuilder.processProject(state, line);
		}else if (state.getStep().equals(Step.BUILDING_FEATURE_GROUP)) {
			FeatureGroupBuilder.processFeatureGroup(state, line);
		} else if (state.getStep().equals(Step.BUILDING_FEATURE)) {
			FeatureBuilder.processFeature(state, line);
		} else if (state.getStep().equals(Step.BUILDING_COLUMN)) {
			ColumnBuilder.processColumn(state, line);
		} else if (state.getStep().equals(Step.ADDING_COMMENT)) {
			CommentAdder.addCommentToElement(state, line);
		} else {
			StoryBuilder.processStory(state, line);
		}
	}

}
