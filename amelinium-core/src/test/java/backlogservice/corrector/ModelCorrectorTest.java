package backlogservice.corrector;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Feature;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;
import com.tomtom.amelinium.backlogservice.model.Story;

public class ModelCorrectorTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void testOneFreeStory_OneFreeFeature() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeStory_OneFreeFeature.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
 		ArrayList<Story> storiesUnderUntitledFeature = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures().get(0).getStories();
		ArrayList<Feature> featuresUnderUntitledFG = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures();
		
		// then
		assertEquals(1,storiesUnderUntitledFeature.size());
		assertEquals(1, storiesUnderUntitledFeature.get(0).getPoints());
		assertEquals(2,featuresUnderUntitledFG.size());
		assertEquals(0, featuresUnderUntitledFG.get(1).getPoints());
	}
	
	@Test
	public void testOneFreeStory_OneFreeFeatureWithOneStory() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFreeStory_OneFreeFeatureWithOneStory.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures().get(0).getStories();
		ArrayList<Feature> featuresUnderUntitledFG = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures();
		
		// then
		assertEquals(1,storiesUnderUntitledFeature.size());
		assertEquals(1, storiesUnderUntitledFeature.get(0).getPoints());
		assertEquals(2,featuresUnderUntitledFG.size());
		assertEquals(2, featuresUnderUntitledFG.get(1).getPoints());
		assertEquals(2, featuresUnderUntitledFG.get(1).getDonePoints());
	}
	
	@Test
	public void testOneFeatureGroup_OneFeature_TwoStories() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_OneFeature_TwoStories.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
        ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then
        assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().size());
        assertEquals(5,featureGroups.get(0).getLastColumn().getFeatures().get(0).getPoints());
        assertEquals(0,featureGroups.get(0).getLastColumn().getFeatures().get(0).getDonePoints());
        assertEquals(1,featureGroups.size());
        assertEquals(5,featureGroups.get(0).getPoints());
        assertEquals(0,featureGroups.get(0).getDonePoints());

	}
	
	@Test
	public void testOneFeatureGroup_TwoFeatures_EachWithOneStory() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_TwoFeatures_EachWithOneStory.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
         ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then
        assertEquals(1,featureGroups.size());
        assertEquals(6,featureGroups.get(0).getPoints());
        assertEquals(1,featureGroups.get(0).getDonePoints());
        assertEquals(2,featureGroups.get(0).getLastColumn().getFeatures().size());
        
        assertEquals(5,featureGroups.get(0).getLastColumn().getFeatures().get(0).getPoints());
        assertEquals(0,featureGroups.get(0).getLastColumn().getFeatures().get(0).getDonePoints());
        assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().size());
        
        assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().get(1).getPoints());
        assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().get(1).getDonePoints());
        assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().size());

	}
	
	@Test
	public void testOneFeatureGroup_TwoFeatures_EachWithTwoStories() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/OneFeatureGroup_TwoFeatures_EachWithTwoStories.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
      ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then
        assertEquals(1,featureGroups.size());
        assertEquals(11,featureGroups.get(0).getPoints());
        assertEquals(5,featureGroups.get(0).getDonePoints());
        assertEquals(2,featureGroups.get(0).getLastColumn().getFeatures().size());
        
        assertEquals(5,featureGroups.get(0).getLastColumn().getFeatures().get(0).getPoints());
        assertEquals(0,featureGroups.get(0).getLastColumn().getFeatures().get(0).getDonePoints());
        assertEquals(2,featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().size());
        
        assertEquals(6,featureGroups.get(0).getLastColumn().getFeatures().get(1).getPoints());
        assertEquals(5,featureGroups.get(0).getLastColumn().getFeatures().get(1).getDonePoints());
        assertEquals(2,featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().size());

	}

}
