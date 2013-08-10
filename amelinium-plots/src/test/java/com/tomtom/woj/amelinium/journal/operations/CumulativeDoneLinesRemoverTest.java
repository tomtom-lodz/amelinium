package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class CumulativeDoneLinesRemoverTest {

	@Test
	public void testCumulative1() throws IOException {
		
		// ma nie usuwac jezeli wpis jest done ale
		// w poprzedniej linijce nie byl done
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/merged_burned1.txt");
		BacklogChunk chunk = chunks.get(0);
		
		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);
		
		// then
		BacklogChunksMerger merger = new BacklogChunksMerger(); 
		BacklogChunk expectedChunk = merger.mergeCumulativeChunks(reader.readFromFileNullAllowed("src/test/resources/backlog_journals/expected_after_removing/merged_burned1.txt"));
		assertEquals(expectedChunk, chunk);
	}

	@Test
	public void testCumulative2() throws IOException {
		
		// ma nie usuwac jezeli wpis jest done ale
		// w poprzedniej linijce nie byl done
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/merged_burned2.txt");
		BacklogChunk chunk = chunks.get(0);
		
		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);
		
		// then
		BacklogChunksMerger merger = new BacklogChunksMerger(); 
		BacklogChunk expectedChunk = merger.mergeCumulativeChunks(reader.readFromFileNullAllowed("src/test/resources/backlog_journals/expected_after_removing/merged_burned2.txt"));
		assertEquals(expectedChunk, chunk);
	}

	@Test
	public void testCumulative3() throws IOException {
		
		// ma nie usuwac jezeli wpis jest done ale
		// w poprzedniej linijce nie byl done
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		BacklogChunksMerger merger = new BacklogChunksMerger(); 
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/merged_burned3.txt");
		BacklogChunk chunk = merger.mergeCumulativeChunks(chunks);
		
		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);
		
		// then
//		System.out.println(chunk);
		BacklogChunk expectedChunk = merger.mergeCumulativeChunks(reader.readFromFileNullAllowed("src/test/resources/backlog_journals/expected_after_removing/merged_burned3.txt"));
		assertEquals(expectedChunk, chunk);
		
	}

	@Test
	public void testCumulative4() throws IOException {
		
		// ma nie usuwac jezeli wpis jest done ale
		// w poprzedniej linijce nie byl done
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		BacklogChunksMerger merger = new BacklogChunksMerger(); 
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/merged_burned4.txt");
		BacklogChunk chunk = merger.mergeCumulativeChunks(chunks);
		
		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);
		
		// then
//		System.out.println(chunk);
		BacklogChunk expectedChunk = merger.mergeCumulativeChunks(reader.readFromFileNullAllowed("src/test/resources/backlog_journals/expected_after_removing/merged_burned4.txt"));
		assertEquals(expectedChunk, chunk);
	}

}
