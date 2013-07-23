package com.tomtom.amelinium.backlogservice.model;

import java.util.ArrayList;

/**
 * Object model of the backlog.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class BacklogModel {

	
	private boolean allowingMulitilineFeatures=false;
	/**
	 * Represents the text on the Confluence page before the BACKLOG marker (not
	 * to be parsed).
	 */
	private String intro = "";

	/**
	 * Sum of all the points burned in the backlog.
	 */
	private int overallBurnedStoryPoints = 0;

	/**
	 * List of sub-projects in the backlog.
	 */
	private ArrayList<SubProject> subProjects = new ArrayList<SubProject>();
	
	private boolean dividedIntoSubProjects = false;

	public BacklogModel() {
		super();
		subProjects.add(new SubProject());
	}

	/**
	 * Concatenates a line to intro followed by new line.
	 */
	public void addLineToIntro(String line) {
		this.intro += line + "\n";
	}
	
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getOverallBurnedStoryPoints() {
		return overallBurnedStoryPoints;
	}

	public void setOverallBurnedStoryPoints(int burnedStoryPoints) {
		this.overallBurnedStoryPoints = burnedStoryPoints;
	}

	public void addToBurnedStoryPoints(int burnedStoryPoints) {
		this.overallBurnedStoryPoints += burnedStoryPoints;
	}

	public ArrayList<SubProject> getSubProjects() {
		return subProjects;
	}

	public void setProjects(ArrayList<SubProject> subProjects) {
		this.subProjects = subProjects;
	}
	
	public SubProject getLastSubProject (){
		return subProjects.get(subProjects.size()-1);
	}
	public SubProject getFirstSubProject (){
		return subProjects.get(0);
	}

	public boolean isDividedIntoSubProjects() {
		return dividedIntoSubProjects;
	}	

	public boolean isAllowingMulitilineFeatures() {
		return allowingMulitilineFeatures;
	}

	public void setAllowingMulitilineFeatures(boolean allowMulitilineFeatures) {
		this.allowingMulitilineFeatures = allowMulitilineFeatures;
	}

	public void setDividedIntoSubProjects(boolean dividedIntoSubProjects) {
		this.dividedIntoSubProjects = dividedIntoSubProjects;
	}

	public ArrayList<FeatureGroup> getFeatureGroupsFromAllSubProjects() {
		ArrayList<FeatureGroup> featureGroups = new ArrayList<FeatureGroup>();
		for (SubProject sp : subProjects){
			for (FeatureGroup fg : sp.getFeatureGroups()){
				featureGroups.add(fg);
			}
		}
		return featureGroups;
	}

}
