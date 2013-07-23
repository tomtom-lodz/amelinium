package com.tomtom.amelinium.backlogservice.builders;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;

public class IntroBuilder {

	/**
	 * Builds <code>intro</code> based on the line of text given as a parameter.
	 * If the line doesn't contain a string marking the end of
	 * <code>intro</code> - building of the <code>intro</code> will continue the
	 * next time function is invoked. If the line contains the marker, step of
	 * the state machine is set to <code>DECIDING</code> and building of the
	 * actual backlog model can begin.
	 * <p>
	 * <code>Intro</code> is understood as text from the beginning of the file
	 * to the line containing an <code>INTRO_END_MARKER</code> set in
	 * <code>config.MarkupConfig</code>, including this line.
	 * 
	 * @param state
	 *            Current state of the state machine.
	 * @param inputLine
	 *            Line currently being processed.
	 */

	public static void buildIntro(State state, String line) {
		if (line.contains(MarkupConfig.SUMMARY_START_MARKER)) {
			state.setStep(Step.SKIPPING_SUMMARY);
		}
		else if (line.contains(MarkupConfig.INTRO_END_MARKER)) {
			state.setStep(Step.DECIDING);
		}
		else{
			state.getBacklogModel().addLineToIntro(line);
		}
	}

	public static void skipSummary(State state, String line) {
		if (!line.contains(MarkupConfig.INTRO_END_MARKER)) {
			//skip line, summary has to be rebuilt after summing up anyway
		}
		else{
			state.setStep(Step.DECIDING);
		}
		
	}

}
