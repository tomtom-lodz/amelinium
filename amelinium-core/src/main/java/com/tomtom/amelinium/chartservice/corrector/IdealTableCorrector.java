package com.tomtom.amelinium.chartservice.corrector;

import java.util.ArrayList;

import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.model.DoubleRow;
import com.tomtom.amelinium.chartservice.model.DoubleTable;
import com.tomtom.amelinium.chartservice.model.IntTable;
import com.tomtom.amelinium.chartservice.model.Line;

public class IdealTableCorrector {

	/**
	 * Method for extending the ideal line - in case when ideal line doesn't
	 * reach the greatest sprint from Feature Group tables. New value for the
	 * last row of this table is calculated basing on the line slope. Last row
	 * is updated with new value and new sprint.
	 */
	public void correctIdealTable(ArrayList<IntTable> tables,
			DoubleTable idealTable, Line line) {
		// find biggestSprint
		int biggestSprint = 0;
		for (IntTable table : tables) {
			if (table.getLastSprint() > biggestSprint
					&& !table.getTitle().equals("ideal")) {
				biggestSprint = table.getLastSprint();
			}
		}
		// correct
		idealTable.setLastSprint(biggestSprint + 1);
		idealTable
				.setLastValue((int) ((biggestSprint + 1) * line.getSlope() + line
						.getBias()));

	}

	/**
	 * Method for recalculating the ideal line based on burned points table.
	 * Ideal line is defined by two points, second of which is always the last
	 * one from the table and the first one depends on the goBack parameter. If
	 * it is set to 5, five last points are taken into consideration. If it is
	 * smaller than the size of the table, especially equal to zero, then the
	 * first point of the line is the first point from the table.
	 */
	public Line computeIdeal(IntTable table, DoubleTable idealTable,
			ChartConfig chartConfig) {
		int x1 = table.getLastSprint(chartConfig.getGoBack_VALUE());
		int y1 = table.getLastValue(chartConfig.getGoBack_VALUE());

		int x2 = table.getLastSprint();
		int y2 = table.getLastValue();

		double slope = 0;
		// to protect from division by 0
		try {
			slope = (double) (y2 - y1) / (double) (x2 - x1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		double bias = y1 - slope * x1;

		idealTable.setLastValue(slope * idealTable.getLastSprint() + bias);
		return new Line(slope, bias);

	}

	/**
	 * Method for automated generation of mock ideal table. Used in case none is
	 * present so far.
	 */
	public DoubleTable generateMockIdealTable() {
		DoubleTable idealTable = new DoubleTable();
		idealTable.setTitle("ideal");
		idealTable.addRow(new DoubleRow(0, 0));
		idealTable.addRow(new DoubleRow(32, 351));
		return idealTable;
	}

}
