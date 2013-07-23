package com.tomtom.amelinium.confluence.logic;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.confluence.client.ConfluenceOperations;
import com.tomtom.amelinium.confluence.config.ConfluenceConfig;

public class ChartPageCorrector {

	private ConfluenceConfig config = new ConfluenceConfig();

	/**
	 * Retrieves, corrects and updates a specified chart on the Confluence.
	 * <p>
	 * First, the method retrieves the content of the chart to be corrected and
	 * the content of the corresponding backlog. If none of the contents is
	 * empty, new chart content is generated based on both contents. Finally,
	 * the old chart content is replaced on the server with the newly generated
	 * one.
	 * <p>
	 * The method uses the server and credentials set in
	 * service.config.confluence.
	 * 
	 * @param chartTitle
	 *            Title of the chart Page to be corrected
	 * @param chartSpace
	 *            Name of the Confluence space where the chart to be corrected
	 *            is located.
	 * 
	 * @param title
	 *            Title of the backlog Page corresponding to the chart to be
	 *            corrected
	 * @param space
	 *            Name of the Confluence space where the appropriate backlog is
	 *            located
	 * 
	 * @param sprint
	 *            Number of the sprint for which the chart should be updated
	 * 
	 * @return message describing if operation is successful or not. If the
	 *         backlog with the specified parameters is found on the server, the
	 *         response "Chart <code>title</code> corrected
	 *         successfully!" is returned. If not, the response is "Chart
	 *         <code>title</code> not found in <code>space</code> or the test
	 *         user is not authorized to it."
	 */

	public String correctChart(String chartTitle, String chartSpace,
			String backlogTitle, String backlogSpace,
			boolean allowingMultilineFeatures) {

		String response;

		String oldChart = ConfluenceOperations.getPageSource(config.SERVER,
				config.USER, config.PASS, chartSpace, chartTitle);
		String oldBacklog = ConfluenceOperations.getPageSource(config.SERVER,
				config.USER, config.PASS, backlogSpace, backlogTitle);

		if (oldChart.isEmpty() || oldBacklog.isEmpty()) {
			response = "Page '" + chartTitle + "' not found in space '"
					+ chartSpace
					+ "' or the test user is not authorized to it.";
		} else {
			String newChart = generateNewChartContent(oldChart, oldBacklog,
					allowingMultilineFeatures);

			if (!newChart.equals(oldChart))

			{
				ConfluenceOperations.updatePageSource(config.SERVER,
						config.USER, config.PASS, chartSpace, chartTitle,
						newChart);
				response = "Chart '" + chartTitle + "' corrected successfully!";
			} else {
				response = "Chart '" + chartTitle
						+ "' didn't require correction.";
			}

		}

		return response;
	}

	/**
	 * Generates new chart content given one to update and corresponding
	 * backlog.
	 * <p>
	 * Two models are built - one based on chart content, second based on
	 * backlog content. Then the backlog model is corrected. Now the chart model
	 * can be corrected based on the corrected backlog model. Then the new
	 * backlog content is serialized from model.
	 * 
	 * @param oldChart
	 *            The chart to be updated
	 * @param oldBacklog
	 *            Backlog corresponding to the chart
	 * @param allowingMultilineFeatures
	 *            Set to true if features can consist of multiple lines
	 * @param sprint
	 *            Number of the sprint
	 * 
	 * @return updated content
	 */

	private String generateNewChartContent(String oldChart, String oldBacklog,
			boolean allowingMultilineFeatures) {
		ChartServiceFactory factory = new ChartServiceFactory();
		String newChartContent = factory
				.readCorrectAndSerializeChartModelFromStrings(oldBacklog,
						oldChart, allowingMultilineFeatures);

		return newChartContent;
	}
}
