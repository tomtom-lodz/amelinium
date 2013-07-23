package com.tomtom.amelinium.db.mappers;

import java.util.List;

import com.tomtom.amelinium.db.beans.Snapshot;

/**
 * Mapper for the sql operations on the snapshot table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public interface SnapshotMapper {

	Integer insert(Snapshot snapshot);

	List<Snapshot> selectAll();

	Snapshot select(Integer id);
	
	Integer selectCurrentId(Integer id);
	
	Integer selectCurrentRevision(Integer id);

	Integer delete(Integer projectId);

}
