package com.tomtom.amelinium.db.beans;

/**
 * Corresponds to the project table in the database.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Project {
	
	/**
	 * Id of the project (generated automatically).
	 */
	private Integer id;
	
	/**
	 * Name of the project.
	 */
	
	private String name;
	
	/**
	 * Date and time when the project was created. Has the form: yyy-MM-dd
	 * HH:mm:ss understandable for the Sqlite database.
	 */
	private String dtCreated;
	
	/**
	 * Date and time when the project was last modified. Has the form: yyy-MM-dd
	 * HH:mm:ss understandable for the Sqlite database.
	 */
	private String dtLastModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project(String dtCreated, String name) {
		super();
		this.setDtCreated(dtCreated);
		this.setDtLastModified(dtCreated);
		this.setName(name);
	}

	public Project() {

	}

	public String getDtCreated() {
		return dtCreated;
	}

	public void setDtCreated(String dtCreated) {
		this.dtCreated = dtCreated;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}
}
