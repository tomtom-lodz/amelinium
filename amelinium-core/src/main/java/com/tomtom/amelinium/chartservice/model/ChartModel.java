package com.tomtom.amelinium.chartservice.model;

import java.util.ArrayList;

import com.tomtom.amelinium.chartservice.config.ChartConfig;
import com.tomtom.amelinium.chartservice.config.MarkupConfig;
/**
 * Representation of the Release Planning page.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class ChartModel {
	/**
	 * Represents configuration table.
	 */
	private ChartConfig chartConfig = new ChartConfig();
	/**
	 * Represents the text on the wiki page before the Configuration table (not
	 * to be parsed).
	 */
	private String intro = "";
	/**
	 * Represents the wiki markup chart start tag.
	 */
	private String chartStartMarker = MarkupConfig.CHART_START_MARKER_DEFAULT;
	/**
	 * Represents the title of a chart. Used only for serialization to google
	 * JSP code.
	 */
	private String chartTitle = "''";
	/**
	 * The burned points table, that is presented on the chart.
	 */
	private IntTable burnedPointsTable = new IntTable("burned story points");
	/**
	 * List of feature groups tables, that are presented on the chart.
	 */
	private ArrayList<IntTable> featureGroupsTables = new ArrayList<IntTable>();
	/**
	 * Represents the ideal line (trend) from the chart.
	 */
	private DoubleTable idealTable = new DoubleTable();
	/**
	 * Table representation of all the feature groups from the corresponding
	 * backlog, together with their finish sprint, finish date and additional
	 * info.
	 */
	private Roadmap roadMap = new Roadmap();
	/**
	 * Table representation of all the feature groups present on the chart that
	 * were matched to the corresponding ones from backlog and possibly
	 * corrected the last time this very tool was run against backlog and
	 * release planning pages.
	 */
	private Log log = new Log();

	/**
	 * Method concatenating given line to the intro.
	 */
	public void addToIntro(String line) {
		if (intro != "") {
			intro += "\n";
		}
		this.intro += line;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public ArrayList<IntTable> getFeatureGroupsTables() {
		return featureGroupsTables;
	}

	public void setFeatureGroupsTables(ArrayList<IntTable> tables) {
		this.featureGroupsTables = tables;
	}

	public IntTable getBurnedPointsTable() {
		return burnedPointsTable;
	}

	public void setBurnedPointsTable(IntTable burnedTable) {
		this.burnedPointsTable = burnedTable;
	}

	public DoubleTable getIdealTable() {
		return idealTable;
	}

	public void setIdealTable(DoubleTable idealTable) {
		this.idealTable = idealTable;
	}

	public Roadmap getRoadmap() {
		return roadMap;
	}

	public void setRoadmap(Roadmap roadmap) {
		this.roadMap = roadmap;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public String getChartStartMarker() {
		return chartStartMarker;
	}

	public void setChartStartMarker(String chartStartMarker) {
		this.chartStartMarker = chartStartMarker;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public ChartConfig getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(ChartConfig chartConfig) {
		this.chartConfig = chartConfig;
	}
}
