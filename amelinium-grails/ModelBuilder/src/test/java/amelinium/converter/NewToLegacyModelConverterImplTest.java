package amelinium.converter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tomtom.amelinium.backlogservice.model.BacklogModel;

import amelinium.model.Project;
import amelinium.model.Release;

public class NewToLegacyModelConverterImplTest {

	private NewToLegacyModelConverter converter;
	private Project project;
	
	@Before
	public void setUp(){
		project = new Project();
		project.setProjectName("Test project");
		Release release = new Release();
		release.setContent("Release - 10/20sp");
		release.setDonePoints(10);
		release.setTotalPoints(20);
		release.setStrikeThrough(false);
		release.setTagName("h3");
		project.addRelease(release);
		converter = new NewToLegacyModelConverterImpl();
	}
	
	@Test
	public void testConvertToLegacyModel() {
		//given
		boolean allowingMultilineFeatures = true;
		
		//when
		BacklogModel model = converter.convertToLegacyModel(project, allowingMultilineFeatures);
		
		//then
		assertEquals(model.getLastSubProject().getFeatureGroups().get(0).getContentLeft(), "Release - ");
		assertEquals(model.getLastSubProject().getFeatureGroups().get(0).getContentMiddle(), "10sp/20sp");
		assertEquals(model.getLastSubProject().getFeatureGroups().get(0).getDonePoints(), 10);
		assertEquals(model.getLastSubProject().getFeatureGroups().get(0).getPoints(), 20);
		assertEquals(model.getLastSubProject().isDone(), false);
		assertEquals(model.getLastSubProject().getDonePoints(), 10);
		assertEquals(model.getLastSubProject().getPoints(), 20);
		assertEquals(model.getOverallBurnedStoryPoints(), 10);
		assertEquals(model.isAllowingMulitilineFeatures(), true);
	}

}
