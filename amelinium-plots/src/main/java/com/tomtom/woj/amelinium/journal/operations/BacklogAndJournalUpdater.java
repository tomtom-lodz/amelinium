package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogAndJournalUpdater {

	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private BacklogJournalReader backlogJournalReader = new BacklogJournalReader();
	private BacklogJournalSerializer backlogJournalSerializer = new BacklogJournalSerializer();
	
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
	
	public ArrayList<BacklogChunk> update(DateTime dateTime, String backlogContent, String journalContent) {

		boolean allowingMultilineFeatures = false;
		
		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromString(
				backlogContent, allowingMultilineFeatures);

		ArrayList<BacklogChunk> chunks = backlogJournalReader.readFromString(journalContent);

		update(dateTime, backlogModel, chunks);
		
		return chunks;
	}

	public String generateUpdatedString(DateTime dateTime, String backlogContent, String journalContent) {
		ArrayList<BacklogChunk> chunks = update(dateTime, backlogContent, journalContent);
		return backlogJournalSerializer.serialize(chunks);
	}
	
}
