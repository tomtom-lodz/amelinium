package com.tomtom.amelinium.projectservice.factory;

import com.tomtom.amelinium.projectservice.model.Project;
import com.tomtom.amelinium.projectservice.snapshot.BacklogSnapshot;
import com.tomtom.amelinium.projectservice.snapshot.ChartSnapshot;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

public class ProjectSnapshotFactory {
	
	public ProjectHistoryItem generateSnapshotFromNewBacklog(Project project, String newWikiMarkupBacklog){
		long revision = project.getProjectSnapshot(project.getHistorySize()-1).getRevision()+1;
		BacklogSnapshot backlogSnapshot = new BacklogSnapshot(revision,newWikiMarkupBacklog);
		ChartSnapshot chartSnapshot = new ChartSnapshot(project.getProjectSnapshot(project.getHistorySize()-1).getChartSnapshot());
		return new ProjectHistoryItem(revision, backlogSnapshot, chartSnapshot);

	}
	
	public ProjectHistoryItem generateSnapshotFromNewChart(Project project, String newWikiMarkupChart){
		long revision = project.getProjectSnapshot(project.getHistorySize()-1).getRevision()+1;
		BacklogSnapshot backlogSnapshot = new BacklogSnapshot(project.getProjectSnapshot(project.getHistorySize()-1).getBacklogSnapshot());
		ChartSnapshot chartSnapshot = new ChartSnapshot(revision, newWikiMarkupChart);
		return new ProjectHistoryItem(revision, backlogSnapshot, chartSnapshot);
	}
}
