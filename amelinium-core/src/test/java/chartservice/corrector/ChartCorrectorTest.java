package chartservice.corrector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.Log;

public class ChartCorrectorTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void testRealChart() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/semi_real/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/semi_real/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName, this.allowingMultilineFeatures);

		// then

		// test tables size before and after correction - only burned and ideal
		// tables must have unchanged size
		// before correction
		assertEquals(24, chartModel.getBurnedPointsTable().getRows().size());// burned
																				// points
		assertEquals(7, chartModel.getFeatureGroupsTables().get(0).getRows()
				.size());
		assertEquals(7, chartModel.getFeatureGroupsTables().get(1).getRows()
				.size());
		assertEquals(7, chartModel.getFeatureGroupsTables().get(2).getRows()
				.size());
		assertEquals(2, chartModel.getIdealTable().getRows().size());// ideal

		// ---CORRECT---
		chartCorrector.correctTables(backlogModel, chartModel);

		// after correction
		assertEquals(24, chartModel.getBurnedPointsTable().getRows().size());// burned
																				// points
		assertEquals(5, chartModel.getFeatureGroupsTables().get(0).getRows()
				.size());
		assertEquals(5, chartModel.getFeatureGroupsTables().get(1).getRows()
				.size());
		assertEquals(5, chartModel.getFeatureGroupsTables().get(2).getRows()
				.size());
		assertEquals(2, chartModel.getIdealTable().getRows().size());// ideal

		// after modifications, all last sprints in Feature Group tables must
		// have 0 value

		assertEquals(0, chartModel.getFeatureGroupsTables().get(0)
				.getLastValue());
		assertEquals(0, chartModel.getFeatureGroupsTables().get(1)
				.getLastValue());
		assertEquals(0, chartModel.getFeatureGroupsTables().get(2)
				.getLastValue());

		// after modifications, two last rows must have the same sprint number
		assertEquals(
				chartModel.getFeatureGroupsTables().get(0).getLastSprint(),
				chartModel.getFeatureGroupsTables().get(0)
						.getSecondLastSprint());
		assertEquals(
				chartModel.getFeatureGroupsTables().get(1).getLastSprint(),
				chartModel.getFeatureGroupsTables().get(1)
						.getSecondLastSprint());
		assertEquals(
				chartModel.getFeatureGroupsTables().get(2).getLastSprint(),
				chartModel.getFeatureGroupsTables().get(2)
						.getSecondLastSprint());

		// after modifications, second last and third last row must have the
		// same value
		assertEquals(chartModel.getFeatureGroupsTables().get(0)
				.getSecondLastValue(),
				chartModel.getFeatureGroupsTables().get(0).getThirdLastValue());
		assertEquals(chartModel.getFeatureGroupsTables().get(1)
				.getSecondLastValue(),
				chartModel.getFeatureGroupsTables().get(1).getThirdLastValue());
		assertEquals(chartModel.getFeatureGroupsTables().get(2)
				.getSecondLastValue(),
				chartModel.getFeatureGroupsTables().get(2).getThirdLastValue());

		// intro must stay unchanged
		assertEquals("h2. TEST\n", chartModel.getIntro());
		// log should be generated
		assertNotSame(new Log(), chartModel.getLog());
	}

	@Test
	public void testSimpleChart() {
		// given
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/simple/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName, this.allowingMultilineFeatures);

		// then

		// test tables size before and after correction - only ideal tables must
		// have unchanged size
		// before correction
		assertEquals(2, chartModel.getBurnedPointsTable().getRows().size());// burned
																			// points
		assertEquals(3, chartModel.getFeatureGroupsTables().get(0).getRows()
				.size());
		assertEquals(3, chartModel.getFeatureGroupsTables().get(1).getRows()
				.size());
		assertEquals(2, chartModel.getIdealTable().getRows().size());// ideal

		// ---CORRECT---
		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction
		assertEquals(3, chartModel.getBurnedPointsTable().getRows().size());// burned
																			// points
		assertEquals(3, chartModel.getFeatureGroupsTables().get(0).getRows()
				.size());
		assertEquals(3, chartModel.getFeatureGroupsTables().get(1).getRows()
				.size());
		assertEquals(2, chartModel.getIdealTable().getRows().size());// ideal

		// after modifications, all last sprints in Feature Group tables must
		// have 0 value

		assertEquals(0, chartModel.getFeatureGroupsTables().get(0)
				.getLastValue());
		assertEquals(0, chartModel.getFeatureGroupsTables().get(1)
				.getLastValue());

		// after modifications, ideal and burned points table's last values
		// shouldn't be 0
		assertFalse(0 == chartModel.getBurnedPointsTable().getLastValue());
		assertFalse(0 == chartModel.getIdealTable().getLastValue());

		// after modifications, two last rows must have the same sprint number
		assertEquals(
				chartModel.getFeatureGroupsTables().get(0).getLastSprint(),
				chartModel.getFeatureGroupsTables().get(0)
						.getSecondLastSprint());
		assertEquals(
				chartModel.getFeatureGroupsTables().get(1).getLastSprint(),
				chartModel.getFeatureGroupsTables().get(1)
						.getSecondLastSprint());

		// FG tables with last sprint smaller than the given one don't need to
		// have second last and third last rows of the same value
		assertFalse(chartModel.getFeatureGroupsTables().get(0)
				.getSecondLastValue() == chartModel.getFeatureGroupsTables()
				.get(0).getThirdLastValue());

		// after modifications, second last and third last row must have the
		// same value
		assertEquals(chartModel.getFeatureGroupsTables().get(1)
				.getSecondLastValue(),
				chartModel.getFeatureGroupsTables().get(1).getThirdLastValue());

		// intro must stay unchanged
		assertEquals("h2. TEST\n", chartModel.getIntro());

		// log should be generated
		assertNotSame(new Log(), chartModel.getLog());
	}

}
