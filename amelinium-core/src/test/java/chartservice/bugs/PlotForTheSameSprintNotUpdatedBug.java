package chartservice.bugs;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;

public class PlotForTheSameSprintNotUpdatedBug {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void bugUpdateInTheSameSprint() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/sprint15/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/sprint15/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		String output = factory.readCorrectAndSerializeChartModelFromFiles(
				backlogFileName, chartFileName,this.allowingMultilineFeatures);
		//System.out.println(output);

		// TODO: ma poprawic burned points na 378 w sprincie 43

		// TODO: add asserts!!!!!!!!
		// FIXME: add asserts!!!!!!!!
	}
}
