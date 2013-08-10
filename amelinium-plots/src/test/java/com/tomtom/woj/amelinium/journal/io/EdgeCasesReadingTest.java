package com.tomtom.woj.amelinium.journal.io;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.tomtom.woj.amelinium.PlotHtmlPageGenerator;
import com.tomtom.woj.amelinium.journal.io.BacklogJournalReader;
import com.tomtom.woj.amelinium.utils.StringUtils;

public class EdgeCasesReadingTest {

	@Test
	public void testEdgeCase() throws IOException {
		
		BacklogJournalReader reader = new BacklogJournalReader();
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case1.txt");
			fail();
		} catch (Exception e) {
		}
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case2.txt");
			fail();
		} catch (Exception e) {
		}
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case3.txt");
			fail();
		} catch (Exception e) {
		}

		reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case4.txt");
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case5.txt");
			fail();
		} catch (Exception e) {
		}
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case6.txt");
			fail();
		} catch (Exception e) {
		}
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case7.txt");
			fail();
		} catch (Exception e) {
		}
		
		reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case8.txt");
		reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case9.txt");
		reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case10.txt");
		reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case11.txt");
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case12.txt");
			fail();
		} catch (Exception e) {
		}
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case13.txt");
			fail();
		} catch (Exception e) {
		}
		
		try {
			reader.readFromFile("src/test/resources/backlog_journals/edge_cases/case14.txt");
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testRenderEmpty() throws IOException {
		
		PlotHtmlPageGenerator.createHtmlPageWithPlots(StringUtils.readFile("src/test/resources/backlog_journals/edge_cases/case4.txt"),true,8,2,6);
		PlotHtmlPageGenerator.createHtmlPageWithPlots(StringUtils.readFile("src/test/resources/backlog_journals/edge_cases/case8.txt"),true,8,2,6);
		PlotHtmlPageGenerator.createHtmlPageWithPlots(StringUtils.readFile("src/test/resources/backlog_journals/edge_cases/case9.txt"),true,8,2,6);
		PlotHtmlPageGenerator.createHtmlPageWithPlots(StringUtils.readFile("src/test/resources/backlog_journals/edge_cases/case10.txt"),true,8,2,6);
		PlotHtmlPageGenerator.createHtmlPageWithPlots(StringUtils.readFile("src/test/resources/backlog_journals/edge_cases/case11.txt"),true,8,2,6);
	}
	
}
