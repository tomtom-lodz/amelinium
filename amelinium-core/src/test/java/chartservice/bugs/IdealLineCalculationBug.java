package chartservice.bugs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.chartservice.corrector.ChartCorrector;
import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class IdealLineCalculationBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test1() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/ideal_line/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/ideal_line/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,this.allowingMultilineFeatures);

		// then
		assertEquals(390.0, chartModel.getIdealTable().getLastValue(), 1e-6);

		// ---CORRECT---
		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction 388.0
		assertEquals(409.5, chartModel.getIdealTable().getLastValue(), 1e-6);
	}

	@Test
	public void test2() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/ideal_line2/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/ideal_line2/chart.txt";
		ChartServiceFactory chartServiceFactory = new ChartServiceFactory();
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		ChartCorrector chartCorrector = new ChartCorrector();

		// when
		ChartModel chartModel = chartServiceFactory
				.readChartModelFromFile(chartFileName);
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,this.allowingMultilineFeatures);

		// then
		assertEquals(383.0, chartModel.getIdealTable().getLastValue(), 1e-6);
		
		// ---CORRECT---
		chartCorrector.correctTables(backlogModel, chartModel);
		// after correction
		assertEquals(367.5, chartModel.getIdealTable().getLastValue(), 1e-6);
	}

}
