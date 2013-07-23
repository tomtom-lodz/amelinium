package backlogservice.difficult_cases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.FeatureGroup;

public class FeatureGroupsTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void test(){
		// given
		String fileName = "src/test/resources/backlogservice/difficult_cases/SixFeatureGroupsDifficultMarkup.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<FeatureGroup> featureGroups = backlogModel.getFirstSubProject().getFeatureGroups();
		
		// then	
		assertEquals("h3. -Some FG- \\- _", featureGroups.get(0).getContentLeft());
		assertEquals("2/5sp", featureGroups.get(0).getContentMiddle());
		assertEquals("_", featureGroups.get(0).getContentRight());
		
		assertEquals("h3. -Some- {color:#ff0000}{-}FG{-}{color} -\\-- ", featureGroups.get(1).getContentLeft());
		assertEquals("5/?sp", featureGroups.get(1).getContentMiddle());
		assertEquals("", featureGroups.get(1).getContentRight());

		assertEquals("h3. -Ddsds dsdsd - ", featureGroups.get(2).getContentLeft());
		assertEquals("2sp/5sp", featureGroups.get(2).getContentMiddle());
		assertEquals("-", featureGroups.get(2).getContentRight());
		
		assertEquals("h3. -asdfasda - ", featureGroups.get(3).getContentLeft());
		assertEquals("1sp/?sp", featureGroups.get(3).getContentMiddle());
		assertEquals("-", featureGroups.get(3).getContentRight());
		
		assertEquals("h3. {color:#ff0000}{-}asdfasda - ", featureGroups.get(4).getContentLeft());
		assertEquals("1sp", featureGroups.get(4).getContentMiddle());
		assertEquals("{-}{color}", featureGroups.get(4).getContentRight());
		
		assertEquals("h3. {color:#ff0000}{-}asdfasda - ", featureGroups.get(5).getContentLeft());
		assertEquals("?sp", featureGroups.get(5).getContentMiddle());
		assertEquals("{-}{color}", featureGroups.get(5).getContentRight());
		
	}
}
