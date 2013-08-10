package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashSet;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class DoneLinesRemover {

	/**
	 * Remove from new lines all values that are complete unless they are
	 * 1. not finished in previous line in merged
	 * 2. not present in merged
	 */
	public void removeDoneFromNewLinesUsingCumulativeMerged(BacklogChunk cumulativeMerged,
			double burned, ArrayList<String> newHeaders, ArrayList<Double> newValues,
			boolean areNewValuesCumulative) {

		if(cumulativeMerged.dates.size()<1) {
			return;
		}
		
		HashSet<String> dontRemove = analysePreviousLinesToDetermineWhichShouldNotBeRemoved(cumulativeMerged, cumulativeMerged.dates.size()-1);

		if(areNewValuesCumulative) {
			removeValuesIfLessThan(burned, dontRemove, newHeaders, newValues);
		} else {
			removeValuesIfLessThan(0, dontRemove, newHeaders, newValues);
		}
	}

	// przechodzac od ostatniej linijki do pierwszej
	// usuwac wszystkie wpisy ktore sa zakonczone pod takim warunkiem
	// 1. sa w pierwszej linii
	// 2. jeszcze poprzedni tez sa zakonczone
	// 3. nie ma tego wpisu w zadnej poprzedniej linijce
	//
	/**
	 * Starting from the last row in merged up to the first row.
	 * Remove (make NaN) all values that are done unless they are
	 * 1. not finished in previous line in merged
	 * 2. not present in merged
	 * Values from the first line can be removed if done
	 */
	public void removeDoneLinesFromCumulativeMerged(BacklogChunk cumulativeMerged) {
		
		if(cumulativeMerged.cols.size()<1) {
			return;
		}
		
		for(int i=cumulativeMerged.dates.size()-1; i>=0; i--) {

			HashSet<String> dontRemove = new HashSet<String>();
			
			if(i>0) {
				dontRemove = analysePreviousLinesToDetermineWhichShouldNotBeRemoved(cumulativeMerged, i-1);
			}
			
			double burned = cumulativeMerged.cols.get(0).get(i);
			for(int j=cumulativeMerged.cols.size()-1; j>0; j--) {
				double value = cumulativeMerged.cols.get(j).get(i);
				if(value>burned) {
					continue;
				}
				// candidate for removal
				String header = cumulativeMerged.header.get(j);
				if(i==0 || !dontRemove.contains(header)) {
					// remove it
					cumulativeMerged.cols.get(j).set(i, Double.NaN);
				}
			}
		}
		
		removeEmptyColumns(cumulativeMerged);
	}

	private void removeValuesIfLessThan(double burned, HashSet<String> dontRemove,
			ArrayList<String> newHeaders, ArrayList<Double> newValues) {
		
		for(int i=newValues.size()-1; i>=0; i--) {
			if(newValues.get(i)<=burned) {
				String header = newHeaders.get(i);
				if(!dontRemove.contains(header)) {
					newHeaders.remove(i);
					newValues.remove(i);
				}
			}
		}
	}
	
	private HashSet<String> analysePreviousLinesToDetermineWhichShouldNotBeRemoved(BacklogChunk cumulativeMerged, int lineIndex) {

		HashSet<String> dontRemove = new HashSet<String>();
		
		for(int k=1; k<cumulativeMerged.header.size(); k++) {
			// this solves the case when value is NaN in previous line
			// but is not done in lines before that
			int prevIndex = getIndexOfPreviousNonemptyValue(cumulativeMerged, k, lineIndex);
			
			double previousBurned = cumulativeMerged.cols.get(0).get(prevIndex);
			double previousValue = cumulativeMerged.cols.get(k).get(prevIndex);

			// dont remove if it was not finished in previous lines
			if(!Double.isNaN(previousValue) && previousValue>previousBurned) {
				dontRemove.add(cumulativeMerged.header.get(k));
			}
		}
		
		return dontRemove;
	}

	private int getIndexOfPreviousNonemptyValue(BacklogChunk cumulativeMerged, int columnIndex, int lineIndex) {
		int prevIndex = lineIndex;
		while(Double.isNaN(cumulativeMerged.cols.get(columnIndex).get(prevIndex)) && prevIndex>0) {
			prevIndex--;
		}
		return prevIndex;
	}

	private void removeEmptyColumns(BacklogChunk merged) {
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
