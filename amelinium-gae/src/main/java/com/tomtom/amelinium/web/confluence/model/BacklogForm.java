package com.tomtom.amelinium.web.confluence.model;

/**
 * Model of the form for backlog recalculation.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class BacklogForm {
	/**
	 * Space on the Confluence where the backlog is stored.
	 */
	private String space;

	/**
	 * Title of the backlog to be recalculated.
	 */
	private String title;

	/**
	 * States whether multiple line work items are supported. Depending on this
	 * parameter, the tool will either treat each line as a new work item or
	 * concatenate every line which doesn't end with "sp" with the preceding one.
	 */
	private Boolean allowMultilineFeatures;

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getAllowMultilineFeatures() {
		return allowMultilineFeatures;
	}

	public void setAllowMultilineFeatures(Boolean allowMultilineFeatures) {
		this.allowMultilineFeatures = allowMultilineFeatures;
	}
}
