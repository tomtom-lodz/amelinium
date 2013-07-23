package com.tomtom.amelinium.backlogservice.builders;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.model.Column;
import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;


public class ColumnBuilder {
	/**
	 * Builds columns ({column} tag in Confluence syntax) that the backlog may
	 * be split to. Consumes array of lines and converts it into model of
	 * columns that contain WorkItems. If no {column} tag is present in the
	 * backlog, the whole is treated as one column. Each column is built only
	 * when the preceding one had a closing tag.
	 * @param state
	 *            Current state of the state machine.
	 * @param inputLine
	 *            Line currently being processed.
	 */
	public static void processColumn(State state, String inputLine) {
		if (state.getLastFeatureGroup().isDividedIntoColumns()) {
			if (state.getLastFeatureGroup().getColumns().size() >= 1) {
				if (state.getLastFeatureGroup().getLastColumn().getCloseTag()
						.isEmpty()) {
					state.getLastFeatureGroup().getLastColumn()
							.setCloseTag(MarkupConfig.COLUMN_TAG);
					state.setStep(Step.DECIDING);
					return;

				} else {
					state.getLastFeatureGroup().getColumns().add(new Column());
				}
			}

		}
		state.getLastFeatureGroup().getLastColumn().setOpenTag(inputLine);
		state.getLastFeatureGroup().setDividedIntoColumns(true);
		state.setStep(Step.DECIDING);
	}

}
