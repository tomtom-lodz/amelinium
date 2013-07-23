package chartservice.serializer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;

public class SerializerTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void correctionAndSerializationTest() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		String output = factory.readCorrectAndSerializeChartModelFromFiles(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);
		assertTrue(output.contains("| AAA | (/) | (!) | AAA -  |"));
	}
	
	@Test
	public void serializationTest() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		String output = factory.serializeChartModelFromFiles(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);
		//nothing in correction log
		assertFalse(output.contains("| AAA | (/) | (!) | AAA -  |"));
	}
}
