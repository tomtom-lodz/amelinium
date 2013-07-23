package com.tomtom.amelinium.backlogservice.builders;

import com.tomtom.amelinium.backlogservice.state.State;
import com.tomtom.amelinium.backlogservice.state.Step;
import com.tomtom.amelinium.backlogservice.state.WorkItem;

public class CommentAdder {
	
	/**
	 * Adds all text from the line to the comment of the last built WorkItem.
	 */
	public static void addCommentToElement(State state, String inputLine){
		
		if (state.getLastAdded()==WorkItem.STORY){
			state.getLastStory().addToComment(inputLine);
		}
		else if (state.getLastAdded()==WorkItem.FEATURE){
			state.getLastFeature().addToComment(inputLine);
		}
		else if (state.getLastAdded()==WorkItem.FEATURE_GROUP){
			state.getLastFeatureGroup().addToComment(inputLine);
		}
		state.setStep(Step.DECIDING);
	}
}
