package com.tomtom.amelinium.db.results;

/**
 * Container class that joins information retrieved from project, backlog and
 * chart database tables.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ResultProject {
	private String name;
	private String wikiMarkupBacklog;
	private String wikiMarkupChart;
	private String sync;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWikiMarkupChart() {
		return wikiMarkupChart;
	}

	public void setWikiMarkupChart(String wikiMarkupChart) {
		this.wikiMarkupChart = wikiMarkupChart;
	}

	public String getWikiMarkupBacklog() {
		return wikiMarkupBacklog;
	}

	public void setWikiMarkupBacklog(String wikiMarkupBacklog) {
		this.wikiMarkupBacklog = wikiMarkupBacklog;
	}

	public ResultProject() {
	}

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}
}
