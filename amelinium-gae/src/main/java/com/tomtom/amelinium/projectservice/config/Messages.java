package com.tomtom.amelinium.projectservice.config;
/**
 * Stores messages that are displayed to the user.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Messages {

	public static final String chartMessage = 
			"Backlog has recently been updated and chart haven't been recalculated since."
			+"<br />To make sure that chart stays up-to-date, please:"
			+ "<br />1. Click EDIT and update the sprint number in the configuration table."
			+ "<br />2. Click appropriate button to update chart.";
	public static final String editChartMessage = 
			"Backlog has recently been updated. Remember to update the sprint number.";
	public static final String editBacklogMessage = 
			"After updating backlog, go to the chart page, change sprint number and recalculate chart.";

	public static final String revertedInfo="Reverted from r. ";
}
