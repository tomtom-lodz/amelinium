package com.tomtom.woj.amelinium.journal.timelapse;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

public class TimeLapseChunkFactory {
	
	private TimeLapseChunk chunk = new TimeLapseChunk();

	public TimeLapseChunk build() {
		return chunk;
	}
	
	public void addRow(DateTime date, ArrayList<String> headers, ArrayList<DateTime> values) {

		if(isEmpty(values)) {
			return;
		}
		
		HashMap<String, ArrayList<DateTime>> nameToColumnMap = createMapOfColumnByName(chunk.headers,chunk.cols);
		HashMap<String, DateTime> nameToValueMap = createMapOfValueByName(headers,values);
		
		// add missing columns
		for(String header:headers) {
			ArrayList<DateTime> column = nameToColumnMap.get(header);
			if(column==null) {
				chunk.headers.add(header);
				chunk.cols.add(createNullList(chunk.dates.size()));
			}
		}
		
		// add all the values to columns
		for(int i=0; i<chunk.headers.size(); i++) {
			String header = chunk.headers.get(i);
			
			DateTime value = nameToValueMap.get(header);
			chunk.cols.get(i).add(value);
		}
		
		chunk.dates.add(date);
	}

	private HashMap<String, DateTime> createMapOfValueByName(ArrayList<String> headers,
			ArrayList<DateTime> values) {
		HashMap<String, DateTime> map = new HashMap<String, DateTime>();
		for(int j=0; j<headers.size(); j++) {
			map.put(headers.get(j), values.get(j));
		}
		return map;
	}

	private HashMap<String, ArrayList<DateTime>> createMapOfColumnByName(ArrayList<String> headers,
			ArrayList<ArrayList<DateTime>> columns) {
		HashMap<String, ArrayList<DateTime>> map = new HashMap<String, ArrayList<DateTime>>();
		for(int j=0; j<headers.size(); j++) {
			map.put(headers.get(j), columns.get(j));
		}
		return map;
	}

	private ArrayList<DateTime> createNullList(int size) {
		ArrayList<DateTime> nans = new ArrayList<DateTime>();
		for(int i=0; i< size; i++) {
			nans.add(null);
		}
		return nans;
	}
	
	private boolean isEmpty(ArrayList<DateTime> values) {
		for(DateTime value:values) {
			if(value!=null) {
				return false;
			}
		}
		return true;
	}

}
