package com.tomtom.amelinium.db.config;

import org.joda.time.DateTimeZone;

public class Config {
	public static final String RESOURCE = "com/tomtom/amelinium/db/configuration.xml";
	/**
	 * TimeZone for amazon database and heroku server.
	 */
	public static final DateTimeZone ZONE = DateTimeZone.forID("Europe/Warsaw");
	public static final String POSTGRES_TIMESTAMP_PATTERN = "yyy-MM-dd HH:mm:ssZZ";
	public static final boolean allowingMultilineFeatures = false;

}
