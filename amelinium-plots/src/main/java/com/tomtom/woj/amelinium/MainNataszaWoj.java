package com.tomtom.woj.amelinium;

import java.io.FileNotFoundException;

import com.tomtom.woj.amelinium.journal.converter.AbsoluteToCumulativeConverterInPlace;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.operations.BacklogChunksMerger;
import com.tomtom.woj.amelinium.plots.burndown.BacklogJournalSubtractorIntoBurndown;
import com.tomtom.woj.amelinium.plots.burndown.BurndownModelFactory;
import com.tomtom.woj.amelinium.plots.burndown.BurndownPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModelFactory;
import com.tomtom.woj.amelinium.plots.burnup.BurnupPlotJavascriptGenerator;
import com.tomtom.woj.amelinium.utils.StringUtils;

public class MainNataszaWoj {

	static BacklogJournalReader reader = new BacklogJournalReader();
	static AbsoluteToCumulativeConverterInPlace cumulativeConverter = new AbsoluteToCumulativeConverterInPlace();
	static BacklogChunksMerger merger = new BacklogChunksMerger();
	
	static BurnupModelFactory burnupModelFactory = new BurnupModelFactory();
	static BurnupPlotJavascriptGenerator burnupGenerator = new BurnupPlotJavascriptGenerator();

	static BurndownModelFactory burndownModelFactory = new BurndownModelFactory();
	static BurndownPlotJavascriptGenerator burndownGenerator = new BurndownPlotJavascriptGenerator();
	static BacklogJournalSubtractorIntoBurndown subtractor = new BacklogJournalSubtractorIntoBurndown();
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		String csv = StringUtils.readFile("src/main/resources/natasza_woj/test1csv.txt");
		
		String out = PlotHtmlPageGenerator.createHtmlPageWithPlots(csv,false,1,0,1);
		
		StringUtils.writeFile(out, "src/main/resources/jqplot/examples/woj5.html");
		
//		System.out.println(out);
	}

}
