package backlogservice.bugs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;

public class ColumnsBugTest {
	private boolean allowingMultilineFeatures = true;
	@Test
	public void test() {
		// given
		String fileName = "src/test/resources/backlogservice/bugs/columns.txt";
		BacklogServiceFactory factory = new BacklogServiceFactory();

		// when
		BacklogModel backlogModel = factory.readAndCorrectBacklogModelFromFile(fileName,this.allowingMultilineFeatures);

		// then
		assertEquals(1, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(0).getColumns().size());
		assertEquals(2, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(1).getColumns().size());
		assertEquals("{column}", backlogModel.getFirstSubProject()
				.getFeatureGroups().get(0).getColumns().get(0).getOpenTag());
		assertEquals("{column:width=50%}", backlogModel.getFirstSubProject()
				.getFeatureGroups().get(1).getColumns().get(0).getOpenTag());
		assertFalse(0 == backlogModel.getFirstSubProject().getFeatureGroups()
				.get(1).getColumns().get(0).getFeatures().get(0).getPoints());
		assertEquals(6, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(1).getColumns().get(0).getFeatures().get(0).getPoints());
		assertEquals(5, backlogModel.getFirstSubProject().getFeatureGroups()
				.get(1).getColumns().get(1).getFeatures().get(0).getPoints());

	}

}
