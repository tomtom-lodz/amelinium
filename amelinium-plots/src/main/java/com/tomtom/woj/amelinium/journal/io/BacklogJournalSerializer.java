package com.tomtom.woj.amelinium.journal.io;

import java.util.ArrayList;

import com.tomtom.woj.amelinium.journal.model.BacklogChunk;

public class BacklogJournalSerializer {
	
	public String serialize(ArrayList<BacklogChunk> chunks) {
		StringBuffer sb = new StringBuffer();
		for(BacklogChunk chunk : chunks) {
			sb.append(chunk.toString());
		}
		return sb.toString();
	}

}
