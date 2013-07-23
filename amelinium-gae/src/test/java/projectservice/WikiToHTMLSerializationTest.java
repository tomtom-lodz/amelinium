package projectservice;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;
import com.tomtom.amelinium.projectservice.factory.ProjectServiceFactory;
import com.tomtom.amelinium.projectservice.model.Project;

public class WikiToHTMLSerializationTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String backlogFileName = "src/test/resources/projectservice/semi_real/backlog.txt";
		String chartFileName = "src/test/resources/projectservice/semi_real/chart.txt";
		
		ProjectServiceFactory factory = new ProjectServiceFactory();
		WikiToHTMLSerializer serializer = new WikiToHTMLSerializer();
		
		Project p = factory.readAndCorrectModelsFromFiles(0, "Project 0", backlogFileName, chartFileName, this.allowingMultilineFeatures);
		
		//System.out.println(p.getBacklogHtml());
		
		assertTrue(p.getBacklogHtml().contains("del"));
		assertTrue(p.getBacklogHtml().contains("<h3"));


	}

}
