package projectservice.bugs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.common.LineProducer;
import com.tomtom.amelinium.projectservice.visualisation.JSPChart;
import com.tomtom.amelinium.projectservice.visualisation.JSPChartSerializer;

public class JSPChartUpdateBug {

	@Test
	public void test() {
		//given
		LineProducer lineProducer = new LineProducer();
		JSPChartSerializer jspChartSerializer = new JSPChartSerializer();
		String backlogFileName = "src/test/resources/projectservice/0/backlog.txt";
		String newChartFileName = "src/test/resources/projectservice/0/newChart.txt";
		
		//when
		String wikiMarkupBacklog = lineProducer.readStringFromFile(backlogFileName);
		String newWikiMarkupChart = lineProducer.readStringFromFile(newChartFileName);
		JSPChart jspChart = jspChartSerializer.serializeWikiMarkupContents(wikiMarkupBacklog, newWikiMarkupChart);
		
		//then
		assertFalse(jspChart.getChart().contains("'AAA', 'AAA',"));
		assertTrue(jspChart.getChart().contains("'AAA'"));
	}
}
