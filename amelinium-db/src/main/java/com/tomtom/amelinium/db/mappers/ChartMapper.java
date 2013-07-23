package com.tomtom.amelinium.db.mappers;

import java.util.List;

import com.tomtom.amelinium.db.beans.Chart;
import com.tomtom.amelinium.db.results.HistoryElement;

/**
 * Mapper for the sql operations on the chart table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public interface ChartMapper {

	Integer insert(Chart chart);

	List<Chart> selectAll();
	
	List<HistoryElement> selectPart(int id, int initialIndex, int finalIndex);

	Chart select(int id, int revision);
	
	Integer selectNewestId();
	
	Integer selectCurrentId(Integer id);

	Integer delete(Integer projectId);
}
