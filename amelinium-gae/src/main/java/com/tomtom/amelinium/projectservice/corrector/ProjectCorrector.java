package com.tomtom.amelinium.projectservice.corrector;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.serializer.ChartModelSerializer;
import com.tomtom.amelinium.projectservice.cache.JSPChart;
import com.tomtom.amelinium.projectservice.config.Messages;
import com.tomtom.amelinium.projectservice.factory.ProjectSnapshotFactory;
import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.serializer.JSPChartSerializer;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

/**
 * Used for updating project model according to changes done by the user.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ProjectCorrector {
	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
	private WikiToHTMLSerializer wikiToHTMLSerializer = new WikiToHTMLSerializer();
	private JSPChartSerializer jspChartSerializer = new JSPChartSerializer();
	private BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
	private ChartModelSerializer chartModelSerializer = new ChartModelSerializer();
	private ProjectSnapshotFactory projectSnapshotFactory = new ProjectSnapshotFactory();

	/**
	 * Reverts backlog to a specified revision.
	 */
	public void revertBacklog(Project project, int revision) {
		ProjectHistoryItem snapshot = project.getProjectSnapshot(revision);

		String wikiMarkupContent = snapshot.getBacklogSnapshot()
				.getWikiMarkupContent();
		boolean allowingMultilineFeatures = project.getBacklogModel()
				.isAllowingMulitilineFeatures();

		this.updateProjectWithNewBacklog(project, wikiMarkupContent,
				allowingMultilineFeatures);
		String info=Messages.revertedInfo+revision;
		project.getProjectSnapshot(project.getHistorySize()-1).getBacklogSnapshot().setInfo(info);

	}

	/**
	 * Reverts chart to a specified revision.
	 */
	public void revertChart(Project project, int revision) {
		ProjectHistoryItem snapshot = project.getProjectSnapshot(revision);

		String wikiMarkupContent = snapshot.getChartSnapshot()
				.getWikiMarkupContent();

		this.updateProjectWithNewChart(project, wikiMarkupContent);
		String info=Messages.revertedInfo+revision;
		project.getProjectSnapshot(project.getHistorySize()-1).getChartSnapshot().setInfo(info);
	}

	/**
	 * Updates backlog of a given project based on a provided content.
	 */
	public void updateProjectWithNewBacklog(Project project,
			String wikiMarkupBacklog, boolean allowingMultilineFeatures) {
		BacklogModel newBacklogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromString(wikiMarkupBacklog,
						allowingMultilineFeatures);
		String newWikiMarkupBacklog = backlogModelSerializer
				.serializeModel(newBacklogModel);
		String newHTMLBacklog = wikiToHTMLSerializer
				.convert(newWikiMarkupBacklog);
		ProjectHistoryItem projectHistoryItem = projectSnapshotFactory
				.generateSnapshotFromNewBacklog(project, newWikiMarkupBacklog);
		updateProjectBacklog(project, newBacklogModel, newWikiMarkupBacklog,
				newHTMLBacklog, projectHistoryItem);
	}

	private void updateProjectBacklog(Project project,
			BacklogModel newBacklogModel, String newWikiMarkupBacklog,
			String newHTMLBacklog, ProjectHistoryItem projectHistoryItem) {
		project.setBacklogModel(newBacklogModel);
		// TODO: remove newWikiMarkupBacklog
//		project.getCache().setWikiMarkupBacklog(newWikiMarkupBacklog);
//		project.getCache().setHtmlBacklog(newHTMLBacklog);
		project.addProjectSnapshotToHistory(projectHistoryItem);
		project.setUpToDate(false);
	}

	/**
	 * Updates chart of a given project based on a provided content. Chart gets
	 * updated for a sprint specified in chart configuration table.
	 * 
	 * @param newWikiMarkupChart
	 *            includes configuration table with sprint number
	 */
	public void updateProjectWithNewChart(Project project,
			String newWikiMarkupChart) {
		BacklogModel backlogModel = project.getBacklogModel();
		ChartModel newChartModel = chartServiceFactory
				.readAndCorrectChartModelFromBacklogModelAndChartString(
						backlogModel, newWikiMarkupChart);
		JSPChart newjspChart = jspChartSerializer.serializeChartModel(
				backlogModel, newChartModel);
		String correctedWikiMarkupChart = chartModelSerializer
				.serializeChartModel(newChartModel, backlogModel);
		ProjectHistoryItem projectHistoryItem = projectSnapshotFactory
				.generateSnapshotFromNewChart(project, correctedWikiMarkupChart);
		updateProjectChart(project, newChartModel, newjspChart,
				correctedWikiMarkupChart, projectHistoryItem);
	}

	private void updateProjectChart(Project project, ChartModel newChartModel,
			JSPChart newjspChart, String correctedWikiMarkupChart,
			ProjectHistoryItem projectHistoryItem) {
		project.setChartModel(newChartModel);
		// TODO: remove newjspChart
//		project.getCache().setWikiMarkupChart(correctedWikiMarkupChart);
//		project.getCache().setJspChart(newjspChart);
		project.addProjectSnapshotToHistory(projectHistoryItem);
		project.setUpToDate(true);
	}
}
