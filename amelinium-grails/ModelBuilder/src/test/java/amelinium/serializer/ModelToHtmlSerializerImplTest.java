package amelinium.serializer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import amelinium.model.Feature;
import amelinium.model.Project;
import amelinium.model.Release;
import amelinium.model.Story;

public class ModelToHtmlSerializerImplTest {

	private Project project;
	private ModelToHtmlSerializer serializer;
	private String expectedSerialization;
	
	@Before
	public void setUp(){
		project = new Project();
		project.setProjectName("Test project");
		//building release
		Release release = new Release();
		release.setContent("Release");
		release.setDonePoints(10);
		release.setTotalPoints(20);
		release.setStrikeThrough(false);
		release.setTagName("h3");
		//building feature
		Feature feature = new Feature();
		feature.setContent("Feature");
		feature.setDonePoints(10);
		feature.setTotalPoints(20);
		feature.setTagName("p");
		feature.setStrikeThrough(false);
		//building stories
		Story story1 = new Story();
		story1.setContent("<del>Story 1 - 10sp</del>");
		story1.setPoints(10);
		story1.setStrikeThrough(true);
		story1.setTagName("li");
		Story story2 = new Story();
		story2.setContent("Story 2 - 10sp");
		story2.setPoints(10);
		story2.setStrikeThrough(false);
		story2.setTagName("li");
		//setting up
		feature.addStory(story1);
		feature.addStory(story2);
		release.addFeature(feature);
		project.addRelease(release);
		
		serializer = new ModelToHtmlSerializerImpl();
		
		expectedSerialization = "<h3>Release</h3>\n"+
								 "<p>Feature</p>\n"+
								 "<ul>\n"+
								 "<li><del>Story 1 - 10sp</del></li>\n"+
								 "<li>Story 2 - 10sp</li>\n"+
								 "</ul>\n\n"+
								 "<p>BACKLOG END</p>\n";
	}
	@Test
	public void testSerializeModelToHtml() {
		//given
		//set project
		
		//when
		String actualSerialization = serializer.serializeModelToHtml(project);
		
		//then
		assertEquals(expectedSerialization, actualSerialization);
	}

}
