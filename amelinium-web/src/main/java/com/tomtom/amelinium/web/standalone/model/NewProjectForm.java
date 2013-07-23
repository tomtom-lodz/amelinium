package com.tomtom.amelinium.web.standalone.model;

import com.tomtom.amelinium.common.LineProducer;

/**
 * Model of the form for adding new project.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class NewProjectForm {
	/**
	 * States whether multiple line work items are supported. Depending on this
	 * parameter, the tool will either treat each line of the backlog as a new
	 * work item or concatenate every line which doesn't end with "sp" with the
	 * preceding one.
	 */
	private Boolean allowMultilineFeatures;

	/**
	 * Name of the newly created project.
	 */
	private String name;

	/**
	 * Backlog of the newly created project. Should be provided as text in wiki
	 * markup syntax.
	 */
	private String backlogContent;

	/**
	 * Chart of the newly created project. Should be provided as text in wiki
	 * markup syntax.
	 */
	private String chartContent;

	public Boolean getAllowMultilineFeatures() {
		return allowMultilineFeatures;
	}

	public void setAllowMultilineFeatures(Boolean allowMultilineFeatures) {
		this.allowMultilineFeatures = allowMultilineFeatures;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBacklogContent() {
		return backlogContent;
	}

	public void setBacklogContent(String backlogContent) {
		this.backlogContent = backlogContent;
	}

	public String getChartContent() {
		return chartContent;
	}

	public void setChartContent(String chartContent) {
		this.chartContent = chartContent;
	}

	public NewProjectForm() {
		String backlogFileName = "/example/backlog.txt";
		String chartFileName = "/example/chart.txt";
		LineProducer lineProducer = new LineProducer();
		this.backlogContent = lineProducer
				.readStringFromResource(backlogFileName);
		this.chartContent = lineProducer.readStringFromResource(chartFileName);

	}

	public NewProjectForm(String name, String backlogContent,
			String chartContent) {
		super();
		this.name = name;
		this.backlogContent = backlogContent;
		this.chartContent = chartContent;
	}

	public String deleteResponse() {
		return "Project " + this.name + " deleted.";
	}
}
