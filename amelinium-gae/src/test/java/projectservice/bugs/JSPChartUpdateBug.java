package projectservice.bugs;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomtom.amelinium.common.LineProducer;
import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
import com.tomtom.amelinium.projectservice.factory.ProjectServiceFactory;
import com.tomtom.amelinium.projectservice.model.Project;

public class JSPChartUpdateBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		ProjectServiceFactory factory = new ProjectServiceFactory();
		ProjectCorrector corrector = new ProjectCorrector();
		LineProducer lineProducer = new LineProducer();
		String backlogFileName = "src/test/resources/projectservice/0/backlog.txt";
		String chartFileName = "src/test/resources/projectservice/0/chart.txt";
		String newChartFileName = "src/test/resources/projectservice/0/newChart.txt";
		Project project = factory.readAndCorrectModelsFromFiles(0, "Project 0", backlogFileName, chartFileName, this.allowingMultilineFeatures);
		String wikiMarkupChart = lineProducer.readStringFromFile(newChartFileName);
		
		corrector.updateProjectWithNewChart(project, wikiMarkupChart);
		
		assertFalse(project.getJspChart().getChart().contains("'AAA', 'AAA',"));
		assertTrue(project.getJspChart().getChart().contains("'AAA'"));
	}
}
