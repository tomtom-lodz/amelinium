package chartservice.bugs;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;

public class HandleLackOfIdealAndFGTablesBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String backlogFileName = "src/test/resources/chartservice/bugs/missing_fg_and_ideal/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/bugs/missing_fg_and_ideal/chart.txt";
		ChartServiceFactory factory = new ChartServiceFactory();
		// when
		ChartModel chartModel = factory.readAndCorrectChartModel(
				backlogFileName, chartFileName, this.allowingMultilineFeatures);
		//then
		assertFalse(chartModel.getBurnedPointsTable().getRows().size()==0);
		assertFalse(chartModel.getIdealTable().getRows().size()==0);
	}

}
