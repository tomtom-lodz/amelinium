package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.journal.operations.BacklogJournalNewLineAdder;

public class BacklogJournalNewLineAdderTest {

	@Test
	public void test1() throws IOException {
		
		// given
		BacklogJournalNewLineAdder updater = new BacklogJournalNewLineAdder();
		BacklogJournalReader reader = new BacklogJournalReader();

		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");

		DateTime dateTime = new DateTime(2013,1,4,0,0);
		
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Feature Group 1");
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(15.);
		
		// when
		updater.addAll(chunks, dateTime, 10., headers, values);
		
		// then
		assertEquals(2, chunks.size());
		assertEquals(2, chunks.get(1).header.size());
		assertEquals("Burned", chunks.get(1).header.get(0));
		assertEquals("Feature Group 1", chunks.get(1).header.get(1));
		assertEquals(1, chunks.get(1).dates.size());
		
//		System.out.println(chunks);
	}

	@Test
	public void test2() throws IOException {
		
		// given
		BacklogJournalNewLineAdder updater = new BacklogJournalNewLineAdder();
		BacklogJournalReader reader = new BacklogJournalReader();

		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");

		DateTime dateTime = new DateTime(2013,1,4,0,0);
		
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Feature Group 1");
		headers.add("Feature Group 2");
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(15.);
		values.add(25.);
		
		// when
		updater.addAll(chunks, dateTime, 10., headers, values);
		
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
	public void test3() throws IOException {
		
		// given
		BacklogJournalNewLineAdder updater = new BacklogJournalNewLineAdder();
		BacklogJournalReader reader = new BacklogJournalReader();

		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");

		DateTime dateTime = new DateTime(2013,1,4,0,0);
		
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Feature Group 1");
		headers.add("Feature Group 3");
		headers.add("Feature Group 2");
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(10.);
		values.add(15.);
		values.add(20.);
		
		// when
		updater.addOnlyExistingColumnsCumulative(chunks, dateTime, 30., headers, values);
		
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
	public void test4() throws IOException {
		
		// given
		BacklogJournalNewLineAdder updater = new BacklogJournalNewLineAdder();
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");
		
		DateTime dateTime = new DateTime(2013,1,4,0,0);
		
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Feature Group 1");
		headers.add("Feature Group 3");
		headers.add("Feature Group 2");
		headers.add("Feature Group 4");
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(10.);
		values.add(15.);
		values.add(20.);
		values.add(40.);
		
		// when
		updater.addOnlyExistingColumnsAbsolute(chunks, dateTime, 30., headers, values);
		
		// then
		assertEquals(2, chunks.size());
		assertEquals(4, chunks.get(1).header.size());
		assertEquals("Burned", chunks.get(1).header.get(0));
		assertEquals("Feature Group 1", chunks.get(1).header.get(1));
		assertEquals("Feature Group 3", chunks.get(1).header.get(2));
		assertEquals("Feature Group 2", chunks.get(1).header.get(3));
		assertEquals(1, chunks.get(1).dates.size());
		
		//System.out.println(chunks);
	}
	
	

}
