package com.tomtom.amelinium.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The purpose of this class is to read the file/string and divide it into
 * separate lines.
 * 
 * @author Natasza Nowak
 */

public class LineProducer {

	/**
	 * Reads file and returns an array of lines.
	 */
	public ArrayList<String> readLinesFromFile(String name) {

		ArrayList<String> lines = new ArrayList<String>();
		FileInputStream fstream = null;
		
		try {
			fstream = new FileInputStream(name);
			lines = readLinesFromStream(fstream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			FileUtil.closeQuietly(fstream);
		}
		
		return lines;
	}

	public ArrayList<String> readLinesFromStream(InputStream fstream) {

		ArrayList<String> lines = new ArrayList<String>();
		DataInputStream in = null;
		
		try {
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			try {
				while ((line = br.readLine()) != null) {
					line = line.replaceAll("\r", "");
					lines.add(line);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			FileUtil.closeQuietly(fstream);
		}
		
		return lines;
	}
	
	/**
	 * Divides a string into array of lines.
	 */

	public ArrayList<String> readLinesFromString(String content) {
		content = content.replaceAll("\r", "");
		return new ArrayList<String>(Arrays.asList(content.split("\n")));
	}

	public String readStringFromFile(String fileName) {
		String result = "";
		FileInputStream fstream = null;
		DataInputStream in = null;

		try {
			try {
				fstream = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			try {
				while ((line = br.readLine()) != null) {
					result += line + "\n";// "<br/>";
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				fstream.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

	public String readStringFromStream(InputStream stream) {
		String result = "";
		DataInputStream in = null;

		try {
			in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			String line;
			try {
				while ((line = br.readLine()) != null) {
					result += line + "\n";// "<br/>";
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			FileUtil.closeQuietly(in);
		}
		
		return result;
	}

	public String readStringFromResource(String name) {
		String result = "";
		InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		DataInputStream in = null;

		try {
			in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
			String line;
			try {
				while ((line = br.readLine()) != null) {
					result += line + "\n";// "<br/>";
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			FileUtil.closeQuietly(in);
			FileUtil.closeQuietly(stream);
		}
		
		return result;
	}
}
