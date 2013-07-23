package backlogservice.difficult_cases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;

public class DoneStoriesInMultipleGroupsTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void testTwoFeatureGroups_EachWithTwoFeatures_EachWithTwoStories(){
		// given
		String fileName = "src/test/resources/backlogservice/difficult_cases/DoneStoriesInMultipleFeatureGroups.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
        ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();

		// then
        assertEquals(10,featureGroups.get(0).getCummulativePoints());
        assertEquals(40,featureGroups.get(1).getCummulativePoints());
        assertEquals(50,featureGroups.get(2).getCummulativePoints());
        
	}
	
}
