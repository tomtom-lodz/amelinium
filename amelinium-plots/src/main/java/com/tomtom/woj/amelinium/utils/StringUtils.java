package com.tomtom.woj.amelinium.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class StringUtils {
	
	public static String readFile(String fileName) throws FileNotFoundException {
		return new Scanner( new File(fileName) ).useDelimiter("\\A").next();
	}
	
	public static void writeFile(String content, String fileName) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(fileName);
		try {
			writer.print(content);
		} finally {
			closeQuietly(writer);
		}
	}

	public static String readResource(String resourceName) {
		InputStream res = StringUtils.class.getResourceAsStream(resourceName);
		String out = convertStreamToString(res);
		try {
			res.close();
		} catch (IOException e) {
		}
		return out;
	}

	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

	private static void closeQuietly(PrintWriter writer) {
		try {
			writer.close();
		} catch (Exception e) {
		}
	}
}
