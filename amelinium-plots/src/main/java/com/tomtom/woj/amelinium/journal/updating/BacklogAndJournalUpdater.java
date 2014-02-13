package com.tomtom.woj.amelinium.journal.updating;

import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.woj.amelinium.journal.converter.AbsoluteToCumulativeConverterInPlace;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalSerializer;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogChunksMerger;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalNewLineAdder;
import com.tomtom.woj.amelinium.journal.operations.DoneLinesRemover;
import com.tomtom.woj.amelinium.utils.ConfluenceCleaningUtils;

public class BacklogAndJournalUpdater {

	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private BacklogJournalReader backlogJournalReader = new BacklogJournalReader();
	private BacklogJournalSerializer backlogJournalSerializer = new BacklogJournalSerializer();
	private BacklogJournalNewLineAdder updater = new BacklogJournalNewLineAdder();
	private DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
	private BacklogChunksMerger merger = new BacklogChunksMerger();
	private AbsoluteToCumulativeConverterInPlace convert = new AbsoluteToCumulativeConverterInPlace();

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
		doneLinesRemover.removeDoneFromNewLinesUsingCumulativeMerged(merged,burnedPoints,headers,values,isCumulative);

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
	
	public ArrayList<BacklogChunk> update(DateTime dateTime, String backlogContent, String journalContent, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate, boolean allowingMultilineFeatures) {

		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromString(
				backlogContent, allowingMultilineFeatures);

		ArrayList<BacklogChunk> chunks = backlogJournalReader.readFromStringNullAllowed(journalContent);
		
		if(overwriteExistingDate) {
			removeLastRowIfDateMatches(dateTime, chunks);
		}

		update(dateTime, backlogModel, chunks, isCumulative, addNewFeatureGroups);
		
		return chunks;
	}
	//TODO: test this
	public ArrayList<BacklogChunk> update(DateTime dateTime, BacklogModel backlogModel, String journalContent, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate, boolean allowingMultilineFeatures) {

		ArrayList<BacklogChunk> chunks = backlogJournalReader.readFromStringNullAllowed(journalContent);
		
		if(overwriteExistingDate) {
			removeLastRowIfDateMatches(dateTime, chunks);
		}

		update(dateTime, backlogModel, chunks, isCumulative, addNewFeatureGroups);
		
		return chunks;
	}

	// TODO: test this
	private void removeLastRowIfDateMatches(DateTime dateTime, ArrayList<BacklogChunk> chunks) {
		while(!chunks.isEmpty()) {
			BacklogChunk lastChunk = chunks.get(chunks.size()-1);
			if(!lastChunk.dates.isEmpty()) {
				break;
			}
			chunks.remove(chunks.size()-1);
		}
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
		while(!chunks.isEmpty()) {
			lastChunk = chunks.get(chunks.size()-1);
			if(!lastChunk.dates.isEmpty()) {
				break;
			}
			chunks.remove(chunks.size()-1);
		}
	}

	public String generateUpdatedString(DateTime dateTime, String backlogContent, String journalContent, boolean isCumulative, boolean addNewFeatureGroups, boolean overwriteExistingDate,boolean allowingMultilineFeatures) {
		ArrayList<BacklogChunk> chunks = update(dateTime, backlogContent, journalContent, isCumulative, addNewFeatureGroups, overwriteExistingDate,allowingMultilineFeatures);
		return backlogJournalSerializer.serialize(chunks);
	}
	
}
