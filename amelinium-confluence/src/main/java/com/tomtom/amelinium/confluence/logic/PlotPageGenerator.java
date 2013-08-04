package com.tomtom.amelinium.confluence.logic;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalConverterIntoCumulative;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalMultipleChunksMerger;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalSubtractorIntoBurndown;
import com.tomtom.woj.amelinium.journal.operations.MergedBacklogColumnSorter;
import com.tomtom.woj.amelinium.plots.burndown.BurndownModel;
import com.tomtom.woj.amelinium.plots.burndown.BurndownModelFactory;
import com.tomtom.woj.amelinium.plots.burndown.BurndownPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModel;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModelFactory;
import com.tomtom.woj.amelinium.plots.burnup.BurnupPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.plots.burnup.BurnupTableGenerator;

@Service
public class PlotPageGenerator {
	
	private ConfluenceConfig config = new ConfluenceConfig();

	private BacklogJournalReader reader = new BacklogJournalReader();
	private BacklogJournalConverterIntoCumulative cumulativeConverter = new BacklogJournalConverterIntoCumulative();
	private BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
	
	private BurnupModelFactory burnupModelFactory = new BurnupModelFactory();
	private BurnupPlotJavascriptGenerator burnupGenerator = new BurnupPlotJavascriptGenerator();

	private BacklogJournalSubtractorIntoBurndown subtractor = new BacklogJournalSubtractorIntoBurndown();
	private BurndownModelFactory burndownModelFactory = new BurndownModelFactory();
	private BurndownPlotJavascriptGenerator burndownGenerator = new BurndownPlotJavascriptGenerator();
	private BurnupTableGenerator burnupTableGenerator = new BurnupTableGenerator();
	private MergedBacklogColumnSorter columnSorter = new MergedBacklogColumnSorter();


	public Plots generatePlotsFromConfluencePage(String space, String title, boolean isCumulative,
			double dailyVelocity, double dailyBlackMatter, double effectiveVelocity) {
		String csv = ConfluenceOperations.getPageSource(config.SERVER, config.USER, config.PASS, space, title);
		
		ArrayList<BacklogChunk> chunks = reader.readFromStringNullAllowed(csv);
		if(!isCumulative) {
			cumulativeConverter.convertIntoCumulative(chunks);
		}
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		columnSorter.sortColumns(merged);
		BurnupModel burnupModel = burnupModelFactory.createModel(merged, dailyVelocity, dailyBlackMatter);
		String chart1 = burnupGenerator.generateBurnup(burnupModel, "chart1", "Burnup chart");

		BacklogChunk merged2 = subtractor.subtractBurnedFromMerged(merged);
		BurndownModel burndownModel = burndownModelFactory.createModel(merged2, effectiveVelocity);
		String chart2 = burndownGenerator.generateBurndown(burndownModel, "chart2", "Burndown chart");
		
		String burnupTable = burnupTableGenerator.generateTable(burnupModel);
		
		Plots plots = new Plots();
		plots.chartName1 = "chart1";
		plots.chartBody1 = chart1;
		plots.chartName2 = "chart2";
		plots.chartBody2 = chart2;
		plots.burnupTable = burnupTable;
				
		return plots;
	}
	
}
