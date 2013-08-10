package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class AbsoluteDoneLinesRemovingTest {

	@Test
	public void test() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger(); 
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/absolute_removing_tests/absolute_burned1.txt");
		BacklogJournalConverterIntoCumulative converter = new BacklogJournalConverterIntoCumulative();
		converter.convertIntoCumulative(chunks);
		BacklogChunk chunk = merger.mergeCumulativeChunks(chunks);

		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);

		// then
		
		ArrayList<BacklogChunk> chunksExpected = reader.readFromFileNullAllowed("src/test/resources/absolute_removing_tests/expected/absolute_burned1_removed.txt");
		
//		System.out.println(chunk);
//		System.out.println(chunksExpected.get(0).toString());

		assertEquals(chunksExpected.get(0).toString(), chunk.toString());
	}

	
	@Test
	public void test2() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger(); 
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/absolute_removing_tests/san_product_backlog.txt");
		BacklogJournalConverterIntoCumulative converter = new BacklogJournalConverterIntoCumulative();
		converter.convertIntoCumulative(chunks);
		BacklogChunk chunk = merger.mergeCumulativeChunks(chunks);

		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);

		// then
//		System.out.println(chunk);
		
		ArrayList<BacklogChunk> chunksExpected = reader.readFromFileNullAllowed("src/test/resources/absolute_removing_tests/expected/san_product_backlog_removed.txt");
//		System.out.println(chunksExpected.get(0).toString());
		assertEquals(chunksExpected.get(0).toString(), chunk.toString());
		
	}

	@Test
	public void test3() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger(); 
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/absolute_removing_tests/san_product_backlog_cut1.txt");
		BacklogJournalConverterIntoCumulative converter = new BacklogJournalConverterIntoCumulative();
		converter.convertIntoCumulative(chunks);
		BacklogChunk chunk = merger.mergeCumulativeChunks(chunks);

		// when
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		doneLinesRemover.removeDoneLinesFromCumulativeMerged(chunk);

		//Date,Burned,aaaa,bbbb,hhhh,eee,ffff,gggg
		//2013-07-19,88.0,0.0,0.0,25.0,5.0,21.0,45.0
		
		double burnedPoints = 88;
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("aaaa");
		headers.add("bbbb");
		headers.add("hhhh");
		headers.add("eee");
		headers.add("ffff");
		headers.add("gggg");
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(0.0);
		values.add(0.0);
		values.add(25.0);
		values.add(5.0);
		values.add(21.0);
		values.add(45.0);
		
		boolean isCumulative = false;
		
//		System.out.println(headers);
		
		doneLinesRemover.removeDoneLinesUsingCumulativeMerged(chunk,burnedPoints,headers,values,isCumulative);
		
		// then
//		System.out.println(chunk);

//		System.out.println(headers);

		ArrayList<BacklogChunk> chunksExpected = reader.readFromFileNullAllowed("src/test/resources/absolute_removing_tests/expected/san_product_backlog_cut1_removed.txt");
//		System.out.println(chunksExpected.get(0).toString());
		assertEquals(chunksExpected.get(0).toString(), chunk.toString());
		
	}
	
}
