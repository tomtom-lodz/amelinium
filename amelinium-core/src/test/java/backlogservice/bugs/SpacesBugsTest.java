package backlogservice.bugs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;

public class SpacesBugsTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void testComments1(){
		// given
		String fileName = "src/test/resources/backlogservice/bugs/spaces.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);

		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures()
				.size());

		assertEquals("h3. sss ", featureGroups.get(0).getContentLeft());
		assertEquals("25 / 90 sp", featureGroups.get(0).getContentMiddle());
		assertEquals("ggg - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals("16 / 64 sp", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentMiddle());
		assertEquals("* Story ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getStories().get(0).getContentLeft());
		assertEquals("5 sp", featureGroups.get(0).getLastColumn().getFeatures()
				.get(0).getStories().get(0).getContentMiddle());

	}
}
