package backlogservice.difficult_cases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Story;

public class StoriesTest {
	private boolean allowingMultilineFeatures = true;
		@Test
		public void test(){
	
		// given
		String fileName = "src/test/resources/backlogservice/difficult_cases/OneFreeStoryDifficultMarkup.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();
	
		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> storiesUnderUntitledFeature = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures().get(0).getStories();
		
		// then
		
		assertEquals("* -Some Story- \\- _", storiesUnderUntitledFeature.get(0).getContentLeft());
		assertEquals("5sp", storiesUnderUntitledFeature.get(0).getContentMiddle());
		assertEquals("_", storiesUnderUntitledFeature.get(0).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(0).isDone());
		assertTrue(storiesUnderUntitledFeature.get(0).isEstimated());
		assertEquals(5, storiesUnderUntitledFeature.get(0).getPoints());
		
		assertEquals("* -Some- {color:#ff0000}{-}Story{-}{color} -\\-- ", storiesUnderUntitledFeature.get(1).getContentLeft());
		assertEquals("5sp", storiesUnderUntitledFeature.get(1).getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(1).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(1).isDone());
		assertTrue(storiesUnderUntitledFeature.get(1).isEstimated());
		assertEquals(5, storiesUnderUntitledFeature.get(1).getPoints());

		assertEquals("* -Ddsds dsdsd - ", storiesUnderUntitledFeature.get(2).getContentLeft());
		assertEquals("5sp", storiesUnderUntitledFeature.get(2).getContentMiddle());
		assertEquals("-", storiesUnderUntitledFeature.get(2).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(2).isDone());
		assertTrue(storiesUnderUntitledFeature.get(2).isEstimated());
		assertEquals(5, storiesUnderUntitledFeature.get(2).getPoints());
		
		assertEquals("* -asdfasda - ", storiesUnderUntitledFeature.get(3).getContentLeft());
		assertEquals("1sp", storiesUnderUntitledFeature.get(3).getContentMiddle());
		assertEquals("-", storiesUnderUntitledFeature.get(3).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(3).isDone());
		assertTrue(storiesUnderUntitledFeature.get(3).isEstimated());
		assertEquals(1, storiesUnderUntitledFeature.get(3).getPoints());
		
		assertEquals("* -{color:#ff0000}asdfasda- - ", storiesUnderUntitledFeature.get(4).getContentLeft());
		assertEquals("1sp", storiesUnderUntitledFeature.get(4).getContentMiddle());
		assertEquals("{color}", storiesUnderUntitledFeature.get(4).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(4).isDone());
		assertTrue(storiesUnderUntitledFeature.get(4).isEstimated());
		assertEquals(1, storiesUnderUntitledFeature.get(4).getPoints());

		assertEquals("* {color:#ff0000}abc - ", storiesUnderUntitledFeature.get(5).getContentLeft());
		assertEquals("2sp", storiesUnderUntitledFeature.get(5).getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(5).getContentRight());
		assertFalse(storiesUnderUntitledFeature.get(5).isDone());
		assertTrue(storiesUnderUntitledFeature.get(5).isEstimated());
		assertEquals(2, storiesUnderUntitledFeature.get(5).getPoints());
		
		assertEquals("* {color:#ff0000}{-}Story{-}{color} - ", storiesUnderUntitledFeature.get(6).getContentLeft());
		assertEquals("5sp", storiesUnderUntitledFeature.get(6).getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(6).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(6).isDone());
		assertTrue(storiesUnderUntitledFeature.get(6).isEstimated());
		assertEquals(5, storiesUnderUntitledFeature.get(6).getPoints());
		
		assertEquals("* {color:#ff0000} - Story - {color} - ", storiesUnderUntitledFeature.get(7).getContentLeft());
		assertEquals("5sp", storiesUnderUntitledFeature.get(7).getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(7).getContentRight());
		assertTrue(storiesUnderUntitledFeature.get(7).isDone());
		assertTrue(storiesUnderUntitledFeature.get(7).isEstimated());
		assertEquals(5, storiesUnderUntitledFeature.get(7).getPoints());
		
		assertEquals("* {color:#ff0000} Not crossed {color} - ", storiesUnderUntitledFeature.get(8).getContentLeft());
		assertEquals("5sp", storiesUnderUntitledFeature.get(8).getContentMiddle());
		assertEquals("", storiesUnderUntitledFeature.get(8).getContentRight());
		assertFalse(storiesUnderUntitledFeature.get(8).isDone());
		assertTrue(storiesUnderUntitledFeature.get(8).isEstimated());
		assertEquals(5, storiesUnderUntitledFeature.get(8).getPoints());
		
	}
}
