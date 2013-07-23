package com.tomtom.woj.amelinium.plots.burndown;

import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.trendline.PlotTrendLine;
import com.tomtom.woj.amelinium.utils.TemplateRenderer;

public class BurndownPlotJavascriptGenerator {

	public DateTime startDate;
	public DateTime endDate;

	public String generateBurndown(BurndownModel model, String plotName, String title) {

		String variableNames = variableNamesString(model);
		String variableDefinitions = variableDefinitionString(model);
		String seriesDefinition = seriesDefinitionString(model);
		
		HashMap<String, String> templateModel = new HashMap<String, String>();
		
		templateModel.put("<LINES1>", variableDefinitions);
		templateModel.put("<LINES2>", variableNames);
		templateModel.put("<SERIES>", seriesDefinition);
//		templateModel.put("<MAXX>", "'" + model.burnedEndDate.toString("yyyy-MM-dd") + "'");
		templateModel.put("<MINY>", Double.toString(model.minPoints));
		templateModel.put("<PLOT>", plotName);
		templateModel.put("<TITLE>", title);
		
		return TemplateRenderer.render("/amelinium/templates/plot1.template", templateModel);
	}

	private String variableDefinitionString(BurndownModel model) {
		if(model.merged.dates.size()<1 || model.merged.cols.size()<1) {
			return "";
		}
		
		StringBuffer sb;
		// merged lines (burned + feature groups)
		
		sb = new StringBuffer();
		for(int i=0; i<model.merged.cols.size();i++) {
			sb.append("  var line");
			sb.append(i);
			sb.append("=[");
			for(int j=0;j<model.merged.cols.get(0).size(); j++) {
				if(Double.isNaN(model.merged.cols.get(i).get(j))) {
					continue;
				}
				sb.append("['");
				sb.append(model.merged.dates.get(j).toString("yyyy-MM-dd"));
				sb.append("',");
				sb.append(model.merged.cols.get(i).get(j));
				sb.append("],");
			}
			sb.append("];\n");
		}
		
		// trends of feature groups

		for(int i=0; i<model.releasesTrends.size();i++) {

			PlotTrendLine trend = model.releasesTrends.get(i);
			
			sb.append("  var trend");
			sb.append(i);
			sb.append("=[");
			sb.append("['");
			sb.append(model.releasesStartDates.get(i).toString("yyyy-MM-dd"));
			sb.append("',");
			sb.append(trend.getPointsAt(model.releasesStartDates.get(i)));
			sb.append("],");
			sb.append("['");
			sb.append(model.releasesEndDates.get(i).toString("yyyy-MM-dd"));
			sb.append("',");
			sb.append(trend.getPointsAt(model.releasesEndDates.get(i)));
			sb.append("],");
			sb.append("['");
			sb.append(model.releasesEndDates.get(i).toString("yyyy-MM-dd"));
			sb.append("',");
			sb.append(model.minPoints);
			sb.append("],");
			sb.append("];\n");
		}

		String variables = sb.toString();
		return variables;
	}

	private String variableNamesString(BurndownModel model) {
		if(model.merged.dates.size()<1 || model.merged.cols.size()<1) {
			return "[[null]]";
		}
		
		StringBuffer sb;
		sb = new StringBuffer();
		for(int i=0; i<model.merged.cols.size(); i++) {
			sb.append("line");
			sb.append(i);
			sb.append(",");
		}
		for(int i=0; i<model.releasesTrends.size(); i++) {
			sb.append("trend");
			sb.append(i);
			sb.append(",");
		}
		String variableNames = sb.toString();
		return variableNames;
	}
	
	private String seriesDefinitionString(BurndownModel model) {
		if(model.merged.dates.size()<1 || model.merged.cols.size()<1) {
			return "[]";
		}
		
		StringBuffer sb;
		sb = new StringBuffer();
		for(int i=0; i<model.merged.cols.size(); i++) {
			sb.append("{label: '");
			sb.append(StringEscapeUtils.escapeJavaScript(model.merged.header.get(i)));
			sb.append("'},");
		}
		for(int i=0; i<model.releasesTrends.size(); i++) {
			sb.append("{lineWidth:1, showMarker:false, linePattern: 'dashed', label: 'Trend (");
			sb.append(StringEscapeUtils.escapeJavaScript(model.merged.header.get(i)));
			sb.append(")'},");
		}
		String seriesDefinition = sb.toString();
		return seriesDefinition;
	}

}
