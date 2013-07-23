package com.tomtom.amelinium.chartservice.corrector;

import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.model.IntRow;
import com.tomtom.amelinium.chartservice.model.IntTable;

public class BurnedPointsTableCorrector {
	
	/**
	 * Updates burned story points table for a given sprint with the value of
	 * overall burned story points from the backlog model. If the given sprint
	 * already exists in the table, the value is overwritten. If not, the row
	 * with appropriate sprint and value is appended.
	 */
	public void correctBurnedPointsTable(IntTable table, int burnedPoints, ChartConfig chartConfig) {
		boolean rowExists = false;
		for (IntRow r : table.getRows()) {
			if (r.getSprint() == chartConfig.getCurrentSprint_VALUE()) {
				rowExists = true;
				r.setValue(burnedPoints);
			}
		}
		if (rowExists == false) {
			IntRow newRow = new IntRow(chartConfig.getCurrentSprint_VALUE(), burnedPoints);
			table.addRow(newRow);
		}
	}
}
