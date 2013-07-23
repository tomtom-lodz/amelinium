package backlogservice.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.model.Story;

public class CommentsTest {
	
	private boolean allowingMultilineFeatures = true;

	@Test
	public void testStoriesWithComments(){
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/comments.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> stories = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures().get(0).getStories();

		// then
		assertEquals(3,stories.size());

		assertEquals("\n{color:#808080}comment{color}", stories.get(0).getComment());
		assertEquals("\n{color:#808080}comment{color}", stories.get(1).getComment());
		assertEquals("\n{color:#808080}comment a{color}\n{color:#808080}comment b{color}\n{color:#808080}comment c{color}", stories.get(2).getComment());
	}
	
	@Test
	public void testStoriesWithDoubleStarsComments(){
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/doubleStarComments.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);
		ArrayList<Story> stories = backlogModel.getFirstSubProject().getFeatureGroups().get(0).getLastColumn().getFeatures().get(0).getStories();
		
		// then
		assertEquals(4,stories.size());

		assertEquals("\n{color:#808080}comment{color}", stories.get(0).getComment());
		assertEquals("\n{color:#808080}comment{color}", stories.get(1).getComment());
		assertEquals("\n{color:#808080}comment a{color}\n{color:#808080}comment b{color}\n{color:#808080}comment c{color}", stories.get(2).getComment());
		assertFalse(""==stories.get(3).getComment());
	}
}
