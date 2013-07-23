package com.tomtom.amelinium.backlogservice.corrector;

import java.util.ArrayList;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Column;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.SubProject;
import com.tomtom.amelinium.backlogservice.model.Story;

public class BacklogModelCorrector {

	public BacklogModel correctModelPoints(BacklogModel backlogModel) {
		ArrayList<SubProject> subProjects = backlogModel.getSubProjects();
		
		correctProjectsPoints(subProjects);

		calculateBurnedStoryPoints(subProjects, backlogModel);
		return backlogModel;
	}

	/**
	 * woj: the algorithm is as follows
	 * 
	 * 0. correct all points for whole backlog
	 * 1. for all DONE feature groups calculate points cumulatively and add them to base points
	 * 2. for all NOT DONE feature groups add done points to base points
	 * 3. for all NOT DONE feature groups calculate cumulatively remaining to-be-done points plus base points
	 * 
	 */
	private void correctProjectsPoints(ArrayList<SubProject> subProjects) {

		// 0. correct all points for whole backlog
		for (SubProject p : subProjects){
			for (FeatureGroup fg : p.getFeatureGroups()) {
				for (Column c : fg.getColumns()){
					for (Feature f : c.getFeatures()) {
						for (Story s : f.getStories()) {
							addPointsFromEachStoryToParentFeature(f, s);
						}
						addPointsFromEachFeatureToParentFeatureGroup(fg, f);
						checkIfFeatureIsDone(f);
					}
				}
				addPointsFromEachFeatureGroupToParentProject(fg, p);
				checkIfFeatureGroupIsDone(fg);
			}
			checkIfProjectIsDone(p);
		}

		int basePoints = 0;//ChartConfig.sidePoints_VALUE;
		
		// 1. for all DONE feature groups calculate points cumulatively and add them to base points
		for (SubProject p : subProjects){
			for (FeatureGroup fg : p.getFeatureGroups()) {
				if(fg.isDone()) {
					basePoints += fg.getDonePoints();
					fg.setCummulativePoints(basePoints);
				}
			}
		}

		// 2. for all NOT DONE feature groups add done points to base points
		for (SubProject p : subProjects){
			for (FeatureGroup fg : p.getFeatureGroups()) {
				if(!fg.isDone()) {
					basePoints += fg.getDonePoints();
				}
			}
		}
		
		// 3. for all NOT DONE feature groups calculate cumulatively remaining to-be-done points plus base points
		
		for (SubProject p : subProjects){
			for (FeatureGroup fg : p.getFeatureGroups()) {
				if(!fg.isDone()) {
					basePoints += fg.getPoints() - fg.getDonePoints();
					fg.setCummulativePoints(basePoints);
				}
			}
		}
		
//		int cummulativePoints = ChartConfig.sidePoints_VALUE;
//		for (Project p : projects){
//			for (FeatureGroup fg : p.getFeatureGroups()) {
//				for (Column c : fg.getColumns()){
//					for (Feature f : c.getFeatures()) {
//						for (Story s : f.getStories()) {
//							addPointsFromEachStoryToParentFeature(f, s);
//						}
//						addPointsFromEachFeatureToParentFeatureGroup(fg, f);
//						checkIfFeatureIsDone(f);
//					}
//				}
//				addPointsFromEachFeatureGroupToParentProject(fg, p);
//				checkIfFeatureGroupIsDone(fg);
//				cummulativePoints = calculateAndUpdateCummulativePoints(cummulativePoints, fg);
//			}
//			checkIfProjectIsDone(p);
//		}
		
	}

//	private int calculateAndUpdateCummulativePoints(int cummulativePoints, FeatureGroup fg) {
//		cummulativePoints += fg.getPoints();
//		fg.setCummulativePoints(cummulativePoints);
//		return cummulativePoints;
//	}

	private void addPointsFromEachStoryToParentFeature(Feature f, Story s) {
		f.addToPoints(s.getPoints());
		f.addToScoredPoints((s.isDone() == true) ? s.getPoints() : 0);
	}

	private void addPointsFromEachFeatureToParentFeatureGroup(FeatureGroup fg, Feature f) {
		fg.addToPoints(f.getPoints());
		fg.addToScoredPoints(f.getDonePoints());
	}

	private void addPointsFromEachFeatureGroupToParentProject(FeatureGroup fg, SubProject p) {
		p.addToPoints(fg.getPoints());
		p.addToScoredPoints(fg.getDonePoints());
		
	}

	private void checkIfFeatureIsDone(Feature f) {
		if (f.getPoints() == f.getDonePoints()) {
			f.setDone(true);
		}
	}

	private void checkIfFeatureGroupIsDone(FeatureGroup fg) {
		if (fg.getPoints() == fg.getDonePoints()) {
			fg.setDone(true);
		}
	}
	
	private void checkIfProjectIsDone(SubProject p) {
		if (p.getPoints() == p.getDonePoints()) {
			p.setDone(true);
		}
	}

	private void calculateBurnedStoryPoints(ArrayList<SubProject> subProjects, BacklogModel backlogModel) {
		for (SubProject p : subProjects) {
			backlogModel.addToBurnedStoryPoints(p.getDonePoints());
		}
	}

}
