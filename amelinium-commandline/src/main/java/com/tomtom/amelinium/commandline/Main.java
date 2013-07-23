package com.tomtom.amelinium.commandline;

import java.io.IOException;
import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.builders.BacklogModelBuilder;
import com.tomtom.amelinium.backlogservice.corrector.BacklogModelCorrector;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.chartservice.builders.ChartModelBuilder;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.common.FileUtil;
import com.tomtom.amelinium.common.LineProducer;

public class Main {
	
	public static void main(String[] args) {
		
		if(args.length<=1) {
			help();
		}
		
		String operation = args[0];
		
		if("calculate".equals(operation)) {
			calculate(args);
		} else if("generate_chart".equals(operation)) {
			generate_html(args);
		} else if("correct_chart".equals(operation)) {
			correct_chart(args);
		} else {
			help();
		}
		
	}

	private static void calculate(String[] args) {
		String input = args[1];
		String output = input;
		if(args.length>=3) {
			output = args[2];
		}

		LineProducer producer = new LineProducer();
		BacklogModelBuilder builder = new BacklogModelBuilder();
		BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
		BacklogModelSerializer generator = new BacklogModelSerializer();
		
		ArrayList<String> lines = producer.readLinesFromFile(input);
		// TODO allow multiline features parameter!
		BacklogModel backlogModel = builder.buildBacklogModel(lines,true);
		backlogModel = backlogModelCorrector.correctModelPoints(backlogModel);
		String newContent = generator.serializeModel(backlogModel);
		try {
			FileUtil.writeContentToFile(newContent, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void generate_html(String[] args) {
		String input = args[1];
		String output = input;
		if(args.length>=3) {
			output = args[2];
		}
		LineProducer producer = new LineProducer();
		ChartModelBuilder chartModelBuilder = new ChartModelBuilder();
//		PureHTMLChartSerializer htmlChartSerializer = new PureHTMLChartSerializer();
		ArrayList<String> chartLines = producer.readLinesFromFile(input);
		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);
		
	//	String content = htmlChartSerializer.generateHTML(chartModel);
		String content = "TODO!";
		
		try {
			FileUtil.writeContentToFile(content, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	private static void correct_chart(String[] args) {
		String chart_file = args[1];
		String backlog_file = chart_file;
		String output_file = chart_file;
		if(args.length<3) {
			help();
		}
		else{
			backlog_file = args[2];
			if(args.length==4) {
				output_file = args[3];
			}
		}
		
		LineProducer producer = new LineProducer();
		ChartModelBuilder chartModelBuilder = new ChartModelBuilder();
		BacklogModelBuilder backlogModelBuilder = new BacklogModelBuilder();
		BacklogModelCorrector backlogModelCorrector = new BacklogModelCorrector();
		ChartCorrector chartCorrector = new ChartCorrector();
	//	PureHTMLChartSerializer htmlChartSerializer = new PureHTMLChartSerializer();

		ArrayList<String> chartLines = producer.readLinesFromFile(chart_file);
		ArrayList<String> backlogLines = producer.readLinesFromFile(backlog_file);

		ChartModel chartModel = chartModelBuilder.buildChartModel(chartLines);
		// TODO allow multiline features parameter!
		BacklogModel backlogModel = backlogModelBuilder.buildBacklogModel(backlogLines,true);
		backlogModel = backlogModelCorrector.correctModelPoints(backlogModel);

		chartCorrector.correctTables(backlogModel, chartModel);
		
		//String content = htmlChartSerializer.generateHTML(chartModel);
		String content = "TODO!";
		
		try {
			FileUtil.writeContentToFile(content, output_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static void help() {
		System.out.println("usage: provide operation and parameters\n\n" +
				"calculate - calculates backlog\n"+
				"\tcalculate input_filename [output_filename]\n\n"+
				"generate_chart - generates html file based on a given txt file\n"+
				"\tgenerate_chart chart_filename [output_filename]\n\n"+
				"correct_chart - generates html file based on a given chart and backlog\n"+
				"\tgenerate_chart chart_filename backlog_filename [output_filename]");
		System.exit(1);
	}
}
