package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalUpdater {

	LineCleaner cleaner = new LineCleaner();
	
//	public void updateOnlyExistingColumns(ArrayList<BacklogChunk> chunks, ArrayList<String> newHeader) {
//		if(chunks.isEmpty()) {
//			return;
//		}
//		
//		BacklogChunk history = chunks.get(chunks.size()-1);

	public void addOnlyExistingColumnsCumulative(ArrayList<BacklogChunk> chunks, DateTime dateTime,
			double burned, ArrayList<String> headers, ArrayList<Double> values) {
		if(!chunks.isEmpty()) {
			cleaner.limitCumulative(chunks.get(chunks.size()-1).header, headers, values);
		}
		
		addAll(chunks,dateTime,burned,headers,values);
	}
	
	public void addOnlyExistingColumnsAbsolute(ArrayList<BacklogChunk> chunks, DateTime dateTime,
			double burned, ArrayList<String> headers, ArrayList<Double> values) {
		if(!chunks.isEmpty()) {
			cleaner.limitAbsolute(chunks.get(chunks.size()-1).header, headers, values);
		}
		
		addAll(chunks,dateTime,burned,headers,values);
	}
	
	public void addOnlyExistingColumnsCumulative2(ArrayList<BacklogChunk> chunks, DateTime dateTime,
			double burned, ArrayList<String> headers, ArrayList<Double> values) {

		BacklogChunk lastChunk = null;
		
		boolean same = true;

		if(chunks.isEmpty()) {
			same = false;
		} else {
			lastChunk = chunks.get(chunks.size()-1);
			
			cleaner.limitCumulative(lastChunk.header, headers, values);
			
			if(lastChunk.header.size()-1>headers.size()) {
				same = false;
			} else {
				for(int i=1; i<lastChunk.header.size(); i++) {
					if(!lastChunk.header.get(i).equals(headers.get(i-1))) {
						same=false;
						break;
					}
				}
			}
		}
		
		if(!same) {
			
			HashMap<String, Double> map = new HashMap<String, Double>();
			for(int i=0; i<headers.size(); i++) {
				map.put(headers.get(i), values.get(i));
			}

			BacklogChunk newChunk = new BacklogChunk();
			newChunk.dates = new ArrayList<DateTime>();
			newChunk.dates.add(dateTime);
			newChunk.header = new ArrayList<String>();
			newChunk.header.add("Burned");
			newChunk.cols=new ArrayList<ArrayList<Double>>();
			newChunk.cols.add(new ArrayList<Double>());
			newChunk.cols.get(0).add(burned);
			for(int i=1; i<lastChunk.header.size(); i++) {
				String header = lastChunk.header.get(i);
				Double value = map.get(header);
				if(value==null) {
				} else {
					newChunk.header.add(header);
					ArrayList<Double> col = new ArrayList<Double>();
					col.add(value);
					newChunk.cols.add(col);
				}
			}
			chunks.add(newChunk);
		} else {
			lastChunk.dates.add(dateTime);
			lastChunk.cols.get(0).add(burned);
			for(int i=1; i<lastChunk.header.size(); i++) {
				lastChunk.cols.get(i).add(values.get(i-1));
			}
		}
		
//		BacklogChunk lastChunk = null;
//		
//		boolean addNew=false;
//
//		if(chunks.isEmpty()) {
//			addNew = true;
//		} else {
//			lastChunk = chunks.get(chunks.size()-1);
//
//			HashMap<String, Double> map = new HashMap<String, Double>();
//			for(int i=0; i<headers.size(); i++) {
//				map.put(headers.get(i), values.get(i));
//			}
//
//			for(int i=1; i<lastChunk.header.size(); i++) {
//				Double value = map.get(lastChunk.header.get(i));
//				if(value==null) {
//					addNew=true;
//					break;
//				}
//			}
//		}
	}
	
	public void addAll(ArrayList<BacklogChunk> chunks, DateTime dateTime,
			double burned, ArrayList<String> headers, ArrayList<Double> values) {

		BacklogChunk lastChunk = null;
		
		boolean same=true;

		if(chunks.isEmpty()) {
			same = false;
		} else {
			lastChunk = chunks.get(chunks.size()-1);
			if(lastChunk.header.size()-1!=headers.size()) {
				same = false;
			} else {
				for(int i=1; i<lastChunk.header.size(); i++) {
					if(!lastChunk.header.get(i).equals(headers.get(i-1))) {
						same=false;
						break;
					}
				}
			}
		}
		
		if(!same) {
			BacklogChunk newChunk = new BacklogChunk();
			newChunk.dates = new ArrayList<DateTime>();
			newChunk.dates.add(dateTime);
			newChunk.header = new ArrayList<String>();
			newChunk.header.add("Burned");
			newChunk.header.addAll(headers);
			newChunk.cols=new ArrayList<ArrayList<Double>>();
			newChunk.cols.add(new ArrayList<Double>());
			newChunk.cols.get(0).add(burned);
			for(int i=0; i<headers.size(); i++) {
				newChunk.cols.add(new ArrayList<Double>());
				newChunk.cols.get(i+1).add(values.get(i));
			}
			chunks.add(newChunk);
		} else {
			lastChunk.dates.add(dateTime);
			lastChunk.cols.get(0).add(burned);
			for(int i=0; i<headers.size(); i++) {
				lastChunk.cols.get(i+1).add(values.get(i));
			}
		}
		
//		BacklogChunk lastChunk = chunks.get(chunks.size()-1);
//
//		HashMap<String, Double> map = new HashMap<String, Double>();
//		for(int i=0; i<headers.size(); i++) {
//			map.put(headers.get(i), values.get(i));
//		}
//		lastChunk.dates.add(dateTime);
//		lastChunk.cols.get(0).add(burned);
//		for(int i=1; i<lastChunk.header.size(); i++) {
//			Double value = map.get(lastChunk.header.get(i));
//			if(value==null) {
//				lastChunk.cols.get(i).add(Double.NaN);
//			} else {
//				lastChunk.cols.get(i).add(value);
//			}
//		}
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
