package com.tomtom.woj.amelinium.journal.timelapse;

import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;

import com.tomtom.woj.amelinium.utils.TemplateRenderer;

public class TimeLapseChartGenerator {

	public String generateTimeLaps(TimeLapseChunk model, String plotName, String title) {

		String variableNames = variableNamesString(model);
		String variableDefinitions = variableDefinitionString(model);
		String seriesDefinition = seriesDefinitionString(model);
		
		HashMap<String, String> templateModel = new HashMap<String, String>();
		
		templateModel.put("<LINES1>", variableDefinitions);
		templateModel.put("<LINES2>", variableNames);
		templateModel.put("<SERIES>", seriesDefinition);
		templateModel.put("<PLOT>", plotName);
		templateModel.put("<TITLE>", title);
		
		return TemplateRenderer.render("/amelinium/templates/timeLapsePlot1.template", templateModel);
	}
	
	private String variableNamesString(TimeLapseChunk model) {
		if(model.dates.size()<1 || model.cols.size()<1) {
			return "[[null]]";
		}
		
		StringBuffer sb;
		sb = new StringBuffer();
		for(int i=0; i<model.cols.size(); i++) {
			sb.append("line");
			sb.append(i);
			sb.append(",");
		}
		String variableNames = sb.toString();
		return variableNames;
	}
	
	private String variableDefinitionString(TimeLapseChunk model) {
		if(model.dates.size()<1 || model.cols.size()<1) {
			return "";
		}
		
		StringBuffer sb;
		// merged lines (burned + feature groups)
		
		sb = new StringBuffer();
		for(int i=0; i<model.cols.size();i++) {
			sb.append("  var line");
			sb.append(i);
			sb.append("=[");
			for(int j=0;j<model.cols.get(0).size(); j++) {
				if(model.cols.get(i).get(j)==null) {
					continue;
				}
				sb.append("['");
				sb.append(model.dates.get(j).toString("yyyy-MM-dd"));
				sb.append("','");
				sb.append(model.cols.get(i).get(j).toString("yyyy-MM-dd"));
				sb.append("'],");
			}
			sb.append("];\n");
		}

		String variables = sb.toString();
		return variables;
	}

	private String seriesDefinitionString(TimeLapseChunk model) {
		if(model.dates.size()<1 || model.cols.size()<1) {
			return "[]";
		}
		
		StringBuffer sb;
		sb = new StringBuffer();
		for(int i=0; i<model.cols.size(); i++) {
			sb.append("{label: '");
			sb.append(StringEscapeUtils.escapeJavaScript(model.headers.get(i)));
			sb.append("'},");
		}

		return sb.toString();
	}

	
}
