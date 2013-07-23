package chartservice.corrector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class MissingFeaturesAdderTest {
	private boolean allowingMultilineFeatures = true;
	
	@Test
	public void namesUnchangedTest() {
		// given
		String backlogFileName = "src/test/resources/chartservice/missing_features_adder/names_unchanged/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/missing_features_adder/names_unchanged/chart.txt";
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

		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction
		assertEquals(15, chartModel.getFeatureGroupsTables().size());

	}
}
