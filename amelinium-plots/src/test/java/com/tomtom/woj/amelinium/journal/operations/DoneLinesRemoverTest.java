package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class DoneLinesRemoverTest {

	@Test
	public void testCumulative1() throws IOException {
		
		// ma nie usuwac jezeli wpis jest done ale
		// w poprzedniej linijce nie byl done
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");
		
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Feature Group 1");
		lines.add("Feature Group 2");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(34.);
		values.add(55.);

		double burned = 40;
		
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		// when
		doneLinesRemover.removeDoneLinesUsingCumulativeMerged(merged,burned,lines,values,true);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("Feature Group 1");
		expectedLine.add("Feature Group 2");
		
		assertEquals(expectedLine,lines);

		ArrayList<Double> expectedValues = new ArrayList<Double>();
		expectedValues.add(34.);
		expectedValues.add(55.);

		assertEquals(expectedValues,values);
	}


	@Test
	public void testCumulative2() throws IOException {
		
		// ma usunac jezeli wpis jest done i
		// w poprzedniej linijce wpis jest done
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative_done.txt");
		
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Feature Group 1");
		lines.add("Feature Group 2");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(34.);
		values.add(55.);

		double burned = 40;
		
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		// when
		doneLinesRemover.removeDoneLinesUsingCumulativeMerged(merged,burned,lines,values,true);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("Feature Group 2");
		
		assertEquals(expectedLine,lines);

		ArrayList<Double> expectedValues = new ArrayList<Double>();
		expectedValues.add(55.);

		assertEquals(expectedValues,values);
	}
	
	@Test
	public void testCumulative3() throws IOException {
		
		// ma usunac jezeli wpis jest done i
		// w poprzedniej linijce go nie ma
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative_done.txt");
		
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Feature Group X");
		lines.add("Feature Group 2");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(34.);
		values.add(55.);

		double burned = 40;
		
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		// when
		doneLinesRemover.removeDoneLinesUsingCumulativeMerged(merged,burned,lines,values,true);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("Feature Group 2");
		
		assertEquals(expectedLine,lines);

		ArrayList<Double> expectedValues = new ArrayList<Double>();
		expectedValues.add(55.);

		assertEquals(expectedValues,values);
	}
	
	@Test
	public void testCumulative4() throws IOException {
		
		// ma usunac jezeli wpis jest done i
		// w poprzedniej linijce go nie ma
		
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example3_cumulative.txt");
		
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Feature Group 1");
		lines.add("Feature Group 2");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(34.);
		values.add(55.);

		double burned = 40;
		
		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
		BacklogJournalMultipleChunksMerger merger = new BacklogJournalMultipleChunksMerger();
		BacklogChunk merged = merger.mergeCumulativeChunks(chunks);
		
		// when
		doneLinesRemover.removeDoneLinesUsingCumulativeMerged(merged,burned,lines,values,true);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("Feature Group 1");
		expectedLine.add("Feature Group 2");
		
		assertEquals(expectedLine,lines);

		ArrayList<Double> expectedValues = new ArrayList<Double>();
		expectedValues.add(34.);
		expectedValues.add(55.);

		assertEquals(expectedValues,values);
	}
	
	
//	@Test
//	public void testAbsolute2() throws IOException {
//		
//		// given
//		BacklogJournalReader reader = new BacklogJournalReader();
//		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");
//		
//		ArrayList<String> lines = new ArrayList<String>();
//		lines.add("A");
//		lines.add("B");
//		lines.add("C");
//		
//		ArrayList<Double> values = new ArrayList<Double>();
//		values.add(0.);
//		values.add(0.);
//		values.add(0.);
//
//		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
//		
//		// when
//		doneLinesRemover.removeAbsoluteDoneLines(chunks,lines,values);
//		
//		// then
//		ArrayList<String> expectedLine = new ArrayList<String>();
//		assertEquals(expectedLine,lines);
//		
//		ArrayList<Double> expectedValues = new ArrayList<Double>();
//		assertEquals(expectedValues,values);
//		
//	}
//
//	@Test
//	public void testCumulative1() throws IOException {
//
//		// ma usunac jezeli wpis jest ponizej burned i juz jest wpis
//		// ponizej burned
//		
//		// given
//		BacklogJournalReader reader = new BacklogJournalReader();
//		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example1_cumulative.txt");
//		
//		ArrayList<String> lines = new ArrayList<String>();
//		lines.add("Burned");
//		lines.add("A");
//		lines.add("B");
//		lines.add("C");
//		
//		ArrayList<Double> values = new ArrayList<Double>();
//		values.add(5.);
//		values.add(1.);
//		values.add(4.);
//		values.add(7.);
//
//		DoneLinesRemover doneLinesRemover = new DoneLinesRemover();
//		
//		// when
//		doneLinesRemover.removeCumulativeDoneLines(chunks,lines,values);
//		
//		// then
//		ArrayList<String> expectedLine = new ArrayList<String>();
//		expectedLine.add("C");
//		
//		assertEquals(expectedLine,lines);
//		
//		ArrayList<Double> expectedValues = new ArrayList<Double>();
//		expectedValues.add(7.);
//
//		assertEquals(expectedValues,values);
//	}

}
