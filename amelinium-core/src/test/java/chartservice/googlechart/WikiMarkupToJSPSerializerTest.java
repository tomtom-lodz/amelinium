package chartservice.googlechart;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;


public class WikiMarkupToJSPSerializerTest {

	@Test
	public void correctAndSerializeJSPTest() {

		// given
		String backlogFileName = "src/test/resources/chartservice/googlechart/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/googlechart/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();
		boolean allowingMultilineFeatures=true;

		// when

		String content = factory.readCorrectAndSerializeChartModelFromFiles(backlogFileName, chartFileName, allowingMultilineFeatures);

		//then
		assertTrue(content.contains("|| Table || matched? || corrected? || Feature Group from backlog ||"));
		assertTrue(content.contains("| AAA |"));
		assertTrue(content.contains("| BBB |"));
		assertTrue(content.contains("| CCC |"));

	}
}
