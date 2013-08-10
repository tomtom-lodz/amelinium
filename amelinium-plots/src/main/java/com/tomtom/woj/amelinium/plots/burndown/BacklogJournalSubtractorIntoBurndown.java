package com.tomtom.woj.amelinium.plots.burndown;

import java.util.ArrayList;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;


public class BacklogJournalSubtractorIntoBurndown {

	public BacklogChunk subtractBurnedFromMerged(BacklogChunk merged) {
		BacklogChunk subtracted = new BacklogChunk();
		
		subtracted.dates = merged.dates;
		subtracted.cols = new ArrayList<ArrayList<Double>>();
		for(int i=1; i<merged.cols.size(); i++) {
			ArrayList<Double> newCol = new ArrayList<Double>();
			for(int j=0; j<merged.cols.get(0).size(); j++) {
				double newValue = merged.cols.get(i).get(j) - merged.cols.get(0).get(j);
				newCol.add(newValue);
			}
			subtracted.cols.add(newCol);
		}
		subtracted.header = new ArrayList<String>();
		if(merged.header.size()>0) {
			subtracted.header.addAll(merged.header);
			subtracted.header.remove(0);
		}
		
		return subtracted;
	}

}
