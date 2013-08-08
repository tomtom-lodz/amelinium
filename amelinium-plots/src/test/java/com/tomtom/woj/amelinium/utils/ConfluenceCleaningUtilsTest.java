package com.tomtom.woj.amelinium.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfluenceCleaningUtilsTest {

	@Test
	public void testRemoveTags() {
		String result = ConfluenceCleaningUtils.cleanConfluenceStringForCsv("{color:#000000}{*}{}asadadasd{*}{color}");
		assertEquals("asadadasd", result);
	}

	@Test
	public void testRemoveStars() {
		String result = ConfluenceCleaningUtils.cleanConfluenceStringForCsv("*asadadasd*");
		assertEquals("asadadasd", result);
	}

}
