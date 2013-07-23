package com.tomtom.amelinium.db.mappers;

import java.util.List;

import com.tomtom.amelinium.db.beans.Backlog;
import com.tomtom.amelinium.db.results.HistoryElement;

/**
 * Mapper for the sql operations on the backlog table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public interface BacklogMapper {

	Integer insert(Backlog backlog);

	List<Backlog> selectAll();
	
	List<HistoryElement> selectPart(int id, int initialIndex, int finalIndex);

	Backlog select(int id, int revision);
	
	Integer selectNewestId();
	
	Integer selectCurrentId(Integer id);

	Integer delete(Integer projectId);

}
