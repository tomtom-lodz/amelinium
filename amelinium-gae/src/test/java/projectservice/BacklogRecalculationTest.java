package projectservice;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomtom.amelinium.projectservice.corrector.ProjectCorrector;
import com.tomtom.amelinium.projectservice.factory.ProjectServiceFactory;
import com.tomtom.amelinium.projectservice.model.Project;

public class BacklogRecalculationTest {
	private boolean allowingMultilineFeatures = true;
	
	@Test
	public void test() {
		//given
		ProjectCorrector corrector = new ProjectCorrector();
		ProjectServiceFactory factory = new ProjectServiceFactory();
		String backlogFileName = "src/test/resources/projectservice/0/backlog.txt";
		String chartFileName = "src/test/resources/projectservice/0/chart.txt";
		
		//when
		Project p = factory.readAndCorrectModelsFromFiles(0, "Project 0", backlogFileName, chartFileName, this.allowingMultilineFeatures);
		
		String text = p.getBacklogWikiMarkup();
		String newtext = text.replace("- 80sp-", "- 90sp-");
		corrector.updateProjectWithNewBacklog(p, newtext, this.allowingMultilineFeatures);
		
		//then
		assertTrue(p.getBacklogWikiMarkup().contains("90/90sp"));
		assertFalse(p.getBacklogWikiMarkup().contains("80/80sp"));

	}

}
