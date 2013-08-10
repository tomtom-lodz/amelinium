package com.tomtom.woj.amelinium.journal.converter;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalNewLineAdder;

public class CumulativeToAbsoluteConverter {
	
	private BacklogJournalNewLineAdder updater = new BacklogJournalNewLineAdder();	
	
	
	// dla kazdej linijki
	// posortuj
	// dodaj header i values
	public ArrayList<BacklogChunk> convert(ArrayList<BacklogChunk> chunks) {

		ArrayList<BacklogChunk> chunksResult = new ArrayList<BacklogChunk>();
		
		for(BacklogChunk chunk:chunks) {
			convertChunk(chunk,chunksResult);
		}
		
		return chunksResult;
	}

	private void convertChunk(BacklogChunk chunk, ArrayList<BacklogChunk> chunksResult) {
		
		ArrayList<HeaderValuePair> pairs = new ArrayList<HeaderValuePair>();
		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		
		for(int i=0; i<chunk.dates.size(); i++) {
			headers.clear();
			values.clear();
			pairs.clear();
			for(int j=1; j<chunk.header.size(); j++) {
				HeaderValuePair pair = new HeaderValuePair();
				pair.header = chunk.header.get(j);
				pair.value = chunk.cols.get(j).get(i);
				pairs.add(pair);
			}
			
			Collections.sort(pairs);
			
			double previousValue=chunk.cols.get(0).get(i);
			for(HeaderValuePair pair:pairs) {
				headers.add(pair.header);
				values.add(pair.value-previousValue);
				previousValue = pair.value;
			}
			
			DateTime dateTime = chunk.dates.get(i);
			double burned = chunk.cols.get(0).get(i);
			
			updater.addAll(chunksResult, dateTime, burned, headers, values);
		}
	}

	static class HeaderValuePair implements Comparable<HeaderValuePair> {
		public String header;
		public double value;
		
		@Override
		public int compareTo(HeaderValuePair o) {
			return value > o.value?1:-1;
		}
		
	}

}
