package projectservice.bugs;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
import com.tomtom.amelinium.projectservice.factory.ProjectServiceFactory;
import com.tomtom.amelinium.projectservice.model.Project;

public class CarriageReturnBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		//given
		ProjectServiceFactory factory = new ProjectServiceFactory();
		ProjectCorrector corrector = new ProjectCorrector();
		String backlogFileName = "src/test/resources/chartservice/charts_and_backlogs/semi_real/backlog.txt";
		String chartFileName = "src/test/resources/chartservice/charts_and_backlogs/semi_real/chart.txt";
		
		//when
		Project p = factory.readAndCorrectModelsFromFiles(0, "Project 0", backlogFileName, chartFileName, this.allowingMultilineFeatures);
		String text = p.getBacklogWikiMarkup();
		String newtext = text.replace("145/145sp", "90/90sp");
		corrector.updateProjectWithNewBacklog(p, newtext, this.allowingMultilineFeatures);
		
		//then
		assertTrue(p.getBacklogWikiMarkup().contains("90/90sp"));
		assertFalse(p.getBacklogWikiMarkup().contains("145/145sp"));

	}


}
