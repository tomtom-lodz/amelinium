package chartservice.bugs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class DateTable_OrderBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/order/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/order/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		ChartModel chartModel = factory.readAndCorrectChartModel(backlogFileName,
				chartFileName,this.allowingMultilineFeatures);

		// then
		// order of sprints in date table must be ascending
		assertTrue(chartModel.getRoadmap().getRows().get(0).getSprint() < chartModel
				.getRoadmap().getRows().get(5).getSprint());

	}

}
