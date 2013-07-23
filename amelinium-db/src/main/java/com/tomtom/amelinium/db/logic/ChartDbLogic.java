package com.tomtom.amelinium.db.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.db.beans.Chart;
import com.tomtom.amelinium.db.mappers.ChartMapper;
import com.tomtom.amelinium.db.results.HistoryElement;

/**
 * Handles operations related to the chart database table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class ChartDbLogic {

	private SqlSessionFactory sqlMapper = null;

	public ChartDbLogic(SqlSessionFactory sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	/**
	 * Inserts chart entity into chart table.
	 */
	public Integer insertChart(Chart chart) {
		Integer result = null;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ChartMapper mapper = session.getMapper(ChartMapper.class);
			result = mapper.insert(chart);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Deletes multiple charts corresponding to one project. Charts shouldn't be
	 * deleted unless their parent project is deleted, because revision history
	 * should be maintained.
	 */
	public Integer deleteBacklogs(int projectId) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ChartMapper chartMapper = session.getMapper(ChartMapper.class);
			result = chartMapper.delete(projectId);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Retrieves id of the newest chart snapshot for the specified project.
	 * 
	 * @param id
	 *            database id of the project
	 */
	public int getCurrentId(int id) {
		int revision = 1;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ChartMapper mapper = session.getMapper(ChartMapper.class);
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
	 * Retrieves id of the recently inserted entity from the chart table.
	 * Relationship of this entity with project entity is disregarded. DateTime
	 * is taken into account. When there are multiple entities with the same
	 * creation date, the one with the newest id is chosen.
	 */
	public int getRecentlyInsertedId() {
		int revision = 1;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ChartMapper mapper = session.getMapper(ChartMapper.class);
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
	 * Retrieves part of the chart history indicated by arguments.
	 * 
	 * @param id
	 *            database id of the project
	 * @param initialIndex
	 *            retrieved charts will have ids greater or equal to this index
	 * @param finalIndex
	 *            retrieved charts will have ids smaller or equal to this index
	 */
	public List<HistoryElement> getHistoryPart(int id, int initialIndex,
			int finalIndex) {
		List<HistoryElement> history = new ArrayList<HistoryElement>();
		SqlSession session = this.sqlMapper.openSession();
		try {
			ChartMapper mapper = session.getMapper(ChartMapper.class);
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
	 * Retrieves chart entity from the backlog table.
	 * 
	 * @param id
	 *            database id of the project
	 * @param revision
	 *            revision of the chart to be retrieved
	 * 
	 */
	public Chart getChart(int id, int revision) {
		Chart result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			ChartMapper mapper = session.getMapper(ChartMapper.class);
			result = mapper.select(id, revision);
		} finally {
			session.close();
		}
		return result;
	}
}
