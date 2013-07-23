package com.tomtom.amelinium.db.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.db.beans.Backlog;
import com.tomtom.amelinium.db.mappers.BacklogMapper;
import com.tomtom.amelinium.db.results.HistoryElement;

/**
 * Handles operations related to the backlog database table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class BacklogDbLogic {

	private SqlSessionFactory sqlMapper = null;

	public BacklogDbLogic(SqlSessionFactory sqlMapper){
		this.sqlMapper = sqlMapper;
	}
	
	/**
	 * Inserts backlog entity into backlog table.
	 */
	public Integer insertBacklog(Backlog backlog) {
		Integer result = null;
		SqlSession session = this.sqlMapper.openSession();
		try {
			BacklogMapper mapper = session.getMapper(BacklogMapper.class);
			result = mapper.insert(backlog);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Deletes multiple backlogs corresponding to one project. Backlogs
	 * shouldn't be deleted unless their parent project is deleted, because
	 * revision history should be maintained.
	 */
	public Integer deleteBacklogs(int projectId) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			BacklogMapper backlogMapper = session
					.getMapper(BacklogMapper.class);
			result = backlogMapper.delete(projectId);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Retrieves id of the newest backlog snapshot for the specified project.
	 * 
	 * @param id
	 *            database id of the project
	 */
	public int getCurrentId(int id) {
		int revision = 1;
		SqlSession session = this.sqlMapper.openSession();
		try {
			BacklogMapper mapper = session.getMapper(BacklogMapper.class);
			Integer rev = mapper.selectCurrentId(id);
			if (rev != null) {
				revision = rev;
			}
		} finally {
			session.close();
		}
		return revision;
	}

	/**
	 * Retrieves id of the recently inserted entity from the backlog table.
	 * Relationship of this entity with project entity is disregarded. DateTime
	 * is taken into account. When there are multiple entities with the same
	 * creation date, the one with the newest id is chosen.
	 */
	public int getRecentlyInsertedId() {
		int revision = 1;
		SqlSession session = this.sqlMapper.openSession();
		try {
			BacklogMapper mapper = session.getMapper(BacklogMapper.class);
			Integer rev = mapper.selectNewestId();
			if (rev != null) {
				revision = rev;
			}
		} finally {
			session.close();
		}
		return revision;
	}

	/**
	 * Retrieves part of the backlog history indicated by arguments.
	 * 
	 * @param id
	 *            database id of the project
	 * @param initialIndex
	 *            retrieved backlogs will have ids greater or equal to this
	 *            index
	 * @param finalIndex
	 *            retrieved backlogs will have ids smaller or equal to this
	 *            index
	 */
	public List<HistoryElement> getHistoryPart(int id, int initialIndex,
			int finalIndex) {
		List<HistoryElement> history = new ArrayList<HistoryElement>();
		SqlSession session = this.sqlMapper.openSession();
		try {
			BacklogMapper mapper = session.getMapper(BacklogMapper.class);
			List<HistoryElement> result = mapper.selectPart(id, initialIndex,
					finalIndex);
			if (result != null) {
				history = result;
			}
		} finally {
			session.close();
		}
		return history;
	}

	/**
	 * Retrieves backlog entity from the backlog table.
	 * 
	 * @param id
	 *            database id of the project
	 * @param revision
	 *            revision of the backlog to be retrieved
	 * 
	 */
	public Backlog getBacklog(int id, int revision) {
		Backlog result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			BacklogMapper mapper = session.getMapper(BacklogMapper.class);
			result = mapper.select(id, revision);
		} finally {
			session.close();
		}
		return result;
	}

}
