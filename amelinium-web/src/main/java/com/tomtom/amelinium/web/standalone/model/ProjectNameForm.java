package com.tomtom.amelinium.web.standalone.model;

/**
 * Model of the form for renaming a project.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ProjectNameForm {

	/**
	 * Name of the project.
	 */
	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ProjectNameForm(String projectName) {
		this.projectName = projectName;
	}
	public ProjectNameForm(){
		
	}
}
