package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.Collections;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class MergedBacklogColumnSorter {

	public void sortColumns(BacklogChunk merged) {
		ArrayList<ColumnInfo> infoList = new ArrayList<ColumnInfo>();
		
		for(int i=1; i<merged.header.size(); i++) {
			ColumnInfo info = new ColumnInfo();
			info.header = merged.header.get(i);
			info.column = merged.cols.get(i);
			info.lastValueIdx = findLastValueIdx(info.column);
			if(info.lastValueIdx>=0) {
				info.lastValue = info.column.get(info.lastValueIdx);
			} else {
				info.lastValue = Double.MIN_VALUE;
			}
			infoList.add(info);
		}
		Collections.sort(infoList);

		String h1 = merged.header.get(0);
		ArrayList<Double> c1 = merged.cols.get(0);
		merged.header.clear();
		merged.cols.clear();
		merged.header.add(h1);
		merged.cols.add(c1);
		for(ColumnInfo info:infoList) {
			merged.header.add(info.header);
			merged.cols.add(info.column);
		}
	}

	private int findLastValueIdx(ArrayList<Double> col) {
		int i;
		for(i=col.size()-1; i>=0; i--) {
			if(!Double.isNaN(col.get(i))) {
				return i;
			}
		}
		return -1;
	}

	
	static class ColumnInfo implements Comparable<ColumnInfo> {

		public double lastValue;
		public int lastValueIdx;
		public ArrayList<Double> column;
		public String header;
		
		@Override
		public int compareTo(ColumnInfo o) {
			if(lastValueIdx!=o.lastValueIdx) {
				return o.lastValueIdx-lastValueIdx;
			}
			if(lastValue!=o.lastValue) {
				return o.lastValue-lastValue<0?-1:1;
			}
			return 0;
		}
	}

}
