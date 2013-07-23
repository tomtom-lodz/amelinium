package com.tomtom.amelinium.db.beans;

/**
 * Corresponds to the snapshot table in the database. Used as a connecting table
 * between tables project, backlog and chart.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Snapshot {
	/**
	 * Id of the snapshot (generated automatically).
	 */
	private Integer id;

	/**
	 * Revision of the project.
	 */
	private Integer revision;

	/**
	 * Date and time when the revision was created. Has the form: yyy-MM-dd
	 * HH:mm:ss understandable for the Sqlite database.
	 */
	private String dt;
	
	/**
	 * Synchronization information. As defined in SyncStatus.java
	 */
	private String sync;

	
	/**
	 * Id of the project that this revision is related to. Like a foreign key
	 * that references project(id).
	 */
	private Integer projectId;
	
	/**
	 * Id of the backlog that this revision is related to. Like a foreign key
	 * that references backlog(id).
	 */
	private Integer backlogId;
	
	/**
	 * Id of the chart that this revision is related to. Like a foreign key
	 * that references chart(id).
	 */
	private Integer chartId;

	public Snapshot() {

	}

	public Snapshot(String dt, Integer revision, String sync, Integer projectId,
			Integer backlogId, Integer chartId) {
		super();
		this.dt = dt;
		this.revision = revision;
		this.setSync(sync);
		this.projectId = projectId;
		this.setBacklogId(backlogId);
		this.setChartId(chartId);
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChartId() {
		return chartId;
	}

	public void setChartId(Integer chartId) {
		this.chartId = chartId;
	}

	public Integer getBacklogId() {
		return backlogId;
	}

	public void setBacklogId(Integer backlogId) {
		this.backlogId = backlogId;
	}

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}
}
