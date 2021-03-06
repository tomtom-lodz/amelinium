package com.tomtom.woj.amelinium.plots.burnup;

import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.DateTime;

public class BurnupTableGenerator {

	public String generateTable(BurnupModel model) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=\"1\">"
				+ "<tr><th>Feature Group</th><th>End of Development</th></tr>");
		for(int i=model.releasesEndDates.size()-1; i>=0; i--) {
			String name = model.merged.header.get(i+1);
			DateTime dateTime = model.releasesEndDates.get(i);
			sb.append("<tr><td>");
			sb.append(StringEscapeUtils.escapeHtml(name));
			sb.append("</td><td>");
			sb.append(dateTime.toString("dd-MMM-yyyy"));
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
}
