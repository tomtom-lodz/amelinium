package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashMap;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalUpdater {
	
//	public void updateOnlyExistingColumns(ArrayList<BacklogChunk> chunks, ArrayList<String> newHeader) {
//		if(chunks.isEmpty()) {
//			return;
//		}
//		
//		BacklogChunk history = chunks.get(chunks.size()-1);

	public void add(ArrayList<BacklogChunk> chunks, ArrayList<String> headers, ArrayList<Double> values) {
		
	}
	
	public void addNewLine(BacklogChunk history, BacklogChunk hist2) {
		HashMap<String, ArrayList<Double>> map = new HashMap<String, ArrayList<Double>>();
		for(int i=0; i<hist2.cols.size(); i++) {
			String colname = hist2.header.get(i);
			ArrayList<Double> colvalues = hist2.cols.get(i);
			map.put(colname, colvalues);
		}
		history.dates.add(hist2.dates.get(0));
		for(int i=0; i<history.header.size(); i++) {
			String group = history.header.get(i);
			ArrayList<Double> values = history.cols.get(i);
			ArrayList<Double> col = map.get(group);
			if(col==null) {
				values.add(Double.NaN);
			} else {
				values.add(col.get(0));
			}
		}
	}
	
}
