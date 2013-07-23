package com.tomtom.amelinium.db.beans;

/**
 * Corresponds to the backlog table in the database. Backlog table contains
 * history of revisions of the backlog.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Backlog {
	/**
	 * Id of the backlog (generated automatically).
	 */
	private Integer id;
	
	/**
	 * Revision of the backlog.
	 */
	private Integer revision;
	
	/**
	 * Date and time when the revision was created. Has the form: yyy-MM-dd
	 * HH:mm:ss understandable for the Sqlite database.
	 */
	private String dt;
	
	/**
	 * Content of the backlog in wiki markup.
	 */
	private String content;
	
	/**
	 * Revision of the backlog from which this backlog was reverted. Set to 0 or
	 * null if this backlog was created by the user, not reverted from already
	 * existing one from the history.
	 */
	private Integer revertedFromRevision;

	public Backlog(String dt, Integer revision, String content) {
		super();
		this.dt = dt;
		this.revision = revision;
		this.content = content;
	}

	public Backlog(String dt, Integer revision, Integer revertedFromRevision,
			String content) {
		super();
		this.dt = dt;
		this.revision = revision;
		this.content = content;
		this.revertedFromRevision = revertedFromRevision;
	}

	public Backlog() {

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRevertedFromRevision() {
		return revertedFromRevision;
	}

	public void setRevertedFromRevision(Integer revertedFromRevision) {
		this.revertedFromRevision = revertedFromRevision;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
