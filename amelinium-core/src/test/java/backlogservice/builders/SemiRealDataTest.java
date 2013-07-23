package backlogservice.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;

public class SemiRealDataTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void changedMarkup() throws IOException {
		// given
		String fileName = "src/test/resources/backlogservice/semi_real_data/changedMarkup.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups=backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then
		assertEquals(7,featureGroups.size());
		assertEquals(2,featureGroups.get(0).getLastColumn().getFeatures().size());
		assertEquals(9,featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().size());
		assertEquals(9,featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().size());
		
		assertFalse(featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().get(8).getComment().isEmpty());
		assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().get(8).getPoints());
	}
	
	@Test
	public void realMarkup() throws IOException {

		// given
		String fileName = "src/test/resources/backlogservice/semi_real_data/realMarkup.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups=backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then
		assertEquals(7,featureGroups.size());
		assertEquals(2,featureGroups.get(0).getLastColumn().getFeatures().size());
		assertEquals(9,featureGroups.get(0).getLastColumn().getFeatures().get(0).getStories().size());
		assertEquals(9,featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().size());
		
		assertFalse(featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().get(8).getComment().isEmpty());
		assertEquals(1,featureGroups.get(0).getLastColumn().getFeatures().get(1).getStories().get(8).getPoints());
	}
	
}
