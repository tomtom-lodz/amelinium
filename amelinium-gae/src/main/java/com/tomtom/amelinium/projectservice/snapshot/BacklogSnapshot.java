package com.tomtom.amelinium.projectservice.snapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;

//@Entity
public class BacklogSnapshot {
	
//	@Id
	private Long id;
	
	private long revision;
	
	private String date;
	
	private String info = "";
	
	private Text wikiMarkupContent;
	
//	@Persistent
//	private ProjectSnapshot projectSnapshot;
	
//	@Ignore
	private static DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
//	static DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");	
	
	public BacklogSnapshot() {
	}
	
	/**
	 * Copy constructor.
	 */
	public BacklogSnapshot(BacklogSnapshot lastBacklogSnapshot) {
		this.revision = lastBacklogSnapshot.getRevision();
		this.wikiMarkupContent = new Text(lastBacklogSnapshot.getWikiMarkupContent());
		Date d = new Date();
		this.date = formatter.format(d);
	}	
	
	public BacklogSnapshot(long revision, String wikiMarkupContent) {
		this.revision = revision;
		this.wikiMarkupContent = new Text(wikiMarkupContent);
		Date d = new Date();
		this.date = formatter.format(d);
//		this.date = dateFormatter.print(date);
	}
	
	public long getRevision() {
		return revision;
	}
	public String getWikiMarkupContent() {
		return wikiMarkupContent.getValue();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDate() {
		return date;
	}

//	public ProjectSnapshot getProjectSnapshot() {
//		return projectSnapshot;
//	}
//
//	public void setProjectSnapshot(ProjectSnapshot projectSnapshot) {
//		this.projectSnapshot = projectSnapshot;
//	}

}
