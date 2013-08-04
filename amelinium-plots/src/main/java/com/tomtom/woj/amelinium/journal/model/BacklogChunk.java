package com.tomtom.woj.amelinium.journal.model;

import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
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

	public void addline(String[] line, boolean nullAllowed) {
		DateTime date = new DateTime(line[0]);
		dates.add(date);
		for(int i=1; i<line.length; i++) {
			if(nullAllowed && line[i].trim().isEmpty()) {
				cols.get(i-1).add(Double.NaN);
			} else {
				cols.get(i-1).add(Double.parseDouble(line[i]));
			}
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
			sb.append(StringEscapeUtils.escapeCsv(header.get(i)));
		}
		sb.append("\n");
		for(int j = 0; j<dates.size(); j++) {
			sb.append(dates.get(j).toString("yyyy-MM-dd"));
			for(int i = 0; i<cols.size(); i++) {
//				if(i!=0) {
					sb.append(",");
//				}
				double value = cols.get(i).get(j);
				if(Double.isNaN(value)) {
					sb.append("");
				} else {
					sb.append(value);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cols == null) ? 0 : cols.hashCode());
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BacklogChunk other = (BacklogChunk) obj;
		if (!cols.equals(other.cols))
			return false;
		if (!dates.equals(other.dates))
			return false;
		if (!header.equals(other.header))
			return false;
		return true;
	}
	
}
