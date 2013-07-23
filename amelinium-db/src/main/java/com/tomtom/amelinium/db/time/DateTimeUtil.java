package com.tomtom.amelinium.db.time;

import java.util.Calendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.tomtom.amelinium.db.config.Config;

/**
 * Used for conversion between Sqlite DateTime and Joda LocalDateTime types.
 * This conversion is done through strings.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class DateTimeUtil {

	/**
	 * Returns DateTime string in the form of: yyy-MM-dd HH:mm:ss. Such a string
	 * is understandable by Postgres database. Creates instance of the current
	 * Joda LocalDateTime. Converts it to string.
	 */
	public String getCurrentPgDtString() {
		DateTime dateTime = new DateTime(Config.ZONE);
		DateTimeFormatter fmt = DateTimeFormat
				.forPattern(Config.POSTGRES_TIMESTAMP_PATTERN);
		String pgDtString = fmt.print(dateTime);
		return pgDtString;
	}

	/**
	 * Converts a given string to Joda LocalDateTime. String should be retrieved
	 * from the Postgres database.
	 */
	public LocalDateTime getJodaLocalDateTime(String pgDtString) {
		TimeZone tz = Calendar.getInstance().getTimeZone();
		DateTimeZone dtZone = DateTimeZone.forID(tz.getID());
		String jodaDtString = pgDtString.replace(" ", "T");
		LocalDateTime localDateTime = new LocalDateTime(jodaDtString,
				dtZone);
		return localDateTime;
	}
}
