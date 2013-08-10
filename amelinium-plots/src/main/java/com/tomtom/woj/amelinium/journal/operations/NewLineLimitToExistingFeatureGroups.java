package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;
import java.util.HashSet;

public class NewLineLimitToExistingFeatureGroups {

	public void limitAbsolute(ArrayList<String> existingHeaders, ArrayList<String> newHeaders,
			ArrayList<Double> newValues) {
		HashSet<String> set = new HashSet<String>();
		for(int i=0; i<existingHeaders.size(); i++) {
			set.add(existingHeaders.get(i));
		}
		
		for(int i=newHeaders.size()-1; i>=0; i--) {
			if(set.contains(newHeaders.get(i))) {
				return;
			} else {
				newHeaders.remove(i);
				newValues.remove(i);
			}
		}
	}

	public void limitCumulative(ArrayList<String> existingHeaders, ArrayList<String> newHeaders,
			ArrayList<Double> newValues) {
		HashSet<String> set = new HashSet<String>();
		for(int i=0; i<existingHeaders.size(); i++) {
			set.add(existingHeaders.get(i));
		}
		
		for(int i=newHeaders.size()-1; i>=0; i--) {
			if(!set.contains(newHeaders.get(i))) {
				newHeaders.remove(i);
				newValues.remove(i);
			}
		}
	}

	// This should in fact never be used
	@Deprecated
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
