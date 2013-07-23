package com.tomtom.amelinium.backlogservice.serializer;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Column;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.SubProject;
import com.tomtom.amelinium.backlogservice.model.Story;

/**
 * Transforms backlog model into string.
 * 
 * @author Natasza Nowak
 * 
 */

public class BacklogModelSerializer {
	private boolean dividedIntoSubProjects = false;

	/**
	 * @return serialized version of the model.
	 */
	public String serializeModel(BacklogModel backlogModel) {
		dividedIntoSubProjects=backlogModel.isDividedIntoSubProjects();

		ArrayList<SubProject> subProjects = backlogModel.getSubProjects();

		String result = "";

		result += backlogModel.getIntro();
		result += MarkupConfig.WARNING_LINE;
		result += serializeSubProjectsArray(subProjects);

		result = result.replaceAll(" +", " ");
		result = result.replaceAll("^\n+", "");

		return result;
	}

	/**
	 * Serializes story into string.
	 */
	private String serializeStory(Story story) {
		String points = (story.getPoints() == 0 && story.isEstimated() == false) ? "?" : String.valueOf(story.getPoints());
		String certainty = (story.isCertain()) ? "" : "?";

		String result = "";

		result += story.getContentLeft();
		result += points + certainty + "sp";
		result += story.getContentRight();
		result += story.getComment();
		result += "\n";

		return result;
	}

	/**
	 * Serializes feature and all its stories into string.
	 */
	private String serializeFeature(Feature feature) {
		String result = "";
		String scoredPoints = String.valueOf(feature.getDonePoints());
		String points = String.valueOf(feature.getPoints());

		if (!feature.getContentLeft().equals("UNTITLED FEATURE - ")) {
			result += "\n";
			result += feature.getContentLeft();
			result += scoredPoints + "/" + points + "sp";
			result += feature.getContentRight();
			result += feature.getComment();
			result += "\n";
		}
		result += serializeStoriesArray(feature.getStories());

		return result;
	}

	/**
	 * Serializes columns and all its features into string.
	 */
	private String serializeColumn(Column column) {
		String result = "";
		result += column.getOpenTag();
		result += "\n";
		result += serializeFeatureArray(column.getFeatures());
		result += column.getCloseTag();
		result += "\n";
		return result;
	}
	

	private String serializeSubProject(SubProject p) {
		String result = "";
		String scoredPoints = String.valueOf(p.getDonePoints());
		String points = String.valueOf(p.getPoints());

		if (dividedIntoSubProjects) {
			result += "\n";
			result += p.getContentLeft();
			result += scoredPoints + "/" + points + "sp";
			result += p.getContentRight();
			result += p.getComment();
			result += "\n";
		}
		result += serializeFeatureGroupArray(p.getFeatureGroups());

		return result;
	}

	/**
	 * Serializes FeatureGroup and all its columns (if exist) into string or all
	 * its features.
	 */
	private String serializeFeatureGroup(FeatureGroup featureGroup) {
		String result = "";
		if (!featureGroup.getContentLeft().equals(MarkupConfig.FEATURE_GROUP_MARKER + " UNTITLED FEATURE GROUP - ")) {
			String scoredPoints = String.valueOf(featureGroup.getDonePoints());
			String points = String.valueOf(featureGroup.getPoints());

			result += "\n\n";
			result += featureGroup.getContentLeft();
			result += scoredPoints + "/" + points + "sp";
			result += featureGroup.getContentRight();
			result += featureGroup.getComment();
			result += "\n";
		}
		if (featureGroup.isDividedIntoColumns()) {
			result += MarkupConfig.SECTION_TAG;
			result += "\n";
			result += serializeColumnsArray(featureGroup.getColumns());
			result += MarkupConfig.SECTION_TAG;
			result += "\n";
		} else {
			result += serializeFeatureArray(featureGroup.getLastColumn().getFeatures());
		}

		return result;
	}

	private String serializeStoriesArray(ArrayList<Story> stories) {
		String result = "";

		for (int i = 0; i < stories.size(); i++) {
			result += serializeStory(stories.get(i));
		}

		return result;
	}

	private String serializeFeatureArray(ArrayList<Feature> features) {
		String result = "";

		for (int i = 0; i < features.size(); i++) {
			result += serializeFeature(features.get(i));
		}

		return result;
	}

	private String serializeColumnsArray(ArrayList<Column> columns) {
		String result = "";

		for (Column column : columns) {
			result += serializeColumn(column);
		}

		return result;
	}

	private String serializeFeatureGroupArray(ArrayList<FeatureGroup> featureGroups) {
		String result = "";

		for (int i = 0; i < featureGroups.size(); i++) {
			result += serializeFeatureGroup(featureGroups.get(i));
		}

		return result;
	}
	
	private String serializeSubProjectsArray(ArrayList<SubProject> subProjects) {
		String result = "";
		for (SubProject p : subProjects){
			result += serializeSubProject(p);
		}
		return result;
	}

}
