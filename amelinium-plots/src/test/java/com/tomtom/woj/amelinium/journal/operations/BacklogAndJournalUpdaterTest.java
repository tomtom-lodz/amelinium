package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.utils.StringUtils;

public class BacklogAndJournalUpdaterTest {

	private BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
	private BacklogAndJournalUpdater updater = new BacklogAndJournalUpdater();

	@Test
	public void test1() throws IOException {

		// given
		String backlogContent = StringUtils
				.readFile("src/test/resources/backlog_and_journal/backlog1.txt");

		// when
		ArrayList<BacklogChunk> chunks = readAndUpdateJournal(backlogContent);
		
		// then
		assertEquals(1, chunks.size());
		assertEquals(3, chunks.get(0).header.size());
		assertEquals("Burned", chunks.get(0).header.get(0));
		assertEquals("Feature Group 1", chunks.get(0).header.get(1));
		assertEquals("Feature Group 2", chunks.get(0).header.get(2));
		assertEquals(4, chunks.get(0).dates.size());
		
//		System.out.println(chunks);
	}

	@Test
	public void test2() throws IOException {

		// given
		String backlogContent = StringUtils
				.readFile("src/test/resources/backlog_and_journal/backlog2.txt");

		// when
		ArrayList<BacklogChunk> chunks = readAndUpdateJournal(backlogContent);
		
		// then
		assertEquals(2, chunks.size());
		assertEquals(4, chunks.get(1).header.size());
		assertEquals("Burned", chunks.get(1).header.get(0));
		assertEquals("Feature Group 1", chunks.get(1).header.get(1));
		assertEquals("Feature Group 2", chunks.get(1).header.get(2));
		assertEquals("Feature Group 3", chunks.get(1).header.get(3));
		assertEquals(1, chunks.get(1).dates.size());
		
//		System.out.println(chunks);
	}

	private ArrayList<BacklogChunk> readAndUpdateJournal(String backlogContent)
			throws IOException {
		boolean allowingMultilineFeatures = false;

		DateTime dateTime = new DateTime(2013, 1, 4, 0, 0);

		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromString(backlogContent,
						allowingMultilineFeatures);

		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader
				.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");

		updater.update(dateTime, backlogModel, chunks);
		return chunks;
	}

}
