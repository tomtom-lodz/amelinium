package com.tomtom.amelinium.web.standalone.model;

import java.io.Serializable;

/**
 * Model of the form for editing chart content.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class TextAreaFormChart implements Serializable {

	private static final long serialVersionUID = 4072421683543950352L;
	
	/**
	 * Chart of the project. Should be provided as text in wiki markup syntax.
	 */
	private String text;

	public TextAreaFormChart() {
	}

	public TextAreaFormChart(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	
}
