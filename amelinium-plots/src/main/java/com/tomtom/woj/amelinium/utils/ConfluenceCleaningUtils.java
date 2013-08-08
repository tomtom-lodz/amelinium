package com.tomtom.woj.amelinium.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfluenceCleaningUtils {
	
	
	static Pattern confluencePattern = Pattern.compile("\\{[^}]*\\}");

	// remove all {aasasa} tags
	// remove all *
	public static String cleanConfluenceStringForCsv(String str) {
		Matcher m = confluencePattern.matcher(str);
		return m.replaceAll("").replace("*", "");
	}

}
