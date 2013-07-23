package chartservice.serializer;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;

public class DarkMatterSerializerTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void darkMatterSerializationTest() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/sprint15/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/sprint15/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		String output = factory.readCorrectAndSerializeChartModelFromFiles(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);
		//System.out.println(output);

		// TODO: add asserts!!!!!!!!
		// FIXME: add asserts!!!!!!!!
	}
}
