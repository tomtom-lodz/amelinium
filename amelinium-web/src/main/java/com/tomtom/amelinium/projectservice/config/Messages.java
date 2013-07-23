package com.tomtom.amelinium.projectservice.config;
/**
 * Stores messages that are displayed to the user.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Messages {

	public static final String BACKLOG_UPDATED_MSG = 
			"Backlog has recently been updated and chart haven't been recalculated since."
			+"<br />To make sure that chart stays up-to-date, please:"
			+ "<br />1. Click EDIT and update the sprint number in the configuration table."
			+ "<br />2. Click appropriate button to update chart.";
	
	public static final String BACKLOG_REVERTED_MSG = 
			"Backlog has recently been reverted and chart haven't been recalculated since."
			+"<br />To make sure that chart stays up-to-date, please:"
			+ "<br />1. Click EDIT."
			+ "<br />2. If needed, update the sprint number in the configuration table."
			+ "<br />3. Click appropriate button to update chart.";
	
	public static final String CHART_REVERTED_MSG = 
			"Chart has recently been reverted. It hasn't been recalculated though."
			+"<br />If you suspect backlog and chart don't match, before recalculating do either of:"
			+"<br />a. revert backlog to a correct revision"
			+"<br />b. manually edit backlog"
			+"<br />If you are sure that backlog and chart match:"
			+"<br />1. Go to the chart page."
			+"<br />2. Click EDIT."
			+"<br />3. If needed, update the sprint number in the configuration table."
			+"<br />4. Recalculate chart.";
	
	public static final String EDIT_CHART_WARNING = 
			"Backlog has recently changed. Remember to update the sprint number if needed.";
	
	public static final String EDIT_BACKLOG_WARNING = 
			"After updating backlog, go to the chart page, change sprint number and recalculate chart.";

	public static final String REVERTED_INFO="Reverted from r. ";
}
