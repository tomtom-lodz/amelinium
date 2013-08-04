package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashSet;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class DoneLinesRemover {

// this is commented out as it is better to work on merged model
// then you can look into not only previous value but the one before as well
//
//	public void removeCumulativeDoneLines(ArrayList<BacklogChunk> chunks,
//			double burned, ArrayList<String> headers, ArrayList<Double> values) {
//
//		double previousBurned;
//		ArrayList<String> previousHeaders = null;
//		ArrayList<Double> previousValues = null;
//		BacklogChunk chunk = null;
//		
//		for(int i=chunks.size()-1; i>=0; i--) {
//			if(chunks.get(i).dates.size()<1) {
//				continue;
//			} else {
//				chunk = chunks.get(i);
//				break;
//			}
//		}
//		
//		if(chunk==null) {
//			return;
//		}
//		
//		previousHeaders = new ArrayList<String>();
//		previousValues = new ArrayList<Double>();
//		int pos = chunk.dates.size()-1;
//		for(int j=1; j<chunk.cols.size(); j++) {
//			previousHeaders.add(chunk.header.get(j));
//			previousValues.add(chunk.cols.get(j).get(pos));
//		}
//		previousBurned = chunk.cols.get(0).get(pos);
//
//		// check which are allowed to be removed
//		// remove only those which are done in previous row
//		// or are not present in previous row
//
//		HashSet<String> allowedToRemove = new HashSet<String>();
//		HashSet<String> dontRemove = new HashSet<String>();
//		
//		for(int i=0; i<previousHeaders.size(); i++) {
//			if(previousValues.get(i)<=previousBurned) {
//				allowedToRemove.add(previousHeaders.get(i));
//			} else {
//				dontRemove.add(previousHeaders.get(i));
//			}
//		}
//		
//		for(int i=values.size()-1; i>=0; i--) {
//			if(values.get(i)<=burned) {
//				String header = headers.get(i);
//				if(allowedToRemove.contains(header) || !dontRemove.contains(header)) {
//					headers.remove(i);
//					values.remove(i);
//				}
//			}
//		}
//	}

	public void removeDoneLinesUsingCumulativeMerged(BacklogChunk merged,
			double burned, ArrayList<String> headers, ArrayList<Double> values,
			boolean areValuesCumulative) {

		if(merged.dates.size()<1) {
			return;
		}
		
		// przechodzac od ostatniej linijki do pierwszej
		// usuwac wszystkie wpisy ktore sa zakonczone pod takim warunkiem
		// 1. sa w pierwszej linii
		// 2. jeszcze poprzedni tez sa zakonczone
		// 3. nie ma w poprzedniej linijce
		
		HashSet<String> allowedToRemove = new HashSet<String>();
		HashSet<String> dontRemove = new HashSet<String>();
		
		for(int k=1; k<merged.header.size(); k++) {
			// kontrowersyjny przypadek - bo co wtedy gdy w poprzedniej jest NaN
			// ale jeszcze w poprzedniej jest nie-burned...
			int prevIndex = merged.dates.size()-1;
			while(Double.isNaN(merged.cols.get(k).get(prevIndex)) && prevIndex>0) {
				prevIndex--;
			}
			double previousBurned = merged.cols.get(0).get(prevIndex);
			if(merged.cols.get(k).get(prevIndex)<=previousBurned || Double.isNaN(merged.cols.get(k).get(prevIndex))) {
				allowedToRemove.add(merged.header.get(k));
			} else {
				dontRemove.add(merged.header.get(k));
			}
		}

		if(areValuesCumulative) {
			for(int i=values.size()-1; i>=0; i--) {
				if(values.get(i)<=burned) {
					String header = headers.get(i);
					if(allowedToRemove.contains(header) || !dontRemove.contains(header)) {
						headers.remove(i);
						values.remove(i);
					}
				}
			}
		} else {
			for(int i=values.size()-1; i>=0; i--) {
				if(values.get(i)<=0) {
					String header = headers.get(i);
					if(allowedToRemove.contains(header) || !dontRemove.contains(header)) {
						headers.remove(i);
						values.remove(i);
					}
				}
			}
			
		}
	}
	
	public void removeDoneLinesFromCumulativeMerged(BacklogChunk merged) {
		// przechodzac od ostatniej linijki do pierwszej
		// usuwac wszystkie wpisy ktore sa ponizej burned pod takim warunkiem
		// 1. sa w pierwszej linii
		// 2. jeszcze poprzedni tez sa burned
		// 3. nie ma w poprzedniej linijce
		
		if(merged.cols.size()<1) {
			return;
		}
		
		for(int i=merged.dates.size()-1; i>=0; i--) {

			HashSet<String> allowedToRemove = new HashSet<String>();
			HashSet<String> dontRemove = new HashSet<String>();
			
			if(i>0) {
				for(int k=1; k<merged.header.size(); k++) {
					// kontrowersyjny przypadek - bo co wtedy gdy w poprzedniej jest NaN
					// ale jeszcze w poprzedniej jest nie-burned...
					int prevIndex = i-1;
					while(Double.isNaN(merged.cols.get(k).get(prevIndex)) && prevIndex>0) {
						prevIndex--;
					}
					double previousBurned = merged.cols.get(0).get(prevIndex);
					if(merged.cols.get(k).get(prevIndex)<=previousBurned || Double.isNaN(merged.cols.get(k).get(prevIndex))) {
						allowedToRemove.add(merged.header.get(k));
					} else {
						dontRemove.add(merged.header.get(k));
					}
				}
			}
			
			double burned = merged.cols.get(0).get(i);
			for(int j=merged.cols.size()-1; j>0; j--) {
				double value = merged.cols.get(j).get(i);
				if(value>burned) {
					continue;
				}
				// candidate for removal
				String header = merged.header.get(j);
				if(i==0 || allowedToRemove.contains(header) || !dontRemove.contains(header)) {
					// remove it
					merged.cols.get(j).set(i, Double.NaN);
				}
			}
		}
		
		// usuniecie pustych kolumn
		for(int k=merged.header.size()-1; k>=1; k--) {
			int l;
			for(l=merged.dates.size()-1; l>=0; l--) {
				if(Double.isNaN(merged.cols.get(k).get(l))) {
					break;
				}
			}
			if(l==0) {
				merged.header.remove(k);
				merged.cols.remove(k);
			}
		}
		
	}

}
