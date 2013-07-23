package com.tomtom.amelinium.chartservice.model;

/**
 * Representation of the row of the roadmap.
 * 
 *  @author Natasza.Nowak@tomtom.com
 */
public class RoadmapRow {
	/**
	 * Name of the feature group. Depending on the option in Configuration
	 * table, this name may be be the one parsed from the release planning page
	 * or changed to the one from backlog or chart.
	 */
	private String featureGroup;
	/**
	 * Number of a sprint in which the development of the corresponding feature
	 * group should be finished.
	 */
	private int sprint;
	/**
	 * Date on which the development of the corresponding feature group should
	 * be finished.
	 */
	private String endOfDevelopment;
	/**
	 * Information whether the corresponding feature group will be deployed to
	 * production (and possibly: when).
	 */
	private String deploymentToProduction;

	public String getFeatureGroup() {
		return featureGroup;
	}

	public void setFeatureGroup(String featureGroup) {
		this.featureGroup = featureGroup;
	}

	public int getSprint() {
		return sprint;
	}

	public void setSprint(int sprint) {
		this.sprint = sprint;
	}

	public String getEndOfDevelopment() {
		return endOfDevelopment;
	}

	public void setEndOfDevelopment(String endOfDevelopment) {
		this.endOfDevelopment = endOfDevelopment;
	}

	public String getDeploymentToProduction() {
		return deploymentToProduction;
	}

	public void setDeploymentToProduction(String deploymentToProduction) {
		this.deploymentToProduction = deploymentToProduction;
	}

	public RoadmapRow(String featureGroup, int sprint, String endOfDevelopment,
			String deploymentToProduction) {
		super();
		this.featureGroup = featureGroup;
		this.sprint = sprint;
		this.endOfDevelopment = endOfDevelopment;
		this.deploymentToProduction = deploymentToProduction;
	}

}
