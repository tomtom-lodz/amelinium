package com.tomtom.amelinium.chartservice.extractor;

/**
 * Helper class extracting the actual name of the feature group from the line
 * containing it.
 * 
 * @author Natasza.Nowak@tomtom.com
 */

public class Extractor {

	public String extractTitleFromTable(String title) {
		title = title.replace("|| || ", "");
		title = title.replace(" ||", "");
		return title;
	}

	public String extractTitleFromFeatureGroup(String title) {
		title = title.replace("h3. ", "");
		title = title.replace(" - ", "");
		return title;
	}

}
