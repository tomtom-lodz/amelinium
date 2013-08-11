package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogChunksMerger {

	public BacklogChunk mergeCumulativeChunks(ArrayList<BacklogChunk> chunks) {
		
		ArrayList<String> newHeaders = collectAllHeadersFromChunks(chunks);
		ArrayList<DateTime> newDates = new ArrayList<DateTime>();
		ArrayList<ArrayList<Double>> newColumns = createArrayOfEmptyArrays(newHeaders.size());
		
		for(BacklogChunk chunk : chunks) {
			addChunkToHeadersAndColumns(chunk, newHeaders, newColumns);
			newDates.addAll(chunk.dates);
		}
		
		BacklogChunk mergedChunk = new BacklogChunk();
		mergedChunk.header = newHeaders;
		mergedChunk.dates = newDates;
		mergedChunk.cols = newColumns;
		
		return mergedChunk;
	}

	private void addChunkToHeadersAndColumns(BacklogChunk chunk,
			ArrayList<String> newHeaders, ArrayList<ArrayList<Double>> newColumns) {

		// for given chunk create a map of column name to column
		HashMap<String, ArrayList<Double>> nameToColumnMap = createMapOfColumnByName(chunk);
		
		int numRows=0;
		if(chunk.cols.size()>0) {
			numRows = chunk.cols.get(0).size();
		}
		
		// for each new column add members from chunk or NaNs if it does not exist
		int columnIdx=0;
		for(String columnName : newHeaders) {
			ArrayList<Double> column = nameToColumnMap.get(columnName);
			if(column==null) {
				// if column does not exist in chunk then add NaNs
				column = createNansList(numRows);
			}
			newColumns.get(columnIdx++).addAll(column);
		}
	}

	private ArrayList<ArrayList<Double>> createArrayOfEmptyArrays(int size) {
		ArrayList<ArrayList<Double>> newColumns = new ArrayList<ArrayList<Double>>();
		for(int i=0; i<size; i++) {
			newColumns.add(new ArrayList<Double>());
		}
		return newColumns;
	}

	private HashMap<String, ArrayList<Double>> createMapOfColumnByName(BacklogChunk chunk) {
		HashMap<String, ArrayList<Double>> map = new HashMap<String, ArrayList<Double>>();
		for(int j=0; j<chunk.header.size(); j++) {
			map.put(chunk.header.get(j), chunk.cols.get(j));
		}
		return map;
	}

	private ArrayList<String> collectAllHeadersFromChunks(ArrayList<BacklogChunk> chunks) {
		
		LinkedHashSet<String> newHeaderSet = new LinkedHashSet<String>();
		for(int i=chunks.size()-1; i>=0; i--) {
			newHeaderSet.addAll(chunks.get(i).header);
		}
		ArrayList<String> newHeader = new ArrayList<String>();
		newHeader.addAll(newHeaderSet);
		
		return newHeader;
	}

	private ArrayList<Double> createNansList(int size) {
		ArrayList<Double> nans = new ArrayList<Double>();
		for(int i=0; i< size; i++) {
			nans.add(Double.NaN);
		}
		return nans;
	}

}
