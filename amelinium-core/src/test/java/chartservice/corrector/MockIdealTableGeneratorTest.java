package chartservice.corrector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class MockIdealTableGeneratorTest {
	boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/missing_ideal/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/missing_ideal/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,
						this.allowingMultilineFeatures);
		chartCorrector.correctTables(backlogModel, chartModel);

		assertEquals(chartModel.getLog().getMessage(),
				"Automatically added required 'ideal' trend line table.");
		
	}

}
