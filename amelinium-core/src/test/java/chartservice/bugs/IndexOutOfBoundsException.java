package chartservice.bugs;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.tomtom.amelinium.chartservice.factory.ChartServiceFactory;
import com.tomtom.amelinium.chartservice.model.ChartModel;
import com.tomtom.amelinium.chartservice.model.IntTable;

public class IndexOutOfBoundsException {
	private boolean allowingMultilineFeatures = true;

		@Test
		public void test() {
			// given
			String backlogFileName = "src/test/resources/chartservice/bugs/exception/backlog.txt";
			String chartFileName = "src/test/resources/chartservice/bugs/exception/chart.txt";
			ChartServiceFactory factory = new ChartServiceFactory();

			//when
			ChartModel chartModel = factory.readAndCorrectChartModel(backlogFileName,
					chartFileName,this.allowingMultilineFeatures);

			
			boolean warn=false;
			
			for (IntTable table : chartModel.getFeatureGroupsTables()){
				if (table.getRows().size()==0){
					warn = true;
				}
			}
			assertFalse(warn);
		}


}
