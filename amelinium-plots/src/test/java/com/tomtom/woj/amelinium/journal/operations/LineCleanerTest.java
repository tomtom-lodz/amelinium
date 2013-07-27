package com.tomtom.woj.amelinium.journal.operations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class LineCleanerTest {

	@Test
	public void test1() throws IOException {

		// given
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Burned");
		headers.add("A");
		headers.add("B");
		headers.add("C");
		
		ArrayList<String> line = new ArrayList<String>();
		line.add("A");
		line.add("B");
		line.add("C");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(1.);
		values.add(2.);
		values.add(3.);
		
		LineCleaner cleaner = new LineCleaner();
		
		// when
		cleaner.limitAbsolute(headers,line,values);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("A");
		expectedLine.add("B");
		expectedLine.add("C");
		
		assertEquals(expectedLine,line);
	}
	
	@Test
	public void test2() throws IOException {

		// given
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Burned");
		headers.add("A");
		headers.add("B");
		headers.add("C");
		
		ArrayList<String> line = new ArrayList<String>();
		line.add("A");
		line.add("D");
		line.add("B");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(1.);
		values.add(2.);
		values.add(3.);
		
		LineCleaner cleaner = new LineCleaner();
		
		// when
		cleaner.limitAbsolute(headers,line,values);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("A");
		expectedLine.add("D");
		expectedLine.add("B");
		
		assertEquals(expectedLine,line);
	}

	
	@Test
	public void test3() throws IOException {

		// given
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Burned");
		headers.add("A");
		headers.add("B");
		headers.add("C");
		
		ArrayList<String> line = new ArrayList<String>();
		line.add("A");
		line.add("B");
		line.add("D");
		line.add("C");
		line.add("E");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(1.);
		values.add(2.);
		values.add(3.);
		values.add(4.);
		values.add(5.);
		
		LineCleaner cleaner = new LineCleaner();
		
		// when
		cleaner.limitAbsolute(headers,line,values);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("A");
		expectedLine.add("B");
		expectedLine.add("D");
		expectedLine.add("C");
		
		assertEquals(expectedLine,line);
	}

	@Test
	public void test4() throws IOException {

		// given
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Burned");
		headers.add("A");
		headers.add("B");
		headers.add("C");
		
		ArrayList<String> line = new ArrayList<String>();
		line.add("A");
		line.add("B");
		line.add("D");
		line.add("C");
		line.add("E");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(1.);
		values.add(2.);
		values.add(3.);
		values.add(4.);
		values.add(5.);
		
		LineCleaner cleaner = new LineCleaner();
		
		// when
		cleaner.limitAbsoluteStrictly(headers,line,values);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("A");
		expectedLine.add("B");
		expectedLine.add("C");

		ArrayList<Double> expectedValues = new ArrayList<Double>();
		expectedValues.add(1.);
		expectedValues.add(2.);
		expectedValues.add(7.);
		
		assertEquals(expectedLine,line);
		assertEquals(expectedValues,values);
	}
	
	@Test
	public void test5() throws IOException {

		// given
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Burned");
		headers.add("A");
		headers.add("B");
		headers.add("C");
		
		ArrayList<String> line = new ArrayList<String>();
		line.add("A");
		line.add("C");
		
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(1.);
		values.add(3.);
		
		LineCleaner cleaner = new LineCleaner();
		
		// when
		cleaner.limitCumulative(headers,line,values);
		
		// then
		ArrayList<String> expectedLine = new ArrayList<String>();
		expectedLine.add("A");
		expectedLine.add("C");
		
		assertEquals(expectedLine,line);
	}
	
}
