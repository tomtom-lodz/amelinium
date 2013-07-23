package com.tomtom.amelinium.projectservice.cache;

/**
 * Used for storing serialized versions of backlog and chart. In this way models
 * don't need to be serialized with each view or edit request.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Cache {
	private String wikiMarkupBacklog;
	private String wikiMarkupChart;
	private String htmlBacklog;
	private JSPChart jspChart;

	public String getWikiMarkupBacklog() {
		return wikiMarkupBacklog;
	}

	public void setWikiMarkupBacklog(String wikiMarkupBacklog) {
		this.wikiMarkupBacklog = wikiMarkupBacklog;
	}

	public String getWikiMarkupChart() {
		return wikiMarkupChart;
	}

	public void setWikiMarkupChart(String wikiMarkupChart) {
		this.wikiMarkupChart = wikiMarkupChart;
	}

	public String getHtmlBacklog() {
		return htmlBacklog;
	}

	public void setHtmlBacklog(String htmlBacklog) {
		this.htmlBacklog = htmlBacklog;
	}

	public JSPChart getJspChart() {
		return jspChart;
	}

	public void setJspChart(JSPChart jspChart) {
		this.jspChart = jspChart;
	}

	public Cache(String wikiMarkupBacklog, String wikiMarkupChart,
			String htmlBacklog, JSPChart jspChart) {
		super();
		this.wikiMarkupBacklog = wikiMarkupBacklog;
		this.wikiMarkupChart = wikiMarkupChart;
		this.htmlBacklog = htmlBacklog;
		this.jspChart = jspChart;
	}
}
