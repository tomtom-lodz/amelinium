package backlogservice.builders;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.Story;

public class ModelBuilderTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void testOneFreeStory() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeStory.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures().get(0).getStories();

		// then
		assertEquals(1, storiesUnderUntitledFeature.size());
		assertEquals("* One Story 5sp inside - ", storiesUnderUntitledFeature
				.get(0).getContentLeft());
		assertEquals("1sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());
		assertEquals(1, storiesUnderUntitledFeature.get(0).getPoints());
	}

	@Test
	public void testTwoFreeStories() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/TwoFreeStories.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures().get(0).getStories();

		// then
		assertEquals(2, storiesUnderUntitledFeature.size());
		assertEquals("* One Story - ", storiesUnderUntitledFeature.get(0)
				.getContentLeft());
		assertEquals("1sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());

		assertEquals("* Story spanning two lines - ",
				storiesUnderUntitledFeature.get(1).getContentLeft());
		assertEquals("2sp", storiesUnderUntitledFeature.get(1)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(1).getContentRight());

	}

	@Test
	public void testOneFreeStory_OneFreeFeature() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeStory_OneFreeFeature.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

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
		assertEquals("* One Story - ", storiesUnderUntitledFeature.get(0)
				.getContentLeft());
		assertEquals("1sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());

		assertEquals(2, featuresUnderUntitledFG.size());

		assertEquals("-One Feature- - ", featuresUnderUntitledFG.get(1)
				.getContentLeft());
		assertEquals("1sp/1sp", featuresUnderUntitledFG.get(1)
				.getContentMiddle());
		assertEquals("", featuresUnderUntitledFG.get(1).getContentRight());
	}

	@Test
	public void testOneFreeStory_OneFreeFeatureWithOneStory() {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeStory_OneFreeFeatureWithOneStory.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

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
		assertEquals("* One Story - ", storiesUnderUntitledFeature.get(0)
				.getContentLeft());
		assertEquals(2, featuresUnderUntitledFG.size());
		assertEquals("One Feature - ", featuresUnderUntitledFG.get(1)
				.getContentLeft());
		assertEquals(1, featuresUnderUntitledFG.get(1).getStories().size());
		assertEquals("* -One Story- - ", featuresUnderUntitledFG.get(1)
				.getStories().get(0).getContentLeft());
		assertEquals(true, featuresUnderUntitledFG.get(1).getStories().get(0)
				.isDone());
		assertEquals(true, featuresUnderUntitledFG.get(1).getStories().get(0)
				.isEstimated());
	}

	@Test
	public void testOneFreeFeature() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeFeature.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Feature> featuresUnderUntitledFG = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures();

		// then
		assertEquals(1, featuresUnderUntitledFG.size());
		assertEquals("One Feature spanning two lines - ",
				featuresUnderUntitledFG.get(0).getContentLeft());
	}

	@Test
	public void testOneFeatureGroup() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
	}

	@Test
	public void testTwoFeatureGroups() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/TwoFeatureGroups.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(2, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals("h3. Two Feature Group - ", featureGroups.get(1)
				.getContentLeft());
	}

	@Test
	public void testOneFeatureGroupAndFeature() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroupAndFeature.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("Feature One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
	}

	@Test
	public void testOneFeatureGroupAndTwoFeatures() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroupAndTwoFeatures.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(2, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("Feature One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals("Feature Two - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(1).getContentLeft());
	}

	@Test
	public void testTwoFeatureGroups_EachWithOneFeature() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/TwoFeatureGroups_EachWithOneFeature.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(2, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("FG1 Feature One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals("h3. Two Feature Group - ", featureGroups.get(1)
				.getContentLeft());
		assertEquals(1, featureGroups.get(1).getLastColumn().getFeatures()
				.size());
		assertEquals("FG2 Feature One - ", featureGroups.get(1).getLastColumn()
				.getFeatures().get(0).getContentLeft());
	}

	@Test
	public void testTwoFeatureGroups_EachWithTwoFeatures() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/TwoFeatureGroups_EachWithTwoFeatures.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(2, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(2, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("FG1 Feature One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals("FG1 Feature Two - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(1).getContentLeft());
		assertEquals("h3. Two Feature Group - ", featureGroups.get(1)
				.getContentLeft());
		assertEquals(2, featureGroups.get(1).getLastColumn().getFeatures()
				.size());
		assertEquals("FG2 Feature One - ", featureGroups.get(1).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals("FG2 Feature Two - ", featureGroups.get(1).getLastColumn()
				.getFeatures().get(1).getContentLeft());
	}

	@Test
	public void testOneFeatureGroup_OneFeature_OneStory() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_OneFeature_OneStory.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("Feature One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals(1,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().size());
		assertEquals("* Story One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getStories().get(0).getContentLeft());
		assertEquals(5,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().get(0).getPoints());
	}

	@Test
	public void testOneFeatureGroup_OneFeature_TwoStories() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_OneFeature_TwoStories.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject()
				.getFeatureGroups();

		// then
		assertEquals(1, featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0)
				.getContentLeft());
		assertEquals(1, featureGroups.get(0).getLastColumn().getFeatures()
				.size());
		assertEquals("Feature One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getContentLeft());
		assertEquals(2,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().size());
		assertEquals("* Story One - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getStories().get(0).getContentLeft());
		assertEquals(5,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().get(0).getPoints());
		assertEquals(false, featureGroups.get(0).getLastColumn().getFeatures()
				.get(0).getStories().get(0).isDone());
		assertEquals(true, featureGroups.get(0).getLastColumn().getFeatures()
				.get(0).getStories().get(0).isEstimated());
		assertEquals("* -Story Two- - ", featureGroups.get(0).getLastColumn()
				.getFeatures().get(0).getStories().get(1).getContentLeft());
		assertEquals(0,
				featureGroups.get(0).getLastColumn().getFeatures().get(0)
						.getStories().get(1).getPoints());
		assertEquals(true, featureGroups.get(0).getLastColumn().getFeatures()
				.get(0).getStories().get(1).isDone());
		assertEquals(false, featureGroups.get(0).getLastColumn().getFeatures()
				.get(0).getStories().get(1).isEstimated());
	}

	@Test
	public void testTwostories_UncertainPoints() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/Twostories_UncertainPoints.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel
				.getFirstSubProject().getFeatureGroups().get(0).getLastColumn()
				.getFeatures().get(0).getStories();

		// then
		assertEquals(2, storiesUnderUntitledFeature.size());
		assertEquals("* One Story - ", storiesUnderUntitledFeature.get(0)
				.getContentLeft());
		assertEquals("1sp", storiesUnderUntitledFeature.get(0)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(0).getContentRight());

		assertEquals("* Story spanning two lines - ",
				storiesUnderUntitledFeature.get(1).getContentLeft());
		assertEquals("2?sp", storiesUnderUntitledFeature.get(1)
				.getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(1).getContentRight());

	}
	
	@Test
	public void testOneFeatureGroup_OneFeature_ThreeStories_UncertainPoints() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_OneFeature_ThreeStories_UncertainPoints.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then
		assertEquals(1,featureGroups.size());
		assertEquals("h3. One Feature Group - ", featureGroups.get(0).getContentLeft());
		assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().size());
		assertEquals("Feature One - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getContentLeft());
		assertEquals(3,featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().size());
		
		assertEquals("* Story One - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(0).getContentLeft());
		assertEquals(5, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(0).getPoints());
		assertEquals(false, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(0).isDone());
		assertEquals(true, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(0).isEstimated());
		
		assertEquals("* -Story Two- - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(1).getContentLeft());
		assertEquals(0, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(1).getPoints());
		assertEquals(true, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(1).isDone());
		assertEquals(false, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(1).isEstimated());
		
		assertEquals("* Story Three - ", featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(2).getContentLeft());
		assertEquals("2?sp", featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(2).getContentMiddle());
		assertEquals(2, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(2).getPoints());
		assertEquals(false, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(2).isDone());
		assertEquals(true, featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().get(2).isEstimated());
	}
}
