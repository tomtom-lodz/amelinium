package com.tomtom.amelinium.projectservice.factory;

import java.io.InputStream;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.serializer.ChartModelSerializer;
import com.tomtom.amelinium.common.FileUtil;
import com.tomtom.amelinium.projectservice.cache.JSPChart;
import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.serializer.JSPChartSerializer;
/**
 * Factory that builds a project mdoel out of the given sources.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class ProjectServiceFactory {

	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
	private WikiToHTMLSerializer wikiToHTMLSerializer = new WikiToHTMLSerializer();
	private JSPChartSerializer jspChartSerializer = new JSPChartSerializer();
	private BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
	private ChartModelSerializer chartModelSerializer = new ChartModelSerializer();

	public Project readAndCorrectModelsFromResources(String name, String backlogFilename, String chartFilename, boolean allowingMultilineFeatures) {
		InputStream backlogStream0 = getClass().getClassLoader().getResourceAsStream(backlogFilename);
		InputStream backlogStream1 = getClass().getClassLoader().getResourceAsStream(backlogFilename);
		InputStream chartStream0 = getClass().getClassLoader().getResourceAsStream(chartFilename);
		InputStream chartStream1 = getClass().getClassLoader().getResourceAsStream(chartFilename);
		Project project;
		try {
			BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromStream(backlogStream0,allowingMultilineFeatures);
			ChartModel chartModel = chartServiceFactory.readAndCorrectChartModelFromBacklogModelAndChartStream(backlogModel,chartStream0);
			String wikiMarkupBacklog = backlogModelSerializer.serializeModel(backlogModel);
			String wikiMarkupChart = chartModelSerializer.serializeChartModel(chartModel, backlogModel);
			String htmlBacklog = wikiToHTMLSerializer.convert(wikiMarkupBacklog);
			JSPChart jspChart = jspChartSerializer.serializeChartModel(backlogModel, chartModel);
		project = new Project(name, wikiMarkupBacklog, wikiMarkupChart, htmlBacklog, jspChart);
		} finally {
			FileUtil.closeQuietly(backlogStream0);
			FileUtil.closeQuietly(backlogStream1);
			FileUtil.closeQuietly(chartStream0);
			FileUtil.closeQuietly(chartStream1);
		}
		return project;
	}
	
	public Project readAndCorrectModelsFromFiles(int id, String name, String backlogFileName, String chartFileName, boolean allowingMultilineFeatures){
		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromFile(backlogFileName, allowingMultilineFeatures);
		ChartModel chartModel = chartServiceFactory.readAndCorrectChartModel(backlogFileName, chartFileName, allowingMultilineFeatures);
		String wikiMarkupBacklog = backlogModelSerializer.serializeModel(backlogModel);
		String wikiMarkupChart = chartModelSerializer.serializeChartModel(chartModel, backlogModel);
		String htmlBacklog = wikiToHTMLSerializer.convert(wikiMarkupBacklog);
		JSPChart jspChart = jspChartSerializer.serializeChartModel(backlogModel, chartModel);
		Project project = new Project(name, wikiMarkupBacklog, wikiMarkupChart, htmlBacklog, jspChart);
		return project;
	}

	public Project readAndCorrectModelsFromStrings(int id, String name,
			String wikiMarkupBacklog, String wikiMarkupChart,
			Boolean allowMultilineFeatures) {
		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromString(wikiMarkupBacklog, allowMultilineFeatures);
		ChartModel chartModel = chartServiceFactory.readAndCorrectChartModelFromBacklogModelAndChartString(backlogModel, wikiMarkupChart);
		JSPChart jspChart = jspChartSerializer.serializeChartModel(backlogModel, chartModel);
		String correctedWikiMarkupChart = chartModelSerializer.serializeChartModel(chartModel, backlogModel);
		String correctedWikiMarkupBacklog = backlogModelSerializer.serializeModel(backlogModel);
		String htmlBacklog = wikiToHTMLSerializer.convert(correctedWikiMarkupBacklog);
		if (name.isEmpty()) {name = "New "+id;}
		Project project = new Project(name, correctedWikiMarkupBacklog, correctedWikiMarkupChart, htmlBacklog, jspChart);
		return project;
	}
}
