package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalMergerTest {

	@Test
	public void test() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example2_cumulative.txt");
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger(); 
		
		// when
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		// then
		
		// check headers
		assertEquals("Burned", merged.header.get(0));
		assertEquals("Feature Group 2", merged.header.get(1));
		assertEquals("Feature Group 3", merged.header.get(2));
		assertEquals("Feature Group 1", merged.header.get(3));
		
		// check dates merged
		assertEquals(new DateTime(2012,1,1,0,0), merged.dates.get(0));
		assertEquals(new DateTime(2012,1,6,0,0), merged.dates.get(5));

		// check burned column merged
		assertEquals(0., merged.cols.get(0).get(0),1e-6);
		assertEquals(45., merged.cols.get(0).get(5),1e-6);
		
		// check nonempty column merged
		assertEquals(50., merged.cols.get(1).get(0),1e-6);
		assertEquals(60., merged.cols.get(1).get(5),1e-6);
		
		// check some empty column merged
		assertEquals(Double.NaN, merged.cols.get(2).get(0),1e-6);
		assertEquals(73., merged.cols.get(2).get(5),1e-6);
	}

}
