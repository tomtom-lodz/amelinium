package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class MergedBacklogColumnSorterTest {

	@Test
	public void test() throws IOException {
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFileNullAllowed("src/test/resources/csvs/csv1.txt");
		BacklogChunksMerger merger = new BacklogChunksMerger(); 
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		System.out.println(merged);
		
		MergedBacklogColumnSorter columnSorter = new MergedBacklogColumnSorter();
		columnSorter.sortColumns(merged);
		
		System.out.println(merged);
	}

}
