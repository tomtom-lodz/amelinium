package com.tomtom.amelinium.db.results;

/**
 * Container class that can be used either with backlog or chart history.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class HistoryElement {

	private int projectRevision;
	private int revision; //either backlog or chart revision
	private String dt;
	private int revertedFromRevision;
	
	public HistoryElement() {
	}
	public int getProjectRevision() {
		return projectRevision;
	}
	public void setProjectRevision(int projectRevision) {
		this.projectRevision = projectRevision;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public int getRevertedFromRevision() {
		return revertedFromRevision;
	}
	public void setRevertedFromRevision(int revertedFromRevision) {
		this.revertedFromRevision = revertedFromRevision;
	}
	
}
