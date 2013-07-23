package com.tomtom.amelinium.web.confluence.model;

/**
 * Model of the form for chart and backlog recalculation.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ChartForm {

	/**
	 * Space on the Confluence where the chart is stored.
	 */
	private String chartSpace;

	/**
	 * Title of the chart to be recalculated.
	 */
	private String chartTitle;

	/**
	 * Space on the Confluence where the backlog is stored.
	 */
	private String backlogSpace;

	/**
	 * Title of the backlog to be recalculated.
	 */
	private String backlogTitle;

	/**
	 * States whether multiple line work items are supported. Depending on this
	 * parameter, the tool will either treat each line as a new work item or
	 * concatenate every line which doesn't end with "sp" with the preceding
	 * one.
	 */
	private Boolean allowMultilineFeatures;

	public String getChartSpace() {
		return chartSpace;
	}

	public void setChartSpace(String chartSpace) {
		this.chartSpace = chartSpace;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getBacklogSpace() {
		return backlogSpace;
	}

	public void setBacklogSpace(String backlogSpace) {
		this.backlogSpace = backlogSpace;
	}

	public String getBacklogTitle() {
		return backlogTitle;
	}

	public void setBacklogTitle(String backlogTitle) {
		this.backlogTitle = backlogTitle;
	}

	public Boolean getAllowMultilineFeatures() {
		return allowMultilineFeatures;
	}

	public void setAllowMultilineFeatures(Boolean allowMultilineFeatures) {
		this.allowMultilineFeatures = allowMultilineFeatures;
	}

}
