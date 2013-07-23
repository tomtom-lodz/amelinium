package com.tomtom.amelinium.backlogservice.model;

import java.util.ArrayList;

public class Column {
	private String openTag="{column}";
	private String closeTag="";
	/**
	 * Array of features that belong to the feature group.
	 */
	private ArrayList<Feature> features = new ArrayList<Feature>();

	public ArrayList<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<Feature> features) {
		this.features = features;
	}

	public String getOpenTag() {
		return openTag;
	}

	public void setOpenTag(String openTag) {
		this.openTag = openTag;
	}

	public String getCloseTag() {
		return closeTag;
	}

	public void setCloseTag(String closeTag) {
		this.closeTag = closeTag;
	}

	
}
