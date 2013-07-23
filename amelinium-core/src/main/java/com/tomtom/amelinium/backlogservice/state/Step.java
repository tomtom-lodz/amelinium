package com.tomtom.amelinium.backlogservice.state;

/**
 * Possible steps of backlog building state machine.
 * 
 * @author Natasza Nowak
 */
public enum Step {
	BUILDING_INTRO,
	SKIPPING_SUMMARY,
    DECIDING,
    BUILDING_STORY,
    BUILDING_FEATURE,
    BUILDING_FEATURE_GROUP,
    BUILDING_PROJECT,
    BUILDING_COLUMN,
    ADDING_COMMENT
}
