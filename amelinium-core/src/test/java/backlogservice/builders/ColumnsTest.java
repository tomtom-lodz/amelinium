package backlogservice.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;

public class ColumnsTest {
	@Test
	public void deserializationTest() {
		// given
		String fileName = "src/test/resources/backlogservice/backlogs/columns.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();
		boolean allowingMultilineFeatures = true;

		// when
		BacklogModel backlogModel = factory.readBacklogModelFromFile(fileName,allowingMultilineFeatures);

		// then
		assertEquals(2, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(0).getColumns().size());
		assertEquals("{column:width=50%}", backlogModel.getFirstSubProject()
				.getFeatureGroups().get(0).getColumns().get(0).getOpenTag());
		assertEquals("{column:width=50%}", backlogModel.getFirstSubProject()
				.getFeatureGroups().get(0).getColumns().get(1).getOpenTag());
	}
}
