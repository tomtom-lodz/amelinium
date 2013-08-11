package com.tomtom.woj.amelinium.journal.timelapse;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.converter.AbsoluteToCumulativeConverterInPlace;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogChunksMerger;
import com.tomtom.woj.amelinium.journal.operations.DoneLinesRemover;
import com.tomtom.woj.amelinium.journal.operations.MergedBacklogColumnSorter;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModel;
import com.tomtom.woj.amelinium.plots.burnup.BurnupModelFactory;

public class TimeLapseStringBuilder {

	private static AbsoluteToCumulativeConverterInPlace cumulativeConverter = new AbsoluteToCumulativeConverterInPlace();
	private static BacklogChunksMerger merger = new BacklogChunksMerger();
	private static BurnupModelFactory burnupModelFactory = new BurnupModelFactory();
	private static MergedBacklogColumnSorter columnSorter = new MergedBacklogColumnSorter();
	private static DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
	
	public TimeLapseChunk createTimeLapseString(String input, double dailyVelocity,
			double dailyBlackMatter, boolean isCumulative) {
		
		TimeLapseChunkFactory factory = new TimeLapseChunkFactory();
		
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromStringNullAllowed(input);
		if(!isCumulative) {
			cumulativeConverter.convertIntoCumulative(chunks);
		}
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(merged);
		
		for(int j=merged.dates.size()-1; j>=0; j--) {
			
			ArrayList<String> headers = new ArrayList<String>();
			ArrayList<DateTime> values = new ArrayList<DateTime>();
			
			columnSorter.sortColumns(merged);
			BurnupModel model = burnupModelFactory.createModel(merged, dailyVelocity, dailyBlackMatter);
			for(int i=model.releasesEndDates.size()-1; i>=0; i--) {
				String name = model.merged.header.get(i+1);
				DateTime dateTime = model.releasesEndDates.get(i);
				
				headers.add(name);
				values.add(dateTime);
			}
			factory.addRow(merged.dates.get(j),headers,values);
			if(j>0) {
				merged.dates.remove(j);
				for(ArrayList<Double> col : merged.cols) {
					col.remove(j);
				}
			}
		}
		
		TimeLapseChunk chunk = factory.build();
		return chunk;
	}

}
