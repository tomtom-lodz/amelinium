package com.tomtom.amelinium.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
/**
 * Class for saving the specified content to a specified file.
 * 
 * @author Natasza Nowak
 */
public class FileUtil {
	/**
	 * Saves specified content to a specified file.
	 */
	public static void writeContentToFile(String content, String file) throws IOException {
		FileWriter fstream = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(content);
		System.out.println("Content saved to file: '"+file+"' .");
		out.close();
	}
	
	public static void closeQuietly(InputStream str) {
		if(str==null) {
			return;
		}
		try {
			str.close();
		} catch (IOException e) {
		}
	}

}
