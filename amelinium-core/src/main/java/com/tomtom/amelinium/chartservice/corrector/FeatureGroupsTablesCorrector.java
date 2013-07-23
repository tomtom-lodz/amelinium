package com.tomtom.amelinium.chartservice.corrector;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.IntRow;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.model.Line;
import com.tomtom.amelinium.chartservice.model.LogRow;
import com.tomtom.amelinium.chartservice.model.Log;

public class FeatureGroupsTablesCorrector {

	/**
	 * Corrects Feature Group tables for a given sprint. Adds the desired rows
	 * to each table and rows with recalculated minimal finish sprints.
	 */
	public void correctFeatureGroupsTables(BacklogModel backlogModel,
			ChartModel chartModel, Line line) {
		ArrayList<FeatureGroup> featureGroups = backlogModel
				.getFeatureGroupsFromAllSubProjects();

		String fgTitle;
		Log log = chartModel.getLog();
		LogRow logRow;
		ArrayList<IntTable> tables = chartModel.getFeatureGroupsTables();
		int currentSprint = chartModel.getChartConfig()
				.getCurrentSprint_VALUE();
		double darkMatterPercentage = chartModel.getChartConfig()
				.getDarkMatterPercentage_VALUE();

		for (IntTable table : tables) {
			logRow = new LogRow(table.getTitle());
			for (FeatureGroup fg : featureGroups) {
				fgTitle = fg.getContentLeft() + fg.getContentRight();
				fgTitle = fgTitle.replace("h3. ", "");
				if (fgTitle.toLowerCase().contains(
						table.getTitle().toLowerCase())) {
					logRow = new LogRow(table.getTitle(), true, fgTitle);
					if (table.getLastSprint() >= currentSprint) {
						logRow.setCorrected(true);
						addNewRow(fg, table, currentSprint);
						addMinimalFinishSprintRows(table.getLastValue(), table,
								line, currentSprint, darkMatterPercentage);
					}

				}

			}
			log.getRows().add(logRow);
		//	chartModel.setLogTable(logTable);
		}
	}

	/**
	 * Method for adding new rows, called only for Feature Groups tables in
	 * which last sprint is greater than the given one - other groups already
	 * intersected the ideal line and shouldn't be modified.
	 */
	private void addNewRow(FeatureGroup fg, IntTable table, int currentSprint) {
		IntRow newRow = new IntRow(currentSprint, fg.getCummulativePoints());
		if (currentSprint <= table.getLastSprint()) {
			removeAllSucceedingRows(table, currentSprint);
		}
		appendRow(table, newRow);
	}

	/**
	 * Method for calculating the smallest number of sprint Y for which ideal
	 * line and line of the given table would intersect. Method adds two new
	 * rows for Y sprint. One with the value copied from X sprint. Second with
	 * the 0 value.
	 * 
	 * @param bias
	 */

	private void addMinimalFinishSprintRows(int lastValue, IntTable table,
			Line line, int currentSprint, double darkMatterPercentage) {
		// int sprint = (int) Math.ceil((lastValue - line.getBias()) /
		// line.getSlope());
		// IntRow rowCopiedValue = new IntRow(sprint, lastValue);

		double slope1 = line.getSlope() * darkMatterPercentage / 100.;
		Line line1 = new Line(slope1, lastValue - slope1 * currentSprint);

		// compute intersection of two lines
		// 1e-6 is to make sure ceil works correctly when compute ideal = 0
		int sprint = (int) Math.ceil((line1.getBias() - line.getBias())
				/ (line.getSlope() - line1.getSlope()) - 1e-6);
		if (sprint < currentSprint) {
			sprint = currentSprint;
		}
		int value = (int) Math
				.ceil(line1.getBias() + line1.getSlope() * sprint);

		IntRow rowCopiedValue = new IntRow(sprint, value);
		IntRow rowZeroValue = new IntRow(sprint, 0);
		table.addRow(rowCopiedValue);
		table.addRow(rowZeroValue);
	}

	/**
	 * Method for removing rows with sprint bigger than the given one. Called in
	 * case when the sprint number specified by the user is smaller or equal to
	 * the last one already contained in the given Feature Group table.
	 */
	private void removeAllSucceedingRows(IntTable table, int currentSprint) {
		ArrayList<IntRow> temp = new ArrayList<IntRow>();
		for (IntRow row : table.getRows()) {
			if (row.getSprint() < currentSprint) {
				temp.add(row);
			}
		}
		table.setRows(temp);

	}

	/**
	 * Method for appending X sprint row at the end of Feature Group table. If
	 * the table contains X-1 sprint row only X sprint row is added. If it
	 * doesn't - first X-1 sprint row is added with a value equal to the last
	 * one already existing in the table.
	 */
	private void appendRow(IntTable table, IntRow newRow) {
		if (table.getRows().size() != 0) {
			if (table.getLastSprint() != newRow.getSprint() - 1) {
				IntRow row = new IntRow(newRow.getSprint() - 1,
						table.getLastValue());
				table.addRow(row);
			}
		}
		table.addRow(newRow);
	}

	/**
	 * Method for adding Feature Groups that are present in the backlog, but are
	 * missing on the chart page. This method is called only when the Amelinium
	 * tool is configured to add missing features.
	 */
	public void addMissingTablesFromBacklogToChart(BacklogModel backlogModel,
			ChartModel chartModel, Line line) {

		ArrayList<FeatureGroup> featureGroups = backlogModel
				.getFeatureGroupsFromAllSubProjects();
		int currentSprint = chartModel.getChartConfig()
				.getCurrentSprint_VALUE();
		double darkMatterPercentage = chartModel.getChartConfig()
				.getDarkMatterPercentage_VALUE();

		boolean found;
		for (FeatureGroup fg : featureGroups) {
			found = false;
			for (IntTable fgTable : chartModel.getFeatureGroupsTables()) {
				if (fg.getContentLeft().toLowerCase()
						.contains(fgTable.getTitle().toLowerCase())) {
					found = true;
				}
			}
			if (found == false) {
				String title = fg.getContentLeft();
				title = title.replace("h3. ", "");
				title = title.replace(" - ", "");
				IntTable fgTable = new IntTable();
				fgTable.setTitle(title);
				fgTable.addRow(new IntRow(currentSprint, fg
						.getCummulativePoints()));
				addMinimalFinishSprintRows(fg.getCummulativePoints(), fgTable,
						line, currentSprint, darkMatterPercentage);
				if (fgTable.getLastSprint() >= currentSprint) {
					chartModel.getFeatureGroupsTables().add(fgTable);
				}
			}
		}

	}

}
