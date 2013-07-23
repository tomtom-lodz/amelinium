package backlogservice.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;

public class ProjectsTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/projects.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,this.allowingMultilineFeatures);

		// then
		assertEquals(2, backlogModel.getSubProjects().size());
	}

}
