package chartservice.corrector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class RoadMapGeneratorTest {
	private boolean allowingMultilineFeatures = true;

	public void roadmapCorrectionTest() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName, this.allowingMultilineFeatures);

		// then
		// before correction there is only one feature listed in the roadmap
		assertEquals(1, chartModel.getRoadmap().getRows().size());

		// ---CORRECT---
		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction there are two features listed in the table under the
		// chart
		assertEquals(2, chartModel.getRoadmap().getRows().size());

	}

	@Test
	public void roadmapGenerationTest() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/no_roadmap/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/no_roadmap/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName, this.allowingMultilineFeatures);

		// then
		// before correction there was NO ROADMAP
		assertEquals(0, chartModel.getRoadmap().getRows().size());

		// ---CORRECT---
		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction there is one feature listed in the table under the
		// chart
		assertEquals(1, chartModel.getRoadmap().getRows().size());
	}

}
