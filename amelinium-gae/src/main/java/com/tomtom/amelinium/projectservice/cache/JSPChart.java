package com.tomtom.amelinium.projectservice.cache;

/**
 * Contains serialized version of the chart in the form of Strings. Such Strings
 * can be easily injected into JSP pages responsible for displaying chart, using
 * Google Visualization API.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class JSPChart {
	private String configuration = "";
	private String chart = "";
	private String roadmap = "";
	private String burnedTable = "";
	private String summary = "";
	private String log = "";

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public String getRoadmap() {
		return roadmap;
	}

	public void setRoadmap(String roadmap) {
		this.roadmap = roadmap;
	}

	public String getBurnedTable() {
		return burnedTable;
	}

	public void setBurnedTable(String burnedTable) {
		this.burnedTable = burnedTable;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public JSPChart(String configuration, String chart, String roadmap,
			String burnedTable, String summary, String log) {
		super();
		this.configuration = configuration;
		this.chart = chart;
		this.roadmap = roadmap;
		this.burnedTable = burnedTable;
		this.summary = summary;
		this.log = log;
	}
}
