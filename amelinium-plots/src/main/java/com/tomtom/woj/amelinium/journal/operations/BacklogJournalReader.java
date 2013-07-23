package com.tomtom.woj.amelinium.journal.operations;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalReader {

	private static final char SEPARATOR = ',';
	private static final char QUOTE = '"';
	private static final char ESCAPE = '\\';

	public ArrayList<BacklogChunk> readFromString(String string) {
		try {
			return readAndClose(new StringReader(string));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<BacklogChunk> readFromFile(String fileName) throws IOException {
		return readAndClose(new FileReader(fileName));
	}

	private ArrayList<BacklogChunk> readAndClose(Reader reader) throws IOException {
		ArrayList<BacklogChunk> chunks;
		try {
			chunks = read(new CSVReader(reader,SEPARATOR,QUOTE,ESCAPE));
		} finally {
			reader.close();
		}
		return chunks;
	}

	private ArrayList<BacklogChunk> read(CSVReader csvReader)
			throws IOException {
		ArrayList<BacklogChunk> chunks = new ArrayList<BacklogChunk>();

		String[] header = null;
		BacklogChunk chunk = null;
		int lineNumber = 0;
		
		while(true) {
			String[] line = csvReader.readNext();
			lineNumber++;
			if(line==null) {
				// end of file
				break;
			}
			if(line.length==1 && "".equals(line[0].trim())) {
				// skip empty line
				continue;
			} else if("Date".equals(line[0])) {
				if(line.length>1 && !"Burned".equals(line[1])) {
					throw new RuntimeException("CSV validation error in line " + lineNumber + ", second column name should be Burned: " + ArrayUtils.toString(line));
				}
				// new chunk
				chunk = new BacklogChunk(line);
				chunks.add(chunk);
				header = line;
			} else if(header==null) {
				// skip line
				continue;
			} else if(line.length!=header.length) {
				throw new RuntimeException("CSV validation error in line " + lineNumber + ", wrong number of items in line: " + ArrayUtils.toString(line));
			} else {
				// add line
				try {
					chunk.addline(line);
				} catch (Exception e) {
					throw new RuntimeException("CSV parsing error in line " + lineNumber + ", " + e.getMessage(),e);
				}
			}
		}
		return chunks;
	}
}
