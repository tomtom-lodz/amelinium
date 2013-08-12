package com.tomtom.woj.amelinium.journal.converter;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.converter.CumulativeToAbsoluteConverter.HeaderValuePair;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalNewLineAdder;

public class BacklogJournalCleaner {
	
	private BacklogJournalNewLineAdder lineAdder = new BacklogJournalNewLineAdder();	
	
	public ArrayList<BacklogChunk> clean(ArrayList<BacklogChunk> chunks) {
		ArrayList<BacklogChunk> chunksResult = new ArrayList<BacklogChunk>();
		
		for(BacklogChunk chunk:chunks) {
			cleanupChunk(chunk,chunksResult);
		}
		
		return chunksResult;
	}

	private void cleanupChunk(BacklogChunk chunk, ArrayList<BacklogChunk> chunksResult) {
		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		
		for(int i=0; i<chunk.dates.size(); i++) {
			headers.clear();
			values.clear();
			for(int j=1; j<chunk.header.size(); j++) {
				String header = chunk.header.get(j);
				double value = chunk.cols.get(j).get(i);
				if(Double.isNaN(value)) {
					continue;
				}
				headers.add(header);
				values.add(value);
			}
			DateTime dateTime = chunk.dates.get(i);
			double burned = chunk.cols.get(0).get(i);
			
			lineAdder.addAll(chunksResult, dateTime, burned, headers, values);
		}
		
	}

}
