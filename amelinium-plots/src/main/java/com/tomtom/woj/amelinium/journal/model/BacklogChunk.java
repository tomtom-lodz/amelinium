package com.tomtom.woj.amelinium.journal.model;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class BacklogChunk {

	public ArrayList<String> header;
	public ArrayList<DateTime> dates;
	public ArrayList<ArrayList<Double>> cols;
	
	public BacklogChunk() {
	}

	public BacklogChunk(String[] headerArray) {
		header = new ArrayList<String>();
		dates = new ArrayList<DateTime>();
		cols = new ArrayList<ArrayList<Double>>();
		for(int i=1; i<headerArray.length; i++) {
			header.add(headerArray[i]);
			cols.add(new ArrayList<Double>());
		}
	}

	public void addline(String[] line) {
		DateTime date = new DateTime(line[0]);
		dates.add(date);
		for(int i=1; i<line.length; i++) {
//			if("".equals(line[i].trim())) {
//				cols.get(i-1).add(Double.NaN);
//			} else {
				cols.get(i-1).add(Double.parseDouble(line[i]));
//			}
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Date");
		for(int i = 0; i<header.size(); i++) {
//			if(i!=0) {
				sb.append(",");
//			}
			sb.append(header.get(i));
		}
		sb.append("\n");
		for(int j = 0; j<cols.get(0).size(); j++) {
			sb.append(dates.get(j).toString("yyyy-MM-dd"));
			for(int i = 0; i<cols.size(); i++) {
//				if(i!=0) {
					sb.append(",");
//				}
				sb.append(cols.get(i).get(j));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	
}
