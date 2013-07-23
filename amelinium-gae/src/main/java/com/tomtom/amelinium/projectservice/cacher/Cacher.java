package com.tomtom.amelinium.projectservice.cacher;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.serializer.ChartModelSerializer;
import com.tomtom.amelinium.projectservice.cache.Cache;
import com.tomtom.amelinium.projectservice.cache.JSPChart;
import com.tomtom.amelinium.projectservice.serializer.JSPChartSerializer;
/**
 * Generates project cache.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Cacher {
	private WikiToHTMLSerializer wikiToHTMLSerializer = new WikiToHTMLSerializer();
	private JSPChartSerializer jspChartSerializer = new JSPChartSerializer();
	private BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
	private ChartModelSerializer chartModelSerializer = new ChartModelSerializer();

	public Cache generateProjectCache(BacklogModel newBacklogModel,
			ChartModel newChartModel) {
		String newWikiMarkupBacklog = backlogModelSerializer
				.serializeModel(newBacklogModel);
		String newWikiMarkupChart = chartModelSerializer.serializeChartModel(
				newChartModel, newBacklogModel);
		String newHTMLBacklog = wikiToHTMLSerializer
				.convert(newWikiMarkupBacklog);
		JSPChart newjspChart = jspChartSerializer.serializeChartModel(
				newBacklogModel, newChartModel);
		Cache cache = new Cache(newWikiMarkupBacklog, newWikiMarkupChart,
				newHTMLBacklog, newjspChart);
		return cache;
	}
}
