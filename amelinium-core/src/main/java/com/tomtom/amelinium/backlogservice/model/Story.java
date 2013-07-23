package com.tomtom.amelinium.backlogservice.model;

/**
 * 
 * Represents story on the backlog.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Story {

	/**
	 * If story is completed. Story is considered completed if it is crossed out
	 * on the backlog.
	 */
	private boolean done = false;

	/**
	 * If story is estimated. Story is considered to be estimated when there is
	 * concrete number of story points, different than question mark ?.
	 */
	private boolean estimated = true;

	/**
	 * Corresponds to the case when story is estimated with some uncertainty.
	 * For example: 2?sp.
	 */
	private boolean certain = true;
	/**
	 * Number of story points for this user story.
	 */
	private int points;

	/**
	 * Used for parsing, everything on the left from the estimate (for example
	 * on the left from 5sp).
	 */
	private String contentLeft = "";

	/**
	 * Content of the estimate string (for example 5sp).
	 */
	private String contentMiddle = "";

	/**
	 * Used for parsing, everything on the right from the estimate (for example
	 * on the right from 5sp).
	 */
	private String contentRight = "";

	/**
	 * Comment that can be added to a story, line that doesn't end with sp.
	 */
	private String comment = "";

	public Story() {
	}

	public Story(int points) {
		this.points = points;
	}

	public Story(boolean isEstimated, int points) {
		super();
		this.estimated = isEstimated;
		this.points = points;
	}

	public Story(String contentLeft) {
		super();
		this.contentLeft = contentLeft;
	}

	public Story(boolean isDone, boolean isEstimated, int points, String contentLeft, String contentMiddle, String contentRight) {
		super();
		this.done = isDone;
		this.estimated = isEstimated;
		this.points = points;
		this.contentLeft = contentLeft;
		this.contentMiddle = contentMiddle;
		this.contentRight = contentRight;
	}

	public Story(boolean isEstimated, int points, String contentLeft, String contentMiddle, String contentRight) {
		super();
		this.estimated = isEstimated;
		this.points = points;
		this.contentLeft = contentLeft;
		this.contentMiddle = contentMiddle;
		this.contentRight = contentRight;
	}

	/**
	 * Used for handling stories spanning two lines. Concatenates current line
	 * with <code>contentLeft</code> of the story currently being built.
	 */
	public void addToContentLeft(String str) {
		if ("".equals(this.contentLeft)) {
			this.contentLeft += str;
		} else {
			this.contentLeft += " " + str;
		}
	}

	/**
	 * Used for handling comments to Stories. Lines that don't start with any
	 * markup, but are not Features. If the preceding line was empty, the
	 * current line is treated as a Feature. If not, the line is added as a
	 * comment to the last built Story.
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

	public boolean isEstimated() {
		return estimated;
	}

	public void setEstimated(boolean isEstimated) {
		this.estimated = isEstimated;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getContentLeft() {
		return contentLeft;
	}

	public String getContentMiddle() {
		return contentMiddle;
	}

	public String getContentRight() {
		return contentRight;
	}

	public void setContentLeft(String contentLeft) {
		this.contentLeft = contentLeft;
	}

	public void setContentMiddle(String contentMiddle) {
		this.contentMiddle = contentMiddle;
	}

	public void setContentRight(String contentRight) {
		this.contentRight = contentRight;
	}

	public boolean isCertain() {
		return certain;
	}

	public void setCertain(boolean certain) {
		this.certain = certain;
	}

	public String getComment() {
		return comment;
	}

}
