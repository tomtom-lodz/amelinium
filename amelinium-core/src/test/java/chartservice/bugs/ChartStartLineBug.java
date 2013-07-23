package chartservice.bugs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class ChartStartLineBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/chart_start_line/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/chart_start_line/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		//when
		ChartModel chartModel = factory.readAndCorrectChartModel(backlogFileName,
				chartFileName,this.allowingMultilineFeatures);

		// then
		assertEquals(
				"{chart:type=xyLine|domainAxisTickUnit=1|dataOrientation=vertical|width=800|height=500|title=abcd}",
				chartModel.getChartStartMarker());

	}

}
