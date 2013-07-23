package com.tomtom.amelinium.web.standalone.model;

/**
 * Model of the form for editing backlog content.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class TextAreaFormBacklog {
	/**
	 * States whether multiple line work items are supported. Depending on this
	 * parameter, the tool will either treat each line as a new work item or
	 * concatenate every line which doesn't end with "sp" with the preceding
	 * one.
	 */
	private Boolean allowMultilineFeatures;

	/**
	 * Backlog of the project. Should be provided as text in wiki markup syntax.
	 */
	private String text;

	public Boolean getAllowMultilineFeatures() {
		return allowMultilineFeatures;
	}

	public void setAllowMultilineFeatures(Boolean allowMultilineFeatures) {
		this.allowMultilineFeatures = allowMultilineFeatures;
	}

	public TextAreaFormBacklog() {
	}

	public TextAreaFormBacklog(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
