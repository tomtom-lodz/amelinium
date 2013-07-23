package com.tomtom.amelinium.projectservice.factory;

import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.db.beans.Backlog;
import com.tomtom.amelinium.db.beans.Chart;
import com.tomtom.amelinium.db.beans.Snapshot;
import com.tomtom.amelinium.db.config.SyncStatus;
import com.tomtom.amelinium.db.logic.BacklogDbLogic;
import com.tomtom.amelinium.db.logic.ChartDbLogic;
import com.tomtom.amelinium.db.logic.ProjectDbLogic;
import com.tomtom.amelinium.db.logic.SnapshotDbLogic;
import com.tomtom.amelinium.db.time.DateTimeUtil;

/**
 * Used for generation of project snapshots/revisions. Such revisions connect
 * backlog and chart revisions. Created snapshots are inserted into database.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ProjectSnapshotFactory {

	private SnapshotDbLogic snapshotDbLogic;
	private BacklogDbLogic backlogDbLogic;
	private ChartDbLogic chartDbLogic;
	private ProjectDbLogic projectDbLogic;// = new ProjectDbLogic();
	private DateTimeUtil dateTimeUtil = new DateTimeUtil();

	public ProjectSnapshotFactory(SqlSessionFactory sqlMapper) {
	this.projectDbLogic = new ProjectDbLogic(sqlMapper);
	this.backlogDbLogic = new BacklogDbLogic(sqlMapper);
	this.chartDbLogic = new ChartDbLogic(sqlMapper);
	this.snapshotDbLogic = new SnapshotDbLogic(sqlMapper);
	}

	/**
	 * Used when existing project backlog is edited. Adds new project revision
	 * to the database (with new backlog and current chart).
	 */
	public Integer generateSnapshotFromNewBacklog(int projectId,
			String wikiMarkupBacklog) {
		int revisionUp = snapshotDbLogic.getCurrentRevision(projectId) + 1;
		String dt = dateTimeUtil.getCurrentPgDtString();
		Backlog backlog = new Backlog(dt, revisionUp, wikiMarkupBacklog);
		backlogDbLogic.insertBacklog(backlog);
		int backlogId = backlogDbLogic.getRecentlyInsertedId();
		int chartId = chartDbLogic.getCurrentId(projectId);
		Integer result = updateDatabaseWithNewSnapshot(dt,SyncStatus.BACKLOG_EDITED,projectId,backlogId,chartId);
		return result;
	}

	/**
	 * Used when existing project chart is edited. Adds new project revision to
	 * the database (with new chart and current backlog).
	 */
	public void generateSnapshotFromNewChart(int projectId,
			String wikiMarkupChart) {
		int revisionUp = snapshotDbLogic.getCurrentRevision(projectId) + 1;
		String dt = dateTimeUtil.getCurrentPgDtString();
		Chart chart = new Chart(dt, revisionUp, wikiMarkupChart);
		chartDbLogic.insertChart(chart);
		int backlogId = backlogDbLogic.getCurrentId(projectId);
		int chartId = chartDbLogic.getRecentlyInsertedId();
		updateDatabaseWithNewSnapshot(dt,SyncStatus.OK,projectId,backlogId,chartId);
	}

	/**
	 * Used when existing project backlog is reverted from an older version.
	 * Adds new project revision to the database (with reverted backlog and
	 * current chart).
	 */
	public void generateSnapshotFromRevertedBacklog(int projectId, int revision) {
		Backlog backlogFromDb = backlogDbLogic.getBacklog(projectId, revision);
		String wikiMarkupBacklog = backlogFromDb.getContent();
		int revisionUp = snapshotDbLogic.getCurrentRevision(projectId) + 1;
		String dt = dateTimeUtil.getCurrentPgDtString();
		Backlog backlog = new Backlog(dt, revisionUp, revision,
				wikiMarkupBacklog);
		backlogDbLogic.insertBacklog(backlog);
		int backlogId = backlogDbLogic.getRecentlyInsertedId();
		int chartId = chartDbLogic.getCurrentId(projectId);
		updateDatabaseWithNewSnapshot(dt,SyncStatus.BACKLOG_REVERTED,projectId,backlogId,chartId);
	}

	/**
	 * Used when existing project chart is reverted from an older version. Adds
	 * new project revision to the database (with reverted chart and current
	 * backlog).
	 */
	public void generateSnapshotFromRevertedChart(int projectId, int revision) {
		Chart chartFromDb = chartDbLogic.getChart(projectId, revision);
		String wikiMarkupChart = chartFromDb.getContent();
		int revisionUp = snapshotDbLogic.getCurrentRevision(projectId) + 1;
		String dt = dateTimeUtil.getCurrentPgDtString();
		Chart chart = new Chart(dt, revisionUp, revision, wikiMarkupChart);
		chartDbLogic.insertChart(chart);
		int backlogId = backlogDbLogic.getCurrentId(projectId);
		int chartId = chartDbLogic.getRecentlyInsertedId();
		updateDatabaseWithNewSnapshot(dt,SyncStatus.CHART_REVERTED,projectId,backlogId,chartId);
	}

	private Integer updateDatabaseWithNewSnapshot(String dt, String sync, int projectId, int backlogId, int chartId) {
		int revisionUp = snapshotDbLogic.getCurrentRevision(projectId) + 1;
		Snapshot snapshot = new Snapshot(dt, revisionUp,sync, projectId, backlogId,
				chartId);
		Integer result = snapshotDbLogic.insertSnapshot(snapshot);
		projectDbLogic.updateDtLastModified(projectId, dt);
		return result;
	}
}
