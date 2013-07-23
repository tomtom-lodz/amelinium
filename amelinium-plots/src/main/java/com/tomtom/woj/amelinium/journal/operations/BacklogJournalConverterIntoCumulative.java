package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalConverterIntoCumulative {

	public void convertIntoCumulative(ArrayList<BacklogChunk> chunks) {
		for(BacklogChunk chunk : chunks) {
			convertIntoCumulative(chunk);
		}
	}
	
	public void convertIntoCumulative(BacklogChunk chunk) {
		for(int j = 0; j<chunk.cols.get(0).size(); j++) {
			double previous = chunk.cols.get(0).get(j);
			for(int i = 1; i<chunk.cols.size(); i++) {
				double value = chunk.cols.get(i).get(j) + previous;
				previous = value;
				chunk.cols.get(i).set(j,value);
				// TODO
//				if(value>0) {
//					value += previous;
//					previous = value;
//					chunk.cols.get(i).set(j,value);
//				} else {
//					chunk.cols.get(i).set(j,chunk.cols.get(i-1).get(j));
//				}
				
			}
		}
	}
	
}
