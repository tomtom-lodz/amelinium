package com.tomtom.woj.amelinium.journal.timelapse;

import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.DateTime;

public class TimeLapseChunk {
	
	public ArrayList<String> headers = new ArrayList<String>();
	public ArrayList<DateTime> dates = new ArrayList<DateTime>();
	public ArrayList<ArrayList<DateTime>> cols = new ArrayList<ArrayList<DateTime>>();

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Date");
		for(int i = 0; i<headers.size(); i++) {
			sb.append(",");
			sb.append(StringEscapeUtils.escapeCsv(headers.get(i)));
		}
		sb.append("\n");
		for(int j = 0; j<dates.size(); j++) {
			sb.append(dates.get(j).toString("yyyy-MM-dd"));
			for(int i = 0; i<cols.size(); i++) {
				sb.append(",");
				DateTime value = cols.get(i).get(j);
				if(value==null) {
					sb.append("");
				} else {
					sb.append(value.toString("yyyy-MM-dd"));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
