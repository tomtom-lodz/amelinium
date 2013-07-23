package com.tomtom.amelinium.projectservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.serializer.ChartModelSerializer;
import com.tomtom.amelinium.projectservice.cache.Cache;
import com.tomtom.amelinium.projectservice.cache.JSPChart;
import com.tomtom.amelinium.projectservice.serializer.JSPChartSerializer;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

/**
 * Object model of the project.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Entity
public class Project {

	public static Key<Project> key(long id) {
		return Key.create(Project.class, id);
	}

	@Id
	private Long id;
	
	private String name;
	
	private Text backlogString;
	
	private Text chartString;
	
	@Load
	private List<ProjectHistoryItem> history = new ArrayList<ProjectHistoryItem>();
	
	@Ignore
	private boolean upToDate = true;
	
	@Ignore
	private static BacklogServiceFactory backlogFactory = new BacklogServiceFactory();
	
	@Ignore
	private static BacklogModelSerializer backlogSerializer = new BacklogModelSerializer();
	
	@Ignore
	private static WikiToHTMLSerializer wikiToHTMLSerializer = new WikiToHTMLSerializer();
	
	@Ignore
	private static ChartServiceFactory chartFactory = new ChartServiceFactory();
	
	@Ignore
	private static ChartModelSerializer chartSerializer = new ChartModelSerializer();
	
	@Ignore
	private static JSPChartSerializer jspChartSerializer = new JSPChartSerializer();

	public Long getId() {
		return id;
	}
	
	public Project() {
	}

	public Project(String name, String wikiMarkupBacklog,
			String wikiMarkupChart, String htmlBacklog, JSPChart jspChart) {
		
		ProjectHistoryItem item = new ProjectHistoryItem(0, wikiMarkupBacklog,
				wikiMarkupChart);
		item.save();
		
		long id = item.getId();
		
		this.name = name;
		this.backlogString = new Text(wikiMarkupBacklog);
		this.chartString = new Text(wikiMarkupChart);
		this.history.add(item);
	}

//	public Project(String name, String wikiMarkupBacklog,
//			String wikiMarkupChart, String htmlBacklog, JSPChart jspChart,
//			BacklogModel backlogModel, ChartModel chartModel) {
//		this.name = name;
//		this.backlogModel = backlogModel;
//		this.chartModel = chartModel;
//		this.setCache(new Cache(wikiMarkupBacklog, wikiMarkupChart,
//				htmlBacklog, jspChart));
//		this.history.add(new ProjectSnapshot(0, wikiMarkupBacklog,
//				wikiMarkupChart));
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBacklogWikiMarkup() {
		return backlogString.getValue();
	}

	public void setBacklogWikiMarkup(String backlogString) {
		this.backlogString = new Text(backlogString);
	}

	public BacklogModel getBacklogModel() {
		return backlogFactory.readAndCorrectBacklogModelFromString(backlogString.getValue(), false);
//		return backlogModel;
	}

	public String getBacklogHtml() {
		return wikiToHTMLSerializer.convert(backlogString.getValue());
	}

	public void setBacklogModel(BacklogModel backlogModel) {
//		this.backlogModel = backlogModel;
		this.backlogString = new Text(backlogSerializer.serializeModel(backlogModel));
	}

	public String getChartWikiMarkup() {
		return chartString.getValue();
	}

	public void setChartWiki(String chartString) {
		this.chartString = new Text(chartString);
	}

	public String getChartHtml() {
		return wikiToHTMLSerializer.convert(chartString.getValue());
	}

	public JSPChart getJspChart() {
		return jspChartSerializer.serializeChartModel(getBacklogModel(), getChartModel());
	}

	public ChartModel getChartModel() {
		return chartFactory.readChartModelFromString(chartString.getValue());
	}

	public void setChartModel(ChartModel chartModel) {
		this.chartString = new Text(chartSerializer.serializeChartModel(chartModel, getBacklogModel()));
	}

	public boolean isUpToDate() {
		return upToDate;
	}

	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}

	public ProjectHistoryItem getProjectSnapshot(int index) {
		return history.get(index);
	}

	public int getHistorySize() {
		return history.size();
	}

	public void addProjectSnapshotToHistory(ProjectHistoryItem projectHistoryItem) {
		this.history.add(projectHistoryItem);
	}

	public ArrayList<ProjectHistoryItem> getPartOfHistory(int initialIndex,
			int finalIndex) {
		if (initialIndex < 0) {
			initialIndex = 0;
		}
		if (finalIndex < 0) {
			finalIndex = 0;
		}
		if (initialIndex >= this.history.size()) {
			initialIndex = this.history.size() - 1;
		}
		if (finalIndex >= this.history.size()) {
			finalIndex = this.history.size() - 1;
		}
		ArrayList<ProjectHistoryItem> partOfHistory = new ArrayList<ProjectHistoryItem>();
		for (int i = finalIndex; i >= initialIndex; i--) {
			partOfHistory.add(this.history.get(i));
		}
		return partOfHistory;
	}

	public void save() {
		OfyService.ofy().save().entity(this).now();
	}

}
