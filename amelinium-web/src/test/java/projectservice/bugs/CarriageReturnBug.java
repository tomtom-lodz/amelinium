package projectservice.bugs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;

public class CarriageReturnBug {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
		String backlogFileName = "src/test/resources/projectservice/semi_real/backlog.txt";

		// when
		BacklogModel backlogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromFile(backlogFileName,
						this.allowingMultilineFeatures);
		String text = backlogModelSerializer.serializeModel(backlogModel);
		
		String newtext = text.replace("145/145sp", "90/90sp");
		BacklogModel newBacklogModel = backlogServiceFactory
				.readAndCorrectBacklogModelFromString(newtext,
						this.allowingMultilineFeatures);
		String newWikiMarkupBacklog = backlogModelSerializer
				.serializeModel(newBacklogModel);

		// then
		assertTrue(newWikiMarkupBacklog.contains("90/90sp"));
		assertFalse(newWikiMarkupBacklog.contains("145/145sp"));

	}

}
