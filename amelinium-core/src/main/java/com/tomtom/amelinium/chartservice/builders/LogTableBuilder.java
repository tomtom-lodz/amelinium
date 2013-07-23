package com.tomtom.amelinium.chartservice.builders;

import com.tomtom.amelinium.chartservice.config.MarkupConfig;
import com.tomtom.amelinium.chartservice.model.LogRow;
import com.tomtom.amelinium.chartservice.state.State;

/**
 * Class used for generating google api table with log. For Confluence tool
 * purposes log table is generated according to changes done by
 * backlog-summator.
 * 
 * @author Natasza.Nowak@tomtom.com
 * 
 */

public class LogTableBuilder {
	/**
	 * Adds data from each given line to the log table.
	 */
	public static void buildTable(State state, String line) {
		if (line.contains(MarkupConfig.LOG_TABLE_HEADER)) {
			//do nothing
		} else if (line.isEmpty()) {
			//do nothing
		} else {
			continueBuildingTable(state, line);
		}
	}

	private static void continueBuildingTable(State state, String line) {
		String arr[] = line.split("\\|");
		String table = "";
		boolean matched = false;
		boolean corrected = false;
		String fgFromBacklog = "";
		
		if (arr.length==5){
			table = arr[1];
			matched = (arr[2].trim().equals("(/)")) ? true : false;
			corrected = (arr[3].trim().equals("(/)")) ? true : false;
			fgFromBacklog = arr[4];
			LogRow logRow = new LogRow(table, matched, corrected, fgFromBacklog);
			state.getChartModel().getLog().getRows().add(logRow);
		}
		
	}
}
