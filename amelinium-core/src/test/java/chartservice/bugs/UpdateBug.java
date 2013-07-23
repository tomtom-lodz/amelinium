package chartservice.bugs;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class UpdateBug {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void update1() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/update1/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/update1/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		ChartModel chartModel = factory.readAndCorrectChartModel(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);

		// then
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(0)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(0)
				.getSecondLastSprint());

		assertFalse(0 == chartModel.getFeatureGroupsTables().get(1)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(1)
				.getSecondLastSprint());

		assertFalse(0 == chartModel.getFeatureGroupsTables().get(2)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(2)
				.getSecondLastSprint());

	}

	@Test
	public void update2() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/update2/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/update2/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		ChartModel chartModel = factory.readAndCorrectChartModel(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);

		// then
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(0)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(0)
				.getSecondLastSprint());

		assertFalse(0 == chartModel.getFeatureGroupsTables().get(1)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(1)
				.getSecondLastSprint());

		assertFalse(0 == chartModel.getFeatureGroupsTables().get(2)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(2)
				.getSecondLastSprint());

	}
	
	@Test
	public void update3() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/update3/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/update3/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		ChartModel chartModel = factory.readAndCorrectChartModel(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);

		// then
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(0)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(0)
				.getSecondLastSprint());

		assertFalse(0 == chartModel.getFeatureGroupsTables().get(1)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(1)
				.getSecondLastSprint());

		assertFalse(0 == chartModel.getFeatureGroupsTables().get(2)
				.getLastSprint());
		assertFalse(0 == chartModel.getFeatureGroupsTables().get(2)
				.getSecondLastSprint());

	}
}
