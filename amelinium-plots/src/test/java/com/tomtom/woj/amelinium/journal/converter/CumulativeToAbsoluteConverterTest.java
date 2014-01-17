package com.tomtom.woj.amelinium.journal.converter;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.tomtom.woj.amelinium.journal.converter.CumulativeToAbsoluteConverter;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalSerializer;
import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class CumulativeToAbsoluteConverterTest {

	@Test
	public void test1() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/backlog_journals/example2_cumulative.txt");
		
		// when
		CumulativeToAbsoluteConverter converter = new CumulativeToAbsoluteConverter();
		ArrayList<BacklogChunk> newChunks = converter.convert(chunks);

//		System.out.println(expectedChunks);
//		System.out.println(newChunks);
		
		// then
		ArrayList<BacklogChunk> expectedChunks = reader.readFromFile("src/test/resources/backlog_journals/expected_after_converting_to_absolute/example2_cumulative_to_absolute.txt");
		assertEquals(expectedChunks.toString(), newChunks.toString());
	}

	@Ignore
	@Test
	public void testBugWhenNulls() throws IOException {
		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		ArrayList<BacklogChunk> chunks = reader.readFromFileNullAllowed("src/test/resources/conversion_tests/bzura_product_backlog.txt");
		
		// when
		CumulativeToAbsoluteConverter converter = new CumulativeToAbsoluteConverter();
		ArrayList<BacklogChunk> newChunks = converter.convert(chunks);

//		BacklogJournalSerializer serializer = new BacklogJournalSerializer();
//		String newChunksStr = serializer.serialize(newChunks);
		
//		System.out.println(expectedChunks);
//		System.out.println(newChunks);
		
		// then
		ArrayList<BacklogChunk> expectedChunks = reader.readFromFileNullAllowed("src/test/resources/conversion_tests/bzura_product_backlog_converted_toabsolute.txt");
		assertEquals(expectedChunks.toString(), newChunks.toString());
	}

}
