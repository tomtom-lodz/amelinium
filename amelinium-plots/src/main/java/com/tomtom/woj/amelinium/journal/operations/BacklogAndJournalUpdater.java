package com.tomtom.woj.amelinium.journal.operations;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.utils.ConfluenceCleaningUtils;

public class BacklogAndJournalUpdater {

	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private BacklogJournalReader backlogJournalReader = new BacklogJournalReader();
	private BacklogJournalSerializer backlogJournalSerializer = new BacklogJournalSerializer();
	private BacklogJournalUpdater updater = new BacklogJournalUpdater();
	private DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
	private BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
	private BacklogJournalConverterIntoCumulative convert = new BacklogJournalConverterIntoCumulative();

	public String create(DateTime dateTime, String backlogContent, boolean isCumulative) {
		boolean allowingMultilineFeatures = false;
		
		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromString(
				backlogContent, allowingMultilineFeatures);
		
		return create(dateTime, backlogModel, isCumulative).toString();
	}
	
	public BacklogChunk create(DateTime dateTime, BacklogModel backlogModel, boolean isCumulative) {
		int burnedPoints = backlogModel.getOverallBurnedStoryPoints();
		ArrayList<FeatureGroup> fetureGroups = backlogModel.getFeatureGroupsFromAllSubProjects();
		
		BacklogChunk chunk = new BacklogChunk();
		chunk.dates = new ArrayList<DateTime>();
		chunk.dates.add(dateTime);
		chunk.header = new ArrayList<String>();
		chunk.header.add("Burned");
		chunk.cols = new ArrayList<ArrayList<Double>>();
		chunk.cols.add(new ArrayList<Double>());
		chunk.cols.get(0).add((double) burnedPoints);

		for (FeatureGroup featureGroup : fetureGroups) {
			if(featureGroup.getCummulativePoints()<=burnedPoints) {
				continue;
			}
			chunk.header.add(featureGroup.getTitle());
			ArrayList<Double> values = new ArrayList<Double>();
			if(isCumulative) {
				values.add((double) featureGroup.getCummulativePoints());
			} else {
				values.add((double) (featureGroup.getPoints() - featureGroup.getDonePoints()));
			}
			chunk.cols.add(values);
		}
		return chunk;
	}
	
	public void update(DateTime dateTime, BacklogModel backlogModel, ArrayList<BacklogChunk> chunks, boolean isCumulative, boolean addNewFeatureGroups) {
		
		int burnedPoints = backlogModel.getOverallBurnedStoryPoints();
		ArrayList<FeatureGroup> fetureGroups = backlogModel
				.getFeatureGroupsFromAllSubProjects();

		ArrayList<String> headers = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		for (FeatureGroup featureGroup : fetureGroups) {
			String title = ConfluenceCleaningUtils.cleanConfluenceStringForCsv(featureGroup.getTitle());
			headers.add(title);
			if(isCumulative) {
				values.add((double) featureGroup.getCummulativePoints());
			} else {
				values.add((double) (featureGroup.getPoints() - featureGroup.getDonePoints()));
			}
		}
		
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		if(!isCumulative) {
			convert.convertIntoCumulative(merged);
			
		}
		doneLinesRemover.removeDoneLinesUsingCumulativeMerged(merged,burnedPoints,headers,values,isCumulative);

		// when
		if(addNewFeatureGroups) {
			updater.addAll(chunks, dateTime, burnedPoints, headers, values);
		} else {
			if(isCumulative) {
				updater.addOnlyExistingColumnsCumulative(chunks, dateTime, burnedPoints, headers, values);
			} else {
				updater.addOnlyExistingColumnsAbsolute(chunks, dateTime, burnedPoints, headers, values);
			}
		}
	}
	
	public ArrayList<BacklogChunk> update(DateTime dateTime, String backlogContent, String journalContent, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate) {

		boolean allowingMultilineFeatures = false;
		
		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromString(
				backlogContent, allowingMultilineFeatures);

		ArrayList<BacklogChunk> chunks = backlogJournalReader.readFromStringNullAllowed(journalContent);
		
		if(overwriteExistingDate) {
			removeLastRowIfDateMatches(dateTime, chunks);
		}

		update(dateTime, backlogModel, chunks, isCumulative, addNewFeatureGroups);
		
		return chunks;
	}

	private void removeLastRowIfDateMatches(DateTime dateTime, ArrayList<BacklogChunk> chunks) {
		if(chunks.isEmpty()) {
			return;
		}
		BacklogChunk lastChunk = chunks.get(chunks.size()-1);
		if(lastChunk.dates.isEmpty()) {
			return;
		}
		int pos = lastChunk.dates.size()-1;
		DateTime existingDate = lastChunk.dates.get(pos);
		if(!existingDate.equals(dateTime)) {
			return;
		}
		lastChunk.dates.remove(pos);
		for(ArrayList<Double> col : lastChunk.cols) {
			col.remove(pos);
		}
	}

	public String generateUpdatedString(DateTime dateTime, String backlogContent, String journalContent, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate) {
		ArrayList<BacklogChunk> chunks = update(dateTime, backlogContent, journalContent, isCumulative, addNewFeatureGroups, overwriteExistingDate);
		return backlogJournalSerializer.serialize(chunks);
	}
	
}
