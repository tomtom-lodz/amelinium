package com.tomtom.amelinium.chartservice.model;

import java.util.ArrayList;

/**
 * Representation of the roadmap table. This table is shown under the chart on
 * the release planning page.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class Roadmap {
	/**
	 * List of the rows of the roadmap table.
	 */
	private ArrayList<RoadmapRow> rows = new ArrayList<RoadmapRow>();

	public ArrayList<RoadmapRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<RoadmapRow> rows) {
		this.rows = rows;
	}

	public Roadmap() {
	}

}
