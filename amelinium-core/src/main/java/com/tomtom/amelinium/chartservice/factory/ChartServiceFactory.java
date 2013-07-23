package com.tomtom.amelinium.chartservice.factory;

import java.io.InputStream;
import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder;
import com.tomtom.amelinium.backlogservice.corrector.BacklogModelCorrector;
import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.builders.ChartModelBuilder;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.serializer.ChartModelSerializer;
import com.tomtom.amelinium.common.LineProducer;
/**
 * Factory that builds a chart model out of the given sources.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */

public class ChartServiceFactory {

	private LineProducer producer = new LineProducer();
	
	private ChartModelBuilder chartModelBuilder = new ChartModelBuilder();
	private ChartCorrector chartCorrector = new ChartCorrector();
	private ChartModelSerializer chartModelSerializer = new ChartModelSerializer();
	
	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private BacklogModelBuilder backlogModelBuilder = new BacklogModelBuilder();
	private BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
	
	public ChartModel readChartModelFromFile(String chartFileName) {
		ArrayList<String> chartLines = producer
				.readLinesFromFile(chartFileName);
		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);

		return chartModel;

	}

	public ChartModel readAndCorrectChartModel(String backlogFileName,
			String chartFileName, boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,allowingMultilineFeatures);
		ChartModel chartModel = readChartModelFromFile(chartFileName);
		chartCorrector.correctTables(backlogModel, chartModel);

		return chartModel;

	}

	public String readCorrectAndSerializeChartModelFromFiles(
			String backlogFileName, String chartFileName, boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,allowingMultilineFeatures);
		ChartModel chartModel = readAndCorrectChartModel(backlogFileName,
				chartFileName, allowingMultilineFeatures);

		String output = chartModelSerializer.serializeChartModel(chartModel,
				backlogModel);

		return output;

	}

	public String readCorrectAndSerializeChartModelFromStrings(
			String oldBacklog, String oldChart, boolean allowingMultilineFeatures) {

		ArrayList<String> chartLines = producer.readLinesFromString(oldChart);
		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);

		ArrayList<String> backlogLines = producer
				.readLinesFromString(oldBacklog);
		BacklogModel backlogModel = backlogModelBuilder
				.buildBacklogModel(backlogLines, allowingMultilineFeatures);

		backlogModelCorrector.correctModelPoints(backlogModel);
		chartCorrector.correctTables(backlogModel, chartModel);

		String newChartContent = chartModelSerializer.serializeChartModel(
				chartModel, backlogModel);

		return newChartContent;
	}

	public String serializeChartModelFromFiles(String backlogFileName,
			String chartFileName,boolean allowingMultilineFeatures) {
		BacklogModel backlogModel = backlogServiceFactory
				.readBacklogModelFromFile(backlogFileName,allowingMultilineFeatures);
		ChartModel chartModel = readChartModelFromFile(chartFileName);
		String output = chartModelSerializer.serializeChartModel(chartModel,
				backlogModel);

		return output;
	}
	
	public ChartModel readChartModelFromString(String chartContent) {
		ArrayList<String> chartLines = producer
				.readLinesFromString(chartContent);
		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);

		return chartModel;
	}

	public ChartModel readChartModelFromStream(InputStream chartStream) {
		ArrayList<String> chartLines = producer
				.readLinesFromStream(chartStream);
		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);

		return chartModel;
	}

	public ChartModel readAndCorrectChartModelFromBacklogModelAndChartString(BacklogModel backlogModel, String backlogContent) {
		ChartModel chartModel = readChartModelFromString(backlogContent);
		chartModel = chartCorrector.correctTables(backlogModel, chartModel);
		return chartModel;
	}
}
