package com.tomtom.amelinium.db.logic;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.db.beans.Snapshot;
import com.tomtom.amelinium.db.mappers.SnapshotMapper;

/**
 * Handles operations related to the snapshot database table.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class SnapshotDbLogic {

	private SqlSessionFactory sqlMapper = null;
	
	public SnapshotDbLogic(SqlSessionFactory sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	/**
	 * Inserts snapshot entity to the snapshot database.
	 */
	public Integer insertSnapshot(Snapshot snapshot) {
		Integer result = null;
		SqlSession session = this.sqlMapper.openSession();
		try {
			SnapshotMapper mapper = session.getMapper(SnapshotMapper.class);
			result = mapper.insert(snapshot);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Deletes multiple snapshots corresponding to one project. Snapshots
	 * shouldn't be deleted unless their parent project is deleted.
	 */
	public Integer deleteSnapshots(int projectId) {
		Integer result;
		SqlSession session = this.sqlMapper.openSession();
		try {
			SnapshotMapper snapshotMapper = session
					.getMapper(SnapshotMapper.class);
			result = snapshotMapper.delete(projectId);
			session.commit();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Retrieves current snapshot revision of the specified project.
	 * 
	 * @id database id of the project
	 */
	public int getCurrentRevision(int id) {
		int revision = 1;
		SqlSession session = this.sqlMapper.openSession();
		try {
			SnapshotMapper mapper = session.getMapper(SnapshotMapper.class);
			Integer rev = mapper.selectCurrentRevision(id);
			if (rev != null) {
				revision = rev;
			}
		} finally {
			session.close();
		}
		return revision;
	}

}
