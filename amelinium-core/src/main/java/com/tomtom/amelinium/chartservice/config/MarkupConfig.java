package com.tomtom.amelinium.chartservice.config;
/**
 * Configuration of markup used in chart.
 * @author Natasza Nowak
 *
 */
public class MarkupConfig {
	
	public static final String SUMMARY_START_MARKER="SUMMARY";
	public static final String LOG_START_MARKER="AUTOCORRECTION LOG";
	public static final String CONFIG_TABLE_HEADER = "|| Configuration || ||";
	public static final String ROADMAP_HEADER = "|| Feature Group || Sprint || End of development || Deployment to production ||";
	public static final String IDEAL_TABLE_HEADER = "|| || ideal ||";
	public static final String LOG_TABLE_HEADER = "|| Table || matched? || corrected? || Feature Group from backlog ||";
	public static final String HEADER_MARKER = "|| ||";
	public static final String CHART_MARKER = "{chart}";
	public static final String CHART_START_MARKER = "{chart:";
	public static final String CHART_START_MARKER_DEFAULT = "{chart:type=xyLine|dataOrientation=vertical|width=800|height=500|title=insert your title here}";
	public static final String BURNED_SP_TABLE_TITLE = "burned story points";

}
