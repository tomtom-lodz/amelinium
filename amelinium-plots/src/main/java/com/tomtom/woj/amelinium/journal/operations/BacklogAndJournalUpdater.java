package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogAndJournalUpdater {

	public void update(DateTime dateTime, BacklogModel backlogModel, ArrayList<BacklogChunk> chunks) {
		
		int points = backlogModel.getOverallBurnedStoryPoints();
		ArrayList<FeatureGroup> fetureGroups = backlogModel
				.getFeatureGroupsFromAllSubProjects();

		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		for (FeatureGroup featureGroup : fetureGroups) {
			headers.add(featureGroup.getTitle());
			values.add((double) (featureGroup.getPoints() - featureGroup.getDonePoints()));
		}

		BacklogJournalUpdater updater = new BacklogJournalUpdater();

		// when
		updater.addAll(chunks, dateTime, points, headers, values);
	}
}
