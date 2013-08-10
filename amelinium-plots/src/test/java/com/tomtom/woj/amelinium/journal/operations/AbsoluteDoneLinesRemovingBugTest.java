package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.updating.BacklogAndJournalUpdater;
import com.tomtom.woj.amelinium.utils.StringUtils;

public class AbsoluteDoneLinesRemovingBugTest {

	@Test
	public void test() throws IOException {
		BacklogAndJournalUpdater updater = new BacklogAndJournalUpdater();
		
		DateTime date = new DateTime(2013,8,8,0,0);
		String backlogContent = StringUtils
				.readFile("src/test/resources/bugs/1/backlog.txt");
		String csvContent = StringUtils
				.readFile("src/test/resources/bugs/1/csv.txt");
		
		String result = updater.generateUpdatedString(date,backlogContent,csvContent,false,true,true, true);
		
//		System.out.println(result);
		String expected = StringUtils.readFile("src/test/resources/bugs/1/expected.txt");
		assertEquals(expected, result);
	}

}
