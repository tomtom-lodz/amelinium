package com.tomtom.woj.amelinium.journal.io;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalReaderTest {

	@Test
	public void testOneChunk() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		// when
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");
		// then
		assertEquals(1,chunks.size());
		assertEquals("Burned", chunks.get(0).header.get(0));
		assertEquals("Feature Group 1", chunks.get(0).header.get(1));
		assertEquals(41., chunks.get(0).cols.get(1).get(1),1e-6);
		assertEquals(3, chunks.get(0).cols.get(0).size());
		assertEquals(new DateTime(2012,1,3,0,0), chunks.get(0).dates.get(2));
	}

	@Test
	public void testTwoChunks() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		// when
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example2_cumulative.txt");
		// then
		
		assertEquals(2,chunks.size());
		
		// chunk1
		assertEquals("Burned", chunks.get(0).header.get(0));
		assertEquals("Feature Group 1", chunks.get(0).header.get(1));
		assertEquals(41., chunks.get(0).cols.get(1).get(1),1e-6);
		assertEquals(3, chunks.get(0).cols.get(0).size());
		assertEquals(new DateTime(2012,1,3,0,0), chunks.get(0).dates.get(2));
		
		// chunk2
		assertEquals("Burned", chunks.get(1).header.get(0));
		assertEquals("Feature Group 2", chunks.get(1).header.get(1));
		assertEquals(59., chunks.get(1).cols.get(1).get(1),1e-6);
		assertEquals(3, chunks.get(1).cols.get(0).size());
		assertEquals(new DateTime(2012,1,6,0,0), chunks.get(1).dates.get(2));
	}

}
