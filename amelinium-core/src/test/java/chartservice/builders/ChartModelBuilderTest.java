package chartservice.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class ChartModelBuilderTest {

	@Test
	public void test() {
		// given
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/semi_real/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();

		// when
		ChartModel chartModel = factory.readChartModelFromFile(chartFileName);

		// then
		
		// test intro / outro
		assertEquals("h2. TEST\n", chartModel.getIntro());
		//assertEquals(testOutro, chartModel.getLog());
		
		// test table titles
		assertEquals(3, chartModel.getFeatureGroupsTables().size());
		assertEquals("burned story points", chartModel.getBurnedPointsTable().getTitle());
		assertEquals("AAA", chartModel.getFeatureGroupsTables().get(0).getTitle());
		assertEquals("BBB", chartModel.getFeatureGroupsTables().get(1).getTitle());
		assertEquals("CCC", chartModel.getFeatureGroupsTables().get(2).getTitle());
		assertEquals("ideal", chartModel.getIdealTable().getTitle());
		
		// test contents of tables
		assertEquals(24, chartModel.getBurnedPointsTable().getRows().size());
		assertEquals(1, chartModel.getBurnedPointsTable().getRows().get(1).getSprint());
		assertEquals(15, chartModel.getBurnedPointsTable().getRows().get(1).getValue());
		assertEquals(2, chartModel.getBurnedPointsTable().getRows().get(2).getSprint());
		assertEquals(34, chartModel.getBurnedPointsTable().getRows().get(2).getValue());
	}

}
