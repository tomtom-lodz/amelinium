package com.tomtom.amelinium.chartservice.config;

import java.util.regex.Pattern;
/**
 * Collects regular expressions used for parsing the chart.
 * 
 * @author Natasza Nowak
 */
public class RegexConfig {
	
	public static final String CONFIG_VALUE="\\|.*\\| (.*) \\|";
	public static final Pattern CONFIG_VALUE_PATTERN=Pattern.compile(CONFIG_VALUE,Pattern.DOTALL);
	
	public static final String CHART_TITLE=".*title=(.*?)\\|.*";
	public static final Pattern CHART_TITLE_PATTERN=Pattern.compile(CHART_TITLE,Pattern.DOTALL);

}
