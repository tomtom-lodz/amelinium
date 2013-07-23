package com.tomtom.amelinium.backlogservice.state;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.SubProject;
import com.tomtom.amelinium.backlogservice.model.Story;

/**
 * Stores state of backlog building state machine.
 * 
 * @author Natasza Nowak
 */
public class State {

	/**
	 * Story that is currently being built.
	 */
	private Story currentStory = null;

	/**
	 * Feature that is currently being built.
	 */
	private Feature currentFeature = null;

	/**
	 * FeatureGroup that is currently being built.
	 */
	private FeatureGroup currentFeatureGroup = null;
	
	/**
	 * Project that is currently being built.
	 */
	private SubProject currentProject = null;

	/**
	 * Story that was previously built. Comments may be added to it when
	 * building the model.
	 */
	private Story lastStory = null;

	/**
	 * Feature that was previously built. New stories are added to it when
	 * building the model.
	 */
	private Feature lastFeature = null;

	/**
	 * FeatureGroup that was previously built. New Features are added to it when
	 * building the model.
	 */
	private FeatureGroup lastFeatureGroup = null;
	
	/**
	 * Project that was previously built. New Feature Groups are added to it when
	 * building the model.
	 */
	private SubProject lastProject = null;

	/**
	 * Information about what element was previously built. Thanks to this
	 * information, comments can be added to the last built element.
	 */
	private WorkItem lastAdded = null;

	/**
	 * The model that is being built.
	 */
	private BacklogModel backlogModel = new BacklogModel();

	/**
	 * Current step of the state machine.
	 */
	private Step step = Step.BUILDING_INTRO;

	/**
	 * \n\n required to build a new Feature \n required to start a comment to
	 * Story
	 */
	private boolean previousLineEmpty = false;

	public State() {
		super();
	}

	public State(Feature lastFeature, FeatureGroup lastFeatureGroup, BacklogModel structure) {
		this.lastFeature = lastFeature;
		this.lastFeatureGroup = lastFeatureGroup;
		this.backlogModel = structure;
	}

	public Story getCurrentStory() {
		return currentStory;
	}

	public void setCurrentStory(Story currentStory) {
		this.currentStory = currentStory;
	}

	public Feature getLastFeature() {
		return lastFeature;
	}

	public void setLastFeature(Feature lastFeature) {
		this.lastFeature = lastFeature;
	}

	public Feature getCurrentFeature() {
		return currentFeature;
	}

	public void setCurrentFeature(Feature currentFeature) {
		this.currentFeature = currentFeature;
	}

	public FeatureGroup getLastFeatureGroup() {
		return lastFeatureGroup;
	}

	public void setLastFeatureGroup(FeatureGroup lastFeatureGroup) {
		this.lastFeatureGroup = lastFeatureGroup;
	}

	public FeatureGroup getCurrentFeatureGroup() {
		return currentFeatureGroup;
	}

	public void setCurrentFeatureGroup(FeatureGroup currentFeatureGroup) {
		this.currentFeatureGroup = currentFeatureGroup;
	}

	public BacklogModel getBacklogModel() {
		return backlogModel;
	}

	public void setModel(BacklogModel backlogModel) {
		this.backlogModel = backlogModel;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public boolean isPreviousLineEmpty() {
		return previousLineEmpty;
	}

	public void setPreviousLineEmpty(boolean previousLineEmpty) {
		this.previousLineEmpty = previousLineEmpty;
	}

	public Story getLastStory() {
		return lastStory;
	}

	public void setLastStory(Story lastStory) {
		this.lastStory = lastStory;
	}

	public WorkItem getLastAdded() {
		return lastAdded;
	}

	public void setLastAdded(WorkItem lastAdded) {
		this.lastAdded = lastAdded;
	}

	public SubProject getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(SubProject currentProject) {
		this.currentProject = currentProject;
	}

	public SubProject getLastProject() {
		return lastProject;
	}

	public void setLastProject(SubProject lastProject) {
		this.lastProject = lastProject;
	}

}
