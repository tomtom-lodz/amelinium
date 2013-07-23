package chartservice.corrector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class RoadmapNamesTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void testNamesFromChart() {
		// given
		String backlogFileName = "src/test/resources/chartservice/missing_features_adder/names_from_chart/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/missing_features_adder/names_from_chart/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName, this.allowingMultilineFeatures);

		// then
		// before correction
		assertEquals(11, chartModel.getFeatureGroupsTables().size());
		assertEquals("NNN - some description", chartModel.getRoadmap()
				.getRows().get(0).getFeatureGroup());
		assertEquals("OOO", chartModel.getRoadmap().getRows().get(1)
				.getFeatureGroup());

		chartCorrector.correctTables(backlogModel, chartModel);

		// after correction
		assertEquals(15, chartModel.getFeatureGroupsTables().size());
		assertEquals("NNN", chartModel.getRoadmap().getRows().get(0)
				.getFeatureGroup());
		assertEquals("JJJ", chartModel.getRoadmap().getRows().get(10)
				.getFeatureGroup());

	}

	@Test
	public void testNamesFromBacklog() {
		// given
		String backlogFileName = "src/test/resources/chartservice/missing_features_adder/names_from_backlog/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/missing_features_adder/names_from_backlog/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName, this.allowingMultilineFeatures);

		// then
		// before correction
		assertEquals(11, chartModel.getFeatureGroupsTables().size());
		assertEquals("NNN", chartModel.getRoadmap().getRows().get(0)
				.getFeatureGroup());
		assertEquals("BBB", chartModel.getRoadmap().getRows().get(2)
				.getFeatureGroup());

		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction
		assertEquals(15, chartModel.getFeatureGroupsTables().size());
		assertEquals("NNN", chartModel.getRoadmap().getRows().get(0)
				.getFeatureGroup());
		assertEquals("BBB - some description", chartModel.getRoadmap()
				.getRows().get(2).getFeatureGroup());

	}
}
