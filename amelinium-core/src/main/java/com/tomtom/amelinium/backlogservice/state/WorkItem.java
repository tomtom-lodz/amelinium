package com.tomtom.amelinium.backlogservice.state;

/**
 * Possible work items. If a comment is found during deserialization it is added
 * to the last WorkItem.
 * 
 * @author Natasza Nowak
 */

public enum WorkItem {
	STORY,
	FEATURE,
	FEATURE_GROUP,
	PROJECT
}