package projectservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;

public class BacklogRecalculationTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
		String backlogFileName = "src/test/resources/projectservice/0/backlog.txt";

		// when
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,
						this.allowingMultilineFeatures);
		String text = backlogModelSerializer.serializeModel(backlogModel);
		String newtext = text.replace("- 80sp-", "- 90sp-");
		BacklogModel newBacklogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromString(newtext,
						this.allowingMultilineFeatures);
		String newWikiMarkupBacklog = backlogModelSerializer
				.serializeModel(newBacklogModel);

		// then
		assertTrue(newWikiMarkupBacklog.contains("90/90sp"));
		assertFalse(newWikiMarkupBacklog.contains("80/80sp"));

	}

}
