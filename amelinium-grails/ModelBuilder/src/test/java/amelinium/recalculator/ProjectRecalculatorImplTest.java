package amelinium.recalculator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import amelinium.model.Feature;
import amelinium.model.Project;
import amelinium.model.Release;
import amelinium.model.Story;

public class ProjectRecalculatorImplTest {

	private ProjectRecalculator recalculator;
	private Project project;
	
	@Before
	public void setUp(){
		project = new Project();
		project.setProjectName("Test project");
		//building release
		Release release = new Release();
		release.setContent("Release");
		release.setStrikeThrough(false);
		release.setTagName("h3");
		//building feature
		Feature feature = new Feature();
		feature.setContent("Feature");
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
		
		recalculator = new ProjectRecalculatorImpl();
	}
	
	@Test
	public void testRecalculateProject() {
		//given
		//set project
		
		//when
		Project recalcProject = recalculator.recalculateProject(project);
		Release lastRelease = recalcProject.getLastRelease();
		Feature lastFeature = lastRelease.getLastFeature();
		
		//then
		assertEquals(20,lastRelease.getTotalPoints());
		assertEquals(10,lastRelease.getDonePoints());
		assertEquals(20,lastFeature.getTotalPoints());
		assertEquals(10,lastFeature.getDonePoints());
	}

}
