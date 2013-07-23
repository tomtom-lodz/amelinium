package com.tomtom.amelinium.db.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.db.beans.Project;
import com.tomtom.amelinium.db.mappers.ProjectMapper;
import com.tomtom.amelinium.db.results.ResultProject;

/**
 * Handles operations related to the project database table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ProjectDbLogic {
	private SqlSessionFactory sqlMapper = null;
	
	public ProjectDbLogic(SqlSessionFactory sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	/**
	 * Retrieves ids of all the project entities from the database.
	 */
	public List<Integer> getProjectIds() {
		List<Integer> ids = new ArrayList<Integer>();
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			ids = projectMapper.selectIds();

		} finally {
			session.close();
		}
		return ids;
	}

	/**
	 * Retrieves all the project entities from the database.
	 */
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<Project>();
		List<Project> result = null;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			result = projectMapper.selectAll();
			if(result!=null){
				projects = result;
			}
			
		} finally {
			session.close();
		}
		return projects;
	}

	/**
	 * Retrieves id of the newest project snapshot for the specified project.
	 * 
	 * @param id
	 *            database id of the project
	 */
	public ResultProject getCurrentRevision(int id) {
		ResultProject resultProject = new ResultProject();
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			resultProject = projectMapper.selectCurrentRevision(id);
		} finally {
			session.close();
		}
		return resultProject;
	}

	/**
	 * Deletes specified project.
	 * 
	 * @param id
	 *            database id of the project
	 */
	public Integer deleteProject(int id) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			result = projectMapper.delete(id);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Updates name of the project.
	 * 
	 * @param id
	 *            database id of the project
	 * @param name
	 *            new name of the project
	 */
	public Integer updateName(int projectId, String name) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			result = projectMapper.updateName(projectId, name);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Retrieves id of the recently inserted project.
	 */
	public Integer getRecentlyInsertedId() {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			result = projectMapper.selectNewestProjectId();
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Inserts project entity to project table in the database.
	 */
	public Integer insertProject(Project project) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			result = projectMapper.insert(project);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	public Integer updateDtLastModified(int id, String dt) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ProjectMapper projectMapper = session
					.getMapper(ProjectMapper.class);
			result = projectMapper.updateDtLastModified(id, dt);
			session.commit();
		} finally {
			session.close();
		}	
		return result;
	}
}
