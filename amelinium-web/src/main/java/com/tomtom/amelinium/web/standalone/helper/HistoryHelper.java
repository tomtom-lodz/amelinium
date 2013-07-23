package com.tomtom.amelinium.web.standalone.helper;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import com.tomtom.amelinium.db.logic.BacklogDbLogic;
import com.tomtom.amelinium.db.logic.ChartDbLogic;
import com.tomtom.amelinium.db.logic.SnapshotDbLogic;
import com.tomtom.amelinium.db.results.HistoryElement;

/**
 * Used for calculation of history indices and retrieving history from database.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class HistoryHelper {
	private SnapshotDbLogic snapshotDbLogic;
	private BacklogDbLogic backlogDbLogic;
	private ChartDbLogic chartDbLogic;

	public HistoryHelper(SqlSessionFactory sqlMapper){
		backlogDbLogic = new BacklogDbLogic(sqlMapper);
		chartDbLogic = new ChartDbLogic(sqlMapper);
		snapshotDbLogic = new SnapshotDbLogic(sqlMapper);
	}
	
	/**
	 * Calculates the number of the history page older than the current part.
	 * Makes sure that the history size is not exceeded.
	 */
	public int calculateTranslationOlder(int translation, int projectId) {
		int translationOlder = translation + 1;
		if ((snapshotDbLogic.getCurrentRevision(projectId) - (translationOlder * 100)) < 1) {
			translationOlder = translation;
		}
		return translationOlder;
	}

	/**
	 * Calculates the number of the history page newer than the current part.
	 * Makes sure that the history size is not exceeded.
	 */
	public int calculateTranslationNewer(int translation) {
		int translationNewer = translation - 1;

		if (translationNewer < 1) {
			translationNewer = 1;
		}
		return translationNewer;
	}

	/**
	 * Returns specified part of the backlog history. Uses database logic
	 * handler responsible for backlog entities.
	 * 
	 * @return list of HistoryElements containing information about backlog
	 *         revisions
	 */
	public List<HistoryElement> getPartOfBacklogHistory(int id, int translation) {
		int finalIndex = calculateFinalIndex(translation, id);
		int initialIndex = calculateInitialIndex(finalIndex);
		List<HistoryElement> history = backlogDbLogic.getHistoryPart(id,
				initialIndex, finalIndex);
		return history;
	}

	/**
	 * Returns specified part of the chart history. Uses database logic handler
	 * responsible for chart entities.
	 * 
	 *  @return list of HistoryElements containing information about chart
	 *         revisions
	 */
	public List<HistoryElement> getPartOfChartHistory(int id, int translation) {
		int finalIndex = calculateFinalIndex(translation, id);
		int initialIndex = calculateInitialIndex(finalIndex);
		List<HistoryElement> history = chartDbLogic.getHistoryPart(id,
				initialIndex, finalIndex);
		return history;
	}

	private int calculateFinalIndex(int translation, int projectId) {
		int finalIndex = (snapshotDbLogic.getCurrentRevision(projectId))
				- (translation * 100);

		if (finalIndex < 100) {
			finalIndex = 100;
		}
		return finalIndex;
	}

	private int calculateInitialIndex(int finalIndex) {
		int initialIndex = finalIndex - 99;
		return initialIndex;
	}
}
