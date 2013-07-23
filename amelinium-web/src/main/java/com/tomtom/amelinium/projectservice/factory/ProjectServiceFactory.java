package com.tomtom.amelinium.projectservice.factory;

import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.serializer.ChartModelSerializer;
import com.tomtom.amelinium.db.beans.Backlog;
import com.tomtom.amelinium.db.beans.Chart;
import com.tomtom.amelinium.db.beans.Project;
import com.tomtom.amelinium.db.beans.Snapshot;
import com.tomtom.amelinium.db.config.SyncStatus;
import com.tomtom.amelinium.db.logic.BacklogDbLogic;
import com.tomtom.amelinium.db.logic.ChartDbLogic;
import com.tomtom.amelinium.db.logic.ProjectDbLogic;
import com.tomtom.amelinium.db.logic.SnapshotDbLogic;
import com.tomtom.amelinium.db.time.DateTimeUtil;

/**
 * Factory that builds a project model out of the given sources.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ProjectServiceFactory {
	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
	private BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
	private ChartModelSerializer chartModelSerializer = new ChartModelSerializer();
	private BacklogDbLogic backlogDbLogic;
	private ChartDbLogic chartDbLogic;
	private ProjectDbLogic projectDbLogic;//= new ProjectDbLogic();
	private SnapshotDbLogic snapshotDbLogic;

	public ProjectServiceFactory(SqlSessionFactory sqlMapper){
		this.projectDbLogic = new ProjectDbLogic(sqlMapper);
		this.backlogDbLogic = new BacklogDbLogic(sqlMapper);
		this.chartDbLogic = new ChartDbLogic(sqlMapper);	
		this.snapshotDbLogic = new SnapshotDbLogic(sqlMapper);
	}
	
	/**
	 * Used when new project is created using Amelinium stand-alone web
	 * application. Reads backlog and chart from wiki markup strings. Builds
	 * models based on them. Corrects models. Serializes models to wiki markup
	 * strings. Creates project, backlog, chart and snapshot database beans with
	 * current Joda DateTime. Persists these beans in the Sqlite database.
	 */
	public void readAndCorrectAndPersistModelsFromStrings(String name,
			String wikiMarkupBacklog, String wikiMarkupChart,
			Boolean allowMultilineFeatures) {

		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromString(wikiMarkupBacklog,
						allowMultilineFeatures);
		ChartModel chartModel = chartServiceFactory
				.readAndCorrectChartModelFromBacklogModelAndChartString(
						backlogModel, wikiMarkupChart);
		String correctedWikiMarkupChart = chartModelSerializer
				.serializeChartModel(chartModel, backlogModel);
		String correctedWikiMarkupBacklog = backlogModelSerializer
				.serializeModel(backlogModel);
		
		DateTimeUtil dtUtil = new DateTimeUtil();
		String sqliteDtString = dtUtil.getCurrentPgDtString();
		int revision = 1;

		Backlog backlog = new Backlog(sqliteDtString, revision,
				correctedWikiMarkupBacklog);
		backlogDbLogic.insertBacklog(backlog);

		Chart chart = new Chart(sqliteDtString, revision,
				correctedWikiMarkupChart);
		chartDbLogic.insertChart(chart);

		Project project = new Project(sqliteDtString, name);
		projectDbLogic.insertProject(project);

		int projectId = projectDbLogic.getRecentlyInsertedId();
		int backlogId = backlogDbLogic.getRecentlyInsertedId();
		int chartId = chartDbLogic.getRecentlyInsertedId();

		Snapshot snapshot = new Snapshot(sqliteDtString, revision, SyncStatus.OK, projectId,
				backlogId, chartId);

		snapshotDbLogic.insertSnapshot(snapshot);
	}
}
