package com.tomtom.amelinium.backlogservice.config;

import java.util.regex.Pattern;

/**
 * Collects regular expressions used for parsing the backlog.
 * 
 * @author Natasza Nowak
 */
public class RegexConfig {
	
	public static final String STORY_LINE_CONTAINS_SP=".*\\d+ ?sp.*|.*\\? ?sp.*$";
	public static final String WORK_ITEM_LINE_CONTAINS_SP=".*\\d* *s?p?/?\\d+ ?sp.*|.*\\d* *s?p?/?\\?+ ?sp.*$";
	
	public static final String STORY_CONTENT_MIDDLE="\\?+ ?sp|\\d+\\?? ?sp";
	public static final String WORK_ITEM_CONTENT_MIDDLE="\\d+ *s?p? ?/? ?\\d+ ?sp|\\d+ *s?p? ?/? ?\\?+ ?sp|\\?+ *s?p? ?/? ?\\?+ ?sp|\\?+ *s?p? ?/? ?\\d+ ?sp|\\?+ ?sp|\\d+ ?sp";
	
	public static final Pattern STORY_CONTENT_MIDDLE_PATTERN = Pattern.compile(STORY_CONTENT_MIDDLE);
	public static final Pattern WORK_ITEM_CONTENT_MIDDLE_PATTERN = Pattern.compile(WORK_ITEM_CONTENT_MIDDLE);
	
	public static final Pattern CROSSOUT_PATTERN=Pattern.compile("-");
	
	public static final String COLUMN_SPECIFIED_WIDTH=".*width=(.*)%.*";
	public static final Pattern WIDTH_PATTERN=Pattern.compile(COLUMN_SPECIFIED_WIDTH);
}
