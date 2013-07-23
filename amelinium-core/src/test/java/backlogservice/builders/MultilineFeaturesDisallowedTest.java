package backlogservice.builders;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.config.MarkupConfig;
import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.Story;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;

public class MultilineFeaturesDisallowedTest {
	private boolean allowingMultilineFeatures = false;

	@Test
	public void testOneFreeMultilineStory() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeMultilineStory.txt";

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures().get(0).getStories();

		// then
		assertEquals(1, storiesUnderUntitledFeature.size());
		assertEquals("* One Story without story points  - ",
				storiesUnderUntitledFeature.get(0).getContentLeft());
		assertEquals("?sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());
		assertEquals(0, storiesUnderUntitledFeature.get(0).getPoints());

	}

	@Test
	public void testTwoFreeMultilineStories() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/backlogs/TwoFreeMultilineStories.txt";
		BacklogModelSerializer serializer = new BacklogModelSerializer();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures().get(0).getStories();
		String content = serializer.serializeModel(backlogModel);

		// then
		assertEquals(2, storiesUnderUntitledFeature.size());
		assertEquals("* One Story without story points  - ",
				storiesUnderUntitledFeature.get(0).getContentLeft());
		assertEquals("?sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());
		assertEquals(0, storiesUnderUntitledFeature.get(0).getPoints());

		assertEquals(
				MarkupConfig.WARNING_LINE
						+ "* One Story without story points - ?sp\n* Second Story with story points - 5sp\n",
				content);
	}

	@Test
	public void testOneFreeStory_OneFreeFeature_NoSP() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeStory_OneFreeFeature_NoSP.txt";

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures().get(0).getStories();
		ArrayList<Feature> featuresUnderUntitledFG = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures();

		// then
		assertEquals(1, storiesUnderUntitledFeature.size());
		assertEquals("* One Story  - ", storiesUnderUntitledFeature.get(0)
				.getContentLeft());
		assertEquals("?sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());

		assertEquals(2, featuresUnderUntitledFG.size());

		assertEquals("-One Feature-  - ", featuresUnderUntitledFG.get(1)
				.getContentLeft());
		assertEquals("0sp/0sp", featuresUnderUntitledFG.get(1)
				.getContentMiddle());
		assertEquals("", featuresUnderUntitledFG.get(1).getContentRight());

		assertEquals(0, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(0).getDonePoints());
		assertEquals(0, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(0).getPoints());
	}

	@Test
	public void testOneFeatureGroup_OneFeature_OneStory() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_OneFeature_OneStory_NoSP.txt";

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group  - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("Feature One  - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals(1,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().size());
		assertEquals("* Story One  - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getStories().get(0).getContentLeft());
		assertEquals(0,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().get(0).getPoints());
	}
	
	@Test
	public void testOneFeatureGroup_OneFeature_TwoStories_SomeSP() throws IOException {
		// given
		BacklogServiceFactory factory = new BacklogServiceFactory();
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_OneFeature_TwoStories_SomeSP.txt";

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group  - ", featureGroups.get(0).getContentLeft());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures().size());
		assertEquals("Feature One  - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getContentLeft());
		assertEquals(2, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().size());
		assertEquals("* Story One  - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(0).getContentLeft());
		assertEquals(0, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(0).getPoints());
		assertEquals("* Story Two - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(1).getContentLeft());
		assertEquals(5, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(1).getPoints());
		assertEquals(5, backlogModel.getFirstSubProject().getFeatureGroups().get(0).getPoints());
	}
}
