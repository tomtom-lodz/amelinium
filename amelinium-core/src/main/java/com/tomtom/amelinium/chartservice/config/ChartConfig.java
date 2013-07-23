package com.tomtom.amelinium.chartservice.config;

public class ChartConfig {

	/**
	 * Number of the current sprint.
	 */
	private int currentSprint_VALUE = 23;
	/**
	 * Length of the day in milliseconds.
	 */
	private long dayLength = 86400000;
	/**
	 * Length of the sprint in milliseconds. 1209600000ms = 2 weeks.
	 */
	private long sprintLength_VALUE = 1209600000;
	/**
	 * When had it all started?
	 */
	private String zeroethSprint_VALUE = "1 Jan 2012";
	/**
	 * Set to true if ideal line should be extended after recalculations.
	 */
	private boolean computeIdeal_VALUE = true;

	/**
	 * If it is set to 5, five last points are taken into consideration when
	 * recalculating ideal line. If it is smaller than the size of the table,
	 * especially equal to zero, then the first point of the line is the first
	 * point from the table.
	 */
	private int goBack_VALUE = 0;

	/**
	 * Set to true if ideal line should be extended after recalculations.
	 */
	private boolean extendIdeal_VALUE = true;

	/**
	 * Set to true if missing features should be added from backlog to chart.
	 */
	private boolean addMissingToChart_VALUE = false;

	/**
	 * Set to true if roadmap should be updated. If set to true, feature groups
	 * that are present on the chart, but not on the roadmap, are added to the
	 * roadmap.
	 */
	private boolean addMissingToRoadMap_VALUE = false;
	
	/**
	 * Set to true if ideal line should be extended after recalculations.
	 */
	private String namesOnRoadmapFrom_VALUE = "unchanged";

	private double darkMatterPercentage_VALUE = 0;

	private boolean showSummary_VALUE = false;

	private boolean showLog_VALUE = true;

	private final String currentSprint_KEY = "*Current sprint (number)*";

	private final String sprintLength_KEY = "Sprint length (days)";

	private final String zeroethSprint_KEY = "Zeroeth sprint (date)";

	private final String goBack_KEY = "Compute ideal (number (0='all', 5='last 5') or false)";

	private final String extendIdeal_KEY = "Extend ideal (true, false)";

	private final String addMissingToChart_KEY = "Add missing feature groups to chart (true, false)";

	private final String addMissingToRoadMap_KEY = "Add missing feature groups to roadmap (true, false)";

	private final String namesOnRoadmapFrom_KEY = "Names on roadmap from (backlog, chart, unchanged)";

	private final String darkMatterPercentage_KEY = "Dark matter as percentage of velocity (0-99)";

	private final String showSummary_KEY = "Show cumulative points summary (true, false)";

	private final String showLog_KEY = "Show autocorrection log (true, false)";

	public int getCurrentSprint_VALUE() {
		return currentSprint_VALUE;
	}

	public void setCurrentSprint_VALUE(int currentSprint_VALUE) {
		this.currentSprint_VALUE = currentSprint_VALUE;
	}

	public long getDayLength() {
		return dayLength;
	}

	public void setDayLength(long dayLength) {
		this.dayLength = dayLength;
	}

	public long getSprintLength_VALUE() {
		return sprintLength_VALUE;
	}

	public void setSprintLength_VALUE(long sprintLength_VALUE) {
		this.sprintLength_VALUE = sprintLength_VALUE;
	}

	public String getZeroethSprint_VALUE() {
		return zeroethSprint_VALUE;
	}

	public void setZeroethSprint_VALUE(String zeroethSprint_VALUE) {
		this.zeroethSprint_VALUE = zeroethSprint_VALUE;
	}

	public boolean isComputeIdeal_VALUE() {
		return computeIdeal_VALUE;
	}

	public void setComputeIdeal_VALUE(boolean computeIdeal_VALUE) {
		this.computeIdeal_VALUE = computeIdeal_VALUE;
	}

	public int getGoBack_VALUE() {
		return goBack_VALUE;
	}

	public void setGoBack_VALUE(int goBack_VALUE) {
		this.goBack_VALUE = goBack_VALUE;
	}

	public boolean isShowLog_VALUE() {
		return showLog_VALUE;
	}

	public void setShowLog_VALUE(boolean showLog_VALUE) {
		this.showLog_VALUE = showLog_VALUE;
	}

	public boolean isExtendIdeal_VALUE() {
		return extendIdeal_VALUE;
	}

	public void setExtendIdeal_VALUE(boolean extendIdeal_VALUE) {
		this.extendIdeal_VALUE = extendIdeal_VALUE;
	}

	public boolean isAddMissingToChart_VALUE() {
		return addMissingToChart_VALUE;
	}

	public void setAddMissingToChart_VALUE(boolean addMissingToChart_VALUE) {
		this.addMissingToChart_VALUE = addMissingToChart_VALUE;
	}

	public boolean isAddMissingToRoadMap_VALUE() {
		return addMissingToRoadMap_VALUE;
	}

	public void setAddMissingToRoadMap_VALUE(boolean addMissingToRoadMap_VALUE) {
		this.addMissingToRoadMap_VALUE = addMissingToRoadMap_VALUE;
	}

	public String getNamesOnRoadmapFrom_VALUE() {
		return namesOnRoadmapFrom_VALUE;
	}

	public void setNamesOnRoadmapFrom_VALUE(String namesOnRoadmapFrom_VALUE) {
		this.namesOnRoadmapFrom_VALUE = namesOnRoadmapFrom_VALUE;
	}

	public double getDarkMatterPercentage_VALUE() {
		return darkMatterPercentage_VALUE;
	}

	public void setDarkMatterPercentage_VALUE(double darkMatterPercentage_VALUE) {
		this.darkMatterPercentage_VALUE = darkMatterPercentage_VALUE;
	}

	public boolean isShowSummary_VALUE() {
		return showSummary_VALUE;
	}

	public void setShowSummary_VALUE(boolean showSummary_VALUE) {
		this.showSummary_VALUE = showSummary_VALUE;
	}

	public String getCurrentSprint_KEY() {
		return currentSprint_KEY;
	}

	public String getSprintLength_KEY() {
		return sprintLength_KEY;
	}

	public String getZeroethSprint_KEY() {
		return zeroethSprint_KEY;
	}

	public String getGoBack_KEY() {
		return goBack_KEY;
	}

	public String getExtendIdeal_KEY() {
		return extendIdeal_KEY;
	}

	public String getAddMissingToChart_KEY() {
		return addMissingToChart_KEY;
	}

	public String getAddMissingToRoadMap_KEY() {
		return addMissingToRoadMap_KEY;
	}

	public String getNamesOnRoadmapFrom_KEY() {
		return namesOnRoadmapFrom_KEY;
	}

	public String getDarkMatterPercentage_KEY() {
		return darkMatterPercentage_KEY;
	}

	public String getShowLog_KEY() {
		return showLog_KEY;
	}

	public String getShowSummary_KEY() {
		return showSummary_KEY;
	}

}
