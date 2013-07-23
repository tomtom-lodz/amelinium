package com.tomtom.amelinium.backlogservice.config;

/**
 * Configuration of markup used in backlog.
 * @author Natasza Nowak
 *
 */
public class MarkupConfig {
	
	public static final String STORY_MARKER = "* ";
	public static final String FEATURE_MARKER = "h5."; //Feature marker may be either empty or h5.
	public static final String FEATURE_GROUP_MARKER = "h3.";
	public static final String PROJECT_MARKER = "h1.";
	public static final String COLUMN_MARKER = "{column";
	public static final String COLUMN_TAG = "{column}";
	public static final String SECTION_TAG = "{section}";
	public static final String SUMMARY_START_MARKER="SUMMARY";
	public static final String INTRO_END_MARKER="BACKLOG";
	public static final String WARNING_LINE="BACKLOG - do not remove this line (needed for automatic recalculation)\n";

}
