package com.tomtom.amelinium.db.mappers;

import java.util.List;

import com.tomtom.amelinium.db.beans.Project;
import com.tomtom.amelinium.db.results.ResultProject;

/**
 * Mapper for the sql operations on the project table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public interface ProjectMapper {

	Integer insert(Project project);

	List<Integer> selectIds();
	
	List<Project> selectAll();

	Project select(Integer id);
	
	ResultProject selectCurrentRevision(Integer id);
	
	Integer selectNewestProjectId();

	Integer updateName(Integer id, String name);

	Integer delete(Integer id);

	Integer updateDtLastModified(Integer id, String dt);

}
