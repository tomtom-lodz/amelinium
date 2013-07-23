package projectservice;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tomtom.amelinium.backlogservice.factory.BacklogServiceFactory;
import com.tomtom.amelinium.backlogservice.model.BacklogModel;
import com.tomtom.amelinium.backlogservice.serializer.BacklogModelSerializer;
import com.tomtom.amelinium.backlogservice.serializer.WikiToHTMLSerializer;

public class WikiToHTMLSerializationTest {
	private boolean allowingMultilineFeatures = true;

	@Test
	public void test() {
		// given
		String backlogFileName = "src/test/resources/projectservice/semi_real/backlog.txt";
		BacklogServiceFactory backlogServiceFactory = new BacklogServiceFactory();
		BacklogModelSerializer backlogModelSerializer = new BacklogModelSerializer();
		BacklogModel backlogModel = backlogServiceFactory.readAndCorrectBacklogModelFromFile(backlogFileName, allowingMultilineFeatures);
		WikiToHTMLSerializer serializer = new WikiToHTMLSerializer();
		String wikiMarkupBacklog = backlogModelSerializer.serializeModel(backlogModel);
		String htmlBacklog = serializer.convert(wikiMarkupBacklog);
			assertTrue(htmlBacklog.contains("del"));
		assertTrue(htmlBacklog.contains("<h3"));


	}

}
