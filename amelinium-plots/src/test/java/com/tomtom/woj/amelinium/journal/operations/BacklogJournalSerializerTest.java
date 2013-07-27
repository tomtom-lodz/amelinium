package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;
import com.tomtom.woj.amelinium.utils.StringUtils;

public class BacklogJournalSerializerTest {

	@Test
	public void test() throws IOException {

		// given
		BacklogJournalReader reader = new BacklogJournalReader();
		BacklogJournalSerializer serializer = new BacklogJournalSerializer();

		ArrayList<BacklogChunk> chunks = reader.readFromFile("src/test/resources/serializer_tests/serializer_input.txt");
		
		// when
		String result = serializer.serialize(chunks);
		
		// then
		String expected = StringUtils.readFile("src/test/resources/serializer_tests/serializer_output.txt");
		
		assertEquals(expected, result);
		
//		System.out.println(result);
	}

}
