package com.tomtom.amelinium.web.standalone.model;

import java.io.Serializable;

/**
 * Model of the form for renaming a project.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ProjectNameForm implements Serializable {

	private static final long serialVersionUID = -4662207567467434165L;
	
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
