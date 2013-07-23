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
import javax.persistence.Embedded;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;
import com.tomtom.amelinium.projectservice.model.OfyService;
import com.tomtom.amelinium.projectservice.model.Project;

/**
 * Used for storing project history.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
@Entity
public class ProjectHistoryItem {

	public static Key<ProjectHistoryItem> key(long id) {
		return Key.create(ProjectHistoryItem.class, id);
	}
	
	@Id
	private Long id;
	
	private long revision;
	
	private String date;

	@Ignore
//	@Load
//	private Ref<BacklogSnapshot> backlogSnapshot;
	private BacklogSnapshot backlogSnapshot;

	@Ignore
//	@Load
//	private Ref<ChartSnapshot> chartSnapshot;
	private ChartSnapshot chartSnapshot;
	
	@Ignore
	private static DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	
	public long getRevision() {
		return revision;
	}
	
	public String getDate() {
		return date;
	}
	
	public BacklogSnapshot getBacklogSnapshot() {
//		return backlogSnapshot.get();
		return backlogSnapshot;
	}
	
	
	public ChartSnapshot getChartSnapshot() {
//		return chartSnapshot.get();
		return chartSnapshot;
	}
	
	public ProjectHistoryItem() {
	}
	
	public ProjectHistoryItem(long revision, BacklogSnapshot backlogSnapshot,
			ChartSnapshot chartSnapshot) {
		this.revision = revision;
		//this.date = new Date();
//		this.backlogSnapshot = Ref.create(backlogSnapshot);
//		this.chartSnapshot = Ref.create(chartSnapshot);
		this.backlogSnapshot = backlogSnapshot;
		this.chartSnapshot = chartSnapshot;
		Date d = new Date();
		this.date = formatter.format(d);
	}
	
	public ProjectHistoryItem(long revision, String wikiMarkupBacklog,
			String wikiMarkupChart) {
		this.revision = revision;
//		this.backlogSnapshot = Ref.create(new BacklogSnapshot(revision, wikiMarkupBacklog));
//		this.chartSnapshot = Ref.create(new ChartSnapshot(revision, wikiMarkupChart));
		this.backlogSnapshot = new BacklogSnapshot(revision, wikiMarkupBacklog);
		this.chartSnapshot = new ChartSnapshot(revision, wikiMarkupChart);
		Date d = new Date();
		this.date = formatter.format(d);
	}

	public void save() {
		OfyService.ofy().save().entity(this).now();
		
		long i = getId();
		i = i;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
