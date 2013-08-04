package com.tomtom.woj.amelinium;

import java.util.ArrayList;
import java.util.HashMap;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalConverterIntoCumulative;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalMultipleChunksMerger;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalSubtractorIntoBurndown;
import com.tomtom.woj.amelinium.journal.operations.DoneLinesRemover;
import com.tomtom.woj.amelinium.plots.burndown.BurndownModel;
import com.tomtom.woj.amelinium.plots.burndown.BurndownModelFactory;
import com.tomtom.woj.amelinium.plots.burndown.BurndownPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModel;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModelFactory;
import com.tomtom.woj.amelinium.plots.burnup.BurnupPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.utils.TemplateRenderer;

public class PlotHtmlPageGenerator {
	
	static BacklogJournalReader reader = new BacklogJournalReader();
	static BacklogJournalConverterIntoCumulative cumulativeConverter = new BacklogJournalConverterIntoCumulative();
	static BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
	
	static BurnupModelFactory burnupModelFactory = new BurnupModelFactory();
	static BurnupPlotJavascriptGenerator burnupGenerator = new BurnupPlotJavascriptGenerator();
	
	static BacklogJournalSubtractorIntoBurndown subtractor = new BacklogJournalSubtractorIntoBurndown();
	static BurndownModelFactory burndownModelFactory = new BurndownModelFactory();
	static BurndownPlotJavascriptGenerator burndownGenerator = new BurndownPlotJavascriptGenerator();
	
	static DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		
	public static String createHtmlPageWithPlots(String csv, boolean isCumulative, double dailyVelocity,
			double dailyBlackMatter, double effecticeVelocity) {
		
		ArrayList<BacklogChunk> chunks = reader.readFromString(csv);
		if(!isCumulative) {
			cumulativeConverter.convertIntoCumulative(chunks);
		}
		
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		// remove done lines from the plot
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(merged);
		
		BurnupModel burnupModel = burnupModelFactory.createModel(merged, dailyVelocity, dailyBlackMatter);
		BacklogChunk merged2 = subtractor.subtractBurnedFromMerged(merged);
		BurndownModel burndownModel = burndownModelFactory.createModel(merged2, effecticeVelocity);
		String chart1 = burnupGenerator.generateBurnup(burnupModel, "chart1", "Burnup chart");
		String chart2 = burndownGenerator.generateBurndown(burndownModel, "chart2", "Burndown chart");
		
		HashMap<String, String> templateModel = new HashMap<String, String>();
		templateModel.put("<CHART1>", chart1);
		templateModel.put("<CHART2>", chart2);
		
		return TemplateRenderer.render("/amelinium/templates/page.template", templateModel);
	}

}
