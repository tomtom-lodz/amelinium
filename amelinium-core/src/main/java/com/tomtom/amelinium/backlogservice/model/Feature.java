/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.amelinium.backlogservice.model;

import java.util.ArrayList;

/**
 * Represents feature on the backlog. Feature can have many Stories.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Feature {

	/**
	 * If feature is completed. Feature is considered completed if all the
	 * stories belonging to it are done.
	 */
	private boolean done = false;

	/**
	 * Number of story points that are already done.
	 */
	private int donePoints;

	/**
	 * Total number of story points to be done.
	 */
	private int points;

	/**
	 * Used for parsing, everything on the left from the estimate (for example
	 * on the left from 3sp/5sp).
	 */
	private String contentLeft = "";

	/**
	 * Content of the estimate string (for example 3sp/5sp).
	 */
	private String contentMiddle = "";

	/**
	 * Used for parsing, everything on the right from the estimate (for example
	 * on the right from 3sp/5sp).
	 */
	private String contentRight = "";

	/**
	 * Comment that can be added to a feature, line that doesn't end with sp.
	 */
	private String comment = "";

	/**
	 * Array of stories that belong to the feature.
	 */
	private ArrayList<Story> stories = new ArrayList<Story>();

	public Feature() {
	}

	public Feature(int points) {
		this.points = points;
	}

	public Feature(String contentLeft) {
		super();
		this.contentLeft = contentLeft;
	}

	public Feature(String contentLeft, String contentMiddle, String contentRight) {
		super();
		this.contentLeft = contentLeft;
		this.contentMiddle = contentMiddle;
		this.contentRight = contentRight;
	}

	/**
	 * Used for handling features spanning two lines. Concatenates current line
	 * with <code>contentLeft</code> of the feature currently being built.
	 */
	public void addToContentLeft(String str) {
		if ("".equals(this.contentLeft)) {
			this.contentLeft += str;
		} else {
			this.contentLeft += " " + str;
		}
	}

	/**
	 * Used for handling comments to Features. Comments are understood as lines
	 * that don't start with any markup, but are not Features. If the preceding
	 * line was empty, the current line is treated as a Feature. If not, the
	 * line is added as a comment to the last built Feature.
	 */
	public void addToComment(String str) {
		this.comment += "\n" + str;
	}

	public Story getStory(int i) {
		return this.stories.get(i);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean isDone) {
		this.done = isDone;
	}

	public int getDonePoints() {
		return donePoints;
	}

	public void setDonePoints(int scoredPoints) {
		this.donePoints = scoredPoints;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addToPoints(int points) {
		this.points += points;
	}

	public void addToScoredPoints(int scoredPoints) {
		this.donePoints += scoredPoints;
	}

	public ArrayList<Story> getStories() {
		return stories;
	}

	public void setStories(ArrayList<Story> stories) {
		this.stories = stories;
	}

	public String getContentLeft() {
		return contentLeft;
	}

	public void setContentLeft(String contentLeft) {
		this.contentLeft = contentLeft;
	}

	public String getContentMiddle() {
		return contentMiddle;
	}

	public void setContentMiddle(String contentMiddle) {
		this.contentMiddle = contentMiddle;
	}

	public String getContentRight() {
		return contentRight;
	}

	public void setContentRight(String contentRight) {
		this.contentRight = contentRight;
	}

	public String getComment() {
		return comment;
	}

}
