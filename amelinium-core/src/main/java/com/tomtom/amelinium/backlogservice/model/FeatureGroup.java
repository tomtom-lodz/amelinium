/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.amelinium.backlogservice.model;

import java.util.ArrayList;

/**
 * Represents feature group on the backlog. FeatureGroup can have many Features.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class FeatureGroup {

	/**
	 * If feature group is completed. FeatureGroup is considered completed if
	 * all the features belonging to it are done.
	 */
	private boolean done = false;

	/**
	 * Total number of story points to be done.
	 */
	private int points;

	/**
	 * Number of story points that are already done.
	 */
	private int donePoints;

	/**
	 * Number of donePoints plus donePoints from all the feature groups
	 * preceding this one.
	 */
	private int cummulativePoints = 0;

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
	 * Array of columns that belong to the feature group. Columns have features.
	 */
	private ArrayList<Column> columns = new ArrayList<Column>();
	
	private boolean dividedIntoColumns = false;

	public FeatureGroup() {
		this.columns.add(new Column());
	}

	public FeatureGroup(int points) {
		this.points = points;
		this.columns.add(new Column());
	}

	public FeatureGroup(String contentLeft) {
		super();
		this.contentLeft = contentLeft;
		this.columns.add(new Column());
	}

	public FeatureGroup(String contentLeft, String contentMiddle, String contentRight) {
		super();
		this.contentLeft = contentLeft;
		this.contentMiddle = contentMiddle;
		this.contentRight = contentRight;
		this.columns.add(new Column());
	}

	/**
	 * Used for handling feature groups spanning two lines. Concatenates current
	 * line with <code>contentLeft</code> of the feature group currently being
	 * built.
	 */

	public void addToContentLeft(String str) {
		if ("".equals(this.contentLeft)) {
			this.contentLeft += str;
		} else {
			this.contentLeft += " " + str;
		}
	}

	/**
	 * Used for handling comments to Feature Groups. Comments are understood as
	 * lines that don't start with any markup, but are not Features. If the
	 * preceding line was empty, the current line is treated as a Feature. If
	 * not, the line is added as a comment to the last built Feature Group.
	 */
	public void addToComment(String str) {
		this.comment += "\n" + str;
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

	public int getCummulativePoints() {
		return cummulativePoints;
	}

	public void setCummulativePoints(int cummulativePoints) {
		this.cummulativePoints = cummulativePoints;
	}

	public void addToCummulativePoints(int cummulativePoints) {
		this.cummulativePoints += cummulativePoints;
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

	public ArrayList<Column> getColumns() {
		return columns;
	}
	public Column getLastColumn() {
		return columns.get(columns.size()-1);
	}

	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}

	public boolean isDividedIntoColumns() {
		return dividedIntoColumns;
	}

	public void setDividedIntoColumns(boolean dividedIntoColumns) {
		this.dividedIntoColumns = dividedIntoColumns;
	}
}
