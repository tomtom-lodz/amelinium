package com.tomtom.amelinium.chartservice.state;

/**
 * Possible steps of backlog building state machine.
 * 
 * @author Natasza Nowak
 */
public enum Step {
	BUILDING_INTRO,
    DECIDING,
    BUILDING_INT_TABLE,
    BUILDING_DOUBLE_TABLE,
    SETTING_CONFIG,
    BUILDING_ROADMAP,
	BUILDING_LOG_TABLE,
    STOP
}
