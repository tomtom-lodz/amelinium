package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashSet;

public class LineCleaner {

	public void limitAbsolute(ArrayList<String> headers, ArrayList<String> line, ArrayList<Double> values) {
		HashSet<String> set = new HashSet<String>();
		for(int i=0; i<headers.size(); i++) {
			set.add(headers.get(i));
		}
		
		for(int i=line.size()-1; i>=0; i--) {
			if(set.contains(line.get(i))) {
				return;
			} else {
				line.remove(i);
				values.remove(i);
			}
		}
	}

	public void limitCumulative(ArrayList<String> headers, ArrayList<String> line, ArrayList<Double> values) {
		HashSet<String> set = new HashSet<String>();
		for(int i=0; i<headers.size(); i++) {
			set.add(headers.get(i));
		}
		
		for(int i=line.size()-1; i>=0; i--) {
			if(!set.contains(line.get(i))) {
				line.remove(i);
				values.remove(i);
			}
		}
	}

	public void limitAbsoluteStrictly(ArrayList<String> headers, ArrayList<String> line, ArrayList<Double> values) {
		HashSet<String> set = new HashSet<String>();
		for(int i=0; i<headers.size(); i++) {
			set.add(headers.get(i));
		}
		
		for(int i=line.size()-1; i>=0; i--) {
			if(!set.contains(line.get(i))) {
				double removeValue = values.get(i);
				line.remove(i);
				values.remove(i);
				if(i<values.size()) {
					values.set(i, values.get(i)+removeValue);
				}
			}
		}
	}

}
