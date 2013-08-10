package com.tomtom.woj.amelinium.journal.converter;

import java.util.ArrayList;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class AbsoluteToCumulativeConverterInPlace {

	public void convertIntoCumulative(ArrayList<BacklogChunk> chunks) {
		for(BacklogChunk chunk : chunks) {
			convertIntoCumulative(chunk);
		}
	}
	
	public void convertIntoCumulative(BacklogChunk chunk) {
		for(int j = 0; j<chunk.cols.get(0).size(); j++) {
			double previous = chunk.cols.get(0).get(j);
			for(int i = 1; i<chunk.cols.size(); i++) {
				double actual = chunk.cols.get(i).get(j);
				if(actual>0) {
					double value = actual + previous;
					previous = value;
					chunk.cols.get(i).set(j,value);
				} else {
					// set it to burned value
					// TODO: test for this - test this fixed bug!!!
					chunk.cols.get(i).set(j,chunk.cols.get(0).get(j));
				}
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
